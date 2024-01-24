package com.castis.pvs.member.entity;

import com.castis.pvs.api.dto.MemberMigrationDTO;
import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.member.dto.MemberDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_member_inactive")
@Getter
@Setter
@NoArgsConstructor
public class MemberInactive implements Serializable{

	
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
	private String pay_pwd;
	private Timestamp create_time;
	private Timestamp update_time;

	private String mso_name;
	private String recommender_id;

	private String ci;
	private String di;
	private String so_id;
	private String app_version;
	
	public MemberInactive(MemberDTO memberDTO) throws Exception {
		//this.id = memberDTO.getId();
		
		this.member_id = memberDTO.getMember_id();
		this.password = memberDTO.getPassword();
		this.type = PVSConstants.ACCOUNT.TYPE.MEMBER;
		this.app_code = memberDTO.getApp_code();
		
		this.tel = memberDTO.getTel();

		if(memberDTO.getAdult_pass() != null) {
			this.adult_pass = memberDTO.getAdult_pass();
		}
		if(memberDTO.getAdult_cert() != null){
			this.adult_cert = memberDTO.getAdult_cert().equalsIgnoreCase("1");
		}else{
			this.adult_cert = false;
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
		this.mso_name = memberDTO.getMso_name();
		this.recommender_id = memberDTO.getRecommender_id();
		this.ci = memberDTO.getCi();
		this.di = memberDTO.getDi();
		this.app_version = memberDTO.getApp_version();
	}
	
	public MemberInactive(MemberMigrationDTO migration) throws Exception {
		this.member_id = migration.getMember_id();
		this.password = migration.getPassword();
		this.app_code = "1";
		this.tel = migration.getTel();
		this.adult_cert = false;
		if(migration.getGender().equalsIgnoreCase("F")) {
			this.gender = false;
		} else if(migration.getGender().equalsIgnoreCase("M")) {
			this.gender = true;
		}
		
		this.email = migration.getEmail();
		if(migration.getMarket_e().equalsIgnoreCase("Y") || migration.getMarket_m().equalsIgnoreCase("Y")) {
			this.market_yn = true;
		}

		this.create_time = new Timestamp(System.currentTimeMillis());
		this.update_time = new Timestamp(System.currentTimeMillis());
		this.mso_name = migration.getMso_name();
		this.recommender_id = migration.getRecommender_id();
		this.ci = migration.getCi();
		this.di = migration.getDi();
		this.app_version = migration.getApp_version();

	}
	
}
