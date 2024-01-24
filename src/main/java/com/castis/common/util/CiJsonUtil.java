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

import com.google.gson.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CiJsonUtil {
	static final Log		log = LogFactory.getLog( CiJsonUtil.class );
	public static final String CHAR_SET_NAME_UTF_8 = "UTF-8";
	
	
	//java Object를 json으로 변환
	public static String objectToJsonForJSPEval(Object obj)
	{
		String jsonString = objectToJson(obj);
		if(jsonString != null && !jsonString.isEmpty())
			jsonString = jsonString.replaceAll("\"", "\\\\\"");
		return jsonString;
	}
	
	//java Object를 json으로 변환
	public static String objectToJson(Object obj)
	{
		String jsonString = null;
		GsonBuilder gb = new GsonBuilder();
		gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Gson gson = gb.create();
		jsonString = gson.toJson(obj);
		
		return jsonString;
	}
	
	
	//json을 java Object로 변환
	public static <T> T jsonToObject(String jsonString, Class<T> outputClass) {
		
		if(jsonString == null || jsonString.isEmpty()) { return null;}
		
		GsonBuilder gb = new GsonBuilder();

		gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Gson gson = gb.create();
		return gson.fromJson(jsonString, outputClass);
		
	}
	
	public static Object jsonToObject(InputStream iStream, Class<?> outputClass) {
		return jsonToObject(iStream, outputClass, CHAR_SET_NAME_UTF_8);
	}
	
	public static Object jsonToObject(InputStream iStream, Class<?> outputClass, String charsetName) {
		Object resultObject = null;
		if(iStream != null) {	
			InputStreamReader reader = null;
			try {
				reader = new InputStreamReader(iStream, charsetName);
			} catch (Exception e) {
				log.error(e);
				log.debug("", e);
				return null;
			}
			
			GsonBuilder gb = new GsonBuilder().registerTypeAdapter(
			Date.class, new WebServiceDateDeserializer());
			gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gb.create();
			resultObject = gson.fromJson(reader, outputClass);
		}
		return resultObject;
	}
	 
	public static Object jsonToObject(InputStream iStream, Type type) {
		return jsonToObject(iStream, type, CHAR_SET_NAME_UTF_8);
	}
	public static Object jsonToObject(InputStream iStream, Type type, String charsetName) {
		Object resultObject = null;
		if(iStream != null) {	
			InputStreamReader reader = null;
			try {
				reader = new InputStreamReader(iStream, charsetName);
			} catch (Exception e) {
				log.error(e);
				log.debug("", e);
				return null;
			}
			
			GsonBuilder gb = new GsonBuilder();
			gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gb.create();
			resultObject = gson.fromJson(reader, type);
		}
		return resultObject;
	}
	
	
	public static void jsonArrayToList(InputStream iStream, Class<?> outputClass, List<Object> result) {
		jsonArrayToList(iStream, outputClass, result, CHAR_SET_NAME_UTF_8);
	}
	
	public static void jsonArrayToList(InputStream iStream, Class<?> outputClass, List<Object> result, String charsetName) {
		if(iStream != null) {	
			InputStreamReader reader = null;
			try {
				//reader = new InputStreamReader(iStream, "euc-kr");
				reader = new InputStreamReader(iStream, charsetName);
			} catch (Exception e) {
				log.error(e);
				log.debug("", e);
				e.printStackTrace();
			}
			
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = parser.parse(reader).getAsJsonArray();
			GsonBuilder gb = new GsonBuilder();
			gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gb.create();
			
			for(JsonElement je:jsonArray) {
				result.add(gson.fromJson(je, outputClass));
			}
		}
	}

	public static <T> List<T> jsonArrayTo(JsonArray jsonArray, Class<T> outputClass) {
		
		if(jsonArray==null) {return null;}
		
		GsonBuilder gb = new GsonBuilder();
		gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Gson gson = gb.create();
		
		List<T> list = new ArrayList<T>();
		for(JsonElement je:jsonArray) {
			list.add(gson.fromJson(je, outputClass));
		}
		
		return list;
	}

	public static <T> T jsonTo(JsonElement je, Class<T> outputClass) {
		
		if(je==null) {return null;}
		else if(je instanceof com.google.gson.JsonNull){return null;}
		
		GsonBuilder gb = new GsonBuilder();
		gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Gson gson = gb.create();
		return gson.fromJson(je, outputClass);
	}
}
