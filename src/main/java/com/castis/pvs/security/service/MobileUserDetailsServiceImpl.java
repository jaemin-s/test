package com.castis.pvs.security.service;

import com.castis.pvs.member.dao.MemberDao;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import com.castis.pvs.security.entity.MobileUserDetail;
import com.castis.pvs.user.service.impl.CMSUserDetailsServiceImpl;
import com.castis.pvs.util.Constants.LoginMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MobileUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	CMSUserDetailsServiceImpl cmsUserDetailsService;
	
	
	@Override
	public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
		Member member = null;
		String[] split = input.split(":");
		if(split.length > 1) {

			String username = split[0];
			String appCode = split[1];
			
			member = memberDao.getMemberbyMemberId(username);
			
			if (member == null) {
				MemberInactive inactive = memberDao.getMemberInactivebyMemberId(username);
				if(inactive == null) {
					throw new UsernameNotFoundException(LoginMessage.INVALID_USER_OR_PASSWD.name());
				} else {
					try {
						Member newMember = new Member(new MemberDTO(inactive));
						Long id = memberDao.saveMember(newMember);
						newMember.setId(id);
						memberDao.deleteMemberInactive(inactive);

						List<SimpleGrantedAuthority> authorities = new ArrayList<>();;
						SimpleGrantedAuthority authority = new SimpleGrantedAuthority("MEMBER");
						authorities.add(authority);
						return new MobileUserDetail(newMember.getId(), newMember.getMember_id(), newMember.getPassword(), authorities);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				List<SimpleGrantedAuthority> authorities = new ArrayList<>();;
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("MEMBER");
				authorities.add(authority);
				return new MobileUserDetail(member.getId(), member.getMember_id(), member.getPassword(), authorities);
			}
		}
		else {
			UserDetails userDetails = 	cmsUserDetailsService.loadUserByUsername(input);
			if(userDetails!=null) return userDetails;
		}
		
		throw new UsernameNotFoundException(LoginMessage.INVALID_USER_OR_PASSWD.name());
	}
	

}
