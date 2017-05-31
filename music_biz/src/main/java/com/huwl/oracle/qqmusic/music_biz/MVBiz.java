package com.huwl.oracle.qqmusic.music_biz;

import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.MusicVideo;

@Service("mvBiz")
public class MVBiz extends BaseBiz{

	public MusicVideo getMusicVideo(String mvId) {
		return musicVideoDao.getMusicVideo(mvId);
	}

}
