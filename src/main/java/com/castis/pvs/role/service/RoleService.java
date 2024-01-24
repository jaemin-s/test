package com.castis.pvs.role.service;

import com.castis.pvs.entity.Role;

import java.util.List;

public interface RoleService {

	List<Role> getRolesFromUser(String UserId);

	List<Role> listRoles();
}
