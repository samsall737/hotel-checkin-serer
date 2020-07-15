package com.nec.hotels.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.amazonaws.services.s3.transfer.ObjectTaggingProvider;
import com.nec.hotels.dao.OtpConfigRepository;
import com.nec.hotels.entity.OtpConfig;
import com.nec.hotels.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.nec.document.model.UploadResponse;
import com.nec.exception.HotelCheckinException;
import com.nec.hotels.entity.Address;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.enums.UploadTypes;
import com.nec.hotels.model.QrCode;
import com.nec.hotels.model.booking.BookingDTO;
import com.nec.hotels.model.booking.BookingResponse;
import com.nec.hotels.model.booking.PreferencesDTO;
import com.nec.hotels.model.checkin.CheckinDTO;
import com.nec.hotels.model.checkin.CheckinDetails;
import com.nec.hotels.model.checkin.PreCheckinDTO;
import com.nec.hotels.model.document.UploadFileRequest;
import com.nec.hotels.model.guest.GuestDetails;
import com.nec.hotels.utils.BookingUtils;
import com.nec.hotels.utils.Constants;
import com.nec.master.model.Hotel;
import com.nec.oauth.model.Token;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
	
	private final BookingService bookingService;
	private final MasterService masterService;
	private final OAuthService authService;
	private final FileService fileService;
	private final BookingSchedulerService bookingSchedulerService;
	private final QREncryptionService qREncryptionService;
	private final QrDecryptionService qrDecryptionService;
	private final EmailAndSmsService emailAndSmsService;
	private final OtpService otpService;


	@Autowired
	public BookingController(final BookingService bookingService, final MasterService masterService,
			final OAuthService authService, final FileService fileService, final BookingSchedulerService bookingSchedulerService,
							 final QREncryptionService qREncryptionService, final QrDecryptionService qrDecryptionService,
							 final EmailAndSmsService emailAndSmsService, final OtpService otpService) {
		this.qrDecryptionService = new QrDecryptionService();
		this.qREncryptionService = new QREncryptionService();
		this.bookingService = bookingService;
		this.masterService = masterService;
		this.authService = authService;
		this.fileService = fileService;
		this.bookingSchedulerService = bookingSchedulerService;
		this.emailAndSmsService = emailAndSmsService;
		this.otpService = otpService;
	}
	
	
	/**
	 * This function is get booking by id
	 */
	@GetMapping("/{booking_id}")
	public ResponseEntity<BookingResponse> getBooking(@PathVariable("booking_id") String bookingId,
			@RequestHeader(Constants.HEADER_USER_AGENT) String userAgent) {
		LOGGER.info("==================getBooking============== {}", bookingId);
		LOGGER.info("==================if condition09==============  ");
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		LOGGER.info("==================if condition09==============  ",hotel.getId());
		Optional<BookingDTO> bookingData = bookingService.getBookingById(hotel.getPmsBaseUrl(), BookingUtils.getConfirmationNumber(bookingId), hotel.getConfiguration(),hotel.getId()	);
		if (!bookingData.isPresent()) {
			throw new HotelCheckinException("No data found corresponding to this booking id", HttpStatus.NO_CONTENT);
		}
		if (Objects.nonNull(bookingData.get())) {
			LOGGER.info("==================if condition04==============  ",hotel.getId());
			HttpHeaders headers = getHeaders(hotel, userAgent);
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new BookingResponse(bookingData.get(), hotel));
		} 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	/**
	 * 
	 * @param hotel
	 * @param userAgent
	 * @return
	 */
	private HttpHeaders getHeaders(Hotel hotel, String userAgent) {
		HttpHeaders headers = new HttpHeaders();
		Token access = authService.generateAccessToken(hotel.getId().toString(), userAgent, hotel.getServiceName());
		headers.add(Constants.HEADER_ACCESS_TOKEN, access.getAccessToken());
		headers.add(Constants.HEADER_ACCESS_REFRESH_TOKEN_, access.getRefreshToken());
		return headers;
	}
	
	
	
	/**
	 * This function is to update Check-in time
	 */
	@PutMapping("/{booking_id}")
	public ResponseEntity<BookingDTO> updateCheckinDetails(@PathVariable("booking_id") String bookingId, @Valid @RequestBody CheckinDetails checkInDetails) {
		LOGGER.info("==================updateCheckinTime============== {}", checkInDetails);
		Optional<BookingDTO> booking = bookingService.updateCheckinDetails(checkInDetails, bookingId);
		if(booking.isPresent()) {
			return ResponseEntity.ok(booking.get());
		}
		return ResponseEntity.badRequest().build();
	}

	/**
	 * This function is to complete the pre-check-in
	 */
	@PostMapping("/{booking_id}/precheckin")
	public ResponseEntity<BookingDTO> preCheckin(@PathVariable("booking_id") String bookingId,
			@Valid @RequestBody PreCheckinDTO preCheckin) {
		LOGGER.info("==================preCheckin=============={} | {}", bookingId, preCheckin);

		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		Optional<BookingDTO> booking = bookingService.preCheckin(hotel, bookingId, preCheckin);
		if (booking.isPresent()) {
			
			//to get email
			//booking.get().getPrimaryGuest().getEmail();
			try {
				qREncryptionService.generate(bookingId);
				emailAndSmsService.sendQRCodeOnMailAndSmsToGuest(hotel, booking.get());
			} catch (Exception e) {
				throw new HotelCheckinException("Error occured on creating qrcode ", HttpStatus.INTERNAL_SERVER_ERROR);
			};
			return ResponseEntity.ok(booking.get());
		}
		throw new HotelCheckinException("Booking Id not found", HttpStatus.NO_CONTENT);
	}
	
	/*
	 * This function is to upload a file
	 */
	@PutMapping("/{booking_id}/guest/{guest_id}/upload")
	public ResponseEntity<UploadFileRequest> uploadFile(@PathVariable("booking_id") String bookingId,
			@PathVariable("guest_id") UUID guestId, @RequestParam("file") MultipartFile file,
			@RequestParam("type") String type,
			@RequestParam(required = false, name = "document_type") String documentType,
			@RequestParam(required = false, name = "doc_issue_place", defaultValue = "India") String docIssuePlace) {
		LOGGER.info("==================uploadFile============== {} | {} | {} | {}", bookingId, guestId, file, type);
		type = type.toLowerCase();
		if (!UploadTypes.contains(type)) {
			throw new HotelCheckinException("Invalid Upload Type", HttpStatus.BAD_REQUEST);
		}
		if ((UploadTypes.valueOf(type.toUpperCase()) == UploadTypes.DOCUMENT_FRONT
				|| UploadTypes.valueOf(type.toUpperCase()) == UploadTypes.DOCUMENT_BACK)
				&& Objects.isNull(documentType)) {
			throw new HotelCheckinException("Document Type Missing", HttpStatus.BAD_REQUEST);
		}
		if (Objects.isNull(file)) {
			throw new HotelCheckinException("Uploaded File cannot be null", HttpStatus.BAD_REQUEST);
		}
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		UploadResponse response = bookingService.saveUploads(bookingId, guestId, file, type, documentType, docIssuePlace, hotel);
		return ResponseEntity
				.ok(new UploadFileRequest(response.getFileName(), response.getFileDownloadUri(), file.getContentType(), file.getSize()));
	}

	/*
	 * This function is to add Preferences
	 */
	@PutMapping("/{booking_id}/preferences")
	public ResponseEntity<String> addPreferences(@PathVariable("booking_id") String bookingId,
			@Valid @RequestBody PreferencesDTO preferences) {
		LOGGER.info("==================addPreferences============== {} | {}", bookingId, preferences);
		if (bookingService.addPreferences(bookingId, preferences)) {
			return ResponseEntity.ok().build();
		}
		throw new HotelCheckinException("Booking not found", HttpStatus.NO_CONTENT);
	}


	/*
	 * This function is to add guest details
	 */
	@PutMapping("/{booking_id}/guest")
	public ResponseEntity<BookingDTO> addGuestDetails(@PathVariable("booking_id") String bookingId,
			@Valid @RequestBody GuestDetails guestDetails) {
		LOGGER.info("==================addGuestDetails============== {} | {}", bookingId, guestDetails);
		Optional<BookingDTO> booking = bookingService.addGuestDetails(bookingId, guestDetails);
		if (booking.isPresent()) {
			return ResponseEntity.ok(booking.get());
		}
		return ResponseEntity.badRequest().build();
	}
	

	/**
	 * This function is to update Guests
	 */
	@PutMapping("{booking_id}/guest/{guest_id}")
	public ResponseEntity<Guest> updateGuest(@PathVariable("booking_id") String bookingId,
			@PathVariable("guest_id") UUID guestId, @Valid @RequestBody Guest inputGuest) {
		LOGGER.info("==================updateSecondaryGuest============== {}", inputGuest);
		Guest guest = bookingService.updateGuest(bookingId, guestId, inputGuest);
		return ResponseEntity.ok(guest);
	}


	/**
	 * This function is to add address for primary guest
	 */
	@PutMapping("{booking_id}/guest/{guest_id}/address")
	public ResponseEntity<Guest> addAddress(@NotBlank @RequestBody Address addressDTO,
			@PathVariable("guest_id") UUID guestId) {
		LOGGER.info("==================addAddress============== {} | {}",
				addressDTO.getCity() + " " + addressDTO.getState() + " " + addressDTO.getCountry(), guestId);
		return ResponseEntity.ok(bookingService.addAddress(guestId, addressDTO));
	}

	/*
	 * This function is to send a Email
	 */
	@GetMapping("/{booking_id}/email")
	public void sendEmail(@PathVariable("booking_id") String bookingId,
						  @RequestParam(name = "hotelId") final UUID hotelId) {
		LOGGER.info("==================sendEmail============== {}", bookingId);
		bookingService.sendEmail(bookingId, hotelId);
	}

	
	@GetMapping("/{bookingId}/regcard")
	public ResponseEntity<UploadFileRequest> getRegCard(@PathVariable String bookingId) throws IOException {
		LOGGER.info("==================getRegCard================ {} ", bookingId);
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		UploadResponse regcard = bookingService.getRegcardData(hotel.getPmsBaseUrl(), bookingId, hotel.getId(), hotel.getConfiguration());
		if(Objects.isNull(regcard)) {
			throw new HotelCheckinException("Error occured on create regcard ", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(new UploadFileRequest(regcard.getFileName(), regcard.getFileDownloadUri(), "application/pdf", regcard.getSize()));
	}
	
	@PostMapping("/{booking_id}/checkin")
	public ResponseEntity<BookingDTO> checkin(@PathVariable("booking_id") String bookingId,
										   @Valid @RequestBody CheckinDTO checkin)
			throws IOException {
		LOGGER.info("==================checkin============== {} | {}", bookingId, checkin);
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		Optional<BookingDTO> booking = bookingService.checkin(hotel, bookingId, checkin);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("Booking not Found", HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(booking.get());
	}
	
	@PostMapping("/qrValidate")
	public ResponseEntity<BookingResponse> validateQr( @Valid @RequestBody QrCode qrCode,
			@RequestHeader(Constants.HEADER_USER_AGENT) String userAgent){
		String bookingId = qrDecryptionService.QrDecrypt(qrCode.getQrCode());
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		attr.setAttribute("bid",bookingId,2);

		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		Optional<BookingDTO> bookingData = bookingService.getBookingById(hotel.getPmsBaseUrl(), BookingUtils.getConfirmationNumber(bookingId), hotel.getConfiguration(),hotel.getId());
		if (!bookingData.isPresent()) {
			throw new HotelCheckinException("No data found corresponding to this booking id", HttpStatus.NO_CONTENT);
		}
		if (Objects.nonNull(bookingData.get())) {
			emailAndSmsService.sendCheckinLinkOrOTPOnMailAndSmsToGuest(hotel, bookingData.get(), otpService.generateOTPForBookingId(bookingId));
			HttpHeaders headers = getHeaders(hotel, userAgent);
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new BookingResponse(bookingData.get(), hotel));
		}
		attr.removeAttribute("bid",2);
		return ResponseEntity.badRequest().build();
	}
	
	
	@DeleteMapping("/{booking_id}/{type}/{file_name}")
	public ResponseEntity<String> deleteFile(@PathVariable("booking_id") String bookingId,
			@PathVariable("type") String type, @PathVariable("file_name") String fileName) {
		LOGGER.info("==================deleteFile============== {} | {} | {}", bookingId, type, fileName);
		type = type.toLowerCase();
		fileService.deleteFile(bookingId, type, fileName);
		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/sync/booking")
	public void syncBooking(HttpServletRequest request) {
	
		LOGGER.info("==================sync booking==============");
		bookingSchedulerService.fetchBookingDetails();
		LOGGER.info("==================sync booking successfully==============");
	}
	
	@GetMapping("/{bookingId}/download/regcard")
	public ResponseEntity<byte[]> downloadRegCard(@PathVariable("bookingId") String bookingId) throws FileNotFoundException, IOException {
		LOGGER.info("==================RegCard -Download ================ {} ", bookingId);
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
	    byte[] regcardByteArray= bookingService.downloadRegCard(hotel, bookingId);
	    if(Objects.nonNull(regcardByteArray)) {
	    	return ResponseEntity.ok(regcardByteArray);
	    }
	    throw new HotelCheckinException("Booking not Found", HttpStatus.NO_CONTENT);			
	}

	/**
	 * This function is get booking by id
	 */
	@GetMapping("/{booking_id}/verifyotp/{otp}")
	public ResponseEntity<BookingResponse> getBookingByOtp(@PathVariable("booking_id") String bookingId, @PathVariable("otp") String otp,
													  @RequestHeader(Constants.HEADER_USER_AGENT) String userAgent) {
		LOGGER.info("==================Verify OTP & Booking Id ============== {}", bookingId,otp);
		if(!otpService.verifyOTP(otp,bookingId))
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		Optional<BookingDTO> bookingData = bookingService.getBookingById(hotel.getPmsBaseUrl(), BookingUtils.getConfirmationNumber(bookingId), hotel.getConfiguration(),hotel.getId()	);
		if (!bookingData.isPresent()) {
			throw new HotelCheckinException("No data found corresponding to this booking id", HttpStatus.NO_CONTENT);
		}
		if (Objects.nonNull(bookingData.get())) {
			HttpHeaders headers = getHeaders(hotel, userAgent);
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new BookingResponse(bookingData.get(), hotel));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}
