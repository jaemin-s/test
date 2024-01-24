package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude= {"password", "new_password"})
public class ApiRequest {

	@NotBlank
	private String transaction_id;
	
	@NotBlank
	private String device_type;
	
	@NotBlank
	private String app_code;
	
	@NotBlank
	private String wifi_mac;
	
	private String account_id;
	private String password;
	private String new_password;
	private String type;
	private String checkType;
	private String model;
	private String language;
}
