package com.castis.pvs.user.controller;

import com.castis.pvs.controller.BaseController;
import com.castis.pvs.entity.Role;
import com.castis.pvs.entity.User;
import com.castis.pvs.exception.DBAlreadyExistsException;
import com.castis.pvs.exception.DBNotFoundException;
import com.castis.pvs.exception.IncorrectOldPasswordException;
import com.castis.pvs.exception.PopupException;
import com.castis.pvs.role.service.RoleService;
import com.castis.pvs.user.dto.TableUserDTO;
import com.castis.pvs.user.dto.UserDTO;
import com.castis.pvs.user.entity.CMSUserDetails;
import com.castis.pvs.user.service.UserService;
import com.castis.pvs.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@lombok.extern.slf4j.Slf4j
public class UserController extends BaseController {

	private static final String PASSWORD_COMBINATION_RULE_ERROR = "Password combination rule error";

	private static final String USER_INFO = "User";

	private static final String ERROR_DB_NOT_FOUND = "error.dbNotFound";

	private static final String LOG_POPUP_ERROR = "{}: {}";

	private static final String ERROR_GENERAL = "error.general";

	private static final String SUCCESS = "Success";
	private static final int MIN_PASSWORD_LENGTH = 3;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;


	@Value("${user.so.default.cpId:administrator}")
	private String defaultCpId;

	// public String createUserPopup
	// public String createUser

	@RequestMapping(value = "/users/view", method = RequestMethod.GET)
	public String listUserView(Model model, HttpServletRequest request) {
		CMSUserDetails adminUserDetail = (CMSUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TableUserDTO> userList = userService.listUserDTO(adminUserDetail.getUsername());

		// Add this to disable delete current admin user
		model.addAttribute("currentAdmin", adminUserDetail.getUsername());
		model.addAttribute("userTypeMap", Constants.UserType.getEnumMap());
		model.addAttribute("userState", Constants.UserState.getEnumMap());
		model.addAttribute("userList", userList);
		return "user/list_user_view_main";
	}

	@RequestMapping(value = "/users/create", method = RequestMethod.GET)
	public String createUserPopup(Model model, HttpServletRequest request) throws PopupException {
		try {
			List<Role> roleList = roleService.listRoles();
			model.addAttribute("roleList", roleList);
			model.addAttribute("userDTO", new UserDTO());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PopupException(wmSource.getMessage(ERROR_GENERAL));
		}
		return "user/create_user_popup";
	}

	@RequestMapping(value = "/users/{userId}/view", method = RequestMethod.GET)
	public String editUserPopup(Model model, HttpServletRequest request, @PathVariable String userId)
			throws PopupException {
		if (StringUtils.isBlank(userId)) {
			throw new PopupException(wmSource.getMsg("error.emptyInput", "User ID"));
		}
		try {
			UserDTO userDTO = userService.getUserDTO(userId);
			model.addAttribute("userDTO", userDTO);
			model.addAttribute("isSO", userDTO.getProviderId().equalsIgnoreCase(defaultCpId));
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			throw new PopupException(wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PopupException(wmSource.getMessage(ERROR_GENERAL));
		}

		return "user/edit_user_popup";
	}

	@RequestMapping(value = "/users/{userId}/password", method = RequestMethod.GET)
	public String editPasswordPopup(@PathVariable String userId, Model model) throws PopupException {
		if (StringUtils.isBlank(userId)) {
			throw new PopupException(wmSource.getMsg("error.emptyInput", "User ID"));
		}
		model.addAttribute("userId", userId);
		return "user/edit_password_popup";
	}

	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String createUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		try {
			CMSUserDetails creatorDetails = (CMSUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			log.info("User ({}) requested to create User({})", creatorDetails.getUsername(), userDTO.toString());
			if (!validatePassword(userDTO.getPassword())) {
				return PASSWORD_COMBINATION_RULE_ERROR;
			}
			userService.createUser(userDTO);
			log.info("User ({}) created User({})", creatorDetails.getUsername(), userDTO.getUserId());
			return SUCCESS;
		} catch (DBAlreadyExistsException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMessage("error.user.same.userId");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wmSource.getMessage(ERROR_GENERAL);
		}
	}

	private boolean validatePassword(String password) {
		if (StringUtils.isBlank(password) || password.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
	public String editUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		try {
			CMSUserDetails creatorDetails = (CMSUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			log.info("User ({}) requested to edit User({})", creatorDetails.getUsername(), userDTO.toString());
			userService.editUserDTO(userDTO);
			log.info("User ({}) edited User({})", creatorDetails.getUsername(), userDTO.getUserId());
			return SUCCESS;
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wmSource.getMessage(ERROR_GENERAL);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/users/password", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
	public String changePassword(@RequestBody Map<String, String> requestMap) {
		try {
			String password = requestMap.get("password");
			String newPassword = requestMap.get("newPassword");
			String userId = requestMap.get("userId");

			if (!validatePassword(password)) {
				return PASSWORD_COMBINATION_RULE_ERROR;
			}
			if (!validatePassword(newPassword)) {
				return PASSWORD_COMBINATION_RULE_ERROR;
			}
			CMSUserDetails creatorDetails = (CMSUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			log.info("User ({}) requested to change password of User({})", creatorDetails.getUsername(), userId);
			userService.editPassword(userId, password, newPassword);
			log.info("User ({}) changed password User({})", creatorDetails.getUsername(), userId);
			return SUCCESS;

		} catch (IncorrectOldPasswordException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMessage("error.oldPassword.incorrect");
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wmSource.getMessage(ERROR_GENERAL);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
	public String deleteUser(@RequestBody String userId) {
		try {
			CMSUserDetails creatorDetails = (CMSUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			log.info("User ({}) requested to delete User({})", creatorDetails.getUsername(), userId);
			userService.deleteUser(userId);
			log.info("User ({}) deleted User({})", creatorDetails.getUsername(), userId);
			return SUCCESS;
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wmSource.getMessage(ERROR_GENERAL);
		}
	}

	@RequestMapping(value = "/users/editContactsForm", method = RequestMethod.GET)
	public String editAccountForm(Model model, HttpServletRequest request) throws PopupException {
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			model.addAttribute("user", userService.getUserDTO(userId));
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			throw new PopupException(wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PopupException(wmSource.getMessage(ERROR_GENERAL));
		}

		return "user/edit_my_contacts_popup";
	}

	@RequestMapping(value = "/users/editContacts", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String editContacts(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest request,
	                    SessionStatus status) {
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.getUser(userId);
			user.setEmail(userDTO.getEmail());
			user.setMobile(userDTO.getMobile());

			userService.editUser(user);

			status.setComplete();
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wmSource.getMessage(ERROR_GENERAL);
		}

		return SUCCESS;
	}

	@RequestMapping(value = "/users/editMyPasswordForm", method = RequestMethod.GET)
	public String editMyPasswordForm(Model model, HttpServletRequest request) {
		return "user/edit_my_password_popup";
	}

	@RequestMapping(value = "/users/editMyPassword", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String editMyPassword(HttpServletRequest request) {
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");

		if (!validatePassword(password)) {
			return PASSWORD_COMBINATION_RULE_ERROR;
		}

		if (!validatePassword(newPassword)) {
			return PASSWORD_COMBINATION_RULE_ERROR;
		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.getUser(userId);

			if (!passwordEncoder.matches(password, user.getPassword())) {
				return wmSource.getMessage("mymenu.error.password.wrong");
			}

			user.setPassword(passwordEncoder.encode(newPassword));
			userService.editUser(user);
		} catch (DBNotFoundException e) {
			log.error(LOG_POPUP_ERROR, e.getClass().toString(), e.getMessage());
			return wmSource.getMsg(ERROR_DB_NOT_FOUND, USER_INFO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wmSource.getMessage(ERROR_GENERAL);
		}

		return SUCCESS;
	}
}
