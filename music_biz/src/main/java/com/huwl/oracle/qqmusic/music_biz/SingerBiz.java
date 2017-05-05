package com.huwl.oracle.qqmusic.music_biz;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.ResultActions;

import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;
import com.huwl.oracle.qqmusic.music_model.Singer;
@Service("singerBiz")
public class SingerBiz  extends BaseBiz{
	public Singer queryMainSinger(String id) {
		return singerDao.getObjectById(id);
	}
	public Long getAlbumCountBySinger(String singerId){
		return albumDao.getSetCount("singer", singerId);
	}
	public Long getMusicCountBySinger(String singerId) {
		return musicDao.getMusicCountBySinger(singerId);
	}
	public Long getMVCountBySinger(String singerId) {
		return musicVideoDao.getMVCountBySinger(singerId);
	}
	public Long getFansCount(String singerId) {
		return singerDao.getFansCount(singerId);
	}
	public List<Music> getTop10Music(String singerId) {
		List<Music> musicList=musicDao.getMusicListBySinger(singerId, 10);
		for(Music m:musicList){
			m.getAlbum().getAlbumName();
		}
		return musicList;
	}
	public List<Album> getTop5Album(String singerId) {
		return albumDao.getAlbumListBySinger(singerId, 5);
	}
	/**
	 * 此方法用于Catcher
	 * @param mvs
	 * @param singerId
	 */
	public void addAlbumForSinger(Set<MusicVideo> mvs,String singerId){
		Singer singer=singerDao.getObjectById(singerId);
		singer.setMusicVideos(mvs);
	}
	public List<MusicVideo> getTop5MusicVideo(String singerId) {
		return musicVideoDao.getMVBySinger(singerId, 5);
	}
	public List<Singer> getTop5SimilarSinger(String singerId) {
		List<Singer> result =new ArrayList<>();
		int limit=10;
		//获取本singer对应的album流派集合  按照album的数量排序
		List<String> genres=albumDao.getAlbumGenresBySinger(singerId);
		//获取本singer对应album的语言集合  按album数量排序
		List<String> lans=albumDao.getAlbumLanguageBySinger(singerId);
		//遍历语言
		LOOP:for(String lan : lans){
			//在语言相同的情况下查询genres的一致性
			for(String g:genres){
				//获取符合language 和  genres都和原来的singer相同的singer
				List<Singer> tempSingers=singerDao.getSingersByGenresAndLanguage(g,lan,limit);
				limit=limit-tempSingers.size();
				for(Singer s:tempSingers)
					result.add(s);
				if(limit<=0)
					break LOOP;
			}
		}
		System.out.println("已查到"+result.size()+"个");
		return getRandomList(result,5);
	}
	public boolean judgeIsCared(String listenerId, String singerId) {
		if(listenerId==null)
			return false;
		return singerDao.judgeIsCaredByOneListener(listenerId,singerId);
	}
	public InputStream addCareSinger(String loginedListenerId, String singerId) {
		int flag=singerDao.addCareSinger(loginedListenerId,singerId);
		return new ByteArrayInputStream((""+(flag==1?"true":"false")).getBytes());
	}
	
}
