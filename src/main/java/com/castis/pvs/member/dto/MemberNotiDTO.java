package com.castis.pvs.member.dto;

import com.castis.pvs.member.entity.BankRef;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberNotiDTO {
	
	private Long id;
	private String member_id;
	private String wifi_mac;
	private String app_code;
	private boolean isAdult;
	private String cardName;

	
	public MemberNotiDTO(Member member) {
		this.id = member.getId();
		this.member_id = member.getMember_id();
		this.app_code = member.getApp_code();
		this.isAdult = member.isAdult_cert();
	}
	
	public MemberNotiDTO(MemberInactive member) {
		this.member_id = member.getMember_id();
		this.app_code = member.getApp_code();
		this.isAdult = member.isAdult_cert();
	}
	
	public MemberNotiDTO(Member member, BankRef bank) {
		this.id = member.getId();
		this.member_id = member.getMember_id();
		this.app_code = member.getApp_code();
		this.isAdult = member.isAdult_cert();
		this.cardName = bank.getName();
	}
	
	public MemberNotiDTO(String wifi_mac, String app_code, boolean isAdult) {
		this.wifi_mac = wifi_mac;
		this.app_code = app_code;
		this.isAdult = isAdult;
	}
}
