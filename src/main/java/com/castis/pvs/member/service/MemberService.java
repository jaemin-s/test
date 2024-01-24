package com.castis.pvs.member.service;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiLogger;
import com.castis.pvs.connector.MBSConnector;
import com.castis.pvs.member.dao.MemberDao;
import com.castis.pvs.member.dto.MemberCardDTO;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.dto.MemberNotiDTO;
import com.castis.pvs.member.dto.STBInfoDTO;
import com.castis.pvs.member.entity.AddressRef;
import com.castis.pvs.member.entity.BankRef;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import com.castis.pvs.security.PasswordEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("memberService")
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private PasswordEncoding password;
	
	@Autowired
	private MBSConnector mbsConnector;

	public CiResponse updateMemeber(MemberDTO memberDTO) throws Exception {
		
		memberDTO.setPassword(password.encode(memberDTO.getPassword()));
		Member member = new Member(memberDTO);
		
		try {
			memberDao.saveOrUpdateMember(member);
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
		
		Member newMember = memberDao.getMemberById(member.getId());
		MemberNotiDTO noti = new MemberNotiDTO(member);

		
		try {
			mbsConnector.noti_modify_member(noti);
		}catch (Exception e) {
			CiLogger.error(e, "Bad GateWay");
			throw new CiException(CiResultCode.BAD_GATEWAY, CiResultCode.MSG.code_503);
		}
		
		return new CiResponse(CiResultCode.SUCCESS,"");
	}
	

	public String checkMemberId(String memberId, String appCode) throws CiException {
		if(memberId==null || memberId.isEmpty()){throw new CiException(CiResultCode.BAD_REQUEST,"");}
		if(appCode==null || appCode.isEmpty()){throw new CiException(CiResultCode.BAD_REQUEST,"");}

		String result = "";
		
		try {
			Member member = memberDao.getMemberbyMemberId(memberId);
			MemberInactive memberInactive = memberDao.getMemberInactivebyMemberId(memberId);
			if(member != null || memberInactive != null) {
				result = "Y";
			} else {
				result = "N";
			}
		} catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
		
		return result;
	}
	
	public List<BankRef> getBankRefList() {
		return memberDao.getBankRefList();
	}
	
	public List<AddressRef> getAddressRefList() {
		List<AddressRef> list = memberDao.getAddressRefList();
		return list;
	}
	
	public List<AddressRef> getAdderssRefListByCityId(int id) {
		List<AddressRef> list = memberDao.getAddressRefListByCityId(id);
		return list;
	}
	


	
	public Member getMemberById(Long id) throws CiException {
		try {
			Member member = memberDao.getMemberById(id);
			return member;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
	}
	
	public List<String> findMemberIdByTel(String tel, String appCode) throws CiException {
		
		try {
			List<Member> list = memberDao.getMemberbyTel(tel);
			List<MemberInactive> inactiveList = memberDao.getMemberInactivebyTel(tel, appCode);
			List<String> resultList = new ArrayList<String>();
			
			for(Member l: list) {
				resultList.add(l.getMember_id());
			}
			for(MemberInactive l : inactiveList) {
				resultList.add(l.getMember_id());
			}
			return resultList;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
	}
	
	public Member findMemberByIdTel(String memberId,String tel) throws CiException {
		
		try {
			Member member = memberDao.getMemberbyIdTel(memberId, tel);
			
			return member;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
	}

	public Member findMemberByIdTelPw(String memberId,String pw) throws CiException {

		try {
			Member member = memberDao.getMemberbyIdTelPw(memberId, pw);

			return member;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
	}

	public Member findMemberByIdEmailPw(String memberId,String pw) throws CiException {

		try {
			Member member = memberDao.getMemberbyIdEmailPw(memberId, pw);

			return member;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
	}
	
	public MemberInactive findMemberInactiveByIdTel(String memberId, String tel) throws CiException {
		
		try {
			MemberInactive member = memberDao.getMemberInactivebyIdTel(memberId, tel);
			
			return member;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
		
	}
	
	public CiResponse changeMemberPassword(String memberId, String pass) throws CiException {
		try {
			Member member = memberDao.getMemberbyMemberId(memberId);
			if(member != null) {
				member.setPassword(password.encode(pass));
				memberDao.saveOrUpdateMember(member);
			} else {
				MemberInactive memberInactive = memberDao.getMemberInactivebyMemberId(memberId);
				memberInactive.setPassword(password.encode(pass));
				memberDao.saveOrUpdateMemberInactive(memberInactive);
			}
			
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
		CiResponse response = new CiResponse();
		return response;
		
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public CiResponse saveMemberForSTB(STBInfoDTO stbInfoDTO) throws Exception{
		Member member = new Member(stbInfoDTO);
		
		try {
			memberDao.saveMember(member);
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
		
		MemberNotiDTO noti = new MemberNotiDTO(member.getWifi_mac(), member.getApp_code(), member.isAdult_cert());
		
		try {
			mbsConnector.noti_adult_auth(noti);
		}catch (Exception e) {
			CiLogger.error(e, "Bad GateWay");
			throw new CiException(CiResultCode.BAD_GATEWAY, CiResultCode.MSG.code_503);
		}
		
		 
		return new CiResponse(CiResultCode.SUCCESS,"");
	}
	
	@Transactional( rollbackFor = {Exception.class})
	public Member changeActiveMember(MemberInactive inactive) throws Exception {
		Member tmpMember = new Member(new MemberDTO(inactive));
		try {
			Long id = memberDao.saveMember(tmpMember);
			tmpMember.setId(id);
			memberDao.deleteMemberInactive(inactive);
			return tmpMember;
		}catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
	}
	
	
}
