package com.huwl.oracle.qqmusic.music_biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_model.Album;


@Service("indexBiz")
public class IndexBiz  extends BaseBiz{
	
	public  String getNewAlbums(String lan) {
		List<Album> albums;
		switch (lan) {
		case "inland":
			albums=albumDao.getRcommendAlbumByLanguage("国语");
			break;
		case "HongKong_And_TaiWan":
			albums=albumDao.getRcommendAlbumByLanguage("粤语");
			break;
		case "Europe_And_America":
			albums=albumDao.getRcommendAlbumByLanguage("英语","西班牙语","德语","意大利语","法语","瑞典语","葡萄牙语","波兰语","丹麦语","拉丁");
			break;
		case "Korea":
			albums=albumDao.getRcommendAlbumByLanguage("韩语");
			break;
		default://日本
			albums=albumDao.getRcommendAlbumByLanguage("日语");
			break;
		}
		try {
			return objectMapper.writeValueAsString(albums);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	public List<Album> getChineseAlbums(String string) {
		return albumDao.getRcommendAlbumByLanguage("国语");
	}
	
}
