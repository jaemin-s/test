package com.castis.pvs.pay.service;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiDateUtil;
import com.castis.common.util.CiLogger;
import com.castis.common.util.CiStringUtil;
import com.castis.pvs.constants.PVSConstants.Pay;
import com.castis.pvs.dao.TransactionInfoDao;
import com.castis.pvs.entity.TransactionInfo;
import com.castis.pvs.member.dao.MemberDao;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.pay.dao.PayLogDao;
import com.castis.pvs.pay.dao.PayMonthInfoDao;
import com.castis.pvs.pay.dao.PayMonthResultDao;
import com.castis.pvs.pay.dao.PayMonthRetryDao;
import com.castis.pvs.pay.entity.PayLog;
import com.castis.pvs.pay.entity.PayMonthInfo;
import com.castis.pvs.pay.entity.PayMonthResult;
import com.castis.pvs.pay.entity.PayMonthRetry;
import com.castis.pvs.pay.model.PurchaseRequest;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("easyPaymentService")
public class EasyPaymentService {

	@Autowired
	private PayLogDao payLogDao;

	@Autowired
	private PayMonthInfoDao payMonthInfoDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private PayMonthRetryDao payMonthRetryDao;

	@Autowired
	private PayMonthResultDao payMonthResultDao;

	public PayLogDao getPayLogDao() {
		return payLogDao;
	}

	public void setPayLogDao(PayLogDao payLogDao) {
		this.payLogDao = payLogDao;
	}


	public CiResponse cancel_MON_pay(CiRequest ciRequest, PayLog payLog) throws Exception{

		if(CiStringUtil.is_empty(ciRequest.getStringValue("account_id"))) {throw new CiException(CiResultCode.BAD_REQUEST,"account_id가 유효하지 않습니다.");}
		if(CiStringUtil.is_empty(ciRequest.getStringValue("app_code"))) {throw new CiException(CiResultCode.BAD_REQUEST,"app_code가 유효하지 않습니다.");}
		if(CiStringUtil.is_empty(ciRequest.getStringValue("account_pay_pwd"))) {throw new CiException(CiResultCode.BAD_REQUEST,"account_pay_pwd가 유효하지 않습니다.");}

		Member member = null;
		{
			//member 유효성 검사
			member = memberDao.selectOneMember(
					new ModelMap("member_id", ciRequest.getStringValue("account_id")));
			if(member==null) {
				throw new CiException(CiResultCode.INVALID_ACCOUNT_ID);
			}else if(member.isUnregister()) {
				throw new CiException(CiResultCode.INVALID_ACCOUNT_ID);
			}
		}

		return cancelMonPay(ciRequest, payLog);
	}

	public CiResponse cancelMonPay(CiRequest ciRequest, PayLog payLog) throws Exception {

		if(ciRequest==null) {throw new CiException(CiResultCode.BAD_REQUEST,"null request.");}
		if(payLog==null) {throw new CiException(CiResultCode.BAD_REQUEST,"null payLog.");}

		try{
			payLog.setStatus(Pay.STATUS.CANCEL);
			payLog.setDescription("고객 취소 요청");
			payLog.setCancel_time(new Date());
			payLogDao.update(payLog);
		}catch(Exception e) {
			CiLogger.error(e, "Fail to update payMonthInfo.");
		}

		return ciRequest.makeCiResponse()
				.addAttribute("pay_type", payLog.getPurchase_type())
				.addAttribute("pay_date", payLog.getPay_sign_date());
	}

	public void cancel_subscription(PayLog payLog) throws Exception {
		payLog.setStatus(Pay.STATUS.CANCEL);
		payLog.setDescription("site 취소 요청");
		payLog.setCancel_time(new Date());
		payLogDao.update(payLog);
	}

	public CiResponse cancel_MON_pay_admin(CiRequest ciRequest, PayLog payLog) throws Exception{

		if(CiStringUtil.is_empty(ciRequest.getStringValue("account_id"))) {throw new CiException(CiResultCode.BAD_REQUEST,"account_id가 유효하지 않습니다.");}
		if(CiStringUtil.is_empty(ciRequest.getStringValue("app_code"))) {throw new CiException(CiResultCode.BAD_REQUEST,"app_code가 유효하지 않습니다.");}

		Member member = null;
		{
			String memberId = ciRequest.getStringValue("account_id");
			member = memberDao.getMemberbyMemberId(memberId);
			//member 유효성 검사
			if(member==null) {
				throw new CiException(CiResultCode.INVALID_ACCOUNT_ID);
			}else if(member.isUnregister()) {
				throw new CiException(CiResultCode.INVALID_ACCOUNT_ID);
			}
		}

		return cancelMonPay(ciRequest, payLog);
	}


	public CiResponse monthlyPaymentList(CiRequest request) throws Exception {
		if(request==null) {throw new CiException(CiResultCode.BAD_REQUEST,"null request.");}

		request.parse();

		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		int setFirstResult = request.getInt("setFirstResult", 0);
		int setMaxResults = request.getInt("setMaxResults", 10);
		paramMap.put("setFirstResult", setFirstResult);
		paramMap.put("setMaxResults", setMaxResults);
		paramMap.put("order_update_time", Order.desc("update_time"));
		if(request.getStringValue("next_pay_date")!=null) {
			paramMap.put("next_pay_date", CiDateUtil.parseSQLDate(request.getStringValue("next_pay_date"),"yyyy-MM-dd"));
		}

		if(request.getStringValue("status")!=null) {
			paramMap.put("status", request.getInt("status",0));
		}

		List<PayMonthInfo> list = payMonthInfoDao.selectList(paramMap);

		return request.makeCiResponse()
				.addAttribute("list", list);
	}

	public CiResponse monthlyPaymentSave(CiRequest request) throws Exception{

		payMonthInfoDao.save(new PayMonthInfo(request));
		return request.makeCiResponse();
	}


	public CiResponse monthlyPaymentRetry(CiRequest request)  throws Exception{
		if(request==null) {throw new CiException(CiResultCode.BAD_REQUEST,"null request.");}
		request.parse();

		if(request.getStringValue("purchase_billing_id")==null || request.getStringValue("purchase_billing_id").isEmpty()) {
			throw new CiException(CiResultCode.BAD_REQUEST);
		}

		PayMonthInfo info = payMonthInfoDao.selectOne(new ModelMap("purchase_billing_id", request.getStringValue("purchase_billing_id")));
		if(info==null) {
			throw new CiException(CiResultCode.BAD_REQUEST);
		}

		PayMonthRetry payMonthRetry = info.makePayMonthRetry();
		Serializable idx = payMonthRetryDao.save(payMonthRetry);
		payMonthRetry.setId((Long)idx);
		return request.makeCiResponse()
				.addAttribute("PayMonthInfo", info)
				.addAttribute("PayMonthRetry", payMonthRetry)
				;
	}


	public CiResponse monthlyPaymentAdd(CiRequest request) throws Exception {

		PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", request.getStringValue("billing_id")));
		if(payLog==null) {throw new CiException(CiResultCode.BAD_REQUEST);}
		PayMonthInfo payMonthInfo = new PayMonthInfo(payLog);
		Serializable idx = payMonthInfoDao.save(payMonthInfo);
		payMonthInfo.setId((long)idx);

		return request.makeCiResponse().addAttribute("payMonthInfo", payMonthInfo);
	}

	public CiResponse monthlyPaymentCancel(CiRequest request) throws Exception {

		PayLog payLog = payLogDao.selectOne(new ModelMap("purchase_billing_id", request.getStringValue("billing_id")));
		if(payLog==null) {throw new CiException(CiResultCode.BAD_REQUEST);}

		ModelMap paramMap = new ModelMap("purchase_billing_id", request.getStringValue("billing_id"));
		paramMap.addAttribute("amount", Restrictions.ne("amount","0"));
		paramMap.addAttribute("status", Pay.STATUS.COMPLETE);

		List<PayMonthResult> list = payMonthResultDao.selectList(paramMap);
		if(list==null || list.isEmpty()) {return request.makeCiResponse();}

		for(PayMonthResult payMonthResult : list) {
			payMonthResult.setCancel_time(new Date());
			payMonthResult.setDescription("고객 취소 요청");
			payMonthResult.setStatus(Pay.STATUS.CANCEL);
			payMonthResultDao.update(payMonthResult);
		}

		PayMonthInfo payMonthInfo = payMonthInfoDao.selectOne(new ModelMap("purchase_billing_id", request.getStringValue("billing_id")));
		if(payMonthInfo!=null) {
			payMonthInfoDao.delete(payMonthInfo);
		}

		payLog.setCancel_time(new Date());
		payLog.setStatus(Pay.STATUS.CANCEL);
		payLogDao.update(payLog);



		return request.makeCiResponse().addAttribute("payLog", payLog).addAttribute("payMonthInfo", payMonthInfo).addAttribute("list", list);
	}
}
