package com.huwl.oracle.qqmusic.music_biz;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.Album;

@Service("albumBiz")
public class AlbumBiz extends BaseBiz{
	public Album queryMainAlbum(String albumId) {
		Album a= albumDao.getObjectById(albumId);
		a.getMusics().size();
		a.getSinger().getSingerName();
		a.getCompany().getComName();
		return a;
	}
	/**
	 * 将sql直接暴露在biz内不恰当，改动起来较为麻烦，还是不改了
	 */
	public List queryOtherAlbum(String singerId) {
		return albumDao.query("SELECT a.albumId , a.albumName ,a.publishTime FROM Album a where a.singer.singerId=?",6, singerId);
	}
	public InputStream collectAlbum(String listenerId, String albumId) {
		int flag=listenerDao.addLikeAlbum(Integer.parseInt(listenerId), albumId);
		return new ByteArrayInputStream((""+(flag==1?true:false)).getBytes());
	}
	public boolean judgeIsCollected(String listenerId, String albumId) {
		if(listenerId==null)
			return false;
		return albumDao.isCollectedByOneListener(listenerId,albumId);
	}
	
}
