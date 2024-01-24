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

import com.google.gson.JsonPrimitive;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CiStringUtil {

	public static boolean is_empty(Object obj){
		
		if(obj == null) {
			return true;
		}else if(obj instanceof Number){
			return false;
		}else if(obj instanceof String){
			return ((String)obj).isEmpty()? true : false;
		}else if(obj instanceof Collection){
			return ((Collection<?>)obj).isEmpty()? true : false;
		}else if(obj instanceof Map<?,?>){
			return ((Map<?,?>)obj).isEmpty()? true : false;
		}else if(obj instanceof java.util.Date){
			return false;
		}else if(obj instanceof JsonPrimitive){
			String str = ((JsonPrimitive)obj).getAsString();
			
			return (str.isEmpty())? true : false;
		}
		
		return (obj.toString().isEmpty())? true : false;
	}

	public static boolean is_not_empty(Object obj){
		return is_empty(obj)? false : true;
	}
	
	public static String sub_string(Object obj, int length, String tailFix) {

		if(obj==null) { return null;}
		
		String message =  "";
		if(obj instanceof String) {message = (String)obj;}
		else {  message =  String.format("%s", obj); }

		if(is_not_empty(tailFix)) {
			return (message.length() <= length)? message :  message.substring(0, length) + tailFix;
		}
		
		return (message.length() <= length)? message :  message.substring(0, length);
	}

	public static String sub_string(Object obj, int length) {
		return sub_string(obj, length, null); 
	}

	@SuppressWarnings("rawtypes")
	public static String implode(Object value, String sep) {
		if(value==null || is_empty(value)) {
			return "";
		}

        StringBuilder stringBuilder = new StringBuilder();
        
		if(value instanceof String[]) {
			String[] obj = (String[]) value;
			int i = 0;
			while(i < obj.length){
				if(stringBuilder.length()>0) {	
					stringBuilder.append(sep);
				}
				stringBuilder.append(obj[i]);
				++i;
			}
		}else if(value instanceof Collection<?>) {

			Iterator<?> itr = ((Collection) value).iterator();
			while(itr.hasNext()) {
				String objstr = (String)itr.next();
				if(stringBuilder.length()>0){
					stringBuilder.append(sep);
				}
				stringBuilder.append(objstr);
			}
		}
		
		return stringBuilder.toString();
	}

	public static boolean is_not_long(Object obj) {
		
		if(obj == null) { return true; }

		try {
			if(obj instanceof Long ){
				return false;
			}else if(obj instanceof String){
				Long.parseLong((String) obj);
				return false; 
			}
		} catch (Exception e) {
			return true;
		}
			
		return true;
	}

	public static boolean is_not_long(Object obj, long min) {
		
		if(obj == null) { return true; }

		try {
			if(obj instanceof Long && ((Long)obj).longValue() >= min){
				return false;
			}else if(obj instanceof String && (Long.parseLong((String) obj) >= min)){
				return false; 
			}else if(obj instanceof Number && (((Number) obj).longValue() >= min)){
				return false; 
			}
		} catch (Exception e) {
			return true;
		}
			
		return true;
	}

	public static boolean is_min_long(String str, long min) {
		
		if(is_empty(str)) {return false;}
		
		try {
			if(Long.parseLong(str) < min)
				return false;
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	public static boolean is_min_int(String str, int min) {
		 
		if(is_empty(str)) {return false;}
		
		try {
			if(Integer.parseInt(str) < min)
				return false;
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	public static boolean is_long(String str) {
		try { 
			Long.parseLong(str); 
		}catch (Exception e) {
			return false; 
		}
		
		return true;
	}

	public static boolean is_in_list(Object object, String[] paramList) {
		if(is_empty(object) || paramList==null || paramList.length < 1)
			return false;
		
		for(String tempStr:paramList){
			if(((String)object).equalsIgnoreCase(tempStr))
				return true;
		}
		
		return false;
	}

	public static boolean is_in_list(Object object, List<String> paramList) {
		if(is_empty(object) || is_empty(paramList))
			return false;
		
		for(String tempStr:paramList){
			if(((String)object).equalsIgnoreCase(tempStr))
				return true;
		}
		
		return false;
	}

	public static boolean is_not_in_list(Object object, List<String> paramList) {
		if(is_empty(object) || is_empty(paramList))
			return true;
		
		for(String tempStr:paramList){
			if(((String)object).equalsIgnoreCase(tempStr))
				return false;
		}
		
		return true;
	}

	public static boolean is_not_Integer(Object parameter) {
		
		if(parameter == null) { return true; }

		try {
			if(parameter instanceof Integer ){
				return false;
			}else if(parameter instanceof String){
				Integer.parseInt((String) parameter);
				return false; 
			}
		} catch (Exception e) {
			return true;
		}
			
		return true;
	}
	
	public static boolean is_not_Integer(Object parameter, int min) {
		
		if(parameter == null) { return true; }

		try {
			if(parameter instanceof Long && ((Integer)parameter).intValue() >= min){
				return false;
			}else if(parameter instanceof String && (Integer.parseInt((String) parameter) >= min)){
				return false; 
			}else if(parameter instanceof Number && (((Number) parameter).intValue() >= min)){
				return false; 
			}
		} catch (Exception e) {
			return true;
		}
			
		return true;
	}

	public static boolean is_not_Integer(Object parameter, boolean mandatory) {

		
		if(parameter == null) { return mandatory ? true : false; }

		try {
			if(parameter instanceof Integer ){
				return false;
			}else if(parameter instanceof String){
				Integer.parseInt((String) parameter);
				return false; 
			}
		} catch (Exception e) {
			return true;
		}
			
		return true;
	}
}
