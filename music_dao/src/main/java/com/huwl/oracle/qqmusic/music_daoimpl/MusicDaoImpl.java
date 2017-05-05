package com.huwl.oracle.qqmusic.music_daoimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.MusicDao;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.Singer;
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
	


}
