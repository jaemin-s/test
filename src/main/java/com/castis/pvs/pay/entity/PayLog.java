package com.castis.pvs.pay.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tbl_pay_log", uniqueConstraints = { @UniqueConstraint(columnNames = "purchase_billing_id") })
public class PayLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String purchase_billing_id;
	private String purchase_type;
	private String device_type;
	private String account_id;
	private String account_type;
	private String wifi_mac;
	private String model;
	private int status;
	private String tel;
	private String product_id;
	private String product_title;
	private String period;
	private Long price;
	private String app_code;
	private Date create_time;
	private Date ok_time;
	private Date cancel_time;
	private String pay_type;
	private String pay_billing_id;
	private String pay_mall_id;
	private String pay_mall_ip;
	private String pay_sign_date;
	private String pay_exec_date;
	private String pay_encrypt_data;
	private String pay_cardnum;
	
	private Long price_card;
	private Long price_phone;
	private Long price_point;
	private Long price_coupon;
	private Long price_discount;
	private String discount_coupon_id;
	private String point_coupon_id;
	private String description;
	private String language;
	private Date update_time;

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
	public String getPurchase_type() {
		return purchase_type;
	}
	public void setPurchase_type(String purchase_type) {
		this.purchase_type = purchase_type;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getWifi_mac() {
		return wifi_mac;
	}
	public void setWifi_mac(String wifi_mac) {
		this.wifi_mac = wifi_mac;
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_title() {
		return product_title;
	}
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getApp_code() {
		return app_code;
	}
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	@Column(name = "create_time", nullable = true,insertable = false,updatable = false)
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getOk_time() {
		return ok_time;
	}
	public void setOk_time(Date ok_time) {
		this.ok_time = ok_time;
	}
	public Date getCancel_time() {
		return cancel_time;
	}
	public void setCancel_time(Date cancel_time) {
		this.cancel_time = cancel_time;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_billing_id() {
		return pay_billing_id;
	}
	public void setPay_billing_id(String pay_billing_id) {
		this.pay_billing_id = pay_billing_id;
	}
	public String getPay_sign_date() {
		return pay_sign_date;
	}
	public void setPay_sign_date(String pay_sign_date) {
		this.pay_sign_date = pay_sign_date;
	}
	public Long getPrice_phone() {
		return price_phone;
	}
	public void setPrice_phone(Long price_phone) {
		this.price_phone = price_phone;
	}
	public Long getPrice_point() {
		return price_point;
	}
	public void setPrice_point(Long price_point) {
		this.price_point = price_point;
	}
	public Long getPrice_coupon() {
		return price_coupon;
	}
	public void setPrice_coupon(Long price_coupon) {
		this.price_coupon = price_coupon;
	}
	public Long getPrice_discount() {
		return price_discount;
	}
	public void setPrice_discount(Long price_discount) {
		this.price_discount = price_discount;
	}
	public String getDiscount_coupon_id() {
		return discount_coupon_id;
	}
	public void setDiscount_coupon_id(String discount_coupon_id) {
		this.discount_coupon_id = discount_coupon_id;
	}
	public String getPoint_coupon_id() {
		return point_coupon_id;
	}
	public void setPoint_coupon_id(String point_coupon_id) {
		this.point_coupon_id = point_coupon_id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPay_mall_ip() {
		return pay_mall_ip;
	}
	public void setPay_mall_ip(String pay_mall_ip) {
		this.pay_mall_ip = pay_mall_ip;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPay_mall_id() {
		return pay_mall_id;
	}
	public void setPay_mall_id(String pay_mall_id) {
		this.pay_mall_id = pay_mall_id;
	}
	public String getPay_encrypt_data() {
		return pay_encrypt_data;
	}
	public void setPay_encrypt_data(String pay_encrypt_data) {
		this.pay_encrypt_data = pay_encrypt_data;
	}
	public String getPay_exec_date() {
		return pay_exec_date;
	}
	public void setPay_exec_date(String pay_exec_date) {
		this.pay_exec_date = pay_exec_date;
	}
	public Long getPrice_card() {
		return price_card;
	}
	public void setPrice_card(Long price_card) {
		this.price_card = price_card;
	}
	
	@Column(name = "update_time", nullable = true,insertable = false,updatable = false)
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getPay_cardnum() {
		return pay_cardnum;
	}
	public void setPay_cardnum(String pay_cardnum) {
		this.pay_cardnum = pay_cardnum;
	}
	
}
