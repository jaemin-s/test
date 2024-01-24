package com.castis.pvs.user.dao.impl;

import com.castis.pvs.entity.User;
import com.castis.pvs.role.dao.RoleDao;
import com.castis.pvs.user.dao.UserDao;
import com.castis.pvs.user.dto.TableUserDTO;
import com.castis.pvs.user.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	private final RoleDao roleDao;
	@PersistenceContext
	private EntityManager entityManager;

	public UserDaoImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public User getUser(String userId) {
		return (User) entityManager.unwrap(Session.class).get(User.class, userId);
	}

	@Override
	public UserDTO getUserDTO(String userId) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT u.userId AS userId, ");
		queryBuilder.append("u.displayName AS displayName, ");
		queryBuilder.append("u.email AS email, ");
		queryBuilder.append("u.mobile AS mobile, ");
		queryBuilder.append("provider.providerId AS providerId, ");
		queryBuilder.append("u.creationTime AS creationTime, ");
		queryBuilder.append("u.lastUpdateTime AS lastUpdateTime ");
		queryBuilder.append("FROM User u ");
		queryBuilder.append("LEFT JOIN u.contentProvider provider ");

		queryBuilder.append("WHERE u.userId = :userId");
		Query query = entityManager.unwrap(Session.class).createQuery(queryBuilder.toString());
		query.setString("userId", userId);

		return (UserDTO) query.setResultTransformer(Transformers.aliasToBean(UserDTO.class)).uniqueResult();
	}

	@Override
	public void deleteUser(User user) {
		entityManager.unwrap(Session.class).delete(user);
	}

	@Override
	public void editUser(User user) {
		entityManager.unwrap(Session.class).update(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TableUserDTO> listUserDTO(String providerId) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT u.userId AS userId, ");
		queryBuilder.append("u.displayName AS displayName, ");
		queryBuilder.append("u.contentProvider.cpName AS providerName, ");
		queryBuilder.append("u.creationTime AS creationTime, ");
		queryBuilder.append("u.lastUpdateTime AS lastUpdateTime ");
		queryBuilder.append("FROM User u ");
		queryBuilder.append("LEFT JOIN u.contentProvider provider ");

		if (StringUtils.isNotEmpty(providerId)) {
			queryBuilder.append("WHERE provider.providerId = :providerId");
		}

		Query query = entityManager.unwrap(Session.class).createQuery(queryBuilder.toString());
		if (StringUtils.isNotEmpty(providerId)) {
			query.setString("providerId", providerId);
		}

		List<TableUserDTO> usrDTOs = query.setResultTransformer(Transformers.aliasToBean(TableUserDTO.class)).list();
		usrDTOs.forEach(usrDTO -> usrDTO.setRoles(roleDao.getRolesFromUser(usrDTO.getUserId())));
		return usrDTOs;

	}

	@Override
	public void createUser(User newUser) {
		entityManager.unwrap(Session.class).save(newUser);
	}
}
