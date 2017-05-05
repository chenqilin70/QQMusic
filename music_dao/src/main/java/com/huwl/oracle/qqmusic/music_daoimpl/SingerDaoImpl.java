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
	public Long getSingersCount(String letter,List<String> category) {
		StringBuffer hql=new StringBuffer("select count(*) from Singer s where ");
		Iterator<String> it=category.iterator();
		while (it.hasNext()) {
			hql.append(" s.language like '%"+it.next()+"%' or ");
		}
		System.out.println(hql.substring(0, hql.lastIndexOf("or")));
		return (Long) uniqueQuery(hql.substring(0, hql.lastIndexOf("or")));
	}
	
	
	
	

	
	
}
