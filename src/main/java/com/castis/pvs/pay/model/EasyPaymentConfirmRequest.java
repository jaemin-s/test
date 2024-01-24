package com.castis.pvs.pay.model;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiStringUtil;

import javax.servlet.http.HttpServletRequest;

public class EasyPaymentConfirmRequest extends CiRequest {

	public EasyPaymentConfirmRequest(HttpServletRequest httpRequest, String requestBody) throws Exception {
		super(httpRequest, requestBody);
		parse();
	}

	@Override
	public void validate() throws Exception{
		
		if(CiStringUtil.is_empty(getStringValue("billing_id"))) {throw new CiException(CiResultCode.BAD_REQUEST,"billing_id가 유효하지 않습니다.");}

		if(CiStringUtil.is_empty(getStringValue("device_type"))) {throw new CiException(CiResultCode.BAD_REQUEST,"device_type이 유효하지 않습니다.");}
		
		if(CiStringUtil.is_empty(getStringValue("type"))) {throw new CiException(CiResultCode.BAD_REQUEST,"type이 유효하지 않습니다.");}
		
		
	}

	@Override
	public CiResponse makeCiResponse() {
		return super.makeCiResponse()
				.addAttribute("transaction_id", getParameter("transaction_id"))
				.addAttribute("billing_id", getStringValue("billing_id"))
				;
	}

	@Override
	public CiResponse makeCiResponse(int code)  {
		return super.makeCiResponse(code)
				.addAttribute("transaction_id", getParameter("transaction_id"))
				.addAttribute("billing_id", getStringValue("billing_id"))
				;
	}

	@Override
	public CiResponse makeCiResponse(int code, String message) {
		return super.makeCiResponse(code, message)
				.addAttribute("transaction_id", getParameter("transaction_id"))
				.addAttribute("billing_id", getStringValue("billing_id"))
				;
	}

	@Override
	public CiResponse makeCiResponse(CiResponse ciResponse) {
		return super.makeCiResponse(ciResponse)
				.addAttribute("transaction_id", getParameter("transaction_id"))
				.addAttribute("billing_id", getStringValue("billing_id"))
				;
	}
	
	

}
