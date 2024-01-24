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
package com.castis.common.ssl;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.util.CiHttpUtil;
import com.castis.common.util.CiLogger;
import com.castis.common.util.CiStringUtil;
import org.apache.hc.client5.http.ConnectTimeoutException;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.apache.http.NameValuePair;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class CiHttpsUtil {
	
	final public static String SCHEME_HTTPS = "https";

	
	/**
	 * @param url
	 * @param requestMethod
	 * @param requestBody
	 * @param contentType
	 * @param connectTimeout
	 * @param readTimeout
	 * @param headers
	 * @param protocol : TLSv1.1, TLSv1.2, TLSv1, SSL, TLS
	 * @return
	 * @throws Exception
	 */
	public static HttpsURLConnection getHttpsURLConnection(URL url, String requestMethod, String requestBody, String contentType, int connectTimeout, int readTimeout
			, List<NameValuePair> headers
			, String protocol, Boolean verifyCertificate, String cacertsPath, String password, Boolean verifyHostName
			) throws Exception{
					
			if(url==null){ CiLogger.error("Null URL"); throw new CiException("Null URL"); }
					
			HttpsURLConnection	urlConn = null;
			try {
						urlConn = (HttpsURLConnection)url.openConnection();
						
						urlConn.setHostnameVerifier(new CiHostnameVerifier(verifyHostName));
						
						SSLContext sc = SSLContext.getInstance(protocol==null? "TLS" : protocol);
			            sc.init(null, new javax.net.ssl.TrustManager[]{new CiX509TrustManager(verifyCertificate, cacertsPath, password)}, new java.security.SecureRandom());
			            urlConn.setSSLSocketFactory(sc.getSocketFactory());
						
						urlConn.setRequestMethod( requestMethod );
						urlConn.setDoInput( true );
						if(CiHttpUtil.HTTP_POST_METHOD.equalsIgnoreCase(requestMethod) || CiHttpUtil.HTTP_PUT_METHOD.equalsIgnoreCase(requestMethod) ) {
							urlConn.setDoOutput( true ); 
							if(CiHttpUtil.HTTP_POST_BODY_X_WWW_FORM_URLENCODE_CONTENT_TYPE.equalsIgnoreCase(contentType)) {
								urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
							} else if (CiHttpUtil.HTTP_TEXT_PLAIN.equalsIgnoreCase(contentType)) {
								urlConn.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
							} else {
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

	public static <T> T communicate(String scheme, String url, String method, String body, String contentType, int conn_timeout, int read_timeout
			, Class<T> outputClass, List<NameValuePair> headers
			, String protocol, Boolean verifyCertificate, String cacertsPath, String password, Boolean verifyHostName
			) throws Exception {
		
		if(SCHEME_HTTPS.equalsIgnoreCase(scheme) == false) { return CiHttpUtil.communicate(url, method, body, contentType, conn_timeout, read_timeout, outputClass, headers); }
		
		HttpsURLConnection urlConn = null;
		T resultObject = null;
		
		try {
			
			CiLogger.info("*** send : %s, method[%s], body[%s]", url, method, (body!=null)? CiStringUtil.sub_string(body, 1024, "...") : "");
			
			urlConn= CiHttpsUtil.getHttpsURLConnection(new URL(url), method, body, contentType, conn_timeout, read_timeout, headers
					, protocol,verifyCertificate, cacertsPath, password, verifyHostName);
			
			if(urlConn==null){ throw new CiException(CiResultCode.BAD_GATEWAY, "External System(%s) connection error", url); }
			
			int resultCode = urlConn.getResponseCode(); 
			if( resultCode  == HttpURLConnection.HTTP_OK ){
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
				throw new CiException(CiResultCode.BAD_GATEWAY,"%s", CiStringUtil.sub_string(resultStr, 1024,"..."));
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
		}finally{ if(urlConn!=null){ try{ urlConn.disconnect(); urlConn = null; }catch(Exception e){} } }
	}

	public static <T> T post(
			String url
			, String body
			, String contentType
			, int conn_timeout
			, int read_timeout
			, Class<T> outputClass) throws Exception {
		return CiHttpUtil.post(url, body, contentType, conn_timeout, read_timeout, outputClass);
	}
	
	public static <T> T post_json(String url
			, Map<String, Object> bodyMap
			, String contentType
			, int conn_timeout
			, int read_timeout
			, Class<T> outputClass
			) throws Exception {
		return CiHttpUtil.post_json(url,bodyMap, contentType, conn_timeout, read_timeout, outputClass);
	}
	
	public static <T> T post_json(
			String scheme, String serverName, int serverPort, String requestURI
			, Map<String, Object> bodyMap
			, String contentType
			, int conn_timeout, int read_timeout
			, Class<T> outputClass
			, List<NameValuePair> headers
			, String protocol, Boolean verifyCertificate, String cacertsPath, String password, Boolean verifyHostName
			) throws Exception {
		return communicate(scheme, 
				CiHttpUtil.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, null)
				, CiHttpUtil.HTTP_POST_METHOD, CiHttpUtil.get_json_body(bodyMap), contentType
				, conn_timeout, read_timeout
				, outputClass
				, headers 
				, protocol, verifyCertificate, cacertsPath, password, verifyHostName);
	}

	public static <T> T post_json(
			String scheme, String serverName, int serverPort, String requestURI
			, String bodyString
			, String contentType
			, int conn_timeout, int read_timeout
			, Class<T> outputClass
			, List<NameValuePair> headers
			, String protocol, Boolean verifyCertificate, String cacertsPath, String password, Boolean verifyHostName
			) throws Exception {
		return communicate(scheme, 
				CiHttpUtil.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, null)
				, CiHttpUtil.HTTP_POST_METHOD, bodyString, contentType
				, conn_timeout, read_timeout
				, outputClass
				, headers 
				, protocol, verifyCertificate, cacertsPath, password, verifyHostName);
	}
	
	public static <T> T post_json(
			String scheme, String serverName, int serverPort, String requestURI
			, Map<String, Object> bodyMap
			, String contentType
			, int conn_timeout, int read_timeout
			, Class<T> outputClass
			) throws Exception {
		return communicate(scheme, 
				CiHttpUtil.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, null)
				, CiHttpUtil.HTTP_POST_METHOD, CiHttpUtil.get_json_body(bodyMap), contentType
				, conn_timeout, read_timeout
				, outputClass
				, null 
				, null, null, null, null, null);
	}
	
	public static <T> T put_json(
			String scheme, String serverName, int serverPort, String requestURI
			, Map<String, Object> bodyMap
			, String contentType
			, int conn_timeout, int read_timeout
			, Class<T> outputClass
			, List<NameValuePair> headers
			, String protocol, Boolean verifyCertificate, String cacertsPath, String password, Boolean verifyHostName
			) throws Exception {
		return communicate(scheme, 
				CiHttpUtil.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, null)
				, CiHttpUtil.HTTP_PUT_METHOD, CiHttpUtil.get_json_body(bodyMap), contentType
				, conn_timeout, read_timeout
				, outputClass
				, headers 
				, protocol, verifyCertificate, cacertsPath, password, verifyHostName);
	}

	public static <T> T get(String url, int conn_timeout, int read_timeout, Class<T> outputClass) throws Exception {
		return CiHttpUtil.get(url, conn_timeout, read_timeout, outputClass );
	}
	
	/**
	 * @param <T>
	 * @param scheme
	 * @param serverName
	 * @param serverPort
	 * @param requestURI
	 * @param paramMap
	 * @param charset
	 * @param conn_timeout
	 * @param read_timeout
	 * @param outputClass
	 * @param headers
	 * @param protocol
	 * @param verifyCertificate
	 * @param cacertsPath
	 * @param password
	 * @param verifyHostName
	 * @return
	 * @throws Exception
	 * get("https", "www.google.com", 443, "/", null, null, 3000, 6000, CiResponse.class, null, "TLSv1.2", true, "D:\\Program Files\\Java\\jre1.8.0_191\\lib\\security\\cacerts", "changeit", false);
	 */
	public static <T> T get(String scheme, String serverName, int serverPort, String requestURI, Map<String, Object> paramMap, final String charset
			, int conn_timeout, int read_timeout, Class<T> outputClass, List<NameValuePair> headers
			, String protocol, Boolean verifyCertificate, String cacertsPath, String password, Boolean verifyHostName) throws Exception {
		return communicate(scheme,
				CiHttpUtil.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, CiHttpUtil.getQueryString(paramMap, charset))
				, CiHttpUtil.HTTP_GET_METHOD, null, null, conn_timeout, read_timeout, outputClass, headers 
				, protocol, verifyCertificate, cacertsPath, password, verifyHostName);
	}

	public static <T> T get(String scheme, String serverName, int serverPort, String requestURI, Map<String, Object> paramMap, final String charset
							, int conn_timeout, int read_timeout, Class<T> outputClass) throws Exception {
		return communicate(scheme,CiHttpUtil.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, CiHttpUtil.getQueryString(paramMap, charset))
				, CiHttpUtil.HTTP_GET_METHOD, null, null, conn_timeout, read_timeout, outputClass, null 
				, null, null, null, null, null
				);
	}

}
