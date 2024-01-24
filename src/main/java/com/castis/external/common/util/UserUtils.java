package com.castis.external.common.util;

import com.castis.pvs.user.entity.CMSUserDetails;
import com.castis.pvs.util.Constants.UserType;
import org.springframework.security.core.GrantedAuthority;

public class UserUtils {
	public static boolean isAdmin(CMSUserDetails userDetail) {
		for (GrantedAuthority authority : userDetail.getAuthorities()) {
			if (UserType.SYSADMIN.getValue().equalsIgnoreCase(authority.getAuthority()) || UserType.SO.getValue().equalsIgnoreCase(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	public static boolean isQc(CMSUserDetails userDetail) {
		for (GrantedAuthority authority : userDetail.getAuthorities()) {
			if (UserType.QC.getValue().equalsIgnoreCase(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	

	public static boolean isADProvider(CMSUserDetails userDetail) {
		for (GrantedAuthority authority : userDetail.getAuthorities()) {
			if (UserType.AD_CP.getValue().equalsIgnoreCase(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
}
