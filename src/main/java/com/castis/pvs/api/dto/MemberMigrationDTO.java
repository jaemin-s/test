package com.castis.pvs.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberMigrationDTO {

	private String member_id;
	private String password;
	private String wifi_mac;
	private String email;
	private String tel;
	private String gender;
	private String market_m;
	private String market_e;
	private String card_cp;
	private String card_no;
	private String card_exp_mon;
	private String card_exp_year;
	private String card_private_no;
	private String card_type;

	private String mso_name;

	private String recommender_id;

	private String ci;
	private String di;
	private String app_version;

}
