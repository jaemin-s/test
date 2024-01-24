package com.castis.pvs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
@lombok.extern.slf4j.Slf4j
public class DBConnectInterceptor extends HandlerInterceptorAdapter {

	private static boolean isconnected = true;
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			if(!isIsconnected()) {
				log.info("PVS DB Pre handle Connection Status: Connected");
			}
			setIsconnected(true);
		} catch (SQLException e) {
			log.error("PVS DB Pre handle Connection Status: Failed", e);
			setIsconnected(false);
		}
		return true;
	}

	public static boolean isIsconnected() {
		return isconnected;
	}

	public static void setIsconnected(boolean isconnected) {
		DBConnectInterceptor.isconnected = isconnected;
	}
}
