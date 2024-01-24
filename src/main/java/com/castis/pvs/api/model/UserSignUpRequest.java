package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignUpRequest {

	@NotBlank
	private String user_id;

	private String user_pass;

	private String user_name;

	private String social_number;

	private String phone_number;

	private String zipcode;

	private String address;

	private String detail_adrdess;

	private String device_type;

	private String adult_cert;

	private String auth_number;

	@NotBlank
	private String app_code;

	private String mso_name;

	private String email;

	private String recommender_id;
	private String so_id;
	private String app_token;
	private String app_version;

}
