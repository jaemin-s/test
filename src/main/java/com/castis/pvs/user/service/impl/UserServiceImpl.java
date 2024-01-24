package com.castis.pvs.user.service.impl;

import com.castis.pvs.entity.Role;
import com.castis.pvs.entity.User;
import com.castis.pvs.entity.UserRoleMap;
import com.castis.pvs.exception.DBAlreadyExistsException;
import com.castis.pvs.exception.DBNotFoundException;
import com.castis.pvs.exception.IncorrectOldPasswordException;
import com.castis.pvs.role.dao.RoleDao;
import com.castis.pvs.service.UserRoleMapService;
import com.castis.pvs.user.dao.UserDao;
import com.castis.pvs.user.dao.UserRoleMapDao;
import com.castis.pvs.user.dto.TableUserDTO;
import com.castis.pvs.user.dto.UserDTO;
import com.castis.pvs.user.service.UserService;
import com.castis.pvs.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;


	@Autowired
	private UserRoleMapService userRoleMapService;

	@Autowired
	private UserRoleMapDao userRoleMapDao;

	@Value("${user.so.default.cpId:administrator}")
	private String defaultCpId;

	private String getProviderIdByUserId(String userId) {
		// Get user by specific admin of a CP is not implemented yet.
		// By default set it to null
		return null;
	}

	@Override
	@Transactional
	public void editUserDTO(UserDTO userDTO) throws DBNotFoundException {
		User dbUser = getUser(userDTO.getUserId());
		dbUser.setMobile(userDTO.getMobile());
		dbUser.setEmail(userDTO.getEmail());
		dbUser.setDisplayName(userDTO.getDisplayName());
		dbUser.setLastUpdateTime(new Date());
	}

	@Override
	@Transactional
	public void editUser(User user) {
		userDao.editUser(user);
	}

	@Override
	@Transactional
	public List<TableUserDTO> listUserDTO(String userId) {
		String providerId = getProviderIdByUserId(userId);
		List<TableUserDTO> usrList = userDao.listUserDTO(providerId);
		// Remove system admin user, hide him from UI
		usrList.removeIf(usr -> usr.getRoles().stream()
				.anyMatch(role -> role.getId().equalsIgnoreCase(Constants.UserType.SYSADMIN.name())));
		return usrList;
	}

	@Override
	@Transactional
	public User getUser(String userId) throws DBNotFoundException {
		User dbUser = userDao.getUser(userId);
		if (dbUser == null) {
			throw new DBNotFoundException("User", userId);
		}
		return userDao.getUser(userId);
	}

	@Override
	@Transactional
	public UserDTO getUserDTO(String userId) throws DBNotFoundException {
		UserDTO dbUser = userDao.getUserDTO(userId);
		if (dbUser == null) {
			throw new DBNotFoundException("User", userId);
		}
		return dbUser;
	}

	@Override
	@Transactional
	public void editPassword(String userId, String password, String newPassword)
			throws IncorrectOldPasswordException, DBNotFoundException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User dbUser = getUser(userId);
		if (!passwordEncoder.matches(password, dbUser.getPassword())) {
			throw new IncorrectOldPasswordException(String.format("(UserId: %s, oldPasswd: %s)", userId, password));
		}

		dbUser.setPassword(passwordEncoder.encode(newPassword));
		dbUser.setLastUpdateTime(new Date());
	}

	@Override
	@Transactional
	public void deleteUser(String userId) throws DBNotFoundException {
		User dbUser = getUser(userId);
		dbUser.getUserRoleMaps().stream().forEach(userRoleMap -> userRoleMapDao.deleteUserRoleMap(userRoleMap));
		userDao.deleteUser(dbUser);
	}

	@Override
	@Transactional
	public void createUser(UserDTO userDTO) throws DBAlreadyExistsException {
		// Check user exist?
		User dbUser = userDao.getUser(userDTO.getUserId());
		if (dbUser != null) {
			throw new DBAlreadyExistsException("UserId", userDTO.getUserId());
		}
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		User newUser = new User();
		newUser.setUserId(userDTO.getUserId());
		newUser.setPassword(pwEncoder.encode(userDTO.getPassword()));
		newUser.setDisplayName(userDTO.getDisplayName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setMobile(userDTO.getMobile());

		Date curDate = new Date();
		newUser.setCreationTime(curDate);
		newUser.setLastUpdateTime(curDate);
		userDao.createUser(newUser);

		Role dbRole = roleDao.getRole(userDTO.getRoleId());
		UserRoleMap newUsrRoleMap = new UserRoleMap();
		newUsrRoleMap.setRole(dbRole);
		newUsrRoleMap.setUser(newUser);
		userRoleMapService.createUserRoleMap(newUsrRoleMap);
	}

}
