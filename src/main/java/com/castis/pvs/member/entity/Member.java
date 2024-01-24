package com.castis.pvs.member.entity;

import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.member.dto.MemberCardDTO;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.dto.STBInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_member")
@Getter
@Setter
@NoArgsConstructor
public class Member implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String member_id;

	private String password;

	private String type;

	private String wifi_mac;

	private String app_code;

	private String tel;

	private boolean adult_cert;

	private String adult_pass;

	private boolean gender;

	private int age;

	private String email;

	private String address_city;

	private String address_dist;

	private boolean market_yn;

	private boolean unregister;

	private Timestamp create_time;

	private Timestamp update_time;

	private String user_name;

	private String social_number;

	private String zipcode;

	private String device_type;

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
	private Timestamp so_happycall_update_date;
	private Integer market_tm_yn;
	private Integer market_email_yn;
	private Integer market_text_yn;
	private Integer push_yn;
	private String so_id;
	private String app_token;
	private String app_version;

	public Member(MemberDTO memberDTO) throws Exception {
		this.id = memberDTO.getId();
		this.member_id = memberDTO.getMember_id();
		this.password = memberDTO.getPassword();
		this.type = PVSConstants.ACCOUNT.TYPE.MEMBER;
		this.wifi_mac = null;
		this.app_code = memberDTO.getApp_code();
		this.tel = memberDTO.getTel();
		if(memberDTO.getAdult_pass() != null) {
			this.adult_pass = memberDTO.getAdult_pass();
		}
		if(memberDTO.getGender() == 0) {
			this.gender = false;
		}else {
			this.gender = true;
		}

		this.age = memberDTO.getAge();
		this.email = memberDTO.getEmail();
		this.address_city = memberDTO.getAddress_city();
		this.address_dist = memberDTO.getAddress_dist();

		if(memberDTO.getMarket_yn() == 0) {
			this.market_yn = false;
		} else {
			this.market_yn = true;
		}

		this.unregister = false;
		this.create_time = new Timestamp(System.currentTimeMillis());
		this.update_time = new Timestamp(System.currentTimeMillis());
		this.so_happycall_update_date = new Timestamp(System.currentTimeMillis());

		this.user_name = memberDTO.getUser_name();
		this.social_number = memberDTO.getSocial_number();
		this.zipcode = memberDTO.getZipcode();
		this.device_type = memberDTO.getDevice_type();
		if(memberDTO.getAdult_cert().equals("1"))
			this.adult_cert = true;
		else{
			this.adult_cert = false;
		}

		this.device_uuid = memberDTO.getDevice_uuid();
		this.happy_call_auth = true;
		this.mso_name = memberDTO.getMso_name();
		this.recommender_id = memberDTO.getRecommender_id();

		this.ci = memberDTO.getCi();
		this.di = memberDTO.getDi();

		if(memberDTO.getLegal_name() != null)
			this.legal_name = memberDTO.getLegal_name();
		if(memberDTO.getLegal_social_number() != null)
			this.legal_social_number = memberDTO.getLegal_social_number();

		if(memberDTO.getLegal_ci() != null)
			this.legal_ci = memberDTO.getLegal_ci();

		if(memberDTO.getLegal_di() != null)
			this.legal_di = memberDTO.getLegal_di();

		if(memberDTO.getLegal_tel() != null)
			this.legal_tel = memberDTO.getLegal_tel();

		if(memberDTO.getSo_happycall_auth() != null)
			this.so_happycall_auth = memberDTO.getSo_happycall_auth();

		if(memberDTO.getSo_happycall_update_date() != null)
			this.so_happycall_update_date = Timestamp.valueOf(memberDTO.getSo_happycall_update_date());

		if(memberDTO.getMarket_tm_yn() != null)
			this.market_tm_yn = memberDTO.getMarket_tm_yn();

		if(memberDTO.getMarket_email_yn() != null)
			this.market_email_yn = memberDTO.getMarket_email_yn();

		if(memberDTO.getMarket_text_yn() != null)
			this.market_text_yn = memberDTO.getMarket_text_yn();

		if(memberDTO.getPush_yn() != null)
			this.push_yn = memberDTO.getPush_yn();

		if(memberDTO.getSo_id() != null)
			this.so_id = memberDTO.getSo_id();

		if(memberDTO.getApp_token() != null)
			this.app_token = memberDTO.getApp_token();

		if(memberDTO.getApp_version() != null)
			this.app_version = memberDTO.getApp_version();
			

	}


	public Member(STBInfoDTO stbInfoDTO) {
		this.member_id = stbInfoDTO.getWifi_mac();
		this.type = PVSConstants.ACCOUNT.TYPE.MEMBER;
		this.wifi_mac = stbInfoDTO.getWifi_mac();
		this.app_code = stbInfoDTO.getApp_code();
		this.adult_cert = true;
		this.unregister = false;
		this.create_time = new Timestamp(System.currentTimeMillis());
		this.update_time = new Timestamp(System.currentTimeMillis());
	}

}
