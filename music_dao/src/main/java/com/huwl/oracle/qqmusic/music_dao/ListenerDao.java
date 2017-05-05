package com.huwl.oracle.qqmusic.music_dao;

import java.io.Serializable;
import java.util.List;

import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.huwl.oracle.qqmusic.music_model.Singer;

public interface ListenerDao  extends BaseDao<Listener>{

	boolean existUsername(String username);

	Listener getListenerByUsernameAndPassword(String username, String password);
	int addLikeAlbum(Integer listenerId,String albumId);

	Integer getLikeCount(Integer loginedListenerId,String propertyName);

	Integer getLikeMusicCount(Integer listenerId);

	Integer getLikeMusicMenuCount(Integer listenerId);

	Integer getLikeAlbumCount(Integer listenerId);

	Integer getLikeMvCount(Integer listenerId);

	List<Album> getLikeAlbumByPage(Integer listenerId, Integer pageSize,
			Integer pageNo);

	List<Singer> getFocusSinger(Serializable listenerId,Integer limit);

}
