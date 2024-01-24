package com.castis.pvs.role.dao;

import com.castis.pvs.entity.Role;

import java.util.List;

public interface RoleDao {
	List<Role> getRolesFromUser(String userId);

	List<Role> listRoles();
	
	Role getRole(String roleId);

}
