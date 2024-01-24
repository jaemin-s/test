package com.castis.pvs.role.service.impl;

import com.castis.pvs.entity.Role;
import com.castis.pvs.role.dao.RoleDao;
import com.castis.pvs.role.service.RoleService;
import com.castis.pvs.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public List<Role> getRolesFromUser(String UserId) {
		return roleDao.getRolesFromUser(UserId);
	}

	@Override
	@Transactional
	public List<Role> listRoles() {
		List<Role> roles = roleDao.listRoles();
		// Do not allow showing System Admin role
		roles.removeIf(role -> role.getId().equalsIgnoreCase(Constants.UserType.SYSADMIN.name()));
		return roles;
	}

}
