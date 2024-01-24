package com.castis.pvs.user.dto;

import com.castis.pvs.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TableUserDTO {
	private String userId;
	private String displayName;
	private String providerName;
	private Date creationTime;
	private Date lastUpdateTime;
	private List<Role> roles = new ArrayList<>();
}
