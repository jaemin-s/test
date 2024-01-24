package com.castis.pvs.security.controller;

import com.castis.pvs.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("counter")
public class SecurityController extends BaseController {

	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied() {
		return "common/exception/access_denied_page";
	}

	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public String unauthorized() {
		return "common/exception/unauthorized_page";
	}

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.GET) public String
	 * loginForm() { return "home/login"; }
	 */

}
