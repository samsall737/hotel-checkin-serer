package com.nec.hotels.utils;

public class Constants {

	public static final long MILLIS_IN_A_DAY = 86400000;
	public static final String ADMIN_TOKEN = "tyfkfvchjbvhjubuybckjsebifuvse";
	public static final int DEFAULT_MAX_ADULTS = 2;
	public static final int DEFAULT_MAX_CHILDREN = 2;
	public static final String UPLOAD_TYPE_DOCUMENT = "document";
	public static final String ADMIN_ROLE = "admin";
	public static final String SUPER_ADMIN_ROLE = "super-admin";
	public static final String HEADER_ACCESS_TOKEN = "Access-Token";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_USERID = "X-UserID";
	public static final String HEADER_USER_AGENT = "User-Agent";
	public static final String HEADER_AUTHORIZATION_REFRESH = "Refresh-Authorization";
	public static final String HEADER_ACCESS_REFRESH_TOKEN_ = "Access-Refresh-Token";

	private Constants() {
		throw new IllegalStateException("Utility class");
	}
	
	
	public static final int PRECHECKIN_STEP_FIRST = 1;
	public static final int PRECHECKIN_STEP_SECOND = 2;
	public static final int PRECHECKIN_STEP_THIRD = 3;
	public static final int PRECHECKIN_STEP_FOURTH = 4;
	public static final String DEFAULT_DATA_SOURCE = "91d15aa5-4cd6-4b1d-bc68-df4e145629f3";
	
	public static final String EMAIL_NAME = "name";
	public static final String EMAIL_BOOKING_ID = "booking_id";
	public static final String EMAIL_PRE_CHECKIN_LINK = "pre_checkin_link";
	public static final String EMAIL_HOTEL_NAME = "hotel_name";
	public static final String EMAIL_HOTEL_CITY = "hotel_city";
	public static final String EMAIL_ARRIVAL_DATE = "arrival_date";
	public static final String EMAIL_DEPARTURE_DATE_ = "departure_date";
	public static final String PNG = ".png";


	
}
