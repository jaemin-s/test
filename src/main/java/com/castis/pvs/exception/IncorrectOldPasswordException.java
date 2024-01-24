package com.castis.pvs.exception;

public class IncorrectOldPasswordException extends Exception {

	private static final long serialVersionUID = 1L;
	public IncorrectOldPasswordException(String msg) {
		super(msg);
	}
}
