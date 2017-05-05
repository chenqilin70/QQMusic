package com.huwl.oracle.qqmusic.music_biz;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.huwl.oracle.qqmusic.music_util.HibernateUtil;

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
