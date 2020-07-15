package com.nec.pms.gateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.nec.exception.PMSException;
import com.nec.master.gateway.MasterGateway;
import com.nec.master.model.Hotel;
import com.nec.pms.client.PMSClient;
import com.nec.pms.model.PMSToken;
import com.nec.pms.model.PMSTokenRequest;
import com.nec.pms.model.checkin.Checkin;
import com.nec.pms.model.checkin.CheckinResponse;
import com.nec.pms.model.checkin.PrecheckinDetailsDTO;
import com.nec.pms.model.guest.AccompanyGuest;
import com.nec.pms.model.packages.PackageList;
import com.nec.pms.model.regcard.RegCardData;
import com.nec.pms.model.reservation.PmsReservation;
import com.nec.pms.model.reservation.PmsReservationsDetails;

import retrofit2.Response;

@Component
public class PMSGateway {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PMSGateway.class);

	private final PMSClient pmsClient;

	private final String RESERVATION_URLS = "/reservations/v1/reservations";
	private final String RESERVATION_URL = "/reservations/v1/reservation/";
	private final String RESERVATION_URL_DETAILS = "/reservations/v1/reservation/";
	private final String GET_PACKAGE_LIST_URL = "/packages/v1/packages";
	
	@Autowired
	public PMSGateway(final PMSClient pmsClient) {
		this.pmsClient = pmsClient;
	}
	
	
	/**
	 * 
	 * @param pmsBaseUrl
	 * @param reservationId
	 * @param pmsActive
	 * @return
	 */
	public PmsReservation getBookingDetails(String pmsBaseUrl, String reservationId, boolean pmsActive,UUID hotelId) {
		if(!pmsActive) {
			return new PmsReservation();
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotelId.toString());
		MDC.setContextMap(contextMap);
		Response<PmsReservation> response=null;
		try {
			response = pmsClient.getBookingDetails(pmsBaseUrl + RESERVATION_URL_DETAILS + reservationId).blockingGet();
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new PMSException("PMS Request fail", HttpStatus.BAD_REQUEST);
		}
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			throw new PMSException("PMS Exception",errorBody, HttpStatus.valueOf(response.code()));
		}

		if (!response.body().getSuccess()) {
			LOGGER.info("PMS Response: {}", response.body().getMessage());
			throw new PMSException("PMS Exception: " + response.body().getMessage(),
					HttpStatus.valueOf(response.body().getStatus()));
		}
		LOGGER.info("success ............");
		return response.body();
	}
	
	/**
	 * 
	 * @param pmsUrl
	 * @param from
	 * @param to
	 * @param brief
	 * @return
	 */
	public PmsReservationsDetails getConfirmationNumbers(String pmsUrl,long from,long to,boolean brief, boolean pmsActive, Hotel hotel) {
		if(!pmsActive) {
			return new PmsReservationsDetails();
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotel.getId().toString());
		MDC.setContextMap(contextMap);
		Response<PmsReservationsDetails> response = pmsClient
				.getConfirmationNumbers(pmsUrl + RESERVATION_URLS+"?" , hotel.getHotelCode(), from, to ,brief).blockingGet();
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			return null;
		}

		if (!response.body().getSuccess()) {
			LOGGER.info("PMS Response: {}", response.body().getMessage()+ " Code : "+ HttpStatus.valueOf(response.body().getStatus()));
			return null;
		}
		return response.body();
	}

	/**
	 * 
	 * @param pmsUrl
	 * @param reservationId
	 * @param precheckinDetails
	 * @return
	 */
	public PmsReservation updateBookingDetails(String pmsUrl, String reservationId,
			PrecheckinDetailsDTO precheckinDetails, boolean pmsActive, UUID hotelId) {
		if(!pmsActive) {
			return new PmsReservation();
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotelId.toString());
		MDC.setContextMap(contextMap);
		Response<PmsReservation> response = pmsClient
				.updateBookingDetails(pmsUrl + RESERVATION_URL + reservationId, precheckinDetails)
				.blockingGet();
		if (HttpStatus.NO_CONTENT.equals(HttpStatus.valueOf(response.code()))) {
			return new PmsReservation();
		}
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			throw new PMSException("PMS Exception", HttpStatus.valueOf(response.code()));
		}

		if (!response.body().getSuccess()) {
			LOGGER.info("PMS Response: {}", response.body().getMessage());
			throw new PMSException("PMS Exception: " + response.body().getMessage(),
					HttpStatus.valueOf(response.body().getStatus()));
		}

		return response.body();
	}

	/**
	 * 
	 * @param pmsUrl
	 * @param reservationId
	 * @return
	 */
	public RegCardData getRegCard(final String pmsUrl, final String reservationId,final boolean pmsActive, UUID hotelId) {
		if(!pmsActive) {
			return new RegCardData();
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotelId.toString());
		MDC.setContextMap(contextMap);
		Response<RegCardData> response = pmsClient
				.getRegCard(pmsUrl + RESERVATION_URL + reservationId + "/regcard").blockingGet();
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			throw new PMSException("PMS Exception" + errorBody, HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	/**
	 * 
	 * @param pmsUrl
	 * @param reservationId
	 * @param checkin
	 * @return
	 */
	public CheckinResponse uploadSignedRegCard(final String pmsUrl,final String reservationId, final Checkin checkin,final boolean pmsActive, UUID hotelId) {
		if(!pmsActive) {
			return new CheckinResponse();
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotelId.toString());
		MDC.setContextMap(contextMap);
		Response<CheckinResponse> response = pmsClient
				.uploadSignedRegCard(pmsUrl + RESERVATION_URL + reservationId + "/checkin", checkin)
				.blockingGet();
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			throw new PMSException("PMS Exception errorBody", HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	/**
	 * 
	 * @param pmsUrl
	 * @param reservationId
	 * @param secondaryGuest
	 * @param pmsActive
	 * @return
	 */
	public String addAcompanyGuest(final String pmsUrl, final String reservationId,final AccompanyGuest secondaryGuest,final boolean pmsActive, UUID hotelId) {
		if(!pmsActive) {
			return "";
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotelId.toString());
		MDC.setContextMap(contextMap);
		Response<String> response = pmsClient.addAcompanyGuest(
				pmsUrl + RESERVATION_URL + reservationId + "/accompany", secondaryGuest).blockingGet();
		if (!response.isSuccessful()) {
			String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			throw new PMSException("PMS Exception", HttpStatus.valueOf(response.code()));
		}
		return response.body();
	}

	/**
	 * 
	 * @param pmsUrl
	 * @param hotelCode
	 * @param chainCode
	 * @param pmsActive
	 * @return
	 */
	public Optional<PackageList> getPackageList(final String pmsUrl,final String hotelCode,final String chainCode,final boolean pmsActive, UUID hotelId) {
		if(!pmsActive) {
			return Optional.of(new PackageList());
		}
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotelId.toString());
		MDC.setContextMap(contextMap);
		Response<PackageList> response = pmsClient.getPackageList(pmsUrl + GET_PACKAGE_LIST_URL, hotelCode, chainCode)
				.blockingGet();
		if (!response.isSuccessful()) {
		String errorBody = "PMS Exception";
			try {
				errorBody = response.errorBody().string();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			LOGGER.error(errorBody);
			throw new PMSException("PMS Exception", HttpStatus.valueOf(response.code()));
		}
		return Optional.of(response.body());
	}

}
