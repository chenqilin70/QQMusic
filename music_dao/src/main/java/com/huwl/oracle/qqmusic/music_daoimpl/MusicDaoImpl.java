package com.huwl.oracle.qqmusic.music_daoimpl;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.MusicDao;
import com.huwl.oracle.qqmusic.music_model.Music;
@Repository("musicDao")
public class MusicDaoImpl  extends BaseDaoImpl<Music> implements MusicDao{
	{
		setType(Music.class);
	}

	@Override
	public Long getMusicCountBySinger(String singerId) {
		return (Long) uniqueQuery(
				"select count(*) from Music as m left join m.album where m.album.singer.singerId=?"
				, singerId);
	}
	
	@Override
	public List<Music> getMusicListBySinger(String singerId, Integer limit) {
		return query("SELECT m FROM Music m left join m.album a left join a.singer s WHERE s.singerId=?"
					, limit, singerId);
	}

	@Override
	public List<Music> getMusicListBySinger(String singerId) {
		return query("SELECT m FROM Music m left join m.album a left join a.singer s WHERE s.singerId=?"
				, singerId);
	}

	@Override
	public List<Object[]> getPlayList(String[] ids) {
		StringBuffer hql=new StringBuffer("select m.musicId, m.musicName,a.albumId,a.albumName,s.singerId,s.singerName from Music m left join m.album a left join a.singer s where ");
		for(String id:ids){
			hql.append("m.musicId=? or ");
		}
		String hqlResult=hql.substring(0, hql.lastIndexOf("or"));
		return query(hqlResult, ids);
	}
	@Override
	public Object[] getSimpleMusicInfo(String nowMusicId) {
		List<Object[]> results=query("select m.musicId,m.musicName,a.albumId,a.albumName,s.singerId,s.singerName,m.music from Music m left join m.album a left join a.singer s where m.musicId=?", nowMusicId);
		return results.get(0);
	}

	@Override
	public List<Object[]> getMusicInfoList(String[] ids) {
		StringBuffer hql=new StringBuffer("select m.musicId,m.musicName,a.albumId,a.albumName,s.singerId,s.singerName from Music m left join m.album a left join a.singer s where ");
		for(String id:ids){
			hql.append("m.musicId=? or ");
		}
		hql=new StringBuffer(hql.substring(0,hql.lastIndexOf("or")));
		return query(hql.toString(), ids);
	}
	@Override
	public List<Object[]> getBatchInfo(String[] musics) {
		StringBuffer hql=new StringBuffer("select m.musicId,m.musicName,m.music from Music where ");
		for(String id:musics){
			hql.append("m.musicId=? or ");
		}
		hql=new StringBuffer(hql.substring(0,hql.lastIndexOf("or")));
		return query(hql.toString(), musics);
	}
}
