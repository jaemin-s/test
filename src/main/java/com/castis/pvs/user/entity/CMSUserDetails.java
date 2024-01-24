package com.castis.pvs.user.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
@Data
public class CMSUserDetails implements UserDetails, Serializable {
	private String username;
	private String password;
	private final List<? extends GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;

	private String userDisplayName;
	private String providerId;

	public CMSUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String userDisplayName, String providerId) {
		this.username = username;
		this.password = password;
		this.authorities = (List<? extends GrantedAuthority>) authorities;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;

		this.userDisplayName = userDisplayName;
		this.providerId = providerId;
	}

	public CMSUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			String userDisplayName, String providerId) {
		this.username = username;
		this.password = password;
		this.authorities = (List<? extends GrantedAuthority>) authorities;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;

		this.userDisplayName = userDisplayName;
		this.providerId = providerId;
	}

}
