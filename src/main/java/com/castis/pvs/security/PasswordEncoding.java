package com.castis.pvs.security;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoding implements PasswordEncoder {

	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	public PasswordEncoding() {
		this.messageDigestPasswordEncoder = new MessageDigestPasswordEncoder("SHA-256");
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return messageDigestPasswordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return messageDigestPasswordEncoder.matches(rawPassword, encodedPassword);
	}
}
