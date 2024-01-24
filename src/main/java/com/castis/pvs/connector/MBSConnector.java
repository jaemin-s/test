package com.castis.pvs.connector;

import com.castis.common.constants.CiResultCode;
import com.castis.common.model.CiResponse;
import com.castis.common.model.mbs.CancelSvodResponse;
import com.castis.common.model.mbs.PurchaseSvodResponse;
import com.castis.common.ssl.CiHttpsUtil;
import com.castis.common.util.CiLogger;
import com.castis.pvs.member.dto.MemberNotiDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("mbsConnector")
public class MBSConnector {

	@Value("${mbs.scheme:http}")
	private String mbs_scheme;

	@Value("${mbs.ip:127.0.0.1}")
	private String mbs_ip;

	@Value("${mbs.port:8080}")
	private int mbs_port;

	@Value("${mbs.pay_confirm_url:/mbs/linkPayment-notify}")
	private String mbs_pay_confirm_url;

	@Value("${mbs.cancel_pay_url:/mbs/payment-cancel}")
	private String mbs_cancel_pay_url;

	@Value("${mbs.cancel_purchase_svod_url:/mbs/cancel-purchase-svod}")
	private String mbs_cancel_purchase_svod_url;


	@Value("${mbs.purchase_svod_url:/mbs/purchase-svod}")
	private String mbs_purchase_svod_url;

	@Value("${mbs.member_url:/mbs/member}")
	private String mbs_member_url;

	@Value("${mbs.delete_smember_url:/mbs/delete-member}")
	private String mbs_delete_member_url;


	@Value("${mbs.adult_auth_url:/mbs/adult-auth-notify}")
	private String mbs_adult_auth_url;

	@Value("${mbs.conn_timeout:3000}")
	private int mbs_conn_timeout;

	@Value("${mbs.read_timeout:5000}")
	private int mbs_read_timeout;


	@Value("${mbs.confirm_success.use:true}")
	private boolean mbs_confirm_success_use;

	@Value("${mbs.confirm_fail.use:true}")
	private boolean mbs_confirm_fail_use;


	@Value("${pvs.ssl.cacerts.verify:false}")
	private boolean ssl_cacerts_verify;

	@Value("${pvs.ssl.cacerts.protocol:TLS}")
	private String ssl_cacerts_protocol;

	@Value("${pvs.ssl.cacerts.path:D:\\Program Files\\Java\\jre1.8.0_191\\lib\\security\\cacerts}")
	private String ssl_cacerts_path;

	@Value("${pvs.ssl.cacerts.password:changeit}")
	private String ssl_cacerts_password;

	@Value("${pvs.ssl.hostname.verify:false}")
	private boolean ssl_hostname_verify;

	public boolean isMbs_confirm_success_use() {
		return mbs_confirm_success_use;
	}

	public void setMbs_confirm_success_use(boolean mbs_confirm_success_use) {
		this.mbs_confirm_success_use = mbs_confirm_success_use;
	}

	public boolean isMbs_confirm_fail_use() {
		return mbs_confirm_fail_use;
	}

	public void setMbs_confirm_fail_use(boolean mbs_confirm_fail_use) {
		this.mbs_confirm_fail_use = mbs_confirm_fail_use;
	}

	public boolean isSsl_hostname_verify() {
		return ssl_hostname_verify;
	}

	public void setSsl_hostname_verify(boolean ssl_hostname_verify) {
		this.ssl_hostname_verify = ssl_hostname_verify;
	}

	public String getMbs_scheme() {
		return mbs_scheme;
	}

	public void setMbs_scheme(String mbs_scheme) {
		this.mbs_scheme = mbs_scheme;
	}

	public String getMbs_ip() {
		return mbs_ip;
	}

	public void setMbs_ip(String mbs_ip) {
		this.mbs_ip = mbs_ip;
	}

	public int getMbs_port() {
		return mbs_port;
	}

	public void setMbs_port(int mbs_port) {
		this.mbs_port = mbs_port;
	}

	public String getMbs_pay_confirm_url() {
		return mbs_pay_confirm_url;
	}

	public void setMbs_pay_confirm_url(String mbs_pay_confirm_url) {
		this.mbs_pay_confirm_url = mbs_pay_confirm_url;
	}

	public String getMbs_cancel_pay_url() {
		return mbs_cancel_pay_url;
	}

	public void setMbs_cancel_pay_url(String mbs_cancel_pay_url) {
		this.mbs_cancel_pay_url = mbs_cancel_pay_url;
	}

	public String getMbs_member_url() {
		return mbs_member_url;
	}

	public void setMbs_member_url(String mbs_member_url) {
		this.mbs_member_url = mbs_member_url;
	}

	public int getMbs_conn_timeout() {
		return mbs_conn_timeout;
	}

	public void setMbs_conn_timeout(int mbs_conn_timeout) {
		this.mbs_conn_timeout = mbs_conn_timeout;
	}

	public int getMbs_read_timeout() {
		return mbs_read_timeout;
	}

	public void setMbs_read_timeout(int mbs_read_timeout) {
		this.mbs_read_timeout = mbs_read_timeout;
	}

	public boolean isSsl_cacerts_verify() {
		return ssl_cacerts_verify;
	}

	public void setSsl_cacerts_verify(boolean ssl_cacerts_verify) {
		this.ssl_cacerts_verify = ssl_cacerts_verify;
	}

	public String getSsl_cacerts_protocol() {
		return ssl_cacerts_protocol;
	}

	public void setSsl_cacerts_protocol(String ssl_cacerts_protocol) {
		this.ssl_cacerts_protocol = ssl_cacerts_protocol;
	}

	public String getSsl_cacerts_path() {
		return ssl_cacerts_path;
	}

	public void setSsl_cacerts_path(String ssl_cacerts_path) {
		this.ssl_cacerts_path = ssl_cacerts_path;
	}

	public String getSsl_cacerts_password() {
		return ssl_cacerts_password;
	}

	public void setSsl_cacerts_password(String ssl_cacerts_password) {
		this.ssl_cacerts_password = ssl_cacerts_password;
	}


	@PostConstruct
	public void initialize() {
		printProperties();
	}


	public void printProperties() {

		CiLogger.info("======================================== MBSConnector Properties ========================================");
		CiLogger.info("%s = %s","mbs.scheme",mbs_scheme);
		CiLogger.info("%s = %s","mbs.ip", mbs_ip);
		CiLogger.info("%s = %s","mbs.port", mbs_port);
		CiLogger.info("%s = %s","mbs.pay_confirm_url", mbs_pay_confirm_url);
		CiLogger.info("%s = %s","mbs.cancel_pay_url", mbs_cancel_pay_url);
		CiLogger.info("%s = %s","mbs.member_url", mbs_member_url);
		CiLogger.info("%s = %s","mbs.adult_auth_url", mbs_adult_auth_url);
		CiLogger.info("%s = %s","mbs.conn_timeout", mbs_conn_timeout);
		CiLogger.info("%s = %s","mbs.read_timeout", mbs_read_timeout);
		CiLogger.info("%s = %s","mbs.confirm_success.use", mbs_confirm_success_use);
		CiLogger.info("%s = %s","mbs.confirm_fail.use", mbs_confirm_fail_use);
		CiLogger.info("%s = %s","pvs.ssl.cacerts.verify", ssl_cacerts_verify);
		CiLogger.info("%s = %s","pvs.ssl.cacerts.protocol", ssl_cacerts_protocol);
		CiLogger.info("%s = %s","pvs.ssl.cacerts.path", ssl_cacerts_path);
		CiLogger.info("%s = %s","pvs.ssl.cacerts.password", ssl_cacerts_password);
		CiLogger.info("%s = %s","pvs.ssl.hostname.verify", ssl_hostname_verify);
		CiLogger.info("======================================== MBSConnector Properties ========================================");
	}

	public <T> T confirm(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_pay_confirm_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public <T> T cancel(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_cancel_pay_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}


	public CiResponse noti_join_member(MemberNotiDTO dto) throws Exception{
		//System.out.println("noti_join_member");
		CiLogger.info("noti_join_member");
		if(dto==null) {
			CiLogger.warn("Bad Request");
			return new CiResponse(CiResultCode.BAD_REQUEST,"Bad Request");
		}
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();

		paramMap.put("appId", Integer.parseInt(dto.getApp_code()));
		paramMap.put("accountId", dto.getMember_id());
		paramMap.put("isActive", true);
		paramMap.put("isAdult", dto.isAdult());
		paramMap.put("cardName", dto.getCardName());
		return member(paramMap, CiResponse.class);
	}

	public CiResponse noti_delete_member(MemberNotiDTO dto) throws Exception{
		if(dto==null) {
			CiLogger.warn("Bad Request");
			return new CiResponse(CiResultCode.BAD_REQUEST,"Bad Request");
		}
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();

		paramMap.put("appId", Integer.parseInt(dto.getApp_code()));
		paramMap.put("accountId", dto.getMember_id());
		return delete_member(paramMap, CiResponse.class);
	}

	public CiResponse noti_migration_member(MemberNotiDTO dto) throws Exception{

		if(dto==null) {
			CiLogger.warn("Bad Request");
			return new CiResponse(CiResultCode.BAD_REQUEST,"Bad Request");
		}
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();

		paramMap.put("appId", Integer.parseInt(dto.getApp_code()));
		paramMap.put("accountId", dto.getMember_id());
		paramMap.put("isActive", true);
		paramMap.put("isAdult", false);
		paramMap.put("cardName", "");
		return member(paramMap, CiResponse.class);
	}

	public CiResponse noti_migration_inactive_member(MemberNotiDTO dto) throws Exception{

		if(dto==null) {
			CiLogger.warn("Bad Request");
			return new CiResponse(CiResultCode.BAD_REQUEST,"Bad Request");
		}
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();

		paramMap.put("appId", Integer.parseInt(dto.getApp_code()));
		paramMap.put("accountId", dto.getMember_id());
		paramMap.put("isActive", false);
		paramMap.put("isAdult", false);
		paramMap.put("cardName", "");
		return member(paramMap, CiResponse.class);
	}

	public <T> T member(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_member_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public <T> T delete_member(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_delete_member_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public CiResponse noti_modify_member(MemberNotiDTO dto) throws Exception{

		if(dto==null) {
			CiLogger.warn("Bad Request");
			return new CiResponse(CiResultCode.BAD_REQUEST,"Bad Request");
		}
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();

		paramMap.put("appId", Integer.parseInt(dto.getApp_code()));
		paramMap.put("accountId", dto.getMember_id());
		paramMap.put("isAdult", dto.isAdult());
		paramMap.put("cardName", dto.getCardName());
		return modify(paramMap, CiResponse.class);
	}

	public <T> T modify(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.put_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_member_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public CiResponse noti_adult_auth(MemberNotiDTO dto) throws Exception{

		if(dto==null) {
			CiLogger.warn("Bad Request");
			return new CiResponse(CiResultCode.BAD_REQUEST,"Bad Request");
		}
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();

		paramMap.put("appId", Integer.parseInt(dto.getApp_code()));
		paramMap.put("deviceInfo", dto.getWifi_mac());
		paramMap.put("isAdult", dto.isAdult());
		return adultAuth(paramMap, CiResponse.class);
	}

	public <T> T adultAuth(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_adult_auth_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public <T> T post_json(String scheme, String serverName, int serverPort, String requestURI
			, String bodyString
			, String contentType
			, int conn_timeout, int read_timeout
			, Class<T> outputClass) throws Exception{

		return CiHttpsUtil.post_json(
				scheme
				, serverName
				, serverPort
				, requestURI
				, bodyString
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);

	}

	public <T> T get(String scheme, String serverName, int serverPort, String requestURI, Map<String, Object> paramMap, final String charset
			, int conn_timeout, int read_timeout, Class<T> outputClass) throws Exception{

		return CiHttpsUtil.get(scheme
				, serverName
				, serverPort
				, requestURI
				, null, null, 3000, 6000, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public <T> T purchase_svod(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_purchase_svod_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}

	public PurchaseSvodResponse purchaseSvod(String offerId, String accountId) {
		Map<String, Object> paramMap  = new LinkedHashMap<>();
		paramMap.put("offerIds", offerId);
		paramMap.put("accountId", accountId);

		try {
			PurchaseSvodResponse response = purchase_svod(paramMap, PurchaseSvodResponse.class);
			CiLogger.info("purchaseSvod response : {}", response);
			response.setResultCode(CiResultCode.SUCCESS);
			return response;
		} catch (Exception e) {
			CiLogger.error("purchaseSvod error : {}", e.getMessage());
			return PurchaseSvodResponse.builder().resultCode(CiResultCode.BAD_GATEWAY).build();
		}
	}

	public CancelSvodResponse cancelSvod(String offerId , String  accountId){
		Map<String, Object> paramMap  = new LinkedHashMap<String, Object>();
		paramMap.put("offerId", offerId);
		paramMap.put("accountId", accountId);

		try {
			CancelSvodResponse response = cancel_Svod(paramMap, CancelSvodResponse.class);
			response.setResultCode(CiResultCode.SUCCESS);
			return response;
		}catch(Exception e) {
			CiLogger.warn(e, "Fail to send notification to MBS");
			return CancelSvodResponse.builder().resultCode(CiResultCode.BAD_REQUEST).build();
		}
	}

	public <T> T cancel_Svod(Map<String, Object> paramMap, Class<T> outputClass) throws Exception{
		return CiHttpsUtil.post_json(
				mbs_scheme
				, mbs_ip
				, mbs_port
				, mbs_cancel_purchase_svod_url
				, paramMap
				, null
				, mbs_conn_timeout
				, mbs_read_timeout
				, outputClass
				, null
				, ssl_cacerts_protocol
				, ssl_cacerts_verify
				, ssl_cacerts_path
				, ssl_cacerts_password
				, ssl_hostname_verify
		);
	}
}
