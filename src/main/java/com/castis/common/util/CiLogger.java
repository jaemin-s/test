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
import org.slf4j.MDC;
import org.slf4j.spi.LocationAwareLogger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@lombok.extern.slf4j.Slf4j
public class CiLogger {

    private static final String FQCN = CiLogger.class.getName();
    
    protected static String APPNAME_KEY,APPNAME,VERSION_KEY,VERSION;
    
    private CiLogger() {
    }
    
    @PostConstruct
    public static void initialize() {
    	CiLogger.APPNAME_KEY = "APP";
    	CiLogger.APPNAME = "";
    	CiLogger.VERSION_KEY = "Version";
    	CiLogger.VERSION = "";
    }
    
    public static void setConfig(String APPNAME_KEY, String APPNAME, String VERSION_KEY, String VERSION ) {
    	CiLogger.APPNAME_KEY = APPNAME_KEY;
    	CiLogger.APPNAME = APPNAME;
    	CiLogger.VERSION_KEY = VERSION_KEY;
    	CiLogger.VERSION = VERSION;
    }
	
	public static void default_app_info() {
		if(CiLogger.VERSION_KEY==null || CiLogger.APPNAME_KEY==null) {initialize();}
		if(MDC.get(CiLogger.VERSION_KEY)==null) {MDC.put(CiLogger.VERSION_KEY, CiLogger.VERSION);}
		if(MDC.get(CiLogger.APPNAME_KEY)==null) {MDC.put(CiLogger.APPNAME_KEY,CiLogger.APPNAME);}
	}
    
    private static void default_processing_time() {
		try{
			if(MDC.get("startTime")!=null){
				long startTime = 0L;
				Object obj = MDC.get("startTime");
				if(obj instanceof Long) { startTime = ((Long) obj).longValue(); } else { startTime =  Long.parseLong((String)obj ); }
				if(startTime > 0) {MDC.put("processingTimeMSec", String.format(", processingTimeMSec[%s]", System.currentTimeMillis() - startTime));}
			}
		}catch(Exception e){}
	}
    
    public static org.slf4j.Logger  getLogger() {
    	 return CiLogger.log;
    }
    
	public static void log(Throwable t, int level, String format, Object...args) {
    	default_processing_time();
    	//default_app_info();
    	if(format==null) {
        	return;
    	}
    	
    	if(log instanceof LocationAwareLogger) {
    		 ((LocationAwareLogger) log).log(null, FQCN, level, String.format(format, args), null, t);
    	}else {
    		
    		switch(level) {
	    		case 0: log.trace(String.format(format, args), t); break;
	    		case 10: log.debug(String.format(format, args), t); break;
	    		case 20: log.info(String.format(format, args), t); break;
	    		case 30: log.warn(String.format(format, args), t); break;
	    		case 40: log.error(String.format(format, args), t); break;
    		}
    	}
	}
	
    public static void trace(String format, Object...args){
    	log(null, LocationAwareLogger.TRACE_INT, format, args);
    }
    
    public static void debug(String format, Object...args){
    	log(null, LocationAwareLogger.DEBUG_INT, format, args);
    }
    
    public static void info(String format, Object...args){
    	log(null, LocationAwareLogger.INFO_INT, format, args);
    }
    
    public static void warn(String format, Object...args){
    	log(null, LocationAwareLogger.WARN_INT, format, args);
    }
    
    public static void error(String format, Object...args){
    	log(null, LocationAwareLogger.ERROR_INT, format, args);
    }
    
    public static void fatal(String format, Object...args){
    	log(null, LocationAwareLogger.ERROR_INT, format, args);
    }
    
	public static void error(Throwable t, String format, Object...args) {
		
    	log(t, LocationAwareLogger.ERROR_INT, format, args);
	}

	public static void warn(Throwable t, String format, Object...args) {
		
    	log(t, LocationAwareLogger.WARN_INT, format, args);
	}

	public static void request_get(HttpServletRequest httpRequest, Integer max_body_length, String tailFix, ModelMap modelMap) {
	
		if(httpRequest==null) {return;}
		String method = httpRequest.getMethod();
		
		if("GET".equalsIgnoreCase(method)==false) {return;}
		
		String message = "";
		message += String.format("request :: method[%s], clientAddr[%s:%s], RequestURI[%s]"
				, method
				, httpRequest.getRemoteAddr()
				, httpRequest.getRemotePort()
				, httpRequest.getRequestURI()
				);

			Enumeration<?> names = httpRequest.getParameterNames();
			if(names!=null) {
				if(modelMap!=null) {
					while(names.hasMoreElements()) {
						Object key = names.nextElement();
						String[] values = httpRequest.getParameterValues((String) key);
						String value = (values!=null && values.length==1)? values[0] : CiStringUtil.implode(values,",");
						if(modelMap.containsAttribute((String) key)) {
							value = CiLogUtil.hiddenString(value);
						}
						message += String.format(", %s[%s]", key, value);
					}
				}else {
					while(names.hasMoreElements()) {
						Object key = names.nextElement();
						String[] values = httpRequest.getParameterValues((String) key);
						message += String.format(", %s[%s]", key, (values!=null && values.length==1)? values[0] : CiStringUtil.implode(values,","));
					}
				}
			}
		
		log(null, LocationAwareLogger.INFO_INT, message);
	}

	public static void request_post_form(HttpServletRequest httpRequest, Integer max_body_length, String tailFix, ModelMap modelMap) {
	
		if(httpRequest==null) {return;}
		String method = httpRequest.getMethod();
		
		if("POST".equalsIgnoreCase(method)==false) {return;}
		
		String contextType = httpRequest.getContentType();
		if(contextType==null || contextType.startsWith("application/x-www-form-urlencoded")==false) { return;}
		
		String message = "";
		message += String.format("request :: method[%s], clientAddr[%s:%s], RequestURI[%s]"
				, method
				, httpRequest.getRemoteAddr()
				, httpRequest.getRemotePort()
				, httpRequest.getRequestURI()
				);


			try {
				
						Enumeration<?> names = httpRequest.getParameterNames();
						if(names!=null) {
							if(modelMap!=null) {
								while(names.hasMoreElements()) {
									Object key = names.nextElement();
									String[] values = httpRequest.getParameterValues((String) key);
									String value = (values!=null && values.length==1)? values[0] : CiStringUtil.implode(values,",");
									if(modelMap.containsAttribute((String) key)) {
										value = CiLogUtil.hiddenString(value);
									}
									message += String.format(", %s[%s]", key, value);
								}
							}else {
								while(names.hasMoreElements()) {
									Object key = names.nextElement();
									String[] values = httpRequest.getParameterValues((String) key);
									message += String.format(", %s[%s]", key, (values!=null && values.length==1)? values[0] : CiStringUtil.implode(values,","));
								}
							}
						}
			  
			} catch (Exception e) { 
				
			}finally {
			}
		
		log(null, LocationAwareLogger.INFO_INT, message);
	}

	public static void request_post(HttpServletRequest httpRequest, String body, Integer max_body_length, String tailFix, ModelMap modelMap) {
	
		if(httpRequest==null) {return;}
		String method = httpRequest.getMethod();
		if("GET".equalsIgnoreCase(method)) { return;}
		String contextType = httpRequest.getContentType();
		if(contextType!=null && contextType.startsWith("application/x-www-form-urlencoded")==true) { 
			request_post_form(httpRequest, max_body_length, tailFix, modelMap);
			return;
		}
		
			String message = "";
			message += String.format("request :: method[%s], clientAddr[%s:%s], RequestURI[%s]"
					, method
					, httpRequest.getRemoteAddr()
					, httpRequest.getRemotePort()
					, httpRequest.getRequestURI()
					);
			
			if(modelMap!=null) {
				String replace = CiLogUtil.hiddenRequest(body, modelMap);
				if(max_body_length!=null && CiStringUtil.is_not_empty(body)) {
					message += String.format(", body[%s]", CiStringUtil.sub_string(replace, max_body_length, tailFix));
				}else {
					message += String.format(", body[%s]", replace);
				}
			}else {
				if(max_body_length!=null && CiStringUtil.is_not_empty(body)) {
					message += String.format(", body[%s]", CiStringUtil.sub_string(body, max_body_length, tailFix));
				}else {
					message += String.format(", body[%s]", body);
				}
			}
			String messageLog = message.replaceAll("%", "%%");
			log(null, LocationAwareLogger.INFO_INT, messageLog);
	}

	public static void request(HttpServletRequest httpRequest, String body, Integer max_body_length, String tailFix, ModelMap modelMap) throws CiException {
		
		String method = httpRequest.getMethod();

		if("GET".equalsIgnoreCase(method)) {
			request_get(httpRequest, max_body_length, tailFix, modelMap);
			return;
		}else if("POST".equalsIgnoreCase(method)) {
			request_post(httpRequest, body, max_body_length, tailFix, modelMap);
			return;
		}

		throw new CiException(CiResultCode.GENERAL_ERROR, "Unsuppoted http method");
	}
	
	public static void request(HttpServletRequest httpRequest, String body) throws CiException {
		request(httpRequest, body, 1024, "...", null);
	}

	public static void request(HttpServletRequest httpRequest) throws Exception {
		request(httpRequest, null, null, null, null);
	}

	public static void hidden_request(HttpServletRequest httpRequest, ModelMap modelMap) throws CiException {
		request(httpRequest, null, null, null, modelMap);
	}
	
	public static void hidden_request(HttpServletRequest httpRequest, String body, ModelMap modelMap) throws CiException {
		request(httpRequest, body, 1024, "...", modelMap);
	}

	public static void response(ModelMap model) {
		String message = "response :: ";
		if(model!=null && model.isEmpty()==false) {
			boolean bFirst = true;
			for(String key : model.keySet()) {
				if("org.springframework.validation.BindingResult.string".equalsIgnoreCase(key)
						|| "org.springframework.validation.BindingResult.pagingCri".equalsIgnoreCase(key)) {
					continue;
				}
				
				if(bFirst==true) {
					message += String.format("%s[%s]", key, CiStringUtil.sub_string(model.get(key), 256, "..."));
					bFirst=false;
				}else {
					message += String.format(", %s[%s]", key, CiStringUtil.sub_string(model.get(key), 256, "..."));
				}
				
			}
		}
		
		log(null, LocationAwareLogger.INFO_INT, message);
	}



	public static void response(ModelAndView mav) {
		response(mav.getModelMap());
	}

    public static void log(String string, String ip) {
    }
}
