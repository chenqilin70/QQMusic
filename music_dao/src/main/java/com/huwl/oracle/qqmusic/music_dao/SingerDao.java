package com.huwl.oracle.qqmusic.music_dao;

import java.util.List;

import com.huwl.oracle.qqmusic.music_model.MusicVideo;
import com.huwl.oracle.qqmusic.music_model.Singer;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;

public interface SingerDao extends BaseDao<Singer>{
	void saveMVOfSinger(String singerId,MusicVideo musicVideo);
	Long getFansCount(String singerId);
	List<Singer> getSingersByGenresAndLanguage(String g, String lan,Integer limit);
	boolean judgeIsCaredByOneListener(String listenerId, String singerId);
	int addCareSinger(String loginedListenerId, String singerId);
	Long getSingersCount(List<String> letter,List<String> category);
	List<Object[]> getSingerListByCondition(PageBean pageBean,List<String> categorys, List<String> letters);
	
}
