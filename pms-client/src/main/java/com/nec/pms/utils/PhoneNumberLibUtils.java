package com.nec.pms.utils;

import org.springframework.stereotype.Service;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Service
public class PhoneNumberLibUtils {
private static Logger logger = LoggerFactory.getLogger(PhoneNumberLibUtils.class);
	
	private PhoneNumberLibUtils() {}
	
	/**
	 * 
	 * @param numberToParse
	 * @param regionCode
	 * @return
	 */
	public static PhoneNumber getNumber(String numberToParse, String regionCode) {
		if(Strings.isEmpty(numberToParse)) {
			try {
				return PhoneNumberUtil.getInstance().parse(numberToParse, regionCode);
			} catch (NumberParseException e) {
				logger.error("Error on parse phone number " ,e.getMessage());
			}		
		}
		return null;
	}

}
