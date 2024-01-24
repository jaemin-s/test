package com.castis.pvs.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
	private String userId;
	private String providerId;
	private String password;
	private String displayName;
	private String email;
	private String mobile;
	private String roleId;
	private Date creationTime;
	private Date lastUpdateTime;

}
