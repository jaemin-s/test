package com.castis.pvs.api.dto;

import com.castis.pvs.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class SignUpDTO {

	private String userId;
	private String userName;
	private String socialNumber;
	private String phoneNumber;
	private String primaryAddress;
	private String secondaryAddress;
	private String deviceType;
	private String ci;
	private String di;

	public SignUpDTO (MemberDTO memberDTO) throws Exception {
		this.userId = memberDTO.getMember_id();
		this.userName = memberDTO.getUser_name();
		this.socialNumber = memberDTO.getSocial_number();
		this.phoneNumber = memberDTO.getTel();
		if(Objects.nonNull(memberDTO.getAddress_city())){
			this.primaryAddress = memberDTO.getAddress_city().replaceAll("\\([^)]*\\)", "");
		}else{
			this.primaryAddress = memberDTO.getAddress_city();
		}

		this.secondaryAddress = memberDTO.getAddress_dist();
		this.deviceType = memberDTO.getDevice_type();
		this.ci = memberDTO.getCi();
		this.di = memberDTO.getDi();
	}
}
