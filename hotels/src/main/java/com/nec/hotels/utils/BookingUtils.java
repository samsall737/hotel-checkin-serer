package com.nec.hotels.utils;


public class BookingUtils {

    public static String generateBookingId(String hotelCode, String confirmationNumber){
    	return hotelCode+'-'+confirmationNumber;
    }

    public static String getHotelCodeByBookingId(String bookingId){
    	String[] strings=bookingId.split("-");
    	return strings.length>0 ? strings[0] : "";
    }
    
    public static String getConfirmationNumber(String bookingId){
    	String[] strings=bookingId.split("-");
    	return strings.length>1 ? strings[1] : "";
    }
}
