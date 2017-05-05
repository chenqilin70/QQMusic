package com.huwl.oracle.qqmusic.music_biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_util_model.AlbumCondition;

@Service("albumDiscoverBiz")
public class AlbumDiscoverBiz extends BaseBiz {

	public List<Company> getTop23Company() {
		return albumDao.getTop23Company();
	}

	public Long getAlbumCount(AlbumCondition albumCondition) {
		return albumDao.getAlbumCount(albumCondition);
	}

	public List<Album> getAlbumListByPage(AlbumCondition albumCondition,Integer pageSize, Integer pageNo) {
		return albumDao.getAlbumListByPage( albumCondition, pageSize,pageNo);
	}
	
}
