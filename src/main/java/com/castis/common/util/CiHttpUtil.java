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

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.google.gson.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.client5.http.ConnectTimeoutException;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.apache.http.NameValuePair;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CiHttpUtil {
	
	private static Log log = LogFactory.getLog(CiHttpUtil.class);
	
	final public static String HTTP_GET_METHOD	= "GET";
	final public static String HTTP_POST_METHOD	= "POST";
	final public static String HTTP_PUT_METHOD	= "PUT";

	final public static String HTTP_DELETE_METHOD	= "DELETE";
	final public static String HTTP_POST_BODY_X_WWW_FORM_URLENCODE_CONTENT_TYPE	= "X-WWW-FORM-URLENCODE";

	final public static String HTTP_TEXT_PLAIN = "TEXT_PLAIN";
	
	public static String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader( new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				} 
			} finally {
					is.close(); 
			}
			return writer.toString(); 
		} else {
			return ""; 
		} 
	}

	public static <T> T getObjFromJson(InputStream iStream, Class<T> outputClass)
	{	
		JsonObject jsonObject = null;
		T resultObject = null;
		if(iStream != null) {				
			GsonBuilder gb = new GsonBuilder();
			gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gb.create();
			InputStreamReader reader = null;
			try {
				reader = new InputStreamReader(iStream, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(),e);
			}

			jsonObject = (JsonObject) new JsonParser().parse(reader);
			resultObject = gson.fromJson(jsonObject.toString(), outputClass);
		}
		
		return resultObject;
	}
	public static Object getObjFromJson(InputStream iStream, Type typeOfT)
    {   
        Object resultObject = null;
        if(iStream != null) {               
            GsonBuilder gb = new GsonBuilder();
            gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Gson gson = gb.create();
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(iStream, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(),e);
            }
            resultObject = gson.fromJson(reader, typeOfT);
        }
        return resultObject;
    }
	public static <T> T getObjFromKeyFromJson(InputStream iStream, Class<T> outputClass, String key)
	{	
		JsonObject jsonObject = null;
		T resultObject = null;
		if(iStream != null) {				
			GsonBuilder gb = new GsonBuilder()
			.registerTypeAdapter(Date.class, new WebServiceDateDeserializer())
			;
			gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gb.create();
			InputStreamReader reader = null;
			try {
				reader = new InputStreamReader(iStream, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(),e);
			}
			jsonObject = (JsonObject) new JsonParser().parse(reader);
//			resultObject = jsonObject.get(key);
			if(jsonObject.get(key)!=null) { resultObject = gson.fromJson((jsonObject.get(key)).toString(), outputClass);}
		}
		
		return resultObject;
	}
	public static Object getObjFromKeyFromJson(InputStream iStream, Type typeOfT, String key)
    {   
        JsonObject jsonObject = null;
        Object resultObject = null;
        if(iStream != null) {               
            GsonBuilder gb = new GsonBuilder()
            .registerTypeAdapter(Date.class, new WebServiceDateDeserializer())
            ;
            gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Gson gson = gb.create();
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(iStream, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(),e);
            }
            jsonObject = (JsonObject) new JsonParser().parse(reader);
            resultObject = jsonObject.get(key);
            resultObject = gson.fromJson(resultObject.toString(), typeOfT);
        }
        
        return resultObject;
    }
	

  
	public static String getQuery(List<NameValuePair> params, final String charset) throws Exception 
	{
		if(params==null) {return "";}
		
		StringBuilder result = new StringBuilder();
		try{
		    boolean first = true;
	
		    for (NameValuePair pair : params)
		    {
		        if (first)
		            first = false;
		        else
		            result.append("&");
	
		        result.append(pair.getName());
		        result.append("=");
		        result.append(pair.getValue());
		    }
		}catch(Exception e){
			log.error(e);
		}
		
	    return UriUtils.encodePath(result.toString(), charset);
	}

	public static String getQueryString(Map<String, Object> paramMap, final String charset)  throws Exception{

			if(paramMap==null) {return "";}
			
			StringBuilder result = new StringBuilder();
			try{
			    boolean first = true;
			    for (Entry<String, Object> entry : paramMap.entrySet())
			    {
			        if (first)
			            first = false;
			        else
			            result.append("&");
		
			        result.append(entry.getKey());
			        result.append("=");
			        result.append(entry.getValue());
			    }
			}catch(Exception e){
				log.error(e);
			}
		    return UriUtils.encodePath(result.toString(), charset);
	}

	public static String buildFullRequestUrl(
			String scheme
			, String serverName
			, int serverPort
			, String requestURI
			, String queryString
			) {

		scheme = scheme.toLowerCase();

		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		// Only add port if not default
		if ("http".equals(scheme)) {if (serverPort != 80) { url.append(":").append(serverPort);}}
		else if ("https".equals(scheme)) {if (serverPort != 443) {url.append(":").append(serverPort);}}

		// Use the requestURI as it is encoded (RFC 3986) and hence suitable for
		// redirects.
		if(requestURI.startsWith("/")) {
			url.append(requestURI);
		}else {

			url.append("/").append(requestURI);
		}

		if (queryString != null && queryString.isEmpty()==false) {url.append("?").append(queryString);}

		return url.toString();
	}
	
	public static HttpURLConnection getConnection(URL url, String requestMethod, String requestBody, String contentType, int connectTimeout, int readTimeout, List<NameValuePair> headers) throws Exception{
		
		if(url==null){ CiLogger.error("Null URL"); throw new CiException("Null URL"); }
		
		HttpURLConnection	urlConn = null;
		try {
					urlConn = (HttpURLConnection)url.openConnection();
					urlConn.setRequestMethod( requestMethod );
					urlConn.setDoInput( true );
					if(HTTP_POST_METHOD.equalsIgnoreCase(requestMethod) || HTTP_PUT_METHOD.equalsIgnoreCase(requestMethod)) {
						urlConn.setDoOutput( true ); 
						if(HTTP_POST_BODY_X_WWW_FORM_URLENCODE_CONTENT_TYPE.equalsIgnoreCase(contentType)) {
							urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
						}else {
							urlConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
						}
					}
					urlConn.setUseCaches( false );
					if(connectTimeout > 0) { urlConn.setConnectTimeout(connectTimeout); }
					if(readTimeout > 0) { urlConn.setReadTimeout(readTimeout); }
					if(headers!=null && headers.isEmpty()==false){ for(NameValuePair pair : headers){ urlConn.setRequestProperty(pair.getName(), pair.getValue()); } }
					if(requestBody != null)
					{
								DataOutputStream 	cgiInput = null;
								try{
									cgiInput = new DataOutputStream( urlConn.getOutputStream() );
									cgiInput.write(requestBody.getBytes("UTF-8"));
									cgiInput.flush();
								} catch(HttpHostConnectException e) {
									CiLogger.error( "External System(%s) Connection Failure(%s)", url, e.getMessage());
									throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) Connection Failure(%s)", url, e.getMessage());
								} catch(ConnectTimeoutException e) {
									CiLogger.error( "External System(%s) Connection Timeout(%s)", url, e.getMessage());
									throw new CiException(CiResultCode.BAD_GATEWAY,"External System(%s) Connection Timeout(%s)", url, e.getMessage());
								}  catch (IOException e) {
									CiLogger.error( "External System(%s) Connection Failure(%s)", url, e.getMessage());
									throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) Connection Failure(%s)", url, e.getMessage());
								}finally { try{ if( cgiInput != null )  cgiInput.close();  } catch(Exception e){ CiLogger.error(e, e.getMessage()); } }
					}
					return urlConn;
		} catch (CiException e) {
			throw e;
		} catch (SocketTimeoutException e) { 
			CiLogger.error("External System(%s) connection timeout(%s)", url, e.getMessage());
			throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) connection timeout(%s)", url, e.getMessage());
		} catch (IOException e) {
			CiLogger.error("External System(%s) connection Failure(%s)", url, e.getMessage());
		    throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) connection Failure(%s)", url, e.getMessage());
		} catch (Exception e) {
			CiLogger.error("External System(%s) connection Failure(%s)", url, e.getMessage());
			throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) connection Failure(%s)", url, e.getMessage());
		}
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T getObjFromJson(String jsonString, Class<T> outputClass) throws Exception
	{	
		T resultObject = null;
		if(jsonString != null) {
			try {
				
				if(outputClass == String.class) {
					return (T) jsonString;
				}
				
				GsonBuilder gb = new GsonBuilder().registerTypeAdapter(Date.class, new WebServiceDateDeserializer());
				gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Gson gson = gb.create();
				JsonElement jsonElement = new JsonParser().parse(jsonString);
				resultObject = gson.fromJson(jsonElement.toString(), outputClass);
				
			}catch (Exception e){
				CiLogger.warn(e, "Response parsing fail");
				throw new CiException(CiResultCode.BAD_GATEWAY, "External response parsing fail[%s]", CiStringUtil.sub_string(e.getMessage(), 256));
			}
		}
		return resultObject;
	}

	public static String get_json_body(Map<String, Object> map)  throws Exception{
		
		if(map==null) { return null;}
		GsonBuilder gb = new GsonBuilder();
		gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 

		Gson gson = gb.create();
		
		if(map.containsKey("ROOTCLASS")==true) {
			return gson.toJson(map.get("ROOTCLASS"));
		}
		
		return gson.toJson(map);
	}


	public static <T> T communicate(String url, String method, String body, String contentType, int conn_timeout, int read_timeout, Class<T> outputClass, List<NameValuePair> headers ) throws Exception {

		HttpURLConnection urlConn = null;
		T resultObject = null;
		
		try {
			
			CiLogger.info("*** send : %s, method[%s], body[%s]"
					, url
					, method
					, (body!=null)? CiStringUtil.sub_string(body, 1024, "...") : ""
					);
			
			urlConn= CiHttpUtil.getConnection(
					new URL(url)
					, method
					, body
					, contentType
					, conn_timeout
					, read_timeout
					, headers
					);
			
			if(urlConn==null){ throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) connection error", url); }
			
			int resultCode = urlConn.getResponseCode(); 
			// 딜라이브는 responseCode를 resultCode와 같은 값을 보냄 HTTP_CREATED는 201 SO가입자 있음 (HTTP_ACCEPTED: SO 미가입자, 해피콜 대상자)
			if( resultCode  == HttpURLConnection.HTTP_OK || resultCode  == HttpURLConnection.HTTP_CREATED || resultCode  == HttpURLConnection.HTTP_ACCEPTED){
				String resultStr = CiHttpUtil.convertStreamToString(urlConn.getInputStream());	
				CiLogger.info("*** recv : %s",CiStringUtil.sub_string(resultStr, 1024, "..."));
				if(resultStr!=null && resultStr.isEmpty()==false) {
					resultObject = CiHttpUtil.getObjFromJson(resultStr, outputClass);
				}
				return resultObject;
			}else if(resultCode == HttpURLConnection.HTTP_NOT_FOUND) {
				CiLogger.warn("*** recv : %s:%s", "status=404", "Not Found");
				throw new CiException(CiResultCode.BAD_GATEWAY, "%s:%s", "status=404", "Not Found");
			} else {
				String resultStr = CiHttpUtil.convertStreamToString(urlConn.getErrorStream());
				CiLogger.warn("*** recv : status=%s:%s", resultCode, resultStr);
				throw new CiException(CiResultCode.BAD_GATEWAY,"%s", CiStringUtil.sub_string(resultStr, 512,"..."));
			}
			
		} catch (CiException e) {
			throw e;
		}catch (SocketTimeoutException e) { 
			CiLogger.error("External System(%s) Receive timeout(%s)", url, e.getMessage());
			throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) Receive timeout(%s)", url, e.getMessage());
		} catch (IOException e) {
			CiLogger.error("External System(%s) Receive Failure(%s)", url, e.getMessage());
		    throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) Receive Failure(%s)", url, e.getMessage());
		} catch (Exception e) {
			CiLogger.error("External System(%s) Receive Failure(%s)", url, e.getMessage());
			throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) Receive Failure(%s)", url, e.getMessage());
		}finally{ if(urlConn!=null){ try{ urlConn.disconnect(); urlConn = null; }catch(Exception e){ } } }
	}
	

	

	public static <T> T post(
			String url
			, String body
			, String contentType
			, int conn_timeout
			, int read_timeout
			, Class<T> outputClass) throws Exception {
		return communicate(url, HTTP_POST_METHOD, body, contentType, conn_timeout, read_timeout, outputClass, null );
	}
	
	public static <T> T post_json(String url
			, Map<String, Object> bodyMap
			, String contentType
			, int conn_timeout
			, int read_timeout
			, Class<T> outputClass
			) throws Exception {
		return communicate(url, HTTP_POST_METHOD, get_json_body(bodyMap), contentType, conn_timeout, read_timeout, outputClass, null );
	}
	
	public static <T> T post_json(String scheme
			, String serverName
			, int serverPort
			, String requestURI
			, Map<String, Object> bodyMap
			, String contentType
			, int conn_timeout
			, int read_timeout
			, Class<T> outputClass
			) throws Exception {
		return communicate(
				buildFullRequestUrl(scheme, serverName, serverPort, requestURI, null)
				, HTTP_POST_METHOD, get_json_body(bodyMap), contentType, conn_timeout, read_timeout, outputClass, null );
	}
	

	public static <T> T get(String url, int conn_timeout, int read_timeout, Class<T> outputClass) throws Exception {
		
		return communicate(url, HTTP_GET_METHOD, null, null, conn_timeout, read_timeout, outputClass, null );
	}
	

	public static <T> T get(String scheme
							, String serverName
							, int serverPort
							, String requestURI
							, Map<String, Object> paramMap
							, final String charset
							, int conn_timeout
							, int read_timeout
							, Class<T> outputClass
			) throws Exception {
		
		return communicate(
				buildFullRequestUrl(scheme, serverName, serverPort, requestURI, getQueryString(paramMap, charset))
				, HTTP_GET_METHOD
				, null
				, null
				, conn_timeout
				, read_timeout
				, outputClass
				, null 
				);
	}
	
	public static String getClientIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");
	 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	 
	    return ip;
	}


}
