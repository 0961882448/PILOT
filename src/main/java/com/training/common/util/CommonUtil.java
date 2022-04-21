package com.training.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * Convert date to string with input format
	 * 
	 * @param inputDate
	 * @param dateFormat
	 * @return outputDateStr
	 */
	public static String cvDateToString(Date inputDate, String dateFormat) {

		String outputDate = StringUtils.EMPTY;
		if (inputDate != null) {
			SimpleDateFormat dateFormatOutput = new SimpleDateFormat(dateFormat);
			outputDate = dateFormatOutput.format(inputDate);
		}
		
		return outputDate;
	}
	
	/**
	 * Convert date to string with input format and locale
	 * 
	 * @param inputDate
	 * @param dateFormat
	 * @return outputDateStr
	 */
	public static String cvDateToString(Date inputDate, String dateFormat, Locale locale) {

		String outputDate = StringUtils.EMPTY;
		if (inputDate != null) {
			SimpleDateFormat dateFormatOutput = new SimpleDateFormat(dateFormat, locale);
			outputDate = dateFormatOutput.format(inputDate);
		}
		
		return outputDate;
	}


	/**
	 * Convert string to date
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return Date
	 */
	public static Date cvStringToDate(String dateStr, String dateFormat) {

		Date outputDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			outputDate = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			LOGGER.error("Error when converting data: " + e.getMessage());
		}
		return outputDate;
	}

	/**
	 * Read Properties
	 * 
	 * @param key
	 * @param fileName
	 * @return value
	 */
	public static String readProperties(String key, String fileName) {

		InputStream inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(fileName);
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
			return prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get value of attribute which is stored in current session from request
	 * 
	 * @return Object
	 */
	public static Object getSessionAttribute(String attrKey) {

		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		return attributes.getRequest().getSession().getAttribute(attrKey);
	}

	/**
	 * Set value of attribute into current session from request
	 * 
	 * @param attrKey
	 * @param attribute
	 */
	public static void setSessionAttribute(String attrKey, Object attrValue) {

		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		attributes.getRequest().getSession().setAttribute(attrKey, attrValue);
	}
	
	

	public static String ecryptMD5(String password) {
		// TODO Auto-generated method stub
		 return DigestUtils.md5Hex(password).toUpperCase();
	}
}