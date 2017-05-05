package com.huwl.oracle.qqmusic.music_biz;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_daoimpl.HibernateConfigurationUtil;
import com.huwl.oracle.qqmusic.music_model.Album;

@Controller("listenerBiz")
public class ListenerBiz extends BaseBiz{

	public Integer getLikeMusicCount(String loginedListenerId) {
		return listenerDao.getLikeMusicCount(Integer.parseInt(loginedListenerId));
	}

	public Integer getLikeMusicMenuCount(String loginedListenerId) {
		return listenerDao.getLikeMusicMenuCount(Integer.parseInt(loginedListenerId));
	}

	public Integer getLikeAlbumCount(String loginedListenerId) {
		return listenerDao.getLikeAlbumCount(Integer.parseInt(loginedListenerId));
	}

	public Integer getLikeMvCount(String loginedListenerId) {
		return listenerDao.getLikeMvCount(Integer.parseInt(loginedListenerId));
	}

	public List<Album> getLikeAlbumByPage(String loginedListenerId,
			Integer pageSize, Integer pageNo) {
		return listenerDao.getLikeAlbumByPage(Integer.parseInt(loginedListenerId),pageSize,pageNo);
	}


	

	

}
