package com.nec.hotels.controller;

import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nec.exception.HotelCheckinException;
import com.nec.hotels.model.booking.BookingResponse;
import com.nec.hotels.service.BookingService;
import com.nec.hotels.service.MasterService;
import com.nec.hotels.service.OAuthService;
import com.nec.hotels.utils.BookingUtils;
import com.nec.hotels.utils.Constants;
import com.nec.master.model.Hotel;
import com.nec.oauth.model.Token;

@RestController
@RequestMapping("/api/v1/face-recognise")
public class FaceRecognitionController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FaceRecognitionController.class);

	private final BookingService bookingService;
	private final OAuthService authService;
	private final MasterService masterService;
	
	@Autowired
	public FaceRecognitionController(final BookingService bookingService,final OAuthService authService,final MasterService masterService) {
		this.bookingService = bookingService;
		this.authService = authService;
		this.masterService= masterService;
	}

	
	/**
	 * This function is used for Face Recognition
	 */
	@PostMapping
	public ResponseEntity<BookingResponse> recogniseFace(@RequestParam("face") MultipartFile face,
			HttpServletRequest request, @RequestHeader(Constants.HEADER_ACCESS_TOKEN) String accesstoken) {
		LOGGER.info("==================recogniseFace============== {}", face);
		final Token token = authService.verifyAndDecodeAccessToken(null,null, accesstoken);
		if(token == null) {
			throw new HotelCheckinException("Booking Id not Found", HttpStatus.NO_CONTENT);
		}
		UUID hotelId = UUID.fromString(token.getId());
		Hotel hotel = masterService.getHotel(hotelId);
		BookingResponse booking = bookingService.recogniseFace(face, request,hotel.getGroupId());
		
		if (Objects.isNull(booking)) {
			throw new HotelCheckinException("Booking Id not Found", HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(booking);
	}

}
