package com.castis.pvs.entity;

import com.castis.common.exception.CiException;
import com.castis.common.util.CiJsonUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "tbl_notification_send", uniqueConstraints = { })
public class NotificationSend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String method;
	private String scheme;
	private String server_name;
	private Integer server_port;
	private String request_uri;
	private String param_map;
	private String content_type;
	private Integer conn_timeout;
	private Integer read_timeout;
	private Integer status;
	private Integer retry_count;
	
	private Date create_time;
	private Date update_time;
	
	public NotificationSend() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idx", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public Integer getServer_port() {
		return server_port;
	}

	public void setServer_port(Integer server_port) {
		this.server_port = server_port;
	}

	public String getRequest_uri() {
		return request_uri;
	}

	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}

	public String getParam_map() {
		return param_map;
	}

	public void setParam_map(String param_map) {
		this.param_map = param_map;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public Integer getConn_timeout() {
		return conn_timeout;
	}

	public void setConn_timeout(Integer conn_timeout) {
		this.conn_timeout = conn_timeout;
	}

	public Integer getRead_timeout() {
		return read_timeout;
	}

	public void setRead_timeout(Integer read_timeout) {
		this.read_timeout = read_timeout;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@SuppressWarnings("unchecked")
	@Transient
	public Map<String, Object> makeParam_map() throws CiException {
		
		if(getParam_map()==null) {return null;}
		
		return CiJsonUtil.jsonToObject(getParam_map(), (new LinkedHashMap<String, Object>()).getClass());
	}

	public Integer getRetry_count() {
		return retry_count;
	}

	public void setRetry_count(Integer retry_count) {
		this.retry_count = retry_count;
	}

	@Transient
	public NotificationSendFail makeNotificationSendFail() {
		return new NotificationSendFail(this);
	}

	@Transient
	public void addRetry_count() {
		if(this.retry_count==null) {this.retry_count = 1; return;};
		
		this.retry_count  = this.retry_count +1;
		
	}
	
}
