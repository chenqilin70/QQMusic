package com.huwl.oracle.qqmusic.music_dao;

import java.io.Serializable;
import java.util.List;

import com.huwl.oracle.qqmusic.music_model.Music;

public interface MusicDao extends BaseDao<Music>{
	Long getMusicCountBySinger(String singerId);
	List<Music> getMusicListBySinger(String singerId , Integer limit);
	List<Music> getMusicListBySinger(String singerId);
	List<Object[]> getPlayList(String[] ids);
	Object[] getSimpleMusicInfo(String nowMusicId);
	List<Object[]> getMusicInfoList(String[] ids);
	List<Object[]> getBatchInfo(String[] split);
	boolean hasMusicFile(String downloadMusicId);
	String getLyric(String nowMusicId);
	List<Serializable> getNullLyricIdBasePage(Integer pageSize,Integer pageNo);
	Long getNullLyricCount();
}
