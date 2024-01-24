package com.castis.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import org.apache.log4j.MDC;

//import com.castis.common.Constant;


public class MDCConfigListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		//MDC Properties Setting...
		// Log4j appName, version setting.
//		MDC.put(Constant.Log4jSetttings.POIS_APPNAME_KEY,Constant.Log4jSetttings.POIS_APPNAME);
//		MDC.put(Constant.Log4jSetttings.POIS_VERSION_KEY,Constant.Log4jSetttings.POIS_VERSION);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
