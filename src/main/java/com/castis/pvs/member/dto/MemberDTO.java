package com.castis.pvs.member.dto;

import com.castis.pvs.api.model.UserSignUpRequest;
import com.castis.pvs.api.model.UserSignUpV2Request;
import com.castis.pvs.api.model.v2.request.UsersignupRequest;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

	private Long id;
	private String member_id;
	private String password;
	private String wifi_mac;
	private String app_code;
	private String tel;
	private String adult_pass;
	private int gender;
	private int age;
	private String email;
	private String address_city;
	private String address_dist;
	private int market_yn;
	private String user_name;
	private String social_number;
	private String zipcode;
	private String device_type;
	private String adult_cert;
	private boolean happy_call_auth;
	private String device_uuid;
	private String mso_name;
	private String recommender_id;

	private String ci;
	private String di;

	private String legal_name;
	private String legal_social_number;
	private String legal_ci;
	private String legal_di;
	private String legal_tel;
	private Integer so_happycall_auth;
	private LocalDateTime so_happycall_update_date;
	private Integer market_tm_yn;
	private Integer market_email_yn;
	private Integer market_text_yn;
	private Integer push_yn;
	private String app_token;
	private String so_id;
	private String app_version;

	public MemberDTO(Member member) throws Exception {
		this.id = member.getId();
		this.member_id = member.getMember_id();
		this.password = member.getPassword();
		this.wifi_mac = member.getWifi_mac();
		this.app_code = member.getApp_code();
		this.tel = member.getTel();

		this.adult_pass = member.getAdult_pass();

		this.adult_cert = member.isAdult_cert()? "1":"0";

		if(member.isGender()) {
			this.gender=1;
		}else {
			this.gender=0;
		}

		this.age = member.getAge();
		this.email = member.getEmail();
		this.address_city = member.getAddress_city();
		this.address_dist = member.getAddress_dist();

		if(member.isMarket_yn()) {
			this.market_yn = 1;
		} else {
			this.market_yn = 0;
		}

		this.mso_name = member.getMso_name();
		this.recommender_id = member.getRecommender_id();
		this.ci = member.getCi();
		this.di = member.getDi();
		this.app_token = member.getApp_token();
		this.app_version = member.getApp_version();
	}

	public MemberDTO(MemberInactive member) throws Exception {

		this.member_id = member.getMember_id();
		this.password = member.getPassword();
		this.wifi_mac = member.getWifi_mac();
		this.app_code = member.getApp_code();
		this.tel = member.getTel();

		this.adult_pass = member.getAdult_pass();
		this.adult_cert = member.isAdult_cert()? "1":"0";

		if(member.isGender()) {
			this.gender=1;
		}else {
			this.gender=0;
		}

		this.age = member.getAge();
		this.email = member.getEmail();
		this.address_city = member.getAddress_city();
		this.address_dist = member.getAddress_dist();

		if(member.isMarket_yn()) {
			this.market_yn = 1;
		} else {
			this.market_yn = 0;
		}
		this.mso_name = member.getMso_name();
		this.recommender_id = member.getRecommender_id();
		this.ci = member.getCi();
		this.di = member.getDi();
		this.so_id = member.getSo_id();
		this.app_version = member.getApp_version();

	}

	public MemberDTO(UserSignUpRequest userSignUpRequest) throws Exception {
		this.member_id = userSignUpRequest.getUser_id();
		this.password = userSignUpRequest.getUser_pass();
		this.app_code = userSignUpRequest.getApp_code();
		this.tel = userSignUpRequest.getPhone_number().replace("-", "");
		this.adult_cert = userSignUpRequest.getAdult_cert();
		this.address_city = userSignUpRequest.getAddress();
		this.address_dist = userSignUpRequest.getDetail_adrdess();
		this.zipcode = userSignUpRequest.getZipcode();
		this.user_name = userSignUpRequest.getUser_name();
		this.device_type = userSignUpRequest.getDevice_type();
		this.social_number = userSignUpRequest.getSocial_number();
		this.market_yn = 0;
		this.mso_name = userSignUpRequest.getMso_name();
		this.email = userSignUpRequest.getEmail();
		this.recommender_id = userSignUpRequest.getRecommender_id();
		this.so_id = userSignUpRequest.getSo_id();
		this.app_token = userSignUpRequest.getApp_token();
		this.app_version = userSignUpRequest.getApp_version();
	}

	public MemberDTO(UserSignUpV2Request userSignUpRequest) throws Exception {
		this.member_id = userSignUpRequest.getUser_id();
		this.password = userSignUpRequest.getUser_pass();
		this.app_code = userSignUpRequest.getApp_code();
		this.tel = userSignUpRequest.getPhone_number().replace("-", "");
		this.adult_cert = userSignUpRequest.getAdult_cert();
		this.address_city = userSignUpRequest.getAddress();
		this.address_dist = userSignUpRequest.getDetail_adrdess();
		this.zipcode = userSignUpRequest.getZipcode();
		this.user_name = userSignUpRequest.getUser_name();
		this.device_type = userSignUpRequest.getDevice_type();
		this.social_number = userSignUpRequest.getSocial_number();
		this.market_yn = 0;
		this.device_uuid = userSignUpRequest.getDevice_uuid();
		this.mso_name = userSignUpRequest.getMso_name();
		this.email = userSignUpRequest.getEmail();
		this.recommender_id = userSignUpRequest.getRecommender_id();
		this.ci = userSignUpRequest.getCi();
		this.di = userSignUpRequest.getDi();
		this.legal_name = userSignUpRequest.getLegal_name();
		this.legal_social_number = userSignUpRequest.getLegal_social_number();
		this.legal_ci = userSignUpRequest.getLegal_ci();
		this.legal_di = userSignUpRequest.getLegal_di();
		this.legal_tel = userSignUpRequest.getLegal_tel();
		if(so_happycall_auth != null) {
			this.so_happycall_auth = userSignUpRequest.getSo_happycall_auth();
		}else{
			this.so_happycall_auth = 1;
		}
		this.so_happycall_update_date = userSignUpRequest.getSo_happycall_update_date();
		this.market_tm_yn = userSignUpRequest.getMarket_tm_yn();
		this.market_email_yn = userSignUpRequest.getMarket_email_yn();
		this.market_text_yn = userSignUpRequest.getMarket_text_yn();
		this.push_yn = userSignUpRequest.getPush_yn();
		this.app_token = userSignUpRequest.getApp_token();
		this.so_id = userSignUpRequest.getSo_id();
		this.app_version = userSignUpRequest.getApp_version();
	}

	public MemberDTO(UsersignupRequest userSignUpRequest) throws Exception {
		this.member_id = userSignUpRequest.getMember_id();
		this.password = userSignUpRequest.getPassword();
		this.app_code = userSignUpRequest.getApp_code().toString();
		this.tel = userSignUpRequest.getTel().replace("-", "");
		this.adult_cert = userSignUpRequest.getAdult_cert().toString();
		this.address_city = userSignUpRequest.getAddress_city();
		this.address_dist = userSignUpRequest.getAddress_dist();;
		this.device_type = userSignUpRequest.getDevice_type(); 
		this.device_uuid = userSignUpRequest.getDevice_uuid();
		this.wifi_mac = userSignUpRequest.getWifi_mac();
		this.user_name = userSignUpRequest.getUser_name();
		this.social_number = userSignUpRequest.getSocial_number();
		this.market_yn = 0;
		this.email = userSignUpRequest.getEmail();
		this.mso_name = userSignUpRequest.getMso_name();
		this.recommender_id = userSignUpRequest.getRecommender_id();
		this.ci = userSignUpRequest.getCi();
		this.di = userSignUpRequest.getDi();

		this.legal_name = userSignUpRequest.getLegal_name();
		this.legal_social_number = userSignUpRequest.getLegal_social_number();
		this.legal_ci = userSignUpRequest.getLegal_ci();
		this.legal_di = userSignUpRequest.getLegal_di();
		this.legal_tel = userSignUpRequest.getLegal_tel();
		this.so_happycall_auth = userSignUpRequest.getSo_happycall_auth();
		this.so_happycall_update_date = userSignUpRequest.getSo_happycall_update_date();
		this.market_tm_yn = userSignUpRequest.getMarket_tm_yn();
		this.market_email_yn = userSignUpRequest.getMarket_email_yn();
		this.market_text_yn = userSignUpRequest.getMarket_text_yn();
		this.push_yn = userSignUpRequest.getPush_yn();
		this.app_token = userSignUpRequest.getApp_token();
		this.so_id = userSignUpRequest.getSo_id();
		this.app_version = userSignUpRequest.getApp_version();
	}

}
