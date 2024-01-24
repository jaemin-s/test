package com.castis.pvs.pay.controller;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiLogUtil;
import com.castis.common.util.CiLogger;
import com.castis.pvs.api.model.PvsCancelPurchaseSvodRequest;
import com.castis.pvs.pay.model.CancelPaymentRequest;
import com.castis.pvs.pay.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Hidden
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	@Deprecated
	@RequestMapping(value="/api/cancel_pay", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse cancel_pay(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			
			CiLogger.request(httpRequest
					, CiLogUtil.hiddenRequest(requestBody
							, new ModelMap("\"account_pay_pwd\":[\\s]*\"(\\w*)\"", "\"account_pay_pwd\": \"%s\"")
					));
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CancelPaymentRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response = paymentService.cancel_pay(request);
			if(response==null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
			}
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",e.getMessage());
			if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
			else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
			else { return new CiResponse(CiResultCode.GENERAL_ERROR); }
			
		}finally {
			CiLogger.response(model);
		}
		
	}

	@Deprecated
	@RequestMapping(value="/api/cancel_point_pay_admin", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse cancel_point_pay_admin(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			
			CiLogger.request(httpRequest);
			
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CancelPaymentRequest(httpRequest, requestBody);
			
			CiResponse response = paymentService.cancel_point_pay_admin(request);
			if(response==null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
			}
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",e.getMessage());
			if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
			else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
			else { return new CiResponse(CiResultCode.GENERAL_ERROR); }
			
		}finally {
			CiLogger.response(model);
		}
		
	}

	@RequestMapping(value = "/api/terminate-subscription", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public CiResponse cancelTerminateSubscription(HttpServletRequest httpRequest,
			@RequestBody String requestBody) {
		long startTime = System.currentTimeMillis();
		try {
			MDC.remove("startTime");
			MDC.remove("processingTimeMSec");
		} catch (Exception e) {
		}
		PvsCancelPurchaseSvodRequest request = null;
		try {

			CiLogger.info("requestBody : " + requestBody);

			try {
				MDC.put("startTime", Long.toString(startTime));
			} catch (Exception e) {
			}
			request = new ObjectMapper().readValue(requestBody, PvsCancelPurchaseSvodRequest.class);

			CiResponse response = paymentService.cancel_purchase_subscription(request);
			CiLogger.info("response : " + response.getResultCode());
			if (response == null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR, "Null response");
			}
			return response;

		} catch (CiException e) {
			return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(), e.getMessage()));

		} catch (Exception e) {
			CiLogger.error(e, e.getMessage());
			return new CiResponse(CiResultCode.GENERAL_ERROR);

		}

	}
	
	@RequestMapping(value="/api/cancel_MON_pay_admin", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse cancel_MON_pay_admin(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			
			CiLogger.request(httpRequest);
			
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CancelPaymentRequest(httpRequest, requestBody);
			
			CiResponse response = paymentService.cancel_MON_pay_admin(request);
			if(response==null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
			}
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",e.getMessage());
			if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
			else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
			else { return new CiResponse(CiResultCode.GENERAL_ERROR); }
			
		}finally {
			CiLogger.response(model);
		}
		
	}

	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	@RequestMapping(value="/api/get_pay_info", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse get_pay_info(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response = paymentService.get_pay_info(request);
			if(response==null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
			}
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",e.getMessage());
			if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
			else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
			else { return new CiResponse(CiResultCode.GENERAL_ERROR); }
			
		}finally {
			CiLogger.response(model);
		}
		
	}
	
	//Only for test
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	@RequestMapping(value="/api/get_pay_month_info", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse get_pay_month_info(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response = paymentService.get_pay_month_info(request);
			if(response==null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
			}
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",e.getMessage());
			if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
			else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
			else { return new CiResponse(CiResultCode.GENERAL_ERROR); }
			
		}finally {
			CiLogger.response(model);
		}
		
	}
	
	//Only for test
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	@RequestMapping(value="/api/get_pay_month_result", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse get_pay_month_result(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response = paymentService.get_pay_month_result(request);
			if(response==null) {
				CiLogger.warn("Null response.");
				throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
			}
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",e.getMessage());
			if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
			else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
			else { return new CiResponse(CiResultCode.GENERAL_ERROR); }
			
		}finally {
			CiLogger.response(model);
		}
		
	}

}
