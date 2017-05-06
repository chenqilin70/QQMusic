package com.huwl.oracle.qqmusic.music_daoimpl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;
import com.huwl.oracle.qqmusic.music_model.Singer;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;
@Repository("singerDao")
public class SingerDaoImpl extends BaseDaoImpl<Singer> implements SingerDao{
	{
		setType(Singer.class);
	}
	@Override
	public void saveMVOfSinger(String singerId, MusicVideo musicVideo) {
//		getObjectById();
		
	}
	@Override
	public Long getFansCount(String singerId) {
		return (Long) uniqueQuery(
				"select count(*) from Listener l left join l.creaSinger c "
				+ " where c.singerId=?"
				, singerId);
	}
	@Override
	public List<Singer> getSingersByGenresAndLanguage(String g, String lan,Integer limit) {
		String hql="SELECT s "
				+ "FROM Album a LEFT JOIN a.singer s "
				+ "WHERE a.language=? AND a.genres=? "
				+ "GROUP BY s.singerId "
				+ "ORDER BY COUNT(s) DESC";
		return query(hql, limit,lan,g);
	}
	@Override
	public boolean judgeIsCaredByOneListener(String listenerId, String singerId) {
		Serializable s=uniqueSqlQuery("select QQLIS_ID from LIS_SIN where QQLIS_ID=? and SINGER_ID=?", listenerId,singerId);
		return s==null?false:true;
	}
	@Override
	public int addCareSinger(String loginedListenerId, String singerId) {
		return updateBySql("insert into LIS_SIN(QQLIS_ID,SINGER_ID) values(?,?)", loginedListenerId,singerId);
	}
	
	@Override
	public Long getSingersCount(List<String> letter,List<String> category) {
		StringBuffer hql=new StringBuffer("select count(*) from Singer s ");
		String conditionHql=getConditionHql(letter, category);
		hql.append(conditionHql);
		System.out.println(hql);
		return (Long) uniqueQuery(hql.toString());
	}
	
	@Override
	public List<Object[]> getSingerListByCondition(PageBean pageBean,List<String> categorys, List<String> letters) {
		StringBuffer hql=new StringBuffer("select s.singerId,s.singerName from Singer s ");
		String conditionHql=getConditionHql(letters, categorys);
		hql.append(conditionHql);
		System.out.println(hql);
		return query(hql.toString(), pageBean.getPageSize(), pageBean.getPageNo());
	}
	
	private String getConditionHql(List<String> letter,List<String> category){
		int categorySize=category.size(),letterSize=letter.size();
		StringBuffer hql= new StringBuffer(" where ");
		if(categorySize==0 && letterSize==0){
			hql=new StringBuffer(hql.substring(0, hql.lastIndexOf("where")));
		}else{
			if(categorySize!=0){
				Iterator<String> it=category.iterator();
				hql.append(" ( ");
				while (it.hasNext()) {
					hql.append(" (s.language like '%"+it.next()+"%') or ");
				}
				hql=new StringBuffer(hql.substring(0, hql.lastIndexOf("or")));
				hql.append(" ) ");
			}
			if(categorySize!=0 && letterSize!=0){
				hql.append(" and ( ");
			}
			if(letterSize!=0){
				hql.append("s.letter='"+letter.get(0)+"' )");
			}
		}
		return hql.toString();
	}
	
	
	
	

	
	
}
