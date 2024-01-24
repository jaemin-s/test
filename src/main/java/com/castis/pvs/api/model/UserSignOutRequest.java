package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignOutRequest {

	@NotBlank
	private String siteName;

	@NotBlank
	private String userId;

	public UserSignOutRequest(Map<String,String> requestBody) {
		this.siteName = requestBody.get("siteName");
		this.userId = requestBody.get("userId");
	}
}
