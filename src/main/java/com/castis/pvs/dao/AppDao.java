package com.castis.pvs.dao;

import com.castis.pvs.entity.App;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository("AppDao")
@Transactional(propagation = Propagation.SUPPORTS)
public class AppDao {
	@PersistenceContext
	private EntityManager entityManager;

	public App getAppbyTitle(String title) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(App.class);
		crit.add(Restrictions.eq("title", title));
		return (App) crit.uniqueResult();
	}
}
