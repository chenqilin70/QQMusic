package com.huwl.oracle.qqmusic.music_dao;

import java.util.List;

import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;

public interface MusicVideoDao  extends BaseDao<MusicVideo>{
	Long getMVCountBySinger(String singerId);
	List<MusicVideo> getMVBySinger(String singerId);
	List<MusicVideo> getMVBySinger(String singerId,Integer limit);
	MusicVideo getRelatedMV(String singerId, String musicName);
}
