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
package com.castis.common.exception;

import com.castis.common.constants.CiResultCode;

public class CiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code = CiResultCode.GENERAL_ERROR;
	

	public CiException() {
		super();
	}

	public CiException(String message) {
		super(message);
		this.code=CiResultCode.GENERAL_ERROR;
	}
	
	public CiException(int code) {
		super();
		this.code=code;
	}
	
	public CiException(int code, String message) {
		super(message);
		this.code=code;
	}
	
	public CiException(int code, String format, Object...args){
		super(String.format(format, args));
		this.code = code;
	}
	
	public CiException( Throwable cause, int code, String message) {
		super(message, cause);
		this.code=code;
	}
	
	public CiException( Throwable cause, int code, String format, Object...args) {
		super(String.format(format, args), cause);
		this.code=code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int resultCode) {
		this.code = resultCode;
	}

	public String getErrorString() {
		return getMessage();
	}
}
