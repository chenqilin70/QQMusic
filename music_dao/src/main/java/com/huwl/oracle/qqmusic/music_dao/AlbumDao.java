package com.huwl.oracle.qqmusic.music_dao;

import java.util.List;

import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_util_model.AlbumCondition;

public interface AlbumDao extends BaseDao<Album>{
	List<Album> getAlbumListBySinger(String singerId);
	List<Album> getAlbumListBySinger(String singerId,Integer limit);
	List<Album> getRcommendAlbumByLanguage(String... lans);
	List<String> getAlbumGenresBySinger(String singerId);
	List<String> getAlbumLanguageBySinger(String singerId);
	boolean isCollectedByOneListener(String listenerId, String albumId);
	List<Company> getTop23Company();
	Long getAlbumCount(AlbumCondition albumCondition);
	List<Album> getAlbumListByPage(AlbumCondition albumCondition, Integer pageSize,
			Integer pageNo);
}
