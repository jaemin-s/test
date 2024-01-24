package com.castis.pvs.dao;

import com.castis.pvs.entity.CommonConfig;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;

@Repository("commonConfigDao")
@Transactional( propagation = Propagation.SUPPORTS)
public class CommonConfigDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional( propagation = Propagation.REQUIRED, readOnly = false)
	public Serializable save(CommonConfig commonConfig)  throws Exception{
		
		if(commonConfig==null) { return null; }
		
		return  entityManager.unwrap(Session.class).save(commonConfig);
	}
	
	@SuppressWarnings("unchecked")
	public List<CommonConfig> selectList(Map<String, Object> paramMap) throws Exception{
		
		if(paramMap==null) {return null;}
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(CommonConfig.class);

		if(paramMap.keySet()!=null && paramMap.keySet().isEmpty()==false){

			Set<String> keySet = paramMap.keySet();
			for(String key : keySet){
				Object value = paramMap.get(key);
				if("setFirstResult".equalsIgnoreCase(key)) {
					criteria.setFirstResult((Integer)value);
				}else if("setMaxResults".equalsIgnoreCase(key)) {
					criteria.setMaxResults((Integer)value);
				}else {
					if(value instanceof Integer){ criteria.add(Restrictions.eq(key, (Integer)value));}
					else if(value instanceof Short){ criteria.add(Restrictions.eq(key, (Short)value));}
					else if (value instanceof Long){ criteria.add(Restrictions.eq(key, (Long)value));}
					else if(value instanceof Float){ criteria.add(Restrictions.eq(key, (Float)value));}
					else if (value instanceof String){criteria.add(Restrictions.eq(key, (String)value));}
					else if(value instanceof Double){ criteria.add(Restrictions.eq(key, (Double)value));}
					else if(value instanceof Number){ criteria.add(Restrictions.eq(key, (Number)value));}
					else if (value instanceof Collection){ criteria.add(Restrictions.in(key, (Collection<?>)value)); }
					else if (value instanceof Date){ criteria.add(Restrictions.eq(key, (Date)value)); }
					else if (value instanceof java.sql.Date){ criteria.add(Restrictions.eq(key, (java.sql.Date)value)); }
					else if(value instanceof SimpleExpression){ criteria.add((SimpleExpression)value); }
					else if(value instanceof Criterion){ criteria.add((Criterion)value); }
					else if(value instanceof Order){ criteria.addOrder((Order) value); }
					else { criteria.add(Restrictions.eq(key, value)); }
				}
			}
		}
		
		List<?> list = criteria.list();
		if(list==null || list.isEmpty()){
			return null;
		}
		
		return (List<CommonConfig>) list;
		
	}
	
	public CommonConfig selectOne(Map<String, Object> paramMap) throws Exception{
		
		if(paramMap==null) {return null;}
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(CommonConfig.class);

		if(paramMap.keySet()!=null && paramMap.keySet().isEmpty()==false){

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
				else if (value instanceof Date){ criteria.add(Restrictions.eq(key, (Date)value)); }
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
		
		return (CommonConfig) obj;
		
	}

	@Transactional( propagation = Propagation.REQUIRED, readOnly = false)
	public int update(Long idx, Map<String, Object> paramMap) throws Exception {
		
		if(paramMap==null || paramMap.isEmpty())
			return 0;

			Set<String> keySet = paramMap.keySet();
			
			StringBuffer bf = new StringBuffer();
			bf.append("update tbl_common_config                       ");
			
			boolean bFirst=true;
			for(String key : keySet){
				if(bFirst==true){
					bf.append(String.format("set %s = :%s                            ", key, key));
					bFirst = false;
					continue;
				}
				bf.append(String.format(", %s = :%s                            ", key, key));
			}
			bf.append("where idx = :idx      ");
			
			Query sqlQuery = entityManager.unwrap(Session.class).createSQLQuery(bf.toString());
			
			for(String key : keySet){
				
				Object value = paramMap.get(key);
				if(value instanceof String){
					sqlQuery.setString(key, (String)value);
				}else if(value instanceof Long){
					sqlQuery.setLong(key, (Long)value);
				}else if(value instanceof Integer){
					sqlQuery.setInteger(key, (Integer)value);
				}else if(value instanceof Collection){
					sqlQuery.setParameterList(key, (Collection<?>)value);
				}else if(value instanceof Date){
					sqlQuery.setTimestamp(key, (Date)value);
				}
			}
			sqlQuery.setLong("idx", idx);
			return sqlQuery.executeUpdate();
			
		
	}
	
	@Transactional( propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(CommonConfig commonConfig) throws Exception {
		entityManager.unwrap(Session.class).delete(commonConfig);
		
	}
	@Transactional( propagation = Propagation.REQUIRED, readOnly = false)
	public int delete(Map<String, Object> paramMap)
			throws Exception {
		
		if(paramMap==null || paramMap.isEmpty())
			return 0;

			Set<String> keySet = paramMap.keySet();
			
			StringBuffer bf = new StringBuffer();
			bf.append("DELETE FROM tbl_common_config                       ");
			
			boolean bFirst=true;
			for(String key : keySet){

				Object value = paramMap.get(key);
				
				if(bFirst==true){
					if(value instanceof Collection){
						bf.append(String.format("WHERE %s IN ( :%s )                           ", key, key));
					}else {
						bf.append(String.format("WHERE %s = :%s                            ", key, key));
					}
					bFirst = false;
					continue;
				}
				
				if(value instanceof Collection){
					bf.append(String.format(" AND %s IN ( :%s )                           ", key, key));
				}else {
					bf.append(String.format(" AND %s = :%s                            ", key, key));
				}
			}
			
			Query sqlQuery = entityManager.unwrap(Session.class).createSQLQuery(bf.toString());
			
			for(String key : keySet){
				
				Object value = paramMap.get(key);
				if(value instanceof String){
					sqlQuery.setString(key, (String)value);
				}else if(value instanceof Long){
					sqlQuery.setLong(key, (Long)value);
				}else if(value instanceof Integer){
					sqlQuery.setInteger(key, (Integer)value);
				}else if(value instanceof Collection){
					sqlQuery.setParameterList(key, (Collection<?>)value);
				}else if(value instanceof Date){
					sqlQuery.setTimestamp(key, (Date)value);
				}else {
					
				}
			}
			return sqlQuery.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(CommonConfig commonConfig)  throws Exception{
		entityManager.unwrap(Session.class).update(commonConfig);
	}
}
