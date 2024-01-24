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
 * 2021-05-10
 */
package com.castis.common.model;

import com.castis.common.constants.CiResultCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.ui.ModelMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CiResponse extends ModelMap {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int resultCode = CiResultCode.SUCCESS;

	protected String resultMessage="";

	protected String returnPage="";
	
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
		addAttribute();
	}

	public CiResponse() {
		addAttribute();
	}
	
	public CiResponse(int resultCode, String errorMessage) {
		this.setResultCode(resultCode);
		this.setErrorMessage(errorMessage);
		addAttribute();
	}

	public void error(String format, Object...args) {
		if(format==null || format.isEmpty()) {
			super.addAttribute("error", new Error("UNKNOWN"));
			return;
		}
		
		super.addAttribute("error",  new Error(String.format(format, args)));
	}

	public CiResponse(int resultCode) {
		this.setResultCode(resultCode);
		this.setErrorMessage(CiResultCode.convert(resultCode));
		addAttribute();
	}

	public CiResponse(CiResponse ciResponse) {
		this.setResultCode(ciResponse.getResultCode());
		this.setErrorMessage(ciResponse.getErrorMessage());
		ciResponse.result(this);
	}

	public void result(ModelMap model) {
		model.put("result_code", resultCode);
		model.put("result_msg", resultMessage);
		
		if(keySet().isEmpty()==false) {
			for(String key : keySet()) {
				model.put(key, get(key));
			}
		}
	};

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
		super.addAttribute("result_code", resultCode);
	}

	public String getErrorMessage() {
		return resultMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.resultMessage = errorMessage;
		super.addAttribute("result_msg", resultMessage);
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String errorMessage) {
		this.resultMessage = errorMessage;
		super.addAttribute("result_msg", errorMessage);
	}

	public CiResponse add(String attributeName, String format, Object...args) {
		if(attributeName!=null && format!=null) {
			super.addAttribute(attributeName, String.format(format, args));
		}
		return this;
	}

	public CiResponse addAttribute() {
		super.addAttribute("result_code", resultCode);
		super.addAttribute("result_msg", resultMessage);
		return this;
	}

	public String getReturnPage() {
		return this.returnPage;
	}

	public CiResponse addAttribute(String attributeName, Object attributeValue) {
		super.addAttribute(attributeName, attributeValue);
		return this;
	}	
	
	
}
