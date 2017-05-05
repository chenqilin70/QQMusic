package com.huwl.oracle.qqmusic.music_daoimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_model.Singer;
import com.huwl.oracle.qqmusic.music_util_model.AlbumCondition;
@Repository("albumDao")
public class AlbumDaoImpl  extends BaseDaoImpl<Album> implements AlbumDao{
	{
		setType(Album.class);
	}
	@Override
	public List<Album> getAlbumListBySinger(String singerId) {
		return query("SELECT a FROM Album a where a.singer.singerId=?",singerId);
	}

	@Override
	public List<Album> getAlbumListBySinger(String singerId, Integer limit) {
		return query("SELECT a FROM Album a where a.singer.singerId=?", limit, singerId);
	}

	@Override
	public List<Album> getRcommendAlbumByLanguage(String... lans) {
		StringBuffer hql=new StringBuffer("SELECT albumId,albumName,singer.singerId,singer.singerName FROM Album WHERE  LANGUAGE in (");
		for(int i=0;i<lans.length;i++){
			hql.append("?,");
		}
		String hqlStr=hql.substring(0, hql.lastIndexOf(","))+") AND IS_RECOMMENED=true";
		List<Album>  result=new ArrayList<>();
		List temp=query(hqlStr,lans);
		for(int i=0;i<temp.size();i++){
			Object[] arr=(Object[]) temp.get(i);
			Album album=getAlbum(arr);
			result.add(album);
		}
		return result;
		
	}
	private Album getAlbum(Object[] arr){
		Album a=new Album();
		Singer s=new Singer();
		a.setAlbumId((String) arr[0]);
		a.setAlbumName((String) arr[1]);
		s.setSingerId((String) arr[2]);
		s.setSingerName((String) arr[3]);
		a.setSinger(s);
		return a;
	}
	@Override
	public List<String> getAlbumGenresBySinger(String singerId) {
		String hql="select a.genres from Album a "
				+ "where a.singer.singerId=? "
				+ "group by a.genres "
				+ "order by count(a.genres) desc";
		return query(hql, singerId);
	}

	@Override
	public List<String> getAlbumLanguageBySinger(String singerId) {
		String hql="select a.language from Album a "
				+ "where a.singer.singerId=? "
				+ "group by a.language "
				+ "order by count(a.language) desc";
		return query(hql, singerId);
	}

	@Override
	public boolean isCollectedByOneListener(String listenerId, String albumId) {
		Serializable s=uniqueSqlQuery("select QQLIS_ID from LIS_LI_AL where QQLIS_ID=? and ALBUM_ID=?", listenerId,albumId);
		return s==null?false:true;
	}

	@Override
	public List<Company> getTop23Company() {
		return query("select c from Album a inner join a.company c where c.comName<>'未确定' group by c order by count(*) desc", 23);
	}

	@Override
	public Long getAlbumCount(AlbumCondition albumCondition) {
		String hql="select count(*) from Album a ";
		hql=hql+getConditionCommand(albumCondition);
		return (Long) uniqueQuery(hql);
	}

	@Override
	public List<Album> getAlbumListByPage(AlbumCondition albumCondition,
			Integer pageSize, Integer pageNo) {
		String hql="select a , a.singer from Album a left join a.singer ";
		hql=hql+getConditionCommand(albumCondition) +" order by a.publishTime desc";
		 List<Object[]> os=query(hql, pageSize, pageNo);
		 List<Album> result=new ArrayList<>();
		 for(Object[] arr:os){
			 result.add((Album)arr[0]);
		 }
		 return result;
	}
	
	private String getConditionCommand(AlbumCondition albumCondition){
		List<String> hqlCondition=new ArrayList<>();
		if(albumCondition.getLanguage()!=null)
			hqlCondition.add("a.language like '%"+albumCondition.getLanguage()+"%'");
		if(albumCondition.getGenres()!=null){
			hqlCondition.add("a.genres like '%"+albumCondition.getGenres()+"%'");
		}
		if(albumCondition.getCategory()!=null)
			hqlCondition.add("a.albumType like '%"+albumCondition.getCategory()+"%'");
		if(albumCondition.getIsIsfree()!=null){
			System.out.println("a.isFree="+albumCondition.getIsIsfree());
			hqlCondition.add("a.isFree="+albumCondition.getIsIsfree());
		}
			
		if(albumCondition.getMaxYear()!=null &&  albumCondition.getMinYear()!=null){
			String min=simpleDateFormat.format(albumCondition.getMinYear());
			String max=simpleDateFormat.format(albumCondition.getMaxYear());
			hqlCondition.add("a.publishTime >= '"+min+"'");
			hqlCondition.add("a.publishTime <= '"+max+"'");
		}
		if(albumCondition.getAlbumCompany()!=null){
			hqlCondition.add("a.company.companyId= '"+albumCondition.getAlbumCompany()+"'");
		}
		hqlCondition.add("a.isShield<>true");
		String result=" ";
		for(String c:hqlCondition){
			result=result+c+" and ";
		}
		int index=result.lastIndexOf("and");
		result=(index==-1)?result:"where "+result.substring(0, index);
		return result;
	}
}