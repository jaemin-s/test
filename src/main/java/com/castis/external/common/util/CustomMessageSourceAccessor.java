package com.castis.external.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

public class CustomMessageSourceAccessor extends MessageSourceAccessor {

	public CustomMessageSourceAccessor(MessageSource messageSource) {
		super(messageSource);
	}

	public CustomMessageSourceAccessor(MessageSource messageSource, Locale defaultLocale) {
		super(messageSource, defaultLocale);
	}

	public String getMsg(String code, String arg) {
		return super.getMessage(code, new Object[] { arg });
	}
	
	public String getMsg(String code, String arg0, String arg1) {
		return super.getMessage(code, new Object[] { arg0, arg1 });
	}
}
