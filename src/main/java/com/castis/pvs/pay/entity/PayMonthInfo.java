package com.castis.pvs.pay.entity;

import com.castis.common.model.CiRequest;
import com.castis.common.util.CiDateUtil;
import com.castis.pvs.constants.PVSConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tbl_pay_month_info", uniqueConstraints = { @UniqueConstraint(columnNames = "purchase_billing_id") })
public class PayMonthInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String purchase_billing_id;
	
	private String mid;
	private String oid;
	private String amount;
	private String buyerid;
	private String buyerphone;//고객휴대폰번호 (SMS 발송에 사용, ‘-‘ 빼고 입력) 
	private String app_code;
	private String productinfo;
	private int status;
	private Integer pay_day;
	private java.sql.Date next_pay_date;
	private Date create_time;
	private Date update_time;
	
	private int cancel_status;
	private Date cancel_time;

	public PayMonthInfo() {
		super();
	}

	public PayMonthInfo(CiRequest request) throws Exception {
		super();
		this.setPurchase_billing_id(request.getStringValue("purchase_billing_id"));
		this.setMid(request.getStringValue("mid"));
		this.setOid(request.getStringValue("oid"));
		this.setAmount(request.getStringValue("amount"));
		this.setBuyerid(request.getStringValue("buyerid"));
		this.setBuyerphone(request.getStringValue("buyerphone"));
		this.setProductinfo(request.getStringValue("productinfo"));
		this.setStatus(request.getInt("status",PVSConstants.Pay.STATUS.START ));
		this.setPay_day(request.getInt("pay_day",1 ));
		if(request.getStringValue("next_pay_date")!=null && request.getStringValue("next_pay_date").isEmpty()==false) {
			this.setNext_pay_date(CiDateUtil.parseSQLDate(request.getStringValue("next_pay_date"), "yyyy-MM-dd"));
		}else {
			this.setNext_pay_date(CiDateUtil.curr_pay_date());
		}
		this.setApp_code(request.getStringValue("app_code"));
		this.setBuyerphone(request.getStringValue("buyerphone"));
	}

	public PayMonthInfo(PayLog payLog) throws Exception {
		super();
		this.setPurchase_billing_id(payLog.getPurchase_billing_id());
		this.setMid(payLog.getPay_mall_id());
		this.setOid(payLog.getPurchase_billing_id());
		this.setAmount(Long.toString(payLog.getPrice()));
		this.setBuyerid(payLog.getAccount_id());
		this.setProductinfo(payLog.getProduct_title());
		this.setStatus(PVSConstants.Pay.STATUS.START);
		this.setApp_code(payLog.getApp_code());
		this.setBuyerphone(payLog.getTel());
		Date signed_date = CiDateUtil.to_date(payLog.getPay_sign_date(), "yyyy-MM-dd");
		int pay_day = CiDateUtil.valueof(signed_date,Calendar.DAY_OF_MONTH);
		this.setPay_day(pay_day);
		this.setNext_pay_date(CiDateUtil.next_pay_date(signed_date, pay_day));
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idx", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPurchase_billing_id() {
		return purchase_billing_id;
	}
	public void setPurchase_billing_id(String purchase_billing_id) {
		this.purchase_billing_id = purchase_billing_id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}
	
	public String getProductinfo() {
		return productinfo;
	}
	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "update_time", nullable = true,insertable = false,updatable = false)
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Column(name = "update_time", nullable = true,insertable = false,updatable = false)
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public java.sql.Date getNext_pay_date() {
		return next_pay_date;
	}

	public void setNext_pay_date(java.sql.Date next_pay_date) {
		this.next_pay_date = next_pay_date;
	}

	public Integer getPay_day() {
		return pay_day;
	}

	public void setPay_day(Integer pay_day) {
		this.pay_day = pay_day;
	}

	public String getApp_code() {
		return app_code;
	}

	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	public PayMonthRetry makePayMonthRetry() {
		PayMonthRetry payMonthRetry = new PayMonthRetry(this);
		return payMonthRetry;
	}

	public int getCancel_status() {
		return cancel_status;
	}

	public void setCancel_status(int cancel_status) {
		this.cancel_status = cancel_status;
	}

	public Date getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(Date cancel_time) {
		this.cancel_time = cancel_time;
	}

	public PayMonthResult makePayMonthResult() {
		PayMonthResult payMonthResult = new PayMonthResult(this);
		return payMonthResult;
	}

	public String getBuyerphone() {
		return buyerphone;
	}

	public void setBuyerphone(String buyerphone) {
		this.buyerphone = buyerphone;
	}
	
}
