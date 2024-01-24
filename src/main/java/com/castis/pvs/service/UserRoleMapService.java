package com.castis.pvs.service;

import com.castis.pvs.entity.UserRoleMap;
import com.castis.pvs.exception.DBNotFoundException;

public interface UserRoleMapService {

	void createUserRoleMap(UserRoleMap userRoleMap);

	UserRoleMap getUserRoleMap(int userRoleMapId) throws DBNotFoundException;

	UserRoleMap getUserRoleMapByRoleId(String roleId) throws DBNotFoundException;

	UserRoleMap getUserRoleMapByUserId(String userId) throws DBNotFoundException;

}
