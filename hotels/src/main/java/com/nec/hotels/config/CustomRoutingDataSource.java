package com.nec.hotels.config;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nec.hotels.utils.BookingUtils;
import com.nec.hotels.utils.Constants;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {


	@Autowired
	public  MasterEurekaConfig masterEurekaConfig;



	@Override
	protected Object determineCurrentLookupKey() {
		logger.info("==== lookup key =======");
		logger.info("=== hotelId===="+MDC.get("hotel_id"));
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attr != null) {
			String accessToken = attr.getRequest().getHeader("Access-Token"); // find parameter from request
			//	if (Objects.isNull(accessToken)) {
			String path = attr.getRequest().getRequestURI();
			final Matcher isBookingByIdMatch = Pattern.compile(".+booking.+\\w$").matcher(path);
			final Matcher isRecogniseFaceMatch = Pattern.compile(".+face-recognise$").matcher(path);
			final Matcher isHotelApi = Pattern.compile("/api/v1/hotel.*").matcher(path);
			final Matcher isTestMatch = Pattern.compile("/api/v1/booking/sync_booking").matcher(path);
			final Matcher isBookingByQRCode = Pattern.compile("/api/v1/booking/qrValidate").matcher(path);
			if(isRecogniseFaceMatch.matches()) {
				return  attr.getRequest().getAttribute("hotelId");
			}
			if (isBookingByQRCode.matches()) {
				return getDataSourceForQRCode(attr.getAttribute("bid",2).toString());
			}
			if (isBookingByIdMatch.matches()) {
				return getDataSourceForBooking(path);
			}
			if(isHotelApi.matches()) {
				return getDataSourceForHotel(path);
			}
			if(isTestMatch.matches() && StringUtils.isNotEmpty(MDC.get("hotel_id"))) {
				logger.info( "test api" + MDC.get("hotel_id"));
				return MDC.get("hotel_id");
			}
			//		}
			logger.info("======= hotelId========= "+ attr.getRequest().getParameter("hotelId"));
			if(Objects.nonNull(attr.getRequest().getParameter("hotelId"))) {
				return attr.getRequest().getParameter("hotelId");
			}
		} else if(StringUtils.isNotEmpty(MDC.get("hotel_id"))){
			return MDC.get("hotel_id");
		}
		return Constants.DEFAULT_DATA_SOURCE; // default data source
	}

	/**
	 * Datasource lookup key for booking
	 * @param url
	 * @return
	 */
	public String getDataSourceForBooking(String url){
		int index=url.lastIndexOf("booking");
		int startIndex=url.indexOf('/', index)+1;
		int endIndex=url.indexOf('/', startIndex);
		String result = (endIndex == -1) ? url.substring(startIndex ) : url.substring(startIndex, endIndex);
		UUID hotelID = masterEurekaConfig.getHotelCodeHotelIdMap().get(BookingUtils.getHotelCodeByBookingId(result));
		return Objects.nonNull(hotelID) ? hotelID.toString() : Constants.DEFAULT_DATA_SOURCE;

	}

	/**
	 * Datasource lookup key for QR_code
	 * @param bookingId
	 * @return
	 */
	public String getDataSourceForQRCode(String bookingId){
		UUID hotelID = masterEurekaConfig.getHotelCodeHotelIdMap().get(BookingUtils.getHotelCodeByBookingId(bookingId));
		return Objects.nonNull(hotelID) ? hotelID.toString() : Constants.DEFAULT_DATA_SOURCE;

	}



	/**
	 * Datasource lookup key for hotel
	 * @param url
	 * @return
	 */
	public String getDataSourceForHotel(String url){
		int index=url.lastIndexOf("hotel");
		int startIndex=url.indexOf('/', index)+1;
		int endIndex=url.indexOf('/', startIndex);
		String result = (endIndex == -1) ? url.substring(startIndex ) : url.substring(startIndex, endIndex);
		return !StringUtils.isEmpty(result) ? result : Constants.DEFAULT_DATA_SOURCE;

	}
}
