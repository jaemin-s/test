package com.castis.common.util;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

@lombok.extern.slf4j.Slf4j
public class WebServiceDateDeserializer implements JsonDeserializer<Object> {
	

	@Override
	public Object deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		Object obj = null;
		try {
			if(json.isJsonNull() == false||json.isJsonPrimitive()){
					
					SimpleDateFormat sdf = null;
					Date date = new Date();
					if(json.getAsString().isEmpty() == false){
						if(json.getAsString().length() == 19 && json.getAsString().contains("-") == true){
							//'yyyy-MM-dd' 패턴의 String 인 경우.
							sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							date = sdf.parse(json.getAsString());
							
						}
						else if(json.getAsString().length() == 10 && json.getAsString().contains("-") == true){
							//'yyyy-MM-dd' 패턴의 String 인 경우.
							sdf = new SimpleDateFormat("yyyy-MM-dd");
							date = sdf.parse(json.getAsString());
							
						}else if(json.getAsString().length() == 8){
							//'yyyyMMdd' 패턴의 String 인 경우.
							sdf = new SimpleDateFormat("yyyyMMdd");
							date = sdf.parse(json.getAsString());
							
						}else if(json.getAsString().length() == 13 && isLong(json.getAsString())){
							//timestamp 인 경우
							date.setTime(json.getAsLong());
						}
						obj = date;
					}else{
						obj = date;
					}
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return obj;
	}
	
	private static boolean isLong(String str) {
		try{
			Long.parseLong(str);
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
