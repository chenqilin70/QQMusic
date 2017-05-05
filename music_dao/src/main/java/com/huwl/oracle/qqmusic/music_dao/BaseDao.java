package com.huwl.oracle.qqmusic.music_dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.mapping.PersistentClass;

import com.huwl.oracle.qqmusic.music_model.Singer;

public interface BaseDao<T> {
	Serializable save(T o);
	<T> Long getCount();
	<T> List<Serializable> getIdBasePage(Integer pageSize,Integer pageNo);
	List query(String hql,Serializable... params);
	List query(String hql,Integer limit,Serializable... params);
	Serializable uniqueQuery(String hql,Serializable... params);
	Serializable uniqueSqlQuery(String sql,Serializable... params);
	<T> T getObjectById(Serializable id);
	int updateByHql(String hql,Serializable... params);
	int updateBySql(String sql,Serializable... params);
	Long getSetCount(String parameterName, Serializable id);
	List query(String hql,Integer pageSize,Integer pageNo,Serializable... params);
	List sqlQuery(String sql,Integer pageSize,Integer pageNo,Serializable... params);
	//以下方法获取配置信息
	String getCenterTableName(String propertyName);
	String getCenterTableAppointColumn(String propertyName);
	String getIdFieldName();
	//测试方法
	Object getTestSecondCache();
	
}
