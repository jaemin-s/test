package com.castis.pvs.member.controller;

import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.dto.STBInfoDTO;
import com.castis.pvs.member.entity.AddressRef;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import com.castis.pvs.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/checkId", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse checkId(ModelMap model, HttpServletRequest httpRequest) throws Exception {
		CiRequest request = new CiRequest(httpRequest);
		String id = (String) request.getParameter("memberId");
		String appCode = (String) request.getParameter("appCode");
		String result = memberService.checkMemberId(id, appCode);
		CiResponse response = new CiResponse();
		response.addAttribute("flag", result);
		
		return response;
	}
	
	@RequestMapping(value = "/getDistListByCity", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse getDistListByCity(ModelMap model, HttpServletRequest httpRequest) throws Exception {
		CiRequest request = new CiRequest(httpRequest);
		String sid = (String) request.getParameter("city_id");
		Integer id = Integer.parseInt(sid);
		List<AddressRef> result = memberService.getAdderssRefListByCityId(id);
		CiResponse response = new CiResponse();
		response.addAttribute("distList", result);
		
		return response;
	}

	@RequestMapping(value = "/modifyMember", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse modifyMember(HttpServletRequest httpRequest, @RequestBody MemberDTO memberDTO) throws Exception {
		
		CiResponse response = memberService.updateMemeber(memberDTO);
		
		return response;
	}

	@RequestMapping(value = "/findByTel", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse findByTel(ModelMap model, HttpServletRequest httpRequest) throws Exception {
		CiRequest request = new CiRequest(httpRequest);
		String tel = (String) request.getParameter("tel");
		String appCode = (String) request.getParameter("appCode");
		List<String> result = memberService.findMemberIdByTel(tel, appCode);
		CiResponse response = new CiResponse();
		response.addAttribute("resultList", result);
		
		return response;
	}
	
	@RequestMapping(value = "/findPass", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse findPass(ModelMap model, HttpServletRequest httpRequest) throws Exception {
		CiRequest request = new CiRequest(httpRequest);
		String memberId = (String) request.getParameter("memberId");
		String appCode = (String) request.getParameter("appCode");
		String tel = (String) request.getParameter("tel");
		Member result = memberService.findMemberByIdTel(memberId, tel);
		CiResponse response = new CiResponse();
		if(result != null) {
			response.addAttribute("result", result);
		} else {
			MemberInactive result2 = memberService.findMemberInactiveByIdTel(memberId, tel);
			response.addAttribute("result", result2);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse changePass(ModelMap model, HttpServletRequest httpRequest) throws Exception {
		CiRequest request = new CiRequest(httpRequest);
		String memberId = (String) request.getParameter("memberId");
		String appCode = (String) request.getParameter("appCode");
		String pass = (String) request.getParameter("pass");
		
		CiResponse response = memberService.changeMemberPassword(memberId, pass);
		
		return response;
	}


	@RequestMapping(value = "/checkSTB", method = RequestMethod.POST)
	@ResponseBody
	public CiResponse checkSTB(HttpServletRequest httpRequest, @RequestBody STBInfoDTO stbInfoDTO) throws Exception {

		CiResponse response = memberService.saveMemberForSTB(stbInfoDTO);
		return response;
	}

}
