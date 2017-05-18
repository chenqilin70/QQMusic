package com.huwl.oracle.qqmusic.music_daoimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.ListenerDao;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.huwl.oracle.qqmusic.music_model.Singer;
@Repository("listenerDao")
public class ListenerDaoImpl  extends BaseDaoImpl<Listener> implements ListenerDao{
	{
		setType(Listener.class);
	}

	@Override
	public boolean existUsername(String username) {
		List list=query("FROM Listener l WHERE l.username=?", username);
		return list.size()==0?false:true;
	}

	@Override
	public Listener getListenerByUsernameAndPassword(String username,
			String password) {
		List ls=query("FROM Listener l WHERE l.username=? AND l.password=?", username,password);
		Listener l=null;
		if(ls.size()==0){
			return null;
		}else{
			l=(Listener) ls.get(0);
		}
		if(password.equals(l.getPassword())){
			if(l.getMyMusicMenu()!=null){
				l.getMyMusicMenu().size();
			}
			if(l.getCreaSinger()!=null){
				l.getCreaSinger().size();
			}
			if(l.getFans()!=null){
				l.getFans().size();
			}
			return l;
		}else{
			return null;
		}
	}
	

	@Override
	public int addLikeAlbum(Integer listenerId, String albumId) {
		return updateBySql("insert into LIS_LI_AL(QQLIS_ID, ALBUM_ID) values(?,?)", listenerId,albumId);
	}

	@Override
	public Integer getLikeCount(Integer loginedListenerId,String propertyName) {
		String column=getCenterTableAppointColumn(propertyName);
		String sql="select count(*) from "+getCenterTableName(propertyName)+" where "+column+" =?";
		return Integer.parseInt(uniqueSqlQuery(sql, loginedListenerId).toString());
	}

	@Override
	public Integer getLikeMusicCount(Integer listenerId) {
		return getLikeCount(listenerId,"likeMusic");
	}

	@Override
	public Integer getLikeMusicMenuCount(Integer listenerId) {
		return getLikeCount(listenerId,"likeMusicMenu");
	}

	@Override
	public Integer getLikeAlbumCount(Integer listenerId) {
		return getLikeCount(listenerId,"likeAlbum");
	}

	@Override
	public Integer getLikeMvCount(Integer listenerId) {
		return getLikeCount(listenerId,"likeMusicVideo");
	}

	@Override
	public List<Album> getLikeAlbumByPage(Integer listenerId,Integer pageSize, Integer pageNo) {
		String hql="SELECT a,s FROM Listener l inner join l.likeAlbum a inner join a.singer s WHERE l.listenerId=?";
		List<Object[]> objArrList=query(hql, pageSize, pageNo,listenerId );
		List<Album> result=new ArrayList<Album>();
		for(Object[] o:objArrList){
			Album a=(Album) o[0];
			result.add(a);
		}
		return result;
	}

	

	@Override
	public List<Singer> getFocusSinger(Serializable listenerId,Integer limit) {
		return query("select s from Listener l left join l.creaSinger s where l.listenerId=?", limit, listenerId);
	}

	@Override
	public boolean updateListenerHead(String fileName,String listenerId) {
		int flag=updateByHql("update Listener l set l.listenerHead=? where l.listenerId=?", fileName,listenerId);
		return (flag==1)?true:false;
	}
}
