package com.castis.pvs.pay.controller;


import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiLogUtil;
import com.castis.common.util.CiLogger;
import com.castis.pvs.connector.MBSConnector;
import com.castis.pvs.pay.model.PurchaseRequest;
import com.castis.pvs.pay.service.EasyPaymentService;
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
import javax.servlet.http.HttpServletResponse;

@Controller
@Hidden
public class EasyPaymentController {
	
	@Autowired
	private EasyPaymentService easyPaymentService;
	
	@Autowired
	private MBSConnector mbsConnector;

	//Only for test
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	@RequestMapping(value="/monthlyPayment/list", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse monthlyPaymentList(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response =  easyPaymentService.monthlyPaymentList(request);
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
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
	@RequestMapping(value="/monthlyPayment/retry", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse monthlyPaymentRetry(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response =  easyPaymentService.monthlyPaymentRetry(request);
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
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
	@RequestMapping(value="/monthlyPayment/save", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse monthlyPaymentSave(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response =  easyPaymentService.monthlyPaymentSave(request);
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
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
	@RequestMapping(value="/monthlyPayment/add", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse monthlyPaymentMonthly(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response =  easyPaymentService.monthlyPaymentAdd(request);
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
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
	@RequestMapping(value="/monthlyPayment/cancel", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public CiResponse monthlyPaymentCancel(ModelMap model, HttpServletRequest httpRequest
			, @RequestBody String requestBody) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest, requestBody);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest, requestBody);
			request.validate();
			CiResponse response =  easyPaymentService.monthlyPaymentCancel(request);
			response.result(model);
			return response;
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
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
	@RequestMapping(value="/monthlyPayment/test")
	public String monthlyPaymenttest(ModelMap model, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

		long startTime = System.currentTimeMillis();
		try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
		httpResponse.setContentType("text/html;charset=UTF-8");
		CiRequest request = null;
		try{			
			CiLogger.request(httpRequest);
			try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
			request = new CiRequest(httpRequest);
			if("POST".equalsIgnoreCase(httpRequest.getMethod())) {
				request.parse();
			}
				String result = "";//
			if("POST".equalsIgnoreCase(request.getStringValue("method"))) {		
				 result = mbsConnector.post_json(
							 request.getStringValue("scheme")!=null ? request.getStringValue("scheme"): "https"
							, request.getStringValue("server")!=null ? request.getStringValue("server"): "www.google.com"
							, request.getInt("port", 443)
							, request.getStringValue("uri")!=null ? request.getStringValue("uri"): "/"
							, request.getStringValue("body")!=null ? request.getStringValue("body"): ""
							, null, 3000, 6000, String.class
							);
			}else {
				 result = mbsConnector.get(request.getStringValue("scheme")!=null ? request.getStringValue("scheme"): "https"
							, request.getStringValue("server")!=null ? request.getStringValue("server"): "www.google.com"
							, request.getInt("port", 443)
							, request.getStringValue("uri")!=null ? request.getStringValue("uri"): "/"
							, null, null, 3000, 6000, String.class);
			}
			CiResponse response =  request.makeCiResponse().addAttribute("result", result);
			response.result(model);
			
			return "common/empty_page";
			
		} catch( CiException e) {
			model.put("resultCode", e.getCode());
			model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
			model.addAttribute("result", e);
			return "common/empty_page";
		}catch(Exception e){
			CiLogger.error(e, e.getMessage());
			model.put("resultCode", CiResultCode.GENERAL_ERROR);
			model.put("errorMessage",e.getMessage());
			model.addAttribute("result", e);
			return "common/empty_page";
			
		}finally {
			CiLogger.response(model);
		}
		
	}

}
