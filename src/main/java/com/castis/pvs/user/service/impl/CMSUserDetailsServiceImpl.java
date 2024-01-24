package com.castis.pvs.user.service.impl;

import com.castis.pvs.entity.Role;
import com.castis.pvs.entity.User;
import com.castis.pvs.role.dao.RoleDao;
import com.castis.pvs.user.dao.UserDao;
import com.castis.pvs.user.entity.CMSUserDetails;
import com.castis.pvs.util.Constants.LoginMessage;
import com.castis.pvs.util.Constants.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CMSUserDetailsServiceImpl implements UserDetailsService {

	@Value("${system.siteDefinition:ALL}")
	private String siteDefinition;

	@Autowired
	UserDao userDao;
	@Autowired
	RoleDao roleDao;

	@Override
	public UserDetails loadUserByUsername(String userId) {
		User user = null;
		List<SimpleGrantedAuthority> authorities = null;
		user = userDao.getUser(userId);
		authorities = new ArrayList<>();
		if (user == null) {
			throw new UsernameNotFoundException(LoginMessage.INVALID_USER_OR_PASSWD.name());
		}

		List<Role> roles = roleDao.getRolesFromUser(user.getUserId());

		for (Role role : roles) {
			String roleStr = "ROLE_" + role.getId();
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleStr);
			authorities.add(authority);
		}

		// site definition check
		List<String> authorityNameString = new ArrayList<>();
		for (int i = 0; i < authorities.size(); i++) {
			authorityNameString.add(authorities.get(i).getAuthority());
		}

		if (authorityNameString.indexOf(UserType.SYSADMIN.getValue()) < 0) {
			if (siteDefinition.equals("CP") && authorityNameString.indexOf(UserType.CP.getValue()) < 0
					&& authorityNameString.indexOf(UserType.AD_CP.getValue()) < 0) {
				throw new AuthenticationServiceException(LoginMessage.CP_AUTHORIZATION.name());
			} else if (siteDefinition.equals("SO") && authorityNameString.indexOf(UserType.SO.getValue()) < 0) {
				throw new AuthenticationServiceException(LoginMessage.OP_AUTHORIZATION.name());
			}
		}

		return new CMSUserDetails(user.getUserId(), user.getPassword(), authorities, user.getDisplayName(),null);
	}
}
