package com.castis.pvs.service.impl;

import com.castis.pvs.entity.UserRoleMap;
import com.castis.pvs.exception.DBNotFoundException;
import com.castis.pvs.service.UserRoleMapService;
import com.castis.pvs.user.dao.UserRoleMapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@lombok.extern.slf4j.Slf4j
public class UserRoleMapServiceImpl implements UserRoleMapService {

	@Autowired
	private UserRoleMapDao userRoleMapDao;

	@Override
	@Transactional
	public void createUserRoleMap(UserRoleMap userRoleMap) {
		int newUserRoleMap = userRoleMapDao.createUserRoleMap(userRoleMap);
		log.info("Created User-Role Map({})", newUserRoleMap);
	}

	@Override
	@Transactional
	public UserRoleMap getUserRoleMap(int userRoleMapId) throws DBNotFoundException {
		UserRoleMap dbMap = userRoleMapDao.getUserRoleMap(userRoleMapId);
		if (dbMap == null) {
			throw new DBNotFoundException("User Role Map ", String.valueOf(userRoleMapId));
		}
		return dbMap;
	}

	@Override
	@Transactional
	public UserRoleMap getUserRoleMapByRoleId(String roleId) throws DBNotFoundException {
		UserRoleMap dbMap = userRoleMapDao.getUserRoleMapByRoleId(roleId);
		if (dbMap == null) {
			throw new DBNotFoundException("User Role Map by Role", String.valueOf(roleId));
		}
		return dbMap;
	}

	@Override
	@Transactional
	public UserRoleMap getUserRoleMapByUserId(String userId) throws DBNotFoundException {
		UserRoleMap dbMap = userRoleMapDao.getUserRoleMapByUserId(userId);
		if (dbMap == null) {
			throw new DBNotFoundException("User Role Map by User", userId);
		}
		return dbMap;
	}
}
