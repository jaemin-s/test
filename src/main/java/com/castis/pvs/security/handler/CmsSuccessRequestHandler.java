package com.castis.pvs.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CmsSuccessRequestHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	public CmsSuccessRequestHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);
		setUseReferer(true);

		if (response.isCommitted()) {
			log.info("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {

		Map<String, String> roleTargetUrlMap = new HashMap<>();
//		roleTargetUrlMap.put("ROLE_SYSADMIN", "/users/view.do");
		roleTargetUrlMap.put("ROLE_SYSADMIN", "/postTest.do");
//		roleTargetUrlMap.put("ROLE_SO", "/group/viewListMain.do");
//		roleTargetUrlMap.put("ROLE_QC", "/qc/viewListMain.do");
//		roleTargetUrlMap.put("ROLE_AD_CP", "/adclip/viewListMain.do");
//		roleTargetUrlMap.put("ROLE_CP", "/group/viewListMain.do");
		roleTargetUrlMap.put("MEMBER", "/mobile/");

		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			String authorityName = grantedAuthority.getAuthority();
			if (roleTargetUrlMap.containsKey(authorityName)) {
				return roleTargetUrlMap.get(authorityName);
			}
		}

		throw new IllegalStateException();
	}

	/*
	 * protected void clearAuthenticationAttributes(HttpServletRequest request) {
	 * HttpSession session = request.getSession(false); if (session == null) {
	 * return; } session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); }
	 */

}
