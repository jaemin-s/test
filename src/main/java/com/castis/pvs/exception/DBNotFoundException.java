package com.castis.pvs.exception;

public class DBNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String fieldName;

	public DBNotFoundException(String fieldValue) {
		super(fieldValue);
		fieldName = "N/A";
	}

	public DBNotFoundException(String fieldName, String fieldValue) {
		super(String.format("%s: %s", fieldName, fieldValue));
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
