package com.castis.external.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {

	public static String defaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static String defaultDateFormat = "yyyy-MM-dd";
	public static String monthlyDateFormat = "yyyy-MM";
	public static String defaultTimeFormat = "HH:mm:ss";
	public static String mbsTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static String mbsReleaseDateFormat = "dd.MM.yyyy";
	public static String adsDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public static String adFileNameDateFormat = "yyyyMMddHHmmss";
	public static String adiFileNameDateFormat = "yyyyMMddHHmmss";
	public static String tsFileNameDateFormat = "yyyyMMddHHmmss";
	public static String apiLogDateFormat = "yyyyMMddHHmmss";
	public static String promoDatetimeFormat = "yyyyMMddHHmmss";
	public static String purchaseDateFormat = "yyyyMMdd";
	public static String adiDateTimeFormat = "yyyy:MM:dd'T'HH:mm:ss";


	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 현재 Date를 리턴함
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 오늘 0시를 리턴함
	 * 
	 * @return
	 */
	public static Date getCurrentDateMidnight() {
		String today = getDateStr(new Date(), "yyyyMMdd");
		return convertStrToDate(today + "000000000", "yyyyMMddHHmmssSSS");
	}

	public static void initProperties(String defaultDateTimeFormat, String defaultDateFormat,
			String defaultTimeFormat) {
		DateUtil.defaultDateTimeFormat = defaultDateTimeFormat;
		DateUtil.defaultDateFormat = defaultDateFormat;
		DateUtil.defaultTimeFormat = defaultTimeFormat;
	}

	/**
	 * 주어진 패턴 날짜형 시스템일자를 구함
	 * 
	 * @param pattern 원하는 일자 패턴(예:yyyy/MM/dd HH:mm:ss)
	 * @return 시스템 일자
	 */
	public static String getCurrentStr(String pattern) {
		return getDateStr(new Date(), pattern);
	}

	/**
	 * 주어진 Date 객체를 이용하여 주어진 패턴 날짜형의 문자열을 구함.
	 * 
	 * @param date    원하는 일자의 Date 객체
	 * @param pattern 원하는 일자 패턴
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateStr(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * Format: yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat);
		return sdf.format(date);
	}

	/**
	 * Format: yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getDateTimeStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimeFormat);
		return sdf.format(date);
	}

	/**
	 * 날짜/시각 스트링을 Date 형으로 변환한다.
	 * 
	 * @param dateTime
	 * @param dateFormat
	 * @return
	 */
	public static Date convertStrToDate(String dateTime, String dateFormat) {
		String simpleFormat = (dateFormat == null) ? defaultDateTimeFormat : dateFormat;

		SimpleDateFormat sdf = new SimpleDateFormat(simpleFormat);

		try {
			return sdf.parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date convertStrToDateExt(String dateTime, String dateFormat) throws ParseException{
		String simpleFormat = (dateFormat == null) ? defaultDateTimeFormat : dateFormat;

		SimpleDateFormat sdf = new SimpleDateFormat(simpleFormat);

		return sdf.parse(dateTime);
	}

	
	public static Date convertStrToDate(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateTimeFormat);
		try {
			return sdf.parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getUnlimitedDate() {
		return convertStrToDate("99991231000000000", "yyyyMMddHHmmssSSS");
	}

	/**
	 * dateStr이 해당 dateFormat에 맞는지 체크한다.
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static boolean validDateFormat(String dateStr, String dateFormat) {

		if (dateStr == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);

		try {
			// if not valid, it will throw ParseException
			@SuppressWarnings("unused")
			Date date = sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}
}
