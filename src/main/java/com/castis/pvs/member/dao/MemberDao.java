package com.castis.pvs.member.dao;

import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.member.entity.AddressRef;
import com.castis.pvs.member.entity.BankRef;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository("memberDao")
@Transactional(propagation = Propagation.SUPPORTS)
public class MemberDao {
	@PersistenceContext
	private EntityManager entityManager;

	public Member getMemberbyMemberId(String memberId) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("member_id", memberId));
		// crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}

	public Member getMemberbyEmail(String email) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("email", email));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}


	public MemberInactive getMemberInactivebyMemberId(String memberId) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(MemberInactive.class);
		crit.add(Restrictions.eq("member_id", memberId));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("unregister", false));
		MemberInactive member = (MemberInactive) crit.uniqueResult();
		return member;
	}
	
	public Member getMemberById(Long id) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("id", id));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Long saveMember(Member member) {
		Long id = (Long) entityManager.unwrap(Session.class).save(member);
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Long saveMemberInactive(MemberInactive member) {
		Long id = (Long) entityManager.unwrap(Session.class).save(member);
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMember(Member member) {
		Member managedMember = entityManager.find(Member.class, member.getId());
		if (managedMember != null) {
			entityManager.remove(managedMember);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMemberInactive(MemberInactive member) {
		entityManager.unwrap(Session.class).delete(member);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String saveOrUpdateMember(Member member) {
		entityManager.unwrap(Session.class).saveOrUpdate(member);
		return "success";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String saveOrUpdateMemberInactive(MemberInactive member) {
		entityManager.unwrap(Session.class).saveOrUpdate(member);
		return "success";
	}

	
	public List<BankRef> getBankRefList() {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(BankRef.class);
		@SuppressWarnings("unchecked")
		List<BankRef> bankRefList = crit.list();
		return bankRefList;
	}
	
	public BankRef getBankRefById(int id) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(BankRef.class);
		crit.add(Restrictions.eq("id", id));
		BankRef bank = (BankRef) crit.uniqueResult();
		return bank;
	}
	
	public List<AddressRef> getAddressRefList() {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(AddressRef.class);
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.distinct(Projections.property("city_id")),"city_id");
		projection.add(Projections.property("city_name"),"city_name");
		crit.setProjection(projection);
		crit.setResultTransformer(Transformers.aliasToBean(AddressRef.class));
		@SuppressWarnings("unchecked")
		List<AddressRef> addressRefList = crit.list();
		return addressRefList;
	}
	
	public List<AddressRef> getAddressRefListByCityId(int id) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(AddressRef.class);
		
		crit.add(Restrictions.eq("city_id", id));
		
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.distinct(Projections.property("dist_id")),"dist_id");
		projection.add(Projections.property("dist_name"),"dist_name");
		crit.setProjection(projection);
		
		crit.setResultTransformer(Transformers.aliasToBean(AddressRef.class));
		@SuppressWarnings("unchecked")
		List<AddressRef> addressRefList = crit.list();
		return addressRefList;
	}
	

	
	public Member selectOneMember(Map<String, Object> paramMap) throws Exception{

		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Member.class);
			criteria.createAlias("this.card", "card", JoinType.INNER_JOIN);
	
			if(paramMap!=null && paramMap.keySet()!=null && paramMap.keySet().isEmpty()==false){
	
				Set<String> keySet = paramMap.keySet();
				for(String key : keySet){
					Object value = paramMap.get(key);
					if(value instanceof Integer){ criteria.add(Restrictions.eq(key, (Integer)value));}
					else if(value instanceof Short){ criteria.add(Restrictions.eq(key, (Short)value));}
					else if (value instanceof Long){ criteria.add(Restrictions.eq(key, (Long)value));}
					else if(value instanceof Float){ criteria.add(Restrictions.eq(key, (Float)value));}
					else if (value instanceof String){criteria.add(Restrictions.eq(key, (String)value));}
					else if(value instanceof Double){ criteria.add(Restrictions.eq(key, (Double)value));}
					else if(value instanceof Number){ criteria.add(Restrictions.eq(key, (Number)value));}
					else if (value instanceof Collection){ criteria.add(Restrictions.in(key, (Collection<?>)value)); }
					else if (value instanceof java.util.Date){ criteria.add(Restrictions.eq(key, (java.util.Date)value)); }
					else if (value instanceof java.sql.Date){ criteria.add(Restrictions.eq(key, (java.sql.Date)value)); }
					else if(value instanceof SimpleExpression){ criteria.add((SimpleExpression)value); }
					else if(value instanceof Criterion){ criteria.add((Criterion)value); }
					else if(value instanceof Order){ criteria.addOrder((Order) value); }
					else { criteria.add(Restrictions.eq(key, value)); }
				}
			}
			
			Object obj = criteria.uniqueResult();
			if(obj==null){
				return null;
			}
			
			return (Member) obj;
		
	}
	
	public Member selectOneMemberById(Map<String, Object> paramMap) throws Exception{

		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Member.class);

		if(paramMap!=null && paramMap.keySet()!=null && paramMap.keySet().isEmpty()==false){

			Set<String> keySet = paramMap.keySet();
			for(String key : keySet){
				Object value = paramMap.get(key);
				if(value instanceof Integer){ criteria.add(Restrictions.eq(key, (Integer)value));}
				else if(value instanceof Short){ criteria.add(Restrictions.eq(key, (Short)value));}
				else if (value instanceof Long){ criteria.add(Restrictions.eq(key, (Long)value));}
				else if(value instanceof Float){ criteria.add(Restrictions.eq(key, (Float)value));}
				else if (value instanceof String){criteria.add(Restrictions.eq(key, (String)value));}
				else if(value instanceof Double){ criteria.add(Restrictions.eq(key, (Double)value));}
				else if(value instanceof Number){ criteria.add(Restrictions.eq(key, (Number)value));}
				else if (value instanceof Collection){ criteria.add(Restrictions.in(key, (Collection<?>)value)); }
				else if (value instanceof java.util.Date){ criteria.add(Restrictions.eq(key, (java.util.Date)value)); }
				else if (value instanceof java.sql.Date){ criteria.add(Restrictions.eq(key, (java.sql.Date)value)); }
				else if(value instanceof SimpleExpression){ criteria.add((SimpleExpression)value); }
				else if(value instanceof Criterion){ criteria.add((Criterion)value); }
				else if(value instanceof Order){ criteria.addOrder((Order) value); }
				else { criteria.add(Restrictions.eq(key, value)); }
			}
		}
		
		Object obj = criteria.uniqueResult();
		if(obj==null){
			return null;
		}
		
		return (Member) obj;
	
	}
	
	public List<Member> getMemberbyTel(String tel) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("tel", tel));
		crit.add(Restrictions.eq("unregister", false));
		@SuppressWarnings("unchecked")
		List<Member> member = crit.list();
		return member;
	}

	public Member getUniqueMemberbyTel(String tel) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		//crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("tel", tel));
		crit.add(Restrictions.eq("unregister", false));
		@SuppressWarnings("unchecked")
		Member member = (Member) crit.uniqueResult();
		return member;
	}
	
	public List<MemberInactive> getMemberInactivebyTel(String tel, String appCode) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(MemberInactive.class);
		// crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("tel", tel));
		crit.add(Restrictions.eq("unregister", false));
		@SuppressWarnings("unchecked")
		List<MemberInactive> member = crit.list();
		return member;
	}
	
	public Member getMemberbyIdTel(String memberId, String tel) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("member_id", memberId));
		// crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("tel", tel));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}

	public Member getMemberbyIdTelPw(String memberId, String pw) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("tel", memberId));
		// crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("password", pw));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}

	public Member getMemberbyIdEmailPw(String memberId, String pw) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("member_id", memberId));
		// crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("password", pw));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}
	
	public MemberInactive getMemberInactivebyIdTel(String memberId, String tel) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(MemberInactive.class);
		crit.add(Restrictions.eq("member_id", memberId));
		//crit.add(Restrictions.eq("app_code", appCode));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("tel", tel));
		crit.add(Restrictions.eq("unregister", false));
		MemberInactive member = (MemberInactive) crit.uniqueResult();
		return member;
	}

	public Member getMemberbyMemberIdAndCi(String memberId, String ci) {
		Criteria crit = entityManager.unwrap(Session.class).createCriteria(Member.class);
		crit.add(Restrictions.eq("member_id", memberId));
		crit.add(Restrictions.eq("ci", ci));
		crit.add(Restrictions.eq("type", PVSConstants.ACCOUNT.TYPE.MEMBER));
		crit.add(Restrictions.eq("unregister", false));
		Member member = (Member) crit.uniqueResult();
		return member;
	}
}
