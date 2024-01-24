package com.castis.pvs.pay.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tbl_pay_month_result", uniqueConstraints = { @UniqueConstraint(columnNames = "purchase_billing_id") })
public class PayMonthResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String purchase_billing_id;
	private String pay_sign_date;
	private Long pay_month_info_idx;

	private String mid;//토스페이먼츠에서 부여한 상점ID
	private String oid; //주문번호, 공백 특수문자 사용불가, 최대 63자
	private String amount;//결제금액, 12자
	private String tid;//결제금액, 12자
	private String paytype;//결제금액, 12자
	private String paydate;
	private String cardnum;
	private String cardgubun1;
	private String cardgubun2;
	private String cardacquirer;
	
	private String buyerid;//구매자아이디, 15자리
	private String app_code;
	private String buyerphone;//고객휴대폰번호 (SMS 발송에 사용, ‘-‘ 빼고 입력) 
	private String buyeremail;//구매자이메일, 40자리
	private String productinfo;//구매내역,128자리
	
	private int status;
	private Integer pay_day;
	private Date create_time;
	private Date update_time;
	private java.sql.Date curr_sign_date;
	
	private Date cancel_time;
	private String description;

	public PayMonthResult() {
		super();
	}

	public PayMonthResult(java.sql.Date date, String purchase_billing_id, long pay_month_info_idx, Integer pay_day) {
		super();
		this.setCurr_sign_date(date);
		this.setPay_sign_date(date.toString());
		this.setPurchase_billing_id(purchase_billing_id);
		this.setPay_month_info_idx(pay_month_info_idx);
		this.setPay_day(pay_day);
		
	}
	
	public PayMonthResult(PayMonthInfo info) {
		this.setCurr_sign_date(info.getNext_pay_date());
		this.setPurchase_billing_id(info.getPurchase_billing_id());
		this.setPay_month_info_idx(info.getId());
		this.setPay_day(info.getPay_day());
		this.setPay_sign_date(info.getNext_pay_date().toString());
		this.setMid(info.getMid());
//		this.setOid(info.getOid());
		this.setAmount(info.getAmount());
		this.setProductinfo(info.getProductinfo());
		this.setBuyerid(info.getBuyerid());
		this.setApp_code(info.getApp_code());
		this.setCancel_time(info.getCancel_time());
		this.setBuyerphone(info.getBuyerphone());
	}

	public PayMonthResult(PayMonthRetry payMonthRetry) {
		this.setCurr_sign_date(payMonthRetry.getNext_pay_date());
		this.setPurchase_billing_id(payMonthRetry.getPurchase_billing_id());
		this.setPay_month_info_idx(payMonthRetry.getPay_month_info_idx());
		this.setPay_day(payMonthRetry.getPay_day());
		this.setPay_sign_date(payMonthRetry.getNext_pay_date().toString());
		this.setMid(payMonthRetry.getMid());
//		this.setOid(payMonthRetry.getOid());
		this.setAmount(payMonthRetry.getAmount());
		this.setProductinfo(payMonthRetry.getProductinfo());
		this.setBuyerid(payMonthRetry.getBuyerid());
		this.setApp_code(payMonthRetry.getApp_code());
		this.setBuyerphone(payMonthRetry.getBuyerphone());
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
	public String getBuyerphone() {
		return buyerphone;
	}
	public void setBuyerphone(String buyerphone) {
		this.buyerphone = buyerphone;
	}
	public String getBuyeremail() {
		return buyeremail;
	}
	public void setBuyeremail(String buyeremail) {
		this.buyeremail = buyeremail;
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
	public String getApp_code() {
		return app_code;
	}
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}
	public String getPay_sign_date() {
		return pay_sign_date;
	}
	public void setPay_sign_date(String pay_sign_date) {
		this.pay_sign_date = pay_sign_date;
	}
	public Long getPay_month_info_idx() {
		return pay_month_info_idx;
	}
	public void setPay_month_info_idx(Long pay_month_info_idx) {
		this.pay_month_info_idx = pay_month_info_idx;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getCardgubun1() {
		return cardgubun1;
	}
	public void setCardgubun1(String cardgubun1) {
		this.cardgubun1 = cardgubun1;
	}
	public String getCardgubun2() {
		return cardgubun2;
	}
	public void setCardgubun2(String cardgubun2) {
		this.cardgubun2 = cardgubun2;
	}
	public String getCardacquirer() {
		return cardacquirer;
	}
	public void setCardacquirer(String cardacquirer) {
		this.cardacquirer = cardacquirer;
	}

	public Integer getPay_day() {
		return pay_day;
	}

	public void setPay_day(Integer pay_day) {
		this.pay_day = pay_day;
	}

	@Transient
	public java.sql.Date getCurr_sign_date() {
		return curr_sign_date;
	}

	public void setCurr_sign_date(java.sql.Date curr_sign_date) {
		this.curr_sign_date = curr_sign_date;
	}

	public Date getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(Date cancel_time) {
		this.cancel_time = cancel_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
