package com.castis.pvs.pay.entity;

import com.castis.pvs.constants.PVSConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tbl_pay_month_retry", uniqueConstraints = { @UniqueConstraint(columnNames = "purchase_billing_id") })
public class PayMonthRetry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String purchase_billing_id;
	

	private long pay_month_info_idx;
	
	private String mid;
	private String oid;
	private String amount;
	private String buyerid;
	private String buyerphone;//고객휴대폰번호 (SMS 발송에 사용, ‘-‘ 빼고 입력) 
	private String app_code;
	private String productinfo;
	private Integer pay_day;
	private java.sql.Date next_pay_date;
	
	private int status;
	private Integer retry_count = 1;
	private Date create_time;
	private Date update_time;

	public PayMonthRetry() {
		super();
	}

	public PayMonthRetry(PayMonthInfo info) {
		super();
		this.setPurchase_billing_id(info.getPurchase_billing_id());
		this.setPay_month_info_idx(info.getId());
		this.setMid(info.getMid());
		this.setOid(info.getOid());
		this.setAmount(info.getAmount());
		this.setBuyerid(info.getBuyerid());
		this.setApp_code(info.getApp_code());
		this.setProductinfo(info.getProductinfo());
		this.setPay_day(info.getPay_day());
		this.setNext_pay_date(info.getNext_pay_date());
		this.setStatus(PVSConstants.Pay.STATUS.START);
		this.setBuyerphone(info.getBuyerphone());
	}

	public PayMonthResult makePayMonthResult() {
		PayMonthResult payMonthResult = new PayMonthResult(this);
		return payMonthResult;
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
	public long getPay_month_info_idx() {
		return pay_month_info_idx;
	}
	public void setPay_month_info_idx(long pay_month_info_idx) {
		this.pay_month_info_idx = pay_month_info_idx;
	}
	public Integer getRetry_count() {
		return retry_count;
	}
	public void setRetry_count(Integer retry_count) {
		this.retry_count = retry_count;
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
	public String getApp_code() {
		return app_code;
	}
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}
	public String getProductinfo() {
		return productinfo;
	}
	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}
	public Integer getPay_day() {
		return pay_day;
	}
	public void setPay_day(Integer pay_day) {
		this.pay_day = pay_day;
	}
	public java.sql.Date getNext_pay_date() {
		return next_pay_date;
	}
	public void setNext_pay_date(java.sql.Date next_pay_date) {
		this.next_pay_date = next_pay_date;
	}

	@Transient
	public void addRetryCont() {
		if(getRetry_count()==null) { 
			setRetry_count(1);
			return;
		}
		
		setRetry_count(getRetry_count()  + 1 );
	}

	public String getBuyerphone() {
		return buyerphone;
	}

	public void setBuyerphone(String buyerphone) {
		this.buyerphone = buyerphone;
	}
}
