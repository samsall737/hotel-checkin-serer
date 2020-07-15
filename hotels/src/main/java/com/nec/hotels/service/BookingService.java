package com.nec.hotels.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

import com.nec.document.model.UploadResponse;
import com.nec.email.client.EmailClient;
import com.nec.exception.HotelCheckinException;
import com.nec.facerecognition.model.response.AuthenticateList;
import com.nec.facerecognition.model.response.RecogniseRes;
import com.nec.hotels.dao.BookingRepository;
import com.nec.hotels.dao.GuestRepository;
import com.nec.hotels.dao.SpecialAmenitiesRepository;
import com.nec.hotels.entity.Address;
import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.entity.GuestDocuments;
import com.nec.hotels.entity.GuestSpecialAmenities;
import com.nec.hotels.entity.SpecialAmenities;
import com.nec.hotels.enums.CheckinStatus;
import com.nec.hotels.enums.DocumentTypes;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.enums.PreCheckinStatus;
import com.nec.hotels.enums.UploadTypes;
import com.nec.hotels.mapper.BookingMapper;
import com.nec.hotels.model.OffsetBasedPageRequest;
import com.nec.hotels.model.booking.BookingDTO;
import com.nec.hotels.model.booking.BookingResponse;
import com.nec.hotels.model.booking.FlightDetails;
import com.nec.hotels.model.booking.PreferencesDTO;
import com.nec.hotels.model.booking.Room;
import com.nec.hotels.model.checkin.CheckinDTO;
import com.nec.hotels.model.checkin.CheckinDetails;
import com.nec.hotels.model.checkin.PreCheckinDTO;
import com.nec.hotels.model.guest.AccompanyGuest;
import com.nec.hotels.model.guest.GuestDTO;
import com.nec.hotels.model.guest.GuestDetails;
import com.nec.hotels.model.guest.GuestFactory;
import com.nec.hotels.model.guest.KidsGuest;
import com.nec.hotels.model.guest.PrimaryGuest;
import com.nec.hotels.model.guest.SecondaryGuest;
import com.nec.hotels.model.guest.SharedGuest;
import com.nec.hotels.utils.BookingUtils;
import com.nec.hotels.utils.Constants;
import com.nec.hotels.utils.CustomMultipartFile;
import com.nec.hotels.utils.DateUtils;
import com.nec.master.model.Configuration;
import com.nec.master.model.Hotel;
import com.nec.ocr.enums.DocumentPage;
import com.nec.ocr.model.OCRResponse;
import com.nec.pms.gateway.PMSGateway;
import com.nec.pms.model.RoomDetail;
import com.nec.pms.model.checkin.CheckinResponse;
import com.nec.pms.model.regcard.RegCardData;
import com.nec.pms.model.reservation.PmsReservation;

import okhttp3.RequestBody;


@Service
public class BookingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
	
	private final BookingRepository bookingRepository;
	private final PMSService pmsService;
	private final SpecialAmenitiesRepository specialAmenitiesRepo;
	private final GuestRepository guestRepository;
	private final MasterService masterService;
	private final EmailClient emailClient;
	private final String preCheckinUrl;
	private final FileService fileService;
	private final OCRService ocrService;
	private final String preCheckinEmailSubject;
	
	private final PMSGateway pmsGateway;
	
	
	@Autowired
	public BookingService(final BookingRepository bookingRepository, final PMSService pmsService,
			 final SpecialAmenitiesRepository specialAmenitiesRepo,
			final GuestRepository guestRepository, final MasterService masterService, final EmailClient emailClient,
			@Value("${pre-checkin.url}") String preCheckinUrl, final FileService fileService, final OCRService ocrService, @Value("${pre-checkin.email.subject}") String preCheckinEmailSubject, final PMSGateway pmsGateway) {
		this.bookingRepository = bookingRepository;
		this.pmsService = pmsService;
		this.ocrService = ocrService;
		this.specialAmenitiesRepo = specialAmenitiesRepo;
		this.guestRepository = guestRepository; 
		this.masterService = masterService;
		this.emailClient = emailClient;
		this.preCheckinUrl = preCheckinUrl;
		this.fileService = fileService;
		this.preCheckinEmailSubject = preCheckinEmailSubject;
		this.pmsGateway = pmsGateway;
	}
	
	/*
	 * This function is to get a booking, search by id
	 */
	public Optional<BookingDTO> getBookingById(String pmsBaseUrl, String confirmationNumber, Configuration configuration, UUID hotelId) {
		LOGGER.info("CheckinServiceImpl :: getBookingById :: {}", confirmationNumber);
		Optional<Booking> booking = bookingRepository.findByConfirmationNumber(confirmationNumber);
		/*LOGGER.info("============by qrcode================== :: {}", booking.get().getId());*/
		if (!booking.isPresent()) {
			throw new HotelCheckinException("CheckinServiceImpl :: Booking id not found", HttpStatus.NO_CONTENT);
		}
		booking = pmsService.updateDetailsFromPms(pmsBaseUrl, booking.get(), configuration, hotelId);
		return Optional.of(BookingMapper.getBookingDTO(booking.get()));
		
	}
	
	
	
	/**
	 * 
	 * @param arrivalDate
	 * @return
	 */
	public boolean isLinkExpired(long arrivalDate) {
		return (arrivalDate - System.currentTimeMillis()) < Constants.MILLIS_IN_A_DAY;
	}
	
	/*
	 * This function is to get all bookings
	 */
	public List<Optional<BookingDTO>> getAllBookings(long startDate, long endDate, int offset, int limit, String hotelId, String sortBy) {
		LOGGER.info("==================getAllBookings============== {} | {} | {} | {}", startDate, endDate, offset,
				limit);
		if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
			try {
				startDate = DateUtils.getTodayStartDate();
			} catch ( ParseException e) {
				LOGGER.error(e.getMessage());
			}
			endDate = startDate + Constants.MILLIS_IN_A_DAY;
		}
		startDate =  startDate -1;
		endDate =  endDate + 1;
		List<Optional<Booking>> bookings;
		if(!StringUtils.isEmpty(hotelId)) {
			bookings = sortBy.equals("desc") ? bookingRepository.findAllBookingByArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeDesc(startDate, endDate,
					new OffsetBasedPageRequest(limit, offset)) : bookingRepository.findAllBookingByArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeAsc(startDate, endDate,
							new OffsetBasedPageRequest(limit, offset));
		} else {
			bookings = sortBy.equals("desc") ? bookingRepository.findAllBookingByHotelIdAndArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeDesc(hotelId, startDate, endDate,
					new OffsetBasedPageRequest(limit, offset)) : bookingRepository.findAllBookingByHotelIdAndArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeAsc(hotelId, startDate, endDate,
							new OffsetBasedPageRequest(limit, offset));
		}
		List<Optional<BookingDTO>> bookingDtos=new ArrayList<>();
		bookings.parallelStream().forEach(booking -> {
			if(booking.isPresent()) {
				Hotel hotel = masterService.getHotel(booking.get().getHotelId());
				booking = pmsService.updateDetailsFromPms(hotel.getPmsBaseUrl(), booking.get(), hotel.getConfiguration(),hotel.getId());
				bookingDtos.add(Optional.of(BookingMapper.getBookingDTO(booking.get())));
			}
		});
		
		return bookingDtos;
	}
	
	/*
	 * This function is to update the Booking details
	 */
	public Optional<BookingDTO> updateCheckinDetails(CheckinDetails checkInDetails, String bookingId) {
		LOGGER.info("==================updateCheckinDetails============== {}", checkInDetails);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		
		if (!booking.isPresent()) {
			throw new HotelCheckinException("Booking id not found", HttpStatus.NO_CONTENT);
		}
		if (checkInDetails.getCheckInTime() < DateUtils.getStartOfTheDay(booking.get().getArrivalTime(), "UTC")) {
			throw new HotelCheckinException("Check-in Time must be Ahead of Arrival date", HttpStatus.BAD_REQUEST);
		}

		booking.get().setCheckInTime(checkInDetails.getCheckInTime());
		booking.get().setStepsCompleted(Constants.PRECHECKIN_STEP_FIRST);
		booking.get().setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
		bookingRepository.save(booking.get());
		return Optional.of(BookingMapper.getBookingDTO(booking.get()));
	}

	

	/*
	 * This function is to Complete the pre-check-in
	 */
	public Optional<BookingDTO> preCheckin(Hotel hotel, String bookingId, PreCheckinDTO preCheckin) {
		LOGGER.info("==================preCheckin============== {} | {}", bookingId, preCheckin);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("Booking id not found", HttpStatus.NO_CONTENT);
		}
		if (booking.get().getStepsCompleted() < 3) {
			throw new HotelCheckinException("Step 3 Must be Completed First", HttpStatus.BAD_REQUEST);
		}
		if(booking.get().getPreCheckinStatus() == PreCheckinStatus.SUCCESS){
			throw new HotelCheckinException("Precheck in already done", HttpStatus.CONFLICT);
		}
		booking.get().setPayAtDesk(preCheckin.isPayAtDesk());
		if (!preCheckin.isPayAtDesk()) {
			booking.get().setCreditCard(preCheckin.getCreditCard());
		}
		booking.get().setPreCheckinStatus(PreCheckinStatus.SUCCESS);
		booking.get().setStepsCompleted(Constants.PRECHECKIN_STEP_FOURTH);
		bookingRepository.save(booking.get());
		pmsService.updateDetailsToPms(hotel, booking.get());
		return Optional.of(BookingMapper.getBookingDTO(booking.get()));
	}
	
	
	/*
	 * This function is to save guest uploads
	 */
	public UploadResponse saveUploads(String bookingId, UUID guestId, MultipartFile file, String type, String documentType,
			String docIssuePlace, Hotel hotel) {
		LOGGER.info("==================saveUploads============== {} | {} | {} | {} | {} ", bookingId, guestId, file,
				type, documentType);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("No Booking corresponding to this id", HttpStatus.NO_CONTENT);
		}
		
		for (Guest findGuest : booking.get().getGuests()) {
			if (findGuest.getId().equals(guestId)) {
				return this.saveUploadsGeneric(booking.get(), findGuest, file, type, documentType, docIssuePlace,hotel);
			}
		}
		throw new HotelCheckinException("No such guest found in this booking", HttpStatus.BAD_REQUEST);
	}
	
	private <T extends Guest> UploadResponse saveUploadsGeneric(Booking booking, T guest, MultipartFile file, String type,
			String documentType, String docIssuePlace, Hotel hotel) {
		 UploadResponse uploadResponse = fileService.storeFile(booking.getId().toString(), guest.getId(), file, type);
		 if(Objects.isNull(uploadResponse)) {
			 throw new HotelCheckinException("Image Upload Fail", HttpStatus.BAD_REQUEST);
		 }
		 Configuration hotelConfiguration =(Objects.nonNull(hotel) &&  Objects.nonNull(hotel.getConfiguration())) ? hotel.getConfiguration() : new Configuration();
		switch (UploadTypes.valueOf(type.toUpperCase())) {
		case IMAGE:
			this.saveImage(booking.getId(), guest, uploadResponse.getFileDownloadUri(), hotelConfiguration.isFaceRecognitionActive(), hotel.getGroupId());
			booking.setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
			break;
		case DOCUMENT_BACK:
			this.saveDocumentBack(booking, guest,  uploadResponse.getFileDownloadUri(), documentType, docIssuePlace, hotelConfiguration.isOcrActive());
			booking.setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
			break;
		case DOCUMENT_FRONT:
			this.saveDocumentFront(booking, guest,  uploadResponse.getFileDownloadUri(), documentType, docIssuePlace, hotelConfiguration.isOcrActive(), hotel.getGroupId());
			booking.setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
			break;
		case VISA:
			this.saveVisa(booking, guest,  uploadResponse.getFileDownloadUri(), hotelConfiguration.isOcrActive(),hotel.getGroupId());
			booking.setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
			break;
		case SIGNATURE:
			this.saveSignature(booking.getId(), guest, uploadResponse.getFileDownloadUri());
		}
		bookingRepository.save(booking);
		return uploadResponse;
	}
	
	
	/*
	 * This function is to save images
	 */
	private <T extends Guest> void saveImage(String bookingId, T guest, String fileUrl, boolean faceRecognitionActive, int groupId) {
		LOGGER.info("==================saveImage============== {} | {} | {}", bookingId, guest, fileUrl);
		fileService.registerFace(bookingId, fileUrl, guest.getFirstName() + " " + guest.getLastName(),
				UploadTypes.IMAGE.toString(), guest.getId().toString(), faceRecognitionActive, groupId);
		guest.setImageUrl(fileUrl);
	}

	/*
	 * This function is to save documents
	 */
	private <T extends Guest> Boolean saveDocumentFront(Booking booking, T guest, String fileName, String documentType,
			String docIssuePlace, boolean ocrActive, int groupId) {
		LOGGER.info("==================saveDocument============== {} | {} | {} | {}", booking.getId(), guest, fileName,
				documentType);
		if(!ocrActive) {
			return true;
		}
		if (!DocumentTypes.contains(documentType.toLowerCase())) {
			throw new HotelCheckinException("Invalid Document Type", HttpStatus.BAD_REQUEST);
		}
		if (Objects.isNull(guest.getGuestUploads())) {
			guest.setGuestDocuments(new GuestDocuments());
		}
		GuestDocuments document = guest.getGuestUploads();
		document.setFrontUrl(fileName);
		document.setType(documentType);
		document.setIssuePlace(docIssuePlace);
		guest.setGuestDocuments(document);
		OCRResponse response = ocrService.ocrRequest(booking.getId(), guest, document.getFrontUrl(),
				DocumentTypes.getDocumentType(documentType), docIssuePlace,
				DocumentPage.FRONT, ocrActive);
		return ocrService.validateDocumentFront(booking,guest,response, DocumentTypes.getDocumentType(documentType),groupId);
	}

	private <T extends Guest> boolean saveDocumentBack(Booking booking, T guest, String fileUrl, String documentType,
			String docIssuePlace, boolean ocrActive) {
		LOGGER.info("==================saveDocument============== {} | {} | {} | {}", booking.getId(), guest, fileUrl,
				documentType);
		if(!ocrActive) {
			return true;
		}
		if (Objects.isNull(guest.getGuestUploads())) {
			guest.setGuestDocuments(new GuestDocuments());
		}
		GuestDocuments document = guest.getGuestUploads();
		document.setBackUrl(fileUrl);
		document.setType(documentType);
		document.setIssuePlace(docIssuePlace);
		guest.setGuestDocuments(document);
		OCRResponse response = ocrService.ocrRequest(booking.getId(), guest, document.getBackUrl(), DocumentTypes.getDocumentType(documentType), docIssuePlace, DocumentPage.BACK, ocrActive);
		if(Objects.nonNull(response) && Objects.nonNull(guest.getAddress())) {
	//		guest.getAddress().setStreetAddressFirst(response.getAddress());
		}
		return ocrService.validateDocumentBack(booking, guest.getAddress(), response);
	}

	/*
	 * This function is to save visa
	 */
	private <T extends Guest> boolean saveVisa(Booking booking, T guest, String fileName, boolean ocrActive, int groupId) {
		LOGGER.info("==================saveVisa============== {} | {} | {}", booking, guest, fileName);
		guest.getGuestUploads().setType(DocumentTypes.VISA.getType());
		guest.getGuestUploads().setFrontUrl(fileName);
		OCRResponse response = ocrService.ocrRequest(booking.getId(), guest, guest.getGuestUploads().getFrontUrl(),
				DocumentTypes.getDocumentType(DocumentTypes.VISA.getType()), guest.getGuestUploads().getIssuePlace(),DocumentPage.FRONT, ocrActive);
		return ocrService.validateDocumentFront(booking,guest,response,DocumentTypes.getDocumentType(DocumentTypes.VISA.getType()), groupId);

	}

	/**
	 * 
	 * @param <T>
	 * @param bookingId
	 * @param guest
	 * @param signatureUrl
	 */
	private <T extends Guest> void saveSignature(String bookingId, T guest, String signatureUrl) {
		guest.setSignatureUrl(signatureUrl);
	}

	
	/**
	 * 
	 * This function is to add preferences
	 */
	public boolean addPreferences(String bookingId, PreferencesDTO preferences) {
		LOGGER.info("==================addPreferences============== {} | {}", bookingId, preferences);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			return false;
		}
		if (booking.get().getStepsCompleted() < 2) {
			throw new HotelCheckinException("Step 2 Must be Completed First", HttpStatus.BAD_REQUEST);
		}
		if (Objects.nonNull(preferences.getSpecialAmenities())) {
			List<GuestSpecialAmenities> newAmenties = new ArrayList<>();
			List<SpecialAmenities> specialAmenities = specialAmenitiesRepo.findAll();
			for (GuestSpecialAmenities guestSpecialAmenity : preferences.getSpecialAmenities()) {
				specialAmenities.parallelStream().forEach(specialAmenity -> {
					if (specialAmenity.getId().equals(guestSpecialAmenity.getId())) {
						newAmenties.add(new GuestSpecialAmenities(specialAmenity.getAmenity(), specialAmenity.getRate(),
								specialAmenity.getPackageCode(), specialAmenity.getStartDate() + "",
								specialAmenity.getEndDate() + "", specialAmenity.getQuantityValue(), bookingId, specialAmenity.getId()));
					}
				});
			}
			booking.get().setSpecialAmenities(newAmenties);
		}
			booking.get().setSpecialRemarks(preferences.getSpecialRemarks());
		if (Objects.nonNull(preferences.getFlightDetailsDTO())) {
			FlightDetails flightDetails = new FlightDetails();
			BeanUtils.copyProperties(preferences.getFlightDetailsDTO(), flightDetails);
			booking.get().setFlightDetails(flightDetails);
		}
		booking.get().setStepsCompleted(Constants.PRECHECKIN_STEP_THIRD);
		booking.get().setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
		bookingRepository.save(booking.get());
		return true;
	}
	
	/*
	 * This function is to remove a guest
	 */
	public boolean removeGuest(String bookingId, UUID guestId) {
		LOGGER.info("==================removeGuest============== {} | {}", bookingId, guestId);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		Map<UUID, Boolean> guestExists = new HashMap<>();
		if (!booking.isPresent()) {
			throw new HotelCheckinException("No Booking Corresponding to this id", HttpStatus.NO_CONTENT);
		}
		for (Guest guest : booking.get().getGuests()) {
			if (guest.getId().equals(guestId)) {
				booking.get().getGuests().remove(guest);
				guestRepository.delete(guest);
				guestExists.put(guest.getId(), true);
				break;
			}
			guestExists.put(guest.getId(), false);
		}
		if (!guestExists.containsValue(true)) {
			throw new HotelCheckinException("No guest found", HttpStatus.NO_CONTENT);
		}
		bookingRepository.save(booking.get());
		return true;
	}
	
	/*
	 * This function is to add guest details
	 */
	public Optional<BookingDTO> addGuestDetails(String bookingId, GuestDetails guestDetails) {
		LOGGER.info("==================addGuestDetails============== {} | {}", bookingId, guestDetails);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		UUID hotelId =null;
		Hotel hotel = null;
		if(booking.isPresent()) {
			hotelId = booking.get().getHotelId();
			hotel = masterService.getHotel(hotelId);	
		}
		Map<UUID, Boolean> guestCheck = new HashMap<>();
		if (!booking.isPresent()) {
			throw new HotelCheckinException("No Booking corresponding to this id", HttpStatus.NO_CONTENT);
		}
		if (booking.get().getStepsCompleted() < 1) {
			throw new HotelCheckinException("Step 1 Must be completed First", HttpStatus.BAD_REQUEST);
		}
		Guest primaryGuest=booking.get().getGuests().stream().filter(guest -> GuestType.PRIMARY.equals(guest.getType())).findFirst().get();
		guestCheck.put(primaryGuest.getId(),
				this.savePrimaryGuest(primaryGuest, guestDetails, hotel));
		List<SecondaryGuest> secondaryGuestDetail = guestDetails.getSecondaryGuest();
		List<Guest> secondaryGuests = booking.get().getGuests().stream().filter(guest -> !GuestType.PRIMARY.equals(guest.getType())).collect(Collectors.toList());
		List<AccompanyGuest> accompanyGuests = new ArrayList<>();
		List<KidsGuest> kidsGuests = new ArrayList<>();
		List<SharedGuest> sharedGuests = new ArrayList<>();
		secondaryGuestDetail.stream().forEach(secondaryGuest -> {
			Optional<Guest> guest = guestRepository.findById(secondaryGuest.getId());
			if(guest.isPresent()) {
			secondaryGuest.setType(guest.get().getType());
			}
			else {
				throw new HotelCheckinException("GuestId not found", HttpStatus.BAD_REQUEST);
			}
		}
		);
		
		GuestFactory guestFactory=new GuestFactory();
		 for (SecondaryGuest guest : secondaryGuestDetail) {
	            switch (guest.getType()){
	                case ACCOMPANY_GUEST:
	                    accompanyGuests.add((AccompanyGuest)guestFactory.getGuest(guest,guest.getType()));
	                    break;
	                case SHARED_GUEST:
	                    sharedGuests.add((SharedGuest)guestFactory.getGuest(guest,guest.getType()));
	                    break;
	                case KIDS:
	                    kidsGuests.add((KidsGuest)guestFactory.getGuest(guest,guest.getType()));
	                    break;
	                default:
	                    break;
	            }
	        }
		guestDetails.setAccompanyGuests(accompanyGuests);
		guestDetails.setKids(kidsGuests);
		guestDetails.setSharerGuests(sharedGuests);

		for (Guest secondaryGuest : secondaryGuests) {
			updateAccompanyGuestDetails(guestDetails.getAccompanyGuests(), guestCheck, secondaryGuest);
			updateKidsGuestDetails(guestDetails.getKids(), guestCheck, secondaryGuest);
			updateSharedGuestDetails(guestDetails.getSharerGuests(), guestCheck, secondaryGuest);
		}
		booking.get().setStepsCompleted(Constants.PRECHECKIN_STEP_SECOND);
		booking.get().setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
		bookingRepository.save(booking.get());
		return Optional.of(BookingMapper.getBookingDTO(booking.get()));
	}

	private void updateAccompanyGuestDetails(List<AccompanyGuest> accompanyGuestGList, Map<UUID, Boolean> guestCheck, Guest secondaryGuest) {
		for (AccompanyGuest secondaryGuestDetails : accompanyGuestGList) {
			if (updateAndSaveSecondaryGuest(guestCheck, secondaryGuest, secondaryGuestDetails)){
				break;
			}
		}
	}

	private <T extends GuestDTO> boolean updateAndSaveSecondaryGuest(Map<UUID, Boolean> guestCheck, Guest secondaryGuest, T secondaryGuestDetails) {
		if (secondaryGuest.getId().equals(secondaryGuestDetails.getId())) {
			guestCheck.put(secondaryGuest.getId(),
					this.saveSecondaryGuest(secondaryGuest, secondaryGuestDetails));
		}
	
		return false;
	}

	/**
	 * 
	 * @param sharedGuests
	 * @param guestCheck
	 * @param secondaryGuest
	 */
	private void updateSharedGuestDetails(List<SharedGuest> sharedGuests, Map<UUID, Boolean> guestCheck, Guest secondaryGuest) {
		for (SharedGuest sharedGuest : sharedGuests) {
			if (updateAndSaveSecondaryGuest(guestCheck, secondaryGuest, sharedGuest)){
				break;
			}
		}
	}

	/**
	 * 
	 * @param kidsGuests
	 * @param guestCheck
	 * @param secondaryGuest
	 */
	private void updateKidsGuestDetails(List<KidsGuest> kidsGuests, Map<UUID, Boolean> guestCheck, Guest secondaryGuest) {
		for (KidsGuest kidsGuest : kidsGuests) {
			if (updateAndSaveSecondaryGuest(guestCheck, secondaryGuest, kidsGuest)){
				break;
			}
		}
	}
	
	/**
	 * 
	 * This function is to save secondary guest details
	 */
	private <T extends GuestDTO> boolean saveSecondaryGuest(Guest guest, T secondaryGuest) {
		guest.setFirstName(secondaryGuest.getFirstName());
		guest.setLastName(secondaryGuest.getLastName());
		if(Objects.nonNull(secondaryGuest.getTitle())) {
			guest.setTitle(secondaryGuest.getTitle().getDescription());
		}
		int secondaryGuestDetailsDOB[] = DateUtils.getDayDateYearFromTimeStamp(secondaryGuest.getDateOfBirth());
		int secondaryGuestDOB[] = DateUtils.getDayDateYearFromTimeStamp(guest.getDateOfBirth());
		secondaryGuestDetailsDOB[2] = Integer.parseInt(Integer.toString(secondaryGuestDetailsDOB[2]).substring(2,4)); 
		secondaryGuestDOB[2] = Integer.parseInt(Integer.toString(secondaryGuestDOB[2]).substring(2,4));
		if(!Arrays.equals(secondaryGuestDetailsDOB, secondaryGuestDOB))
		{
		throw new HotelCheckinException("Date of Birth is incorrect, please enter the Date of Birth as per " + guest.getGuestUploads().getType(), HttpStatus.BAD_REQUEST);
	}	

		if (!StringUtils.equals(secondaryGuest.getNationality(), guest.getNationality())) {
			throw new HotelCheckinException("Nationality did not match with uploaded documents " + guest.getGuestUploads().getType(), HttpStatus.BAD_REQUEST);
		}
		guest.setNationality(secondaryGuest.getNationality());
		guest.setDateOfBirth(secondaryGuest.getDateOfBirth());
		if (Objects.isNull(guest.getGuestUploads())
				|| Objects.isNull(guest.getGuestUploads().getFrontUrl())) {
			throw new HotelCheckinException("Document is required for every guest", HttpStatus.BAD_REQUEST);
		}
		if (guest.getGuestUploads().getType().equalsIgnoreCase(DocumentTypes.DRIVING_LICENCE.toString())
				&& Objects.isNull(guest.getGuestUploads().getFrontUrl())) {
			throw new HotelCheckinException(
					"Back Side is required for" + guest.getGuestUploads().getType(),
					HttpStatus.BAD_REQUEST);
		}
		return true;
	}



	/**
	 * 
	 * This function is to save primary guest details
	 */
	private boolean savePrimaryGuest(Guest guest, GuestDetails guestDetails, Hotel hotel) {
		LOGGER.info("==================savePrimaryGuest============== {} | {}", guest, guestDetails);
		PrimaryGuest primaryGuestDTO=(PrimaryGuest)new GuestFactory().getGuest(guest,guest.getType());
		if (primaryGuestDTO.isDocumentRequired() && Objects.isNull(guest.getGuestUploads())) {
			throw new HotelCheckinException("Primary Guest Uploads Missing", HttpStatus.BAD_REQUEST);
		}
		if (guest.getId().equals(guestDetails.getPrimaryGuest().getId())) {
				int guestDetailsDOB[] = DateUtils.getDayDateYearFromTimeStamp(guestDetails.getPrimaryGuest().getDateOfBirth());
				int guestDOB[] = DateUtils.getDayDateYearFromTimeStamp(guest.getDateOfBirth());
				guestDetailsDOB[2] = Integer.parseInt(Integer.toString(guestDetailsDOB[2]).substring(2,4)); 
				guestDOB[2] = Integer.parseInt(Integer.toString(guestDOB[2]).substring(2,4));
				if(!Arrays.equals(guestDetailsDOB, guestDOB))
				{
				throw new HotelCheckinException("Date of Birth is incorrect, please enter the Date of Birth as per " + guest.getGuestUploads().getType(), HttpStatus.BAD_REQUEST);
			}	
			//guest.setNationality(guestDetails.getPrimaryGuest().getNationality());
			//guest.setDateOfBirth(guestDetails.getPrimaryGuest().getDateOfBirth());
			if (!StringUtils.equals(guestDetails.getPrimaryGuest().getNationality(), guest.getNationality())) {
				throw new HotelCheckinException("Nationality did not match with uploaded " + guest.getGuestUploads().getType(), HttpStatus.BAD_REQUEST);
			}
			if (primaryGuestDTO.isFaceRequired() && Objects.isNull(guest.getImageUrl())) {
				throw new HotelCheckinException("Image is required for primary guest", HttpStatus.BAD_REQUEST);
			}
			if (Objects.isNull(guest.getGuestUploads())
					|| Objects.isNull(guest.getGuestUploads().getFrontUrl())) {
				throw new HotelCheckinException("Document is required for every guest", HttpStatus.BAD_REQUEST);
			}
			if (guest.getGuestUploads().getType()
					.equalsIgnoreCase(com.nec.hotels.enums.DocumentTypes.DRIVING_LICENCE.toString())
					&& Objects.isNull(guest.getGuestUploads().getFrontUrl())) {
				throw new HotelCheckinException(
						"Back Side is required for" + guest.getGuestUploads().getType(),
						HttpStatus.BAD_REQUEST);
			}
			//todo age config
			if(Objects.nonNull(hotel) && Objects.nonNull(hotel.getConfiguration()))
			if (DateUtils.getAge(guest.getDateOfBirth()) < hotel.getConfiguration().getKidsAge()) {
				throw new HotelCheckinException("Primary Guest must be an Adult", HttpStatus.BAD_REQUEST);
			}
			return true;
		}
		return false;
	}
	

	/**
	 * This function is to add a new guest
	 */
	public Guest addGuest(String bookingId) {
		LOGGER.info("==================addGuest============== {}", bookingId);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("No booking by this Id", HttpStatus.NO_CONTENT);
		}
		Guest guest = guestRepository.save(new Guest(GuestType.ACCOMPANY_GUEST));
		booking.get().setPreCheckinStatus(PreCheckinStatus.ATTEMPTED);
		bookingRepository.save(booking.get());
		return guest;
	}

	
	/*
	 * This function is to update the guest Details
	 */
	public Guest updateGuest(String bookingId, UUID guestId, Guest guest) {
		LOGGER.info("==================updateGuest============== {}", guest);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("Booking id not found", HttpStatus.NO_CONTENT);
		}
		Optional<Guest> optionalNewGuest = guestRepository.findById(guestId);
		if (!optionalNewGuest.isPresent()) {
			throw new HotelCheckinException("guest id not found", HttpStatus.NO_CONTENT);
		}
		Guest newGuest = optionalNewGuest.get();
		newGuest.setIsdCode(guest.getIsdCode());
		newGuest.setContactNumber(guest.getContactNumber() + "");
		newGuest.setEmail(guest.getEmail());
		newGuest = guestRepository.save(newGuest);
		return newGuest;
	}
	
	
	public void sendEmail(String bookingId, UUID hotelId) {
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("No booking Found", HttpStatus.NO_CONTENT);
		}
		 Guest primaryGuest = booking.get().getGuests().stream().filter(guest -> GuestType.PRIMARY.equals(guest.getType())).findFirst().get();
		 if(Objects.nonNull(primaryGuest.getEmail())) {
			 Hotel hotel = masterService.getHotel(hotelId);
			 Map<String, String> model = new HashMap<>();
			 model.put(Constants.EMAIL_NAME, primaryGuest.getName());
			 model.put(Constants.EMAIL_BOOKING_ID, booking.get().getId());
			 model.put(Constants.EMAIL_PRE_CHECKIN_LINK, preCheckinUrl + hotel.getId()+"/booking/");
			 model.put(Constants.EMAIL_HOTEL_NAME, hotel.getName());
			 model.put(Constants.EMAIL_HOTEL_CITY, hotel.getAddress().getCity());
			 model.put(Constants.EMAIL_ARRIVAL_DATE,
				DateUtils.millisecondToDateWithMonthName(booking.get().getArrivalTime(), hotel.getTimezone()));
			 model.put(Constants.EMAIL_DEPARTURE_DATE_,
				DateUtils.millisecondToDateWithMonthName(booking.get().getDepartureTime(), hotel.getTimezone()));
			 Configuration hoteConfiguration = hotel.getConfiguration();
			 if(Objects.nonNull(hoteConfiguration) && !StringUtils.isEmpty(hoteConfiguration.getEmailUserName())) {
				 	emailClient.emailConfig(model, hoteConfiguration.getEmailUserName(), hoteConfiguration.getEmailPassword(), hotelId.toString(), primaryGuest.getEmail(), preCheckinEmailSubject, null, null);
				 	return;
			 }
			 emailClient.emailConfig(model, primaryGuest.getEmail(), preCheckinEmailSubject);
		 }
	}
	
	/**
	 * 
	 * @param pmsbaseUrl
	 * @param booking
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public UploadResponse getRegcardData(String pmsbaseUrl, String bookingId, UUID hotelId, Configuration configuration) throws FileNotFoundException, IOException {
		RegCardData regCardData = pmsService.getRegCard(pmsbaseUrl, BookingUtils.getConfirmationNumber(bookingId),configuration,hotelId);
		if(regCardData != null) {
			byte[] bytes = Base64.getDecoder().decode(regCardData.getData().getDocumentToSign().getFileContents().getValue());
			return uploadAndSaveRegcard(bytes, bookingId, hotelId.toString());
		}
		return null;
	}
	
	public byte[] downloadRegCard(Hotel hotel, String bookingId) throws IOException {
		
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		String regUrl = null;
		if(booking.isPresent()) {
			List<Guest> guests = booking.get().getGuests();
			for(Guest guest : guests) {
				if(guest.getType() == GuestType.PRIMARY) {
					regUrl = guest.getRegcardUrl();
					if(Objects.isNull(regUrl)) {
						throw new HotelCheckinException("Reg card with booking Id not found ", HttpStatus.NOT_FOUND);
					}
				}
			}
			return fileService.downloadFile(regUrl, bookingId);
		}
		return null;	
		
	}
	
	/**
	 * 
	 * @param bytes
	 * @param bookingId
	 * @param hotelId
	 * @return
	 */
	private UploadResponse uploadAndSaveRegcard(byte[] bytes, String bookingId, String hotelId) {
		MultipartFile customMultipartFile = new CustomMultipartFile(bytes, bookingId +"_regcard.pdf","application/pdf");
		try {
			customMultipartFile.transferTo(((CustomMultipartFile)customMultipartFile).getFile());
			UploadResponse uploadResponse = fileService.uploadFile(((CustomMultipartFile)customMultipartFile).getFile(), hotelId, bookingId + "/regcard");
			Optional<Booking> booking = bookingRepository.findById(bookingId); 
			if(booking.isPresent()) {
				Guest primaryGuest=booking.get().getGuests().stream().filter(guest -> GuestType.PRIMARY.equals(guest.getType())).findFirst().get();
				if(primaryGuest != null) {
					primaryGuest.setRegcardUrl(uploadResponse.getFileDownloadUri());
					guestRepository.save(primaryGuest);
				}
			}
			return uploadResponse;
		} catch (IllegalStateException | IOException e) {
		    LOGGER.info("Error on parse regcard " + e);
		}
		return null;
	}
	
	/*
	 * This function is to Add signature and Credit card
	 */
	public Optional<BookingDTO> checkin(Hotel hotel, String bookingId, CheckinDTO checkin) throws IOException {
		LOGGER.info("==================Checkin============== {} | {}", bookingId, checkin);
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("Booking id not found", HttpStatus.NO_CONTENT);
		}
		if (booking.get().getCheckinStatus() == CheckinStatus.SUCCESS) {
			throw new HotelCheckinException("Checkin is already complete", HttpStatus.BAD_REQUEST);
		}
		if (booking.get().getPreCheckinStatus() != PreCheckinStatus.SUCCESS) {
			throw new HotelCheckinException("Pre-checkin is not Successful", HttpStatus.BAD_REQUEST);
		}
		Guest primaryGuest=booking.get().getGuests().stream().filter(guest -> GuestType.PRIMARY.equals(guest.getType())).findFirst().get();
		if (Objects.isNull(primaryGuest.getSignatureUrl())) {
			throw new HotelCheckinException("Signature Missing", HttpStatus.BAD_REQUEST);
		}
	//	booking.get().setCheckinStatus(CheckinStatus.SUCCESS);
		if(Objects.nonNull(checkin.getCreditCard())){
			booking.get().setCreditCard(checkin.getCreditCard());
			booking.get().setPayAtDesk(false);
		}
		if(Objects.isNull(checkin.getCreditCard().getId())) {
			bookingRepository.save(booking.get());	
		}
		
		String regCard = primaryGuest.getRegcardUrl();
		if(StringUtils.isEmpty(regCard)) {
			UploadResponse regcardRes = getRegcardData(hotel.getPmsBaseUrl(), bookingId, hotel.getId(), hotel.getConfiguration());
			if(Objects.isNull(regcardRes)) {
				throw new HotelCheckinException("Regcard downloading Failed", HttpStatus.BAD_REQUEST);
			}
		}
		Configuration configuration = hotel.getConfiguration();
		byte[] regCardBytes = pmsService.addSignatureToRegCardInString(regCard, primaryGuest.getSignatureUrl(), configuration != null ? configuration.getSignaturePositionX() : 500, configuration != null ? configuration.getSignaturePositionY() : 1040, bookingId);
		UploadResponse regcardUploadResponse = uploadAndSaveRegcard(regCardBytes, bookingId, hotel.getId().toString());
	//	bookingRepository.save(booking.get());
		LOGGER.info("================== Checkin SUCCESS.......============== {} | {}");
		Optional<BookingDTO> bookingCopy = Optional.of(BookingMapper.getBookingDTO(booking.get()));
		try {
			byte[] signedDocumentArray = Base64.getEncoder().encode(regCardBytes);
			String signedDocuments = new String(signedDocumentArray); 
			CheckinResponse response = pmsService.reservationCheckin(hotel, booking.get().getConfirmationNumber(), booking.get().getCreditCard(), signedDocuments);
			if(Objects.nonNull(response)) {				
				PmsReservation pmsReservation = pmsGateway.getBookingDetails(hotel.getPmsBaseUrl(), booking.get().getConfirmationNumber(), configuration.isPmsActive(),hotel.getId());
				List<Room> rooms = bookingCopy.get().getRoom();
				if(Objects.isNull(rooms)) {
					rooms = new ArrayList<Room>();
				}
		        for (int index = 0; index < pmsReservation.getData().getRoomDetails().size(); index++) {
		            RoomDetail roomDetails = pmsReservation.getData().getRoomDetails().get(index);
		            Room room=new Room();
		            if (roomDetails.getRoomNumber() != null) {
		                room.setRoomNumber(Integer.parseInt(roomDetails.getRoomNumber()));
		            } else {
		                room.setRoomNumber(null);
		            }
		            room.setRoomStatus(roomDetails.getRoomStatus());
		            room.setType(roomDetails.getRoomType());
		            rooms.add(room);
		        }
		        bookingCopy.get().setRoom(rooms);
			}
			
		}catch(Exception e) {
			LOGGER.error("==================PMS Checkin FAIL.......============== {} ", e);
			throw new HotelCheckinException("PMS Checkin Failed with this id", HttpStatus.BAD_REQUEST);  
		}
		booking.get().setCheckinStatus(CheckinStatus.SUCCESS);
		bookingRepository.save(booking.get());
		return bookingCopy;
	}
	
	public Guest addAddress(UUID guestId, Address addressDTO) {
		LOGGER.info("==================addAddress============== {} | {} ", guestId, addressDTO);
		Optional<Guest> guest = guestRepository.findById(guestId);
		if (!guest.isPresent()) {
			throw new HotelCheckinException("No guest Found with this Id", HttpStatus.NO_CONTENT);
		}
		Address address = new Address();
		BeanUtils.copyProperties(addressDTO, address);
		guest.get().setAddress(address);
		return guestRepository.save(guest.get());
	}
	
	
	
	/**
	 * This function is to do Face Recognition
	 */
	public BookingResponse recogniseFace(MultipartFile face, HttpServletRequest request, int groupId) {
		LOGGER.info("==================Recognise Face============== {}", face);
		RecogniseRes response = fileService.recogniseFace(face);

		List<AuthenticateList> authenticateList = response.getResData().getAuthenticateList().stream().
				filter(authenticate -> authenticate.getRegistrant().getGroupIDs().contains(groupId))
				.collect(Collectors.toList());
		if(Objects.isNull(authenticateList) || authenticateList.size() == 0) {
			throw new HotelCheckinException("Booking not Present", HttpStatus.NO_CONTENT);
		}
		authenticateList.sort((AuthenticateList list1, AuthenticateList list2) -> list1.compareTo(list2));
		int lastIndex = authenticateList.size() - 1;
		String bookingId = authenticateList.get(lastIndex).getUniqueID();
		String faceId = authenticateList.get(lastIndex).getRegistrant().getMemo();
		Hotel hotel = masterService.getHotelByHotelCode(BookingUtils.getHotelCodeByBookingId(bookingId));
		request.setAttribute("hotelId", hotel.getId().toString());
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new HotelCheckinException("Booking not Present", HttpStatus.NO_CONTENT);
		}
		booking.get().setFaceRecognitionGuestId(UUID.fromString(faceId));
		booking = pmsService.updateDetailsFromPms(hotel.getPmsBaseUrl(), booking.get(), hotel.getConfiguration(),hotel.getId());
		
		BookingResponse getBookinResponse = null;
		if (booking.isPresent()) {
			bookingRepository.save(booking.get());
			getBookinResponse = new BookingResponse(BookingMapper.getBookingDTO(booking.get()), hotel);
		}
		return getBookinResponse;
	}
	
	
	public UUID getHotelIdByBookingId(String bookingId) {
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if(booking.isPresent()) {
			return booking.get().getHotelId();
		}
		return null;
	}


}
