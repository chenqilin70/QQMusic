package com.huwl.oracle.qqmusic.music_daoimpl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_model.LanOfSinger;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.huwl.oracle.qqmusic.music_model.Singer;
@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> ,ApplicationContextAware{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	public SimpleDateFormat simpleDateFormat;
	public ApplicationContext applicationContext;
	public Configuration configuration;
	public Class type;
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	
	@Override
	public  List<Serializable> getIdBasePage(Integer pageSize,Integer pageNo) {
		String hql="SELECT "+getIdFieldName()+" FROM "+getType().getSimpleName();
		return getUsableHqlQuery(hql)
				.setFirstResult((pageNo-1)*pageSize)
				.setMaxResults(pageSize).list();
	}
	@Override
	public <T> Long getCount(){
		String hql="SELECT COUNT(*) FROM "+getType().getSimpleName();
		return (Long) getUsableHqlQuery(hql).uniqueResult();
	}
	@Override
	public Serializable save(T o) {
		Session session=sessionFactory.getCurrentSession();
		return session.save(o);
	}
	@Override
	public List query(String hql,Serializable... params) {
		return getUsableHqlQuery(hql,params).list();
	}
	@Override
	public List query(String hql,Integer limit,Serializable... params) {
		return getUsableHqlQuery(hql,params).setFirstResult(0).setMaxResults(limit).list();
	}
	@Override
	public List query(String hql,Integer pageSize,Integer pageNo,Serializable... params){
		return getUsableHqlQuery(hql,params).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
	}
	@Override
	public List sqlQuery(String sql,Integer pageSize,Integer pageNo,Serializable... params){
		return getUsableSqlQuery(sql,params).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
	}
	@Override
	public Serializable uniqueQuery(String hql, Serializable... params) {
		return (Serializable) getUsableHqlQuery(hql,params).uniqueResult();
	}
	@Override
	public Serializable uniqueSqlQuery(String sql, Serializable... params) {
		return (Serializable) getUsableSqlQuery(sql,params).uniqueResult();
	}
	@Override
	public <T> T getObjectById(Serializable id) {
		Session session=sessionFactory.getCurrentSession();
		return (T) session.get(getType(), id);
	}
	@Override
	public int updateByHql(String hql,Serializable... params){
		return getUsableHqlQuery(hql,params).executeUpdate();
	}
	@Override
	public int updateBySql(String sql, Serializable... params) {
		return getUsableSqlQuery(sql, params).executeUpdate();
	}
	/**
	 * 此方法在两表之间查询效率极高，但如果存在多表则效率不高，此时建议使用join语句
	 * 所谓多表查询可以理解为parameterName中存在“.”
	 * @param mainClass
	 * @param parameterName
	 * @param id
	 * @return
	 */
	public Long getSetCount(String parameterName, Serializable id){
		String hql="SELECT COUNT(*) FROM "+getType().getSimpleName()+" c WHERE c."+parameterName+"=?";
		return (Long) uniqueQuery(hql, id);
	}
	private Query getUsableHqlQuery(String hql, Serializable... params){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(hql);
		fillQuery(query, params);
		return query;
	}
	private Query getUsableSqlQuery(String sql, Serializable... params){
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		fillQuery(query, params);
		return query;
	}
	private static Method getMethodByParams(Serializable s){
		String type=s.getClass().getSimpleName();
		String methodName="set"+type;
		Method method=null;
		Class valType=null;
		try {
			valType=(Class) s.getClass().getDeclaredField("TYPE").get(s);
		}catch (java.lang.NoSuchFieldException e) {
			valType=s.getClass();
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			method=Query.class.getMethod(methodName, int.class, valType);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}
	private static void fillQuery(Query query,Serializable... params){
		if(params==null || params.length==0)
			return;
		for(int i=0;i<params.length;i++){
			Serializable s=params[i];
			Method method=getMethodByParams(s);
			try {
				method.invoke(query,i, s);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//以下方法获取配置信息
	public String getCenterTableName(String propertyName) {
		PersistentClass persistentClass = getPersistentClass();
		Property property = persistentClass.getProperty(propertyName);
		return ((Set)property.getValue()).getCollectionTable().getName();
	}
	public String getCenterTableAppointColumn(String propertyName) {
		PersistentClass persistentClass = getPersistentClass();
		Property property = persistentClass.getProperty(propertyName);
		Iterator it=((Set)property.getValue()).getCollectionTable().getForeignKeyIterator();
		while(it.hasNext()){
			ForeignKey fk=(ForeignKey) it.next();
			if(type.getName().equals(fk.getReferencedEntityName())){
				return ((Column)fk.getColumns().get(0)).getName();
			}
		}
		return "";
	}
	public String getIdFieldName(){
		PersistentClass pc=getPersistentClass();
		return pc.getIdentifierProperty().getName();
	}
	public PersistentClass getPersistentClass() {
		synchronized (BaseDaoImpl.class) {
			PersistentClass pc = getConfiguration().getClassMapping(type.getSimpleName());
			if (pc == null) {
				configuration = configuration.addClass(type);
				pc = configuration.getClassMapping(type.getName());
			}
			return pc;
		}
	}
	public Configuration getConfiguration() {
		if (configuration == null) {
			// 取sessionFactory的时候要加上&
			LocalSessionFactoryBean factory = (LocalSessionFactoryBean) applicationContext
					.getBean("&sessionFactory");
			configuration = factory.getConfiguration();
		}
		return configuration;
	}
	
	
	
	//以下方法获取上下文
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
		
	}
	
	@Override
	public Object getTestSecondCache() {
		List<LanOfSinger> list=query("FROM LanOfSinger");
		for(LanOfSinger l:list){
			String singerId=l.getSingerId();
			String lanString=l.getLanguage();
			Singer s=getObjectById(singerId);
			if(s!=null){
				if(s.getLanguage()!=null && !"".equals(s.getLanguage()))
					lanString=lanString+","+s.getLanguage();
				System.out.println(singerId+","+lanString);
				updateByHql("update Singer s set s.language=? where s.singerId=?", lanString,singerId);
			}else{
				System.out.println("singer 未取到");
			}
		}
		return null;
	}
	
	@Override
	public boolean exists(Serializable id) {
		long count=(Long) uniqueQuery("select count(*) from "+getType().getName()+" o where o."+getIdFieldName()+"=?", id);
		return count>0?true:false;
	}
}
