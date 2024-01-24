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
@Table(name = "Recommend")
@Getter
@Setter
@NoArgsConstructor
public class Recommend implements Serializable{


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

}
