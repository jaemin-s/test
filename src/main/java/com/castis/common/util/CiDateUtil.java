/*
 *     Copyright (c) 2021 chonkk@castis.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 2021-05-03
 */
package com.castis.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CiDateUtil {

	public static String first_day_of_month(Date date, String pattern) {

		if(date==null) {
			return "";
		}
		
		String str = "yyyy-MM-dd";
		if(pattern!=null) {
			str = pattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(str);
		Calendar cal = Calendar.getInstance();
		
		if(date!=null) {
			cal.setTime(date);
		}
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return format.format(cal.getTime());
	}

	public static String first_day_of_month(String pattern) {
		
		return first_day_of_month(new Date(), pattern);
	}
	public static String to_string(String dateFormat) {
		return to_string(new Date(), dateFormat);
	}

	/**
	 * format : "yyyy-MM-dd HH:mm:ss.SSS"
	 * */
	public static String to_string(Date date, String pattern) {

		if(date==null) {
			return "";
		}

		String str = "yyyy-MM-dd";
		if(pattern!=null) {
			str = pattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(str);
		Calendar cal = Calendar.getInstance();
		
		if(date!=null) {
			cal.setTime(date);
		}

		return format.format(cal.getTime());
	}

	public static String to_string(Long millis, String pattern) {

		if(millis==null || millis < 0) {
			return "";
		}

		String str = "yyyy-MM-dd";
		if(pattern!=null) {
			str = pattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(str);
		Date date = new Date(millis);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return format.format(cal.getTime());
	}

	public static String first_day_of_month(Long millis, String pattern) {

		if(millis==null || millis < 0) {
			return "";
		}
		
		String str = "yyyy-MM-dd";
		if(pattern!=null) {
			str = pattern;
		}
		SimpleDateFormat format = new SimpleDateFormat(str);
		Date date = new Date(millis);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return format.format(cal.getTime());
	}

	public static Date to_date(Long millis) {

		if(millis==null || millis < 0) {
			return null;
		}

		return new Date(millis);
	}

	public static Date add(int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

	public static Date add(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Date subtract(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, (amount*-1));
		return cal.getTime();
	}

	public static Date subtract(int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, (amount*-1));
		return cal.getTime();
	}

	public static String date2String(long millis, String pattern) {

		if(millis < 0 || pattern==null || pattern.isEmpty()) { 
			return ""; 
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format( new Date(millis));
	}

	public static Date after_date(Date first, Date second) {
		
		if(first==null) {
			return second;
		}else if(second==null) {
			return first;
		}
		
		return first.after(second)? first : second;
	}

	public static Date before_date(Date first, Date second) {
		
		if(first==null) {
			return second;
		}else if(second==null) {
			return first;
		}
		
		return first.before(second)? first : second;
	}

	public static Collection<? extends String> get_periodlist_date(Date startDate, Date endDate) {

		List<String> resultList = new ArrayList<String>();
		if(startDate==null || endDate==null) { return resultList; }
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			Calendar yesterday = Calendar.getInstance();

			c1.setTime(startDate);
			c2.setTime(endDate);
			yesterday.setTime(new Date());
			yesterday.add(Calendar.DAY_OF_MONTH, -1);

			if (c2.compareTo(yesterday) > 0) {
				c2 = yesterday;
			}
			while (c1.compareTo(c2) != 1) {

				resultList.add(df.format(c1.getTime()));
				c1.add(Calendar.DATE, 1);
			}

		return resultList;
	}

	/**
	 * pattern : "yyyy-MM-dd HH:mm:ss.SSS"
	 * */
	public static String convert(String str, String inPattern, String outPattern) {
		if(CiStringUtil.is_empty(str) ||CiStringUtil.is_empty(inPattern)||CiStringUtil.is_empty(outPattern)) {
			return "";
		}
		DateFormat in = new SimpleDateFormat(inPattern);
		DateFormat out = new SimpleDateFormat(outPattern);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(in.parse(str));
		} catch (ParseException e) {
			return "";
		}
		return out.format(cal.getTime());
	}

	public static Integer valueof(String date, String pattern, int field) throws Exception{
		DateFormat in = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(in.parse(date));
		return cal.get(field);
	}

	/**
	 * pattern : "yyyy-MM-dd HH:mm:ss.SSS"
	 * */
	public static java.sql.Date next_pay_date(String date, String pattern, int pay_day) throws ParseException {
		DateFormat in = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(in.parse(date));
		
		cal.add(Calendar.MONTH, 1);
		
		if(pay_day == cal.get(Calendar.DAY_OF_MONTH)) {
			return new java.sql.Date(cal.getTimeInMillis());
		}
		
		int day_of_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(pay_day < day_of_month) {
			day_of_month = pay_day;
		}
		
		cal.set(Calendar.DAY_OF_MONTH, day_of_month);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static Date to_date(String date, String pattern) throws Exception{
		DateFormat in = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(in.parse(date));
		return cal.getTime();
	}

	public static int valueof(Date date, int field) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(field);
	}

	public static java.sql.Date next_pay_date(Date date, int pay_day) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.MONTH, 1);
		
		if(pay_day == cal.get(Calendar.DAY_OF_MONTH)) {
			return new java.sql.Date(cal.getTimeInMillis());
		}
		
		int day_of_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(pay_day < day_of_month) {
			day_of_month = pay_day;
		}
		
		cal.set(Calendar.DAY_OF_MONTH, day_of_month);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static java.sql.Date curr_pay_date() {
		Calendar cal = Calendar.getInstance();
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static java.sql.Date pay_date(int amount) {
		return pay_date(Calendar.DAY_OF_MONTH, amount);
	}

	public static java.sql.Date pay_date(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		if(amount!=0) {
			cal.add(field, amount);
		}
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static java.sql.Date parseSQLDate(String date, String pattern) throws ParseException {
		if(date==null || date.isEmpty()) {return null;}
		if(pattern==null || pattern.isEmpty()) {return null;}

		DateFormat in = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(in.parse(date));
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static Date today_00() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}

	public static Date yesterday_00() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
	
	public static final synchronized String getyyyyMMddHHmmss(){
	    SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	    return yyyyMMddHHmmss.format(new Date());
	}
}
