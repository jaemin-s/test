package com.castis.pvs.pay.service;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiStringUtil;
import com.castis.pvs.api.model.PvsCancelPurchaseSvodRequest;
import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.dao.TransactionInfoDao;
import com.castis.pvs.entity.TransactionInfo;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.pay.dao.PayLogDao;
import com.castis.pvs.pay.dao.PayMonthInfoDao;
import com.castis.pvs.pay.dao.PayMonthResultDao;
import com.castis.pvs.pay.entity.PayLog;
import com.castis.pvs.pay.entity.PayMonthInfo;
import com.castis.pvs.pay.entity.PayMonthResult;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

@Service("paymentService")
public class PaymentService {

	@Autowired
	private TransactionInfoDao transactionInfoDao;

	@Autowired
	private PayLogDao payLogDao;

	@Autowired
	private PayMonthInfoDao payMonthInfoDao;

	@Autowired
	private EasyPaymentService easyPaymentService;


	@Autowired
	private PayMonthResultDao payMonthResultDao;

	public PayLogDao getPayLogDao() {
		return payLogDao;
	}

	public void setPayLogDao(PayLogDao payLogDao) {
		this.payLogDao = payLogDao;
	}

	public CiResponse cancel_pay(CiRequest ciRequest) throws Exception {

		TransactionInfo oldProcessing =  transactionInfoDao.selectOne(new ModelMap("transaction_id", ciRequest.getStringValue("billing_id")));
		if(oldProcessing!=null) { throw new CiException(CiResultCode.IN_PROGRESS_ANOTHER,""); }

		PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", ciRequest.getStringValue("billing_id")));
		if(payLog==null) { throw new CiException(CiResultCode.BAD_REQUEST,"billing_id가 유효하지 않습니다."); }

		if(payLog.getStatus()!=PVSConstants.Pay.STATUS.COMPLETE) {

			if(payLog.getStatus()==PVSConstants.Pay.STATUS.CANCEL) {return (ciRequest.makeCiResponse(CiResultCode.PAY_CANCELLED, "결제가 이미 취소되었습니다."))
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date());} else if(payLog.getStatus()==PVSConstants.Pay.STATUS.FAIL) {return (ciRequest.makeCiResponse(CiResultCode.PAY_FAILED, "결제가 이미 실패되었습니다."))
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date());}

			throw new CiException(CiResultCode.BAD_REQUEST,"진행중인 결제를 취소할 수 없습니다.");
		}
		CiResponse resultResponse = null;

		long price = 0;

		if(payLog.getPrice() != null)
			price = payLog.getPrice();


		try {
			if (PVSConstants.Pay.TYPE.MON.equalsIgnoreCase(payLog.getPurchase_type())) {
				resultResponse = easyPaymentService.cancel_MON_pay(ciRequest, payLog);
			}
		} catch (Exception e) {
			throw new CiException(CiResultCode.UNSUPPORTED_REQUEST, "billing_id가 유효하지 않습니다.");
		}


		return resultResponse;

	}

	@Transactional(rollbackFor = {Exception.class})
	public CiResponse cancel_purchase_subscription(PvsCancelPurchaseSvodRequest cancelPurchaseSvodRequest) throws Exception {
		for (String billingId : cancelPurchaseSvodRequest.getBilling_ids()) {
			PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", billingId));
			easyPaymentService.cancel_subscription(payLog);
		}
		return new CiResponse(CiResultCode.SUCCESS, "결제가 취소되었습니다.");
		//(new ModelMap("purchase_billing_id", ciRequest.getStringValue("billing_id")
	}

	@Transactional(rollbackFor = {Exception.class})
	public CiResponse cancel_purchase_subscription(String[] billingIds) throws Exception {
		for (String billingId : billingIds) {
			PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", billingId));
			easyPaymentService.cancel_subscription(payLog);
		}
		return new CiResponse(CiResultCode.SUCCESS, "결제가 취소되었습니다.");
		//(new ModelMap("purchase_billing_id", ciRequest.getStringValue("billing_id")
	}

	public CiResponse cancel_MON_pay_admin(CiRequest ciRequest) throws Exception {

		TransactionInfo oldProcessing =  transactionInfoDao.selectOne(new ModelMap("transaction_id", ciRequest.getStringValue("billing_id")));
		if(oldProcessing!=null) { throw new CiException(CiResultCode.IN_PROGRESS_ANOTHER,""); }

		PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", ciRequest.getStringValue("billing_id")));
		if(payLog==null) { throw new CiException(CiResultCode.BAD_REQUEST,"billing_id가 유효하지 않습니다."); }

		if(payLog.getStatus()!=PVSConstants.Pay.STATUS.COMPLETE) {

			if(payLog.getStatus()==PVSConstants.Pay.STATUS.CANCEL) {return (ciRequest.makeCiResponse(CiResultCode.PAY_CANCELLED, "결제가 이미 취소되었습니다."))
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date());} else if(payLog.getStatus()==PVSConstants.Pay.STATUS.FAIL) {return (ciRequest.makeCiResponse(CiResultCode.PAY_FAILED, "결제가 이미 실패되었습니다."))
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date());}

			throw new CiException(CiResultCode.BAD_REQUEST,"진행중인 결제를 취소할 수 없습니다.");
		}

		if(PVSConstants.Pay.TYPE.MON.equalsIgnoreCase(payLog.getPurchase_type())) {
			return easyPaymentService.cancel_MON_pay_admin(ciRequest, payLog);
		}

		throw new CiException(CiResultCode.UNSUPPORTED_REQUEST,"billing_id가 유효하지 않습니다.");
	}

	public CiResponse cancel_point_pay_admin(CiRequest ciRequest) throws Exception {

		TransactionInfo oldProcessing =  transactionInfoDao.selectOne(new ModelMap("transaction_id", ciRequest.getStringValue("billing_id")));
		if(oldProcessing!=null) { throw new CiException(CiResultCode.IN_PROGRESS_ANOTHER,""); }

		PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", ciRequest.getStringValue("billing_id")));
		if(payLog==null) { throw new CiException(CiResultCode.BAD_REQUEST,"billing_id가 유효하지 않습니다."); }

		if(payLog.getStatus()!=PVSConstants.Pay.STATUS.COMPLETE) {

			if(payLog.getStatus()==PVSConstants.Pay.STATUS.CANCEL) {return (ciRequest.makeCiResponse(CiResultCode.PAY_CANCELLED, "결제가 이미 취소되었습니다."))
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date());} else if(payLog.getStatus()==PVSConstants.Pay.STATUS.FAIL) {return (ciRequest.makeCiResponse(CiResultCode.PAY_FAILED, "결제가 이미 실패되었습니다."))
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date());}

			throw new CiException(CiResultCode.BAD_REQUEST,"진행중인 결제를 취소할 수 없습니다.");
		}
		try {
			//smartXPayConnector.cancel(payLog, "고객 취소 요청");

			return ciRequest.makeCiResponse()
					.addAttribute("billing_id", payLog.getPurchase_billing_id())
					.addAttribute("pay_type", payLog.getPurchase_type())
					.addAttribute("pay_date", payLog.getPay_sign_date())
					;
		} catch (Exception e) {
			throw new CiException(CiResultCode.UNSUPPORTED_REQUEST,"billing_id가 유효하지 않습니다.");
		}
	}

	public CiResponse get_pay_info(CiRequest request) throws Exception{
		request.parse();

		ModelMap paramMap = new ModelMap();
		paramMap.addAttribute("setFirstResult", request.getInt("setFirstResult", 0));
		paramMap.addAttribute("setMaxResults", request.getInt("setMaxResults", 5));
		if(CiStringUtil.is_not_empty(request.getStringValue("purchase_billing_id"))) { paramMap.addAttribute("purchase_billing_id", request.getStringValue("purchase_billing_id")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("purchase_type"))) { paramMap.addAttribute("purchase_type", request.getStringValue("purchase_type")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("device_type"))) { paramMap.addAttribute("device_type", request.getStringValue("device_type")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("account_id"))) { paramMap.addAttribute("account_id", request.getStringValue("account_id")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("account_type"))) { paramMap.addAttribute("account_type", request.getStringValue("account_type")); }

		if(CiStringUtil.is_not_empty(request.getStringValue("wifi_mac"))) { paramMap.addAttribute("wifi_mac", request.getStringValue("stb_serial")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("model"))) { paramMap.addAttribute("model", request.getStringValue("model")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("status"))) { paramMap.addAttribute("status", request.getInt("status",0)); }
		if(CiStringUtil.is_not_empty(request.getStringValue("tel"))) { paramMap.addAttribute("tel", request.getStringValue("tel")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("product_id"))) { paramMap.addAttribute("product_id", request.getStringValue("product_id")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("product_title"))) { paramMap.addAttribute("product_title", Restrictions.ilike("product_title", request.getStringValue("product_title"), MatchMode.ANYWHERE)); }
		if(CiStringUtil.is_not_empty(request.getStringValue("pay_sign_date"))) { paramMap.addAttribute("pay_sign_date", request.getStringValue("pay_sign_date")); }

		if(CiStringUtil.is_not_empty(request.getStringValue("order"))) {
			paramMap.addAttribute("order_column", Order.desc(request.getStringValue("order")));
			if("id".equalsIgnoreCase(request.getStringValue("order"))==false) {
				paramMap.addAttribute("order_id", Order.desc("id"));
			}
		}else {
			paramMap.addAttribute("order_id", Order.desc("id"));
		}

		List<PayLog> list = payLogDao.selectList(paramMap);

		return request.makeCiResponse().addAttribute("list", list);
	}

	public PayLog selectOne(Map<String, Object> paramMap)  throws Exception{
		return payLogDao.selectOne(paramMap);
	}

	public void memberUnregister(Member member) throws Exception{
		//회원 탈퇴시 호출
		if(member==null) {return;}

		ModelMap paramMap = new ModelMap();
		paramMap.addAttribute("buyerid",member.getMember_id());

		List<PayMonthInfo> list = payMonthInfoDao.selectList(paramMap);
		if(list==null || list.isEmpty()) {return;}

		for(PayMonthInfo payMonthInfo : list) {
			payMonthInfo.setCancel_status(PVSConstants.Pay.STATUS.CANCEL);
			payMonthInfoDao.update(payMonthInfo);
		}

	}

	public void memberModifyCard(Member member) throws Exception{
		//회원 카드 정보 변경시 호출
		if(member==null) {return;}
	}

	public CiResponse get_pay_month_info(CiRequest request) throws Exception {
		if(request==null) {throw new CiException(CiResultCode.BAD_REQUEST,"null request.");}

		request.parse();

		ModelMap paramMap = new ModelMap();
		paramMap.addAttribute("setFirstResult", request.getInt("setFirstResult", 0));
		paramMap.addAttribute("setMaxResults", request.getInt("setMaxResults", 5));
		if(CiStringUtil.is_not_empty(request.getStringValue("purchase_billing_id"))) { paramMap.addAttribute("purchase_billing_id", request.getStringValue("purchase_billing_id")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("mid"))) { paramMap.addAttribute("mid", request.getStringValue("mid")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("productinfo"))) { paramMap.addAttribute("productinfo", Restrictions.ilike("productinfo", request.getStringValue("productinfo"), MatchMode.ANYWHERE)); }
		if(CiStringUtil.is_not_empty(request.getStringValue("buyerid"))) { paramMap.addAttribute("buyerid", request.getStringValue("buyerid")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("status"))) { paramMap.addAttribute("status", request.getInt("status",0)); }

		if(CiStringUtil.is_not_empty(request.getStringValue("order"))) {
			paramMap.addAttribute("order_column", Order.desc(request.getStringValue("order")));
			if("id".equalsIgnoreCase(request.getStringValue("order"))==false) {
				paramMap.addAttribute("order_id", Order.desc("id"));
			}
		}else {
			paramMap.addAttribute("order_id", Order.desc("id"));
		}

		List<PayMonthInfo> list = payMonthInfoDao.selectList(paramMap);

		return request.makeCiResponse().addAttribute("list", list);
	}

	public CiResponse get_pay_month_result(CiRequest request) throws Exception {
		if(request==null) {throw new CiException(CiResultCode.BAD_REQUEST,"null request.");}

		request.parse();


		ModelMap paramMap = new ModelMap();
		paramMap.addAttribute("setFirstResult", request.getInt("setFirstResult", 0));
		paramMap.addAttribute("setMaxResults", request.getInt("setMaxResults", 5));
		if(CiStringUtil.is_not_empty(request.getStringValue("purchase_billing_id"))) { paramMap.addAttribute("purchase_billing_id", request.getStringValue("purchase_billing_id")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("mid"))) { paramMap.addAttribute("mid", request.getStringValue("mid")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("productinfo"))) { paramMap.addAttribute("productinfo", Restrictions.ilike("productinfo", request.getStringValue("productinfo"), MatchMode.ANYWHERE)); }
		if(CiStringUtil.is_not_empty(request.getStringValue("buyerid"))) { paramMap.addAttribute("buyerid", request.getStringValue("buyerid")); }
		if(CiStringUtil.is_not_empty(request.getStringValue("status"))) { paramMap.addAttribute("status", request.getInt("status",0)); }

		if(CiStringUtil.is_not_empty(request.getStringValue("order"))) {
			paramMap.addAttribute("order_column", Order.desc(request.getStringValue("order")));
			if("id".equalsIgnoreCase(request.getStringValue("order"))==false) {
				paramMap.addAttribute("order_id", Order.desc("id"));
			}
		}else {
			paramMap.addAttribute("order_id", Order.desc("id"));
		}

		List<PayMonthResult> list = payMonthResultDao.selectList(paramMap);

		return request.makeCiResponse().addAttribute("list", list);
	}





}
