package com.castis.pvs.user.dao;

import com.castis.pvs.entity.UserRoleMap;

public interface UserRoleMapDao {

	int createUserRoleMap(UserRoleMap userRoleMap);

	UserRoleMap getUserRoleMap(int userRoleMapId);

	UserRoleMap getUserRoleMapByRoleId(String roleId);

	UserRoleMap getUserRoleMapByUserId(String userId);

	void deleteUserRoleMap(UserRoleMap userRoleMap);

}
