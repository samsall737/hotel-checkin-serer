package com.nec.hotels.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.nec.exception.HotelCheckinException;
import com.nec.hotels.entity.SpecialAmenities;
import com.nec.hotels.enums.DocumentTypes;
import com.nec.hotels.enums.UserType;
import com.nec.hotels.model.AdminStats;
import com.nec.hotels.model.booking.BookingDTO;
import com.nec.hotels.service.BookingService;
import com.nec.hotels.service.HotelService;
import com.nec.hotels.service.MasterService;
import com.nec.hotels.service.OAuthService;
import com.nec.hotels.utils.Constants;
import com.nec.hotels.utils.DateUtils;
import com.nec.master.model.Hotel;
import com.nec.oauth.model.Token;


@RequestMapping("/api/v1/hotel")
@RestController
public class HotelController {
	
	private static Logger logger = LoggerFactory.getLogger(HotelController.class);
	
	private final BookingService bookingService;
	private final MasterService masterService;
	private HotelService hotelService;
	private OAuthService oAuthService;
	
	@Autowired
	public HotelController(final BookingService bookingService,final MasterService masterService,
			final HotelService hotelService, final OAuthService oAuthService) {
		this.bookingService = bookingService;
		this.masterService = masterService;
		this.hotelService = hotelService;
		this.oAuthService = oAuthService;
	}

	/**
	 * This function is to get all special amenities
	 */
	@GetMapping("/{hotel_id}/special-amenities")
	public ResponseEntity<List<SpecialAmenities>> getSpecialAmenities(@RequestHeader HttpHeaders headers, @PathVariable("hotel_id") String hotelId) {
		logger.info("==================getSpecialAmenities================");
		Hotel hotel = masterService.getHotel(UUID.fromString(hotelId));
		try {
			String userId = headers.get(Constants.HEADER_USERID).get(0);
			String userAgent = headers.get(Constants.HEADER_USER_AGENT).get(0);
			String authToken = headers.get(Constants.HEADER_AUTHORIZATION).get(0);
			
			final Token token = oAuthService.verifyAndDecodeAuthToken(userId, userAgent, authToken);
			if(token.getUserType().equals(String.valueOf(UserType.HOTEL_ADMIN.getCode()))) {
				return ResponseEntity.ok(hotelService.getSpecialAmenities(hotel));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(hotelService.getSpecialAmenitiesForWeb(hotel));
		}
		return ResponseEntity.ok(hotelService.getSpecialAmenitiesForWeb(hotel));
	}
	

	//no use
	/**
	 * This function is to add a special amenity
	 */
	@PostMapping("/{hotel_id}/special-amenity")
	public ResponseEntity<SpecialAmenities> addSpecialAmenity(@Valid @RequestBody SpecialAmenities specialAmenity) {
		logger.info("==================addSpecialAmenity================");
		return ResponseEntity.ok(hotelService.addSpecialAmenity(specialAmenity));
	}
	

	//no use
	/*
	 * This function is to remove a special amenity
	 */
	@DeleteMapping("/{hotel_id}/special-amenity/{specialAmenityId}")
	public ResponseEntity<UUID> deleteSpecialAmenity(@PathVariable UUID specialAmenityId) {
		logger.info("==================deleteSpecialAmenity================ {} ", specialAmenityId);
		hotelService.deleteSpecialAmenities(specialAmenityId);
		return ResponseEntity.ok(specialAmenityId);
	}
	
	
	//no use
	@PutMapping("/{hotel_id}/special-amenities")
	public ResponseEntity<List<SpecialAmenities>> updateSpecialAmenities(@RequestBody List<String> amenityIds) {
		logger.info("==================updateSpecialAmenities==================");
		return ResponseEntity.ok(hotelService.updateAmenity(amenityIds));
	}
	
	
	
	/*
	 * This function is to get all document types
	 */
	@GetMapping("/{hotel_id}/document-types")
	public ResponseEntity<DocumentTypes[]> getDocumentTypes() {
		logger.info("==================getDocumentTypes================");
		return ResponseEntity.ok(hotelService.getDocumentTypes());
	}
	
	/**
	 * This function is to get booking stats
	 */
	@GetMapping("/{hotel_id}/stats")
	public ResponseEntity<AdminStats> getStats(@PathVariable("hotel_id") String hotelId,
			@RequestParam(required = false) Long startDate,
			@RequestParam(required = false) Long endDate) {
		logger.info("==================getStats============== {} | {}", startDate, endDate);
		if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			if (startDate > endDate) {
			throw new HotelCheckinException("Invalid Start date and End date", HttpStatus.BAD_REQUEST);
			}
		}
		AdminStats adminStats = hotelService.getBookingStats(startDate, endDate,UUID.fromString(hotelId));
		if (adminStats.isEmpty()) {
			throw new HotelCheckinException("No booking found for these dates", HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(adminStats);
	}
	
	
	/**
	 * This function is get all bookings
	 */
	@GetMapping("/{hotel_id}/booking")
	public ResponseEntity<List<BookingDTO>> getBookings(@RequestParam(required = false) Long startDate,
			@RequestParam(required = false) Long endDate,
			@RequestParam(defaultValue = "0", required = false) int offset,
			@RequestParam(defaultValue = "10", required = false) int limit,
			@PathVariable("hotel_id") String hotelId,
			@RequestParam(defaultValue = "asc" ,name = "sort", required = false) String sort) {
		logger.info("==================getBooking============== {} | {} | {} | {}", startDate, endDate, offset, limit);
		if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
			try {
				startDate = DateUtils.getTodayStartDate();
			} catch (ParseException parseException) {
				logger.error("Error on parse date " ,parseException.getMessage());
			}
			endDate = startDate + Constants.MILLIS_IN_A_DAY;
		}
		if (startDate > endDate) {
			throw new HotelCheckinException("Invalid Start and End dates", HttpStatus.BAD_REQUEST);
		}
		List<Optional<BookingDTO>> bookingData = bookingService.getAllBookings(startDate, endDate, offset, limit, hotelId, sort);
		if(bookingData.isEmpty()) {
			return ResponseEntity.noContent().build(); 
		}
		return ResponseEntity.ok(bookingData.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()));
	}

	
}
