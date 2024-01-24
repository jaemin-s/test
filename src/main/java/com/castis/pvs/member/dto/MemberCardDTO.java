package com.castis.pvs.member.dto;

import com.castis.pvs.api.dto.MemberMigrationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCardDTO {

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
	
	private String card_type;
	private String card_no;
	private int card_cp;
	private String card_exp;
	private String card_private_no;
	private String card_pwd;
	private String pay_pwd;

	private String mso_name;

	private String recommender_id;

	private String ci;

	private String di;
	
	public MemberCardDTO(MemberMigrationDTO migration) {
		this.member_id = migration.getMember_id();
		this.password = migration.getPassword();
		//this.wifi_mac = migration.getWifi_mac();
		this.app_code = "1";
		this.tel = migration.getTel();
		
		if(migration.getGender().equalsIgnoreCase("F")) {
			this.gender = 0;
		} else if(migration.getGender().equalsIgnoreCase("M")) {
			this.gender = 1;
		}
		
		this.email = migration.getEmail();
		
		if(migration.getMarket_e().equalsIgnoreCase("Y") || migration.getMarket_m().equalsIgnoreCase("Y")) {
			this.market_yn = 1;
		}
		
		
		if(!migration.getCard_no().equalsIgnoreCase("") && migration.getCard_no() != null) {
			if(migration.getCard_type().equalsIgnoreCase("1")){
				this.card_type = "0";
			} else if(migration.getCard_type().equalsIgnoreCase("2")){
				this.card_type = "1";
			}
			this.card_no = migration.getCard_no();
			this.card_cp = Integer.parseInt(migration.getCard_cp());
			this.card_exp = migration.getCard_exp_mon() + migration.getCard_exp_year();
			this.card_private_no = migration.getCard_private_no();
		}
		this.mso_name = migration.getMso_name();
		this.recommender_id = migration.getRecommender_id();
		this.ci = migration.getCi();
		this.di = migration.getDi();
	}
}
