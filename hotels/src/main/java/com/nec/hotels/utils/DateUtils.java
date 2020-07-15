package com.nec.hotels.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.nec.exception.HotelCheckinException;

/**
 * 
 * @author 
 *
 */
public class DateUtils {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String MONTH_NAME_DATE_FORMAT = "d MMM yyyy";
	private static final String TIME_FORMAT = "hh:mm aaa";

	static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public static long getTodayStartDate() throws ParseException {
		LOGGER.info("==================getTodayStartDate==============");
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date today = new Date();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.parse(formatter.format(today)).getTime();
	}

	public static long getStartOfTheDay(long date, String timeZone) {
		LOGGER.info("==================getStartOfTheDay=============={} | {}", date, timeZone);
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date startDate = new Date(date);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			return formatter.parse(formatter.format(startDate)).getTime();
		} catch (ParseException e) {
			throw new HotelCheckinException(e, "Parse Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * This function is to get age from date of birth
	 */
	public static int getAge(long dateOfBirth) {
		LOGGER.info("==================getAge============== {}", dateOfBirth);
		LocalDate curDate = LocalDate.now();
		LocalDate birthDay = Instant.ofEpochMilli(dateOfBirth).atZone(ZoneId.systemDefault()).toLocalDate();
		return Period.between(birthDay, curDate).getYears();
	}

	public static String millisecondToDateWithMonthName(Long date, String timeZone) {
		LOGGER.info("==================millisecondToDateWithMonthName============== {} | {}", date, timeZone);
		DateFormat formatter = new SimpleDateFormat(MONTH_NAME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(calendar.getTime());
	}

	public static String getTimeFromDate(long date, String timeZone) {
		LOGGER.info("==================getTime============== {} | {}", date, timeZone);
		DateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(calendar.getTime());
	}

	public static Long getMillisFromTimestamp(String date) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate newDate = LocalDate.parse(date, inputFormatter);
		return newDate.toEpochDay() * Constants.MILLIS_IN_A_DAY;
	}

	public static Long getMillisFromDateAndTime(String date, String time) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate newDate = LocalDate.parse(date, inputFormatter);
		return newDate.toEpochDay() * Constants.MILLIS_IN_A_DAY;
	}
	
	public static Long getMillisFromTimestamp(String date, String pattern) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
		LocalDate newDate = LocalDate.parse(date, inputFormatter);
		return newDate.toEpochDay() * Constants.MILLIS_IN_A_DAY;
	}
	
	public static String getTimestampfromMillis(Long millis) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 
	 * @param millis
	 * @return
	 */
	public static String getTimefromMillis(Long millis) {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return formatter.format(calendar.getTime());
	}
	
	/**
	 * 
	 * @param millis
	 * @return
	 */
	public static long getSecFromTime(String formatedTime, String timezone) {
		try {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
	    Date reference;
		reference = dateFormat.parse("00:00:00");

	    Date date = dateFormat.parse(formatedTime);
	    return (date.getTime() - reference.getTime()) / 1000L;
		} catch (ParseException e) {
			LOGGER.error("error on parse date : " + e.getMessage());	
		}
		return 0;
	}
	
	/**
	 * 
	 * @param millis
	 * @return
	 */
	public static String getDatefromMillis(Long millis) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return formatter.format(calendar.getTime());
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDate(String date) {
		String[] dateArray = date.split(",");
		if(dateArray[1].trim().length() > 2) {
			return date;
		}
		return (dateArray[0] + ", 19" + dateArray[1].trim());		
	}
	
	public static int[] getDayDateYearFromTimeStamp(long millis) {
		int date[] = new int[3];
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		date[2] = calendar.get(Calendar.YEAR);
		date[1] = calendar.get(Calendar.MONTH);
		date[0] = calendar.get(Calendar.DAY_OF_MONTH);
		return date;
	}
	
	
}
