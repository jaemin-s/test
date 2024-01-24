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
package com.castis.common.model;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.util.CiHttpUtil;
import com.castis.common.util.CiJsonUtil;
import com.castis.common.util.CiLogger;
import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CiRequest {
	
	private int code = CiResultCode.SUCCESS;
	private String message;
	
	protected StringBuffer stringBuffer = new StringBuffer();
	protected JsonObject requestObj = null;
	protected Map<String, String[]> requestMap = null;
	protected String method = "";
	protected String body = "";
	private String queryString;
	protected String remoteAddr="";
	protected HttpServletRequest httpServletRequest = null;

	public CiRequest() {
		super();
	}

	public CiRequest(String body) throws Exception {
		super();
		this.setBody(body);
		this.setMethod("POST");
	}

	public CiRequest(CiRequest request) {
		this.setStringBuffer(request.getStringBuffer());
		this.setCode(request.getCode());
		this.setMessage(request.getMessage());
		this.setRequestObj(request.getRequestObj());
		this.setRequestMap(request.getRequestMap());
		this.setMethod(request.getMethod());
		this.setBody(request.getBody());
		this.setHttpServletRequest(request.getHttpServletRequest());
		this.setRemoteAddr(request.getRemoteAddr());
	}

	public CiRequest(HttpServletRequest httpRequest) throws Exception {
		
		this.setMethod(httpRequest.getMethod());
		this.setRemoteAddr(CiHttpUtil.getClientIpAddr(httpRequest));
		this.setHttpServletRequest(httpRequest);
		
		if("GET".equalsIgnoreCase(httpRequest.getMethod())) { acquire_GET(httpRequest);
		}else { acquire_POST(httpRequest); }
		if(code!=CiResultCode.SUCCESS) { throw new CiException(getCode(),getMessage()); }
		
	}
	public CiRequest(HttpServletRequest httpRequest, String requestBody) throws Exception {
		
		this.setMethod(httpRequest.getMethod());
		this.setHttpServletRequest(httpRequest);
		this.setRemoteAddr(CiHttpUtil.getClientIpAddr(httpRequest));
		
		if(requestBody!=null && requestBody.isEmpty()==false) {  this.setBody(requestBody); }

		if(code!=CiResultCode.SUCCESS) { throw new CiException(getCode(),getMessage()); }
	}
	
	public void parse() throws Exception {
		parse(getBody());
	}
	
	public void parse(String jb) throws Exception {
		
		if(jb==null){
			setCode(CiResultCode.PARSING_ERROR);
			setMessage("null buffer");
			return;
		}
		else if(jb.isEmpty()){
			return;
		}
		
		Gson gson = new GsonBuilder().create();
		
		try {
			
			requestObj = gson.fromJson(jb, JsonObject.class);
			
		}catch (Exception e) {
			CiLogger.error(e, "Fail to parse");
			throw new CiException(CiResultCode.PARSING_ERROR, "Fail to parse");
		}
		
	}

	public void setResponse(int code, String format, Object...args) {
		this.setCode(code);
		this.setMessage(String.format(format, args));
	}

	private void acquire_GET(HttpServletRequest request) {
		
		this.requestMap = request.getParameterMap();
		this.setQueryString(request.getQueryString());
	}

	protected void acquire_POST(HttpServletRequest request) throws Exception{
		String contextType = request.getContentType();
		if(contextType!=null && contextType.startsWith("application/x-www-form-urlencoded")) {
			this.requestMap = request.getParameterMap();
			this.setQueryString(request.getQueryString());
		
		}else {

			String line = null;
			try {
				
				  BufferedReader reader = request.getReader();
				  
				  while ((line = reader.readLine()) != null) {  stringBuffer.append(line);}
				  
				  this.setBody(getStringBuffer().toString());
			  
			} catch (Exception e) { 
				
				CiLogger.error("request read error, exception(%s)", e.getMessage());
				throw new CiException(CiResultCode.PARSING_ERROR, "request read error");
			}
		}
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StringBuffer getStringBuffer() {
		return stringBuffer;
	}

	public void setStringBuffer(StringBuffer stringBuffer) {
		this.stringBuffer = stringBuffer;
	}

	public JsonObject getRequestObj() {
		return requestObj;
	}

	public void setRequestObj(JsonObject requestObj) {
		this.requestObj = requestObj;
	}

	public Map<String, String[]> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String[]> requestMap) {
		this.requestMap = requestMap;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Object getParameter(String key) {
		
		if("GET".equalsIgnoreCase(getMethod())) { return getParameter_get(key); }
		
		return getParameter_post(key);
	}

	private Object getParameter_post(String key) {
		
		if(this.requestObj==null) {
			if(this.requestMap!=null) {
				String[] ret = this.requestMap.get(key);
				if(ret==null) { return null;
				}else if(ret instanceof String[] && ret.length > 0) { return ret[0]; }
			}
			return null; 
		}
		
		Object obj = this.requestObj.get(key);
		if(obj instanceof JsonPrimitive) {
			JsonPrimitive prim=((JsonPrimitive)obj);
			if(prim.isString()) { return prim.getAsString();
			}else if(prim.isBoolean()) { return prim.getAsBoolean();
			}else if(prim.isNumber()) { return prim.getAsNumber();
			}else if(prim.isJsonNull()) { return null; }
		}
		
		return obj;
	}

	protected Object getParameter_get(String key) {
		
		if(this.requestMap == null) { return null; }
		
		String[] ret = this.requestMap.get(key);
		if(ret==null) { return null;
		}else if(ret instanceof String[] && ret.length > 0) { return ret[0]; }
		
		return null;
	}

	public Object getParameters(String key) {
		
		if("GET".equalsIgnoreCase(getMethod())) { return getParameters_get(key); }
		
		return getParameter_post(key);
	}

	protected Object getParameters_get(String key) {
		
		if(this.requestMap == null) { return null; }
		
		String[] ret = this.requestMap.get(key);
		if(ret==null) { return null; }
		
		return ret;
	}

	public Long getLongValue(String key) {
		
		Object obj = getParameter(key);
		
		if(obj==null) {return null;}
		
		try {

			
			if(obj instanceof Number) {
				return ((Number) obj).longValue();
			}else if(obj instanceof String) {
				return Long.parseLong((String) obj);
			} else if(obj instanceof JsonPrimitive) {
				return CiJsonUtil.jsonTo((JsonPrimitive)obj, Long.class);
			}else if(obj instanceof JsonObject) {
				return CiJsonUtil.jsonTo((JsonObject)obj, Long.class);
			}
			
		}catch(Exception e) {
			return null;
		}
		
		return null;
	}

	public <T> List<T> getList(String key, Class<T> outputClass) {
		
		if("GET".equalsIgnoreCase(getMethod())) {
			return getList_get(key, outputClass);
		}
		
		return getList_post(key, outputClass);
	}
	public <T> List<T> getList_get(String key, Class<T> outputClass) {
		
		ArrayList<T> cols = new ArrayList<T>();
		if(this.requestMap == null) { return null; }
		
		String[] ret = this.requestMap.get(key);
		if(ret==null) { return null; }
		
		for(String str : ret) { 
			T obj = convert(str, outputClass);
			if(obj!=null)
				cols.add(obj); 
			}
		
		return cols;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T convert(String input, Class<T> outputClass) {
		
		if(input==null || input.isEmpty()) {return null;}
		
		try {
			T obj = outputClass.newInstance();
			if(obj instanceof String) {
				return (T) input;
			}else if(obj instanceof Integer) {
				return (T) new Integer(input);
			}else if(obj instanceof Long) {
				return (T) new Long(input);
			}else if(obj instanceof Boolean) {
				return (T) new Boolean(input);
			}else if(obj instanceof Double) {
				return (T) new Double(input);
			}else if(obj instanceof Short) {
				return (T) new Short(input);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> List<T> getList_post(String key, Class<T> outputClass) {
		ArrayList<T> cols = new ArrayList<T>();
		if(this.requestObj==null) {
			return null;
		}
		
		try {
			
			Object obj = this.requestObj.get(key);
			
			if(obj instanceof JsonArray) {
				return CiJsonUtil.jsonArrayTo((JsonArray) obj, outputClass);
			}else if(obj instanceof JsonPrimitive) {
				cols.add(CiJsonUtil.jsonTo((JsonPrimitive)obj, outputClass));
				return cols;
			}else if(obj instanceof JsonObject) {
				cols.add(CiJsonUtil.jsonTo((JsonObject)obj, outputClass));
				return cols;
			}
			
		}catch(Exception e) {
			return null;
		}
		
		return null;
	}

	public <T> T getValue(String key, Class<T> outputClass) { return getParameter(key, outputClass); }

	@SuppressWarnings("unchecked")
	public <T> T getParameter(String key, Class<T> outputClass) {

		if(this.requestObj==null) {
			if(this.requestMap!=null) {
				String[] ret = this.requestMap.get(key);
				if(ret instanceof String[] && ret.length > 0) { 
					JsonPrimitive obj = new JsonPrimitive(ret[0]);
					return CiJsonUtil.jsonTo(obj, outputClass);
				}
			}
			return null; 
		}
		
		
		try {
				Object obj = this.requestObj.get(key);
				if(obj instanceof JsonNull) { return null;}
				else if(obj instanceof JsonPrimitive) { return CiJsonUtil.jsonTo((JsonPrimitive)obj, outputClass);}
				else if(obj instanceof JsonObject) { return CiJsonUtil.jsonTo((JsonObject)obj, outputClass); }
				return (T) obj;
		}catch(Exception e) { return null; }
	}
	
	public String getStringValue(String key) { return getValue(key, String.class); }
	
	public Boolean getBooleanValue(String key) { return getValue(key, Boolean.class); }
	
	public Integer getIntValue(String key) { return getValue(key, Integer.class); }
	
	public Date getDateValue(String key) throws ParseException 
	{
		String from = getValue(key, String.class);
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		return transFormat.parse(from);
	}

	public List<String> getList(String key) { return getList(key, String.class); }

	public int getInt(String key, int replace) {
		
		Object obj = getParameter(key);
		
		if(obj==null) {return replace;}
		
		try {

			
			if(obj instanceof Number) { return ((Number) obj).intValue();
			}else if(obj instanceof String) { return Integer.parseInt((String) obj);
			} else if(obj instanceof JsonPrimitive) {
				Integer value =  CiJsonUtil.jsonTo((JsonPrimitive)obj, Integer.class);
				return (value!=null) ? value.intValue() : replace;
			}else if(obj instanceof JsonObject) {
				Integer value =   CiJsonUtil.jsonTo((JsonObject)obj, Integer.class);
				return (value!=null) ? value.intValue() : replace;
			}
			
		}catch(Exception e) { return replace; }
		
		return replace;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public CiResponse makeCiResponse() {
		CiResponse response = new CiResponse();
		return response;
	}

	public CiResponse makeCiResponse(int code) {
		CiResponse response = new CiResponse(code);
		return response;
	}

	public CiResponse makeCiResponse(int code, String message) {
		CiResponse response = new CiResponse(code, message);
		return response;
	}

	public CiResponse makeCiResponse(CiResponse ciResponse) {
		return new CiResponse(ciResponse);
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public void validate() throws Exception {
		
	}
	

}
