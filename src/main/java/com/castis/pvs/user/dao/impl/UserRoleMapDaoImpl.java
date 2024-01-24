package com.castis.pvs.user.dao.impl;

import com.castis.pvs.entity.UserRoleMap;
import com.castis.pvs.user.dao.UserRoleMapDao;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRoleMapDaoImpl implements UserRoleMapDao {
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public int createUserRoleMap(UserRoleMap userRoleMap) {
		return (int) entityManager.unwrap(Session.class).save(userRoleMap);
	}

	@Override
	public UserRoleMap getUserRoleMap(int userRoleMapId) {
		return (UserRoleMap) entityManager.unwrap(Session.class).get(UserRoleMap.class, userRoleMapId);
	}

	@Override
	public UserRoleMap getUserRoleMapByRoleId(String roleId) {
		return (UserRoleMap) entityManager.unwrap(Session.class).createCriteria(UserRoleMap.class)
				.add(Restrictions.eq("role.id", roleId)).uniqueResult();
	}

	@Override
	public UserRoleMap getUserRoleMapByUserId(String userId) {
		return (UserRoleMap) entityManager.unwrap(Session.class).createCriteria(UserRoleMap.class)
				.add(Restrictions.eq("user.userId", userId)).uniqueResult();
	}

	@Override
	public void deleteUserRoleMap(UserRoleMap userRoleMap) {
		entityManager.unwrap(Session.class).delete(userRoleMap);
	}

}
