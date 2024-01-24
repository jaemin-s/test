package com.castis.pvs.role.dao.impl;

import com.castis.pvs.entity.Role;
import com.castis.pvs.role.dao.RoleDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
@Transactional
public class RoleDaoImpl implements RoleDao {
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Role> getRolesFromUser(String userId) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT r FROM Role r ");
		queryBuilder.append("INNER JOIN r.userRoleMaps urm ");
		queryBuilder.append("WHERE urm.user.userId = :userId");
		
		Query query = entityManager.unwrap(Session.class).createQuery(queryBuilder.toString());
		query.setString("userId", userId);
		return query.list();
	}

	@Override
	public List<Role> listRoles() {
		return entityManager.unwrap(Session.class).createCriteria(Role.class).list();
	}

	@Override
	public Role getRole(String roleId) {
		return (Role) entityManager.unwrap(Session.class).get(Role.class, roleId);
	}

}
