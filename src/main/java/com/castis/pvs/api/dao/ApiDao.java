package com.castis.pvs.api.dao;

import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("apiDao")
@Transactional(propagation = Propagation.SUPPORTS)
public class ApiDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Member getMemberByPassword(String id, String password) {
        Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
        crit.add(Restrictions.eq("member_id", id));
        crit.add(Restrictions.eq("password", password));
        crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
        // crit.add(Restrictions.eq("app_code", appCode));
        Member member = (Member) crit.uniqueResult();
        return member;
    }

    public Member getMember(String id) {
        Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
        crit.add(Restrictions.eq("member_id", id));
        crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
        Member member = (Member) crit.uniqueResult();
        return member;
    }

    public MemberInactive getMemberInactiveByPassword(String id, String password) {
        Criteria crit = entityManager.unwrap(Session.class).createCriteria(MemberInactive.class);
        crit.add(Restrictions.eq("member_id", id));
        crit.add(Restrictions.eq("password", password));
        crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
        // crit.add(Restrictions.eq("app_code", appCode));
        MemberInactive member = (MemberInactive) crit.uniqueResult();
        return member;
    }

    public Member getMemberbySTB(String memberId) {
        Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
        crit.add(Restrictions.eq("member_id", memberId));
        // crit.add(Restrictions.eq("app_code", appCode));
        //crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.STB));
        Member member = (Member) crit.uniqueResult();
        return member;
    }


    public List<Member> getMemberBySoHappyCallAuth(boolean soHappyCallAuth) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> member = cq.from(Member.class);
        cq.where(cb.equal(member.get("type"), PVSConstants.ACCOUNT.TYPE.MEMBER), cb.equal(member.get("so_happycall_auth"), soHappyCallAuth));

        return entityManager.createQuery(cq).getResultList();
    }
}
