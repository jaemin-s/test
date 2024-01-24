package com.castis.pvs.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbl_transaction_info", uniqueConstraints = { })
@NoArgsConstructor
public class TransactionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String url_code;
	
	private String type;
	
	private String wifi_mac;
	
	private String app_code;
	
	private String app_name;
	
//	private Long member_id;
		
	private String transaction_id;
	
	private String params;
	
	private Integer status;
	
	private Date create_time;
	
	private Date update_time;


	public TransactionInfo (String url, String type, String wifi_mac, String appCode, String appName) {
		this.url_code = url;
		this.type = type;
		this.wifi_mac = wifi_mac;
		this.app_code = appCode;
		this.app_name = appName;
		this.create_time = new Date();
		this.update_time = new Date();
	}
	
	@Id
	@Column(name = "url_code", unique = true, nullable = false)
	public String getUrl_code() {
		return url_code;
	}


	public void setUrl_code(String url_code) {
		this.url_code = url_code;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getWifi_mac() {
		return wifi_mac;
	}


	public void setWifi_mac(String wifi_mac) {
		this.wifi_mac = wifi_mac;
	}
	
	public String getApp_code() {
		return app_code;
	}
	
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	public String getApp_name() {
		return app_name;
	}
	
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

//	public Long getMember_id() {
//		return this.member_id;
//	}
//	
//	public void setMember_id(Long member_id) {
//		this.member_id = member_id;
//	}
	
	public String getTransaction_id() {
		return transaction_id;
	}


	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}


	public String getParams() {
		return params;
	}


	public void setParams(String params) {
		this.params = params;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	@Column(name = "create_time", nullable = true,insertable = false,updatable = false)
	public Date getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	@Column(name = "update_time", nullable = true,insertable = false,updatable = false)
	public Date getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	
}
