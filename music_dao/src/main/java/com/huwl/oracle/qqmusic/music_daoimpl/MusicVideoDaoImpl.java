package com.huwl.oracle.qqmusic.music_daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.MusicDao;
import com.huwl.oracle.qqmusic.music_dao.MusicVideoDao;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;
@Repository("musicVideoDao")
public class MusicVideoDaoImpl   extends BaseDaoImpl<MusicVideo> implements MusicVideoDao{
	{
		setType(MusicVideo.class);
	}

	@Override
	public Long getMVCountBySinger(String singerId) {
		return getSetCount("singer", singerId);
	}

	@Override
	public List<MusicVideo> getMVBySinger(String singerId) {
		return query("FROM MusicVideo m WHERE m.singer.singerId=?",singerId);
	}
	@Override
	public List<MusicVideo> getMVBySinger(String singerId, Integer limit) {
		return query("FROM MusicVideo m WHERE m.singer.singerId=?",limit,singerId);
	}
}
