package com.huwl.oracle.qqmusic.music_biz;

import com.huwl.oracle.qqmusic.music_model.Music;

public class MusicBiz extends BaseBiz {

	public Music getMusicById(String musicId) {
		Object[] oarr=musicDao.getMainMusicInfo(musicId);
		return null;
	}
	

}
