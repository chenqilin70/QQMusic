package com.huwl.oracle.qqmusic.music_dao;

import java.util.List;

import com.huwl.oracle.qqmusic.music_model.MusicVideo;
import com.huwl.oracle.qqmusic.music_model.Singer;

public interface SingerDao extends BaseDao<Singer>{
	void saveMVOfSinger(String singerId,MusicVideo musicVideo);
	Long getFansCount(String singerId);
	List<Singer> getSingersByGenresAndLanguage(String g, String lan,Integer limit);
	boolean judgeIsCaredByOneListener(String listenerId, String singerId);
	int addCareSinger(String loginedListenerId, String singerId);
	Long getSingersCount(String letter,List<String> category);
	
}
