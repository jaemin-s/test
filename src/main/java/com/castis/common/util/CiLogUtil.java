package com.castis.common.util;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CiLogUtil {

	public static String hiddenRequest(String body, LinkedHashMap<String, Object> modelMap) {
		if(body==null || body.isEmpty()) {return body;}
		if(modelMap==null || modelMap.isEmpty()) {return body;}
		
		String replaced = body;
		
		
		for(String key : modelMap.keySet()) {
			
			if(modelMap.get(key)==null) {continue;}
			
			String value = (String)(modelMap.get(key));
			 Pattern pattern = Pattern.compile(key);
		     Matcher matcher = pattern.matcher(replaced);
		     if(matcher.find()) {
		    	 String str = matcher.group(1).replaceAll("\\w","*");
		    	 value = String.format(value, str);
		    	 replaced =  matcher.replaceAll(value);
		     }
		}
		
		return replaced;
	}

	public static String hiddenString(String value) {
		if(value==null || value.isEmpty()) {return value;}
		
		String str = value;
		
		return str.replaceAll("\\w","*");
	}

}
