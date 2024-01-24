

package com.castis.pvs.api.dto;
import com.castis.pvs.member.dto.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpSoDTO {

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


	public SignUpSoDTO(MemberDTO memberDTO) throws Exception {
		this.member_id = memberDTO.getMember_id();
		this.password = memberDTO.getPassword();
		this.app_code = memberDTO.getApp_code().toString();
		this.tel = memberDTO.getTel().replace("-", "");
		this.adult_cert = memberDTO.getAdult_cert().toString();
		// this.address_city = memberDTO.getAddress_city();
		if(Objects.nonNull(memberDTO.getAddress_city())){
			this.address_city = memberDTO.getAddress_city().replaceAll("\\([^)]*\\)", "");
		}else{
			this.address_city = memberDTO.getAddress_city();
		}
		this.address_dist = memberDTO.getAddress_dist();;
		this.user_name = memberDTO.getUser_name();
		this.social_number = memberDTO.getSocial_number();
		this.market_yn = 0;
		this.mso_name = memberDTO.getMso_name();
		this.email = memberDTO.getEmail();
		this.recommender_id = memberDTO.getRecommender_id();
		this.ci = memberDTO.getCi();
		this.di = memberDTO.getDi();

		this.legal_name = memberDTO.getLegal_name();
		this.legal_social_number = memberDTO.getLegal_social_number();
		this.legal_ci = memberDTO.getLegal_ci();
		this.legal_di = memberDTO.getLegal_di();
		this.legal_tel = memberDTO.getLegal_tel();
		this.so_happycall_auth = memberDTO.getSo_happycall_auth();
        if (memberDTO.getSo_happycall_update_date() == null){
            this.so_happycall_update_date = memberDTO.getSo_happycall_update_date();
        }
		
		this.market_tm_yn = memberDTO.getMarket_tm_yn();
		this.market_email_yn = memberDTO.getMarket_email_yn();
		this.market_text_yn = memberDTO.getMarket_text_yn();
		this.push_yn = memberDTO.getPush_yn();
		this.app_token = memberDTO.getApp_token();
		this.so_id = memberDTO.getSo_id();
		this.app_version = memberDTO.getApp_version();
	}

}
