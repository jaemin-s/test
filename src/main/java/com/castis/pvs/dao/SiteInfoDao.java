package com.castis.pvs.dao;

import com.castis.pvs.entity.SiteInfo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("ImsServerInfoDao")
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteInfoDao {

    @PersistenceContext
    private EntityManager entityManager;


    public SiteInfo getSiteInfobyMsoName(String title) {
        Criteria crit = entityManager.unwrap(Session.class).createCriteria(SiteInfo.class);
        crit.add(Restrictions.eq("mso_name", title));
        return (SiteInfo) crit.uniqueResult();
    }

    public SiteInfo getSiteInfobySoId(String soId) {
        Criteria crit = entityManager.unwrap(Session.class).createCriteria(SiteInfo.class);
        crit.add(Restrictions.eq("so_id", soId));
        return (SiteInfo) crit.uniqueResult();
    }
}
