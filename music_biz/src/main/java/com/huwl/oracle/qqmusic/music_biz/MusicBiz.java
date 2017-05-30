package com.huwl.oracle.qqmusic.music_biz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.MusicMenu;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;
import com.huwl.oracle.qqmusic.music_model.Singer;
@Controller("musicBiz")
public class MusicBiz extends BaseBiz {

	public Music getMusicById(String musicId) {
		Music music=musicDao.getObjectById(musicId);
		Album album=music.getAlbum();
		album.getSinger().getSingerName();
		album.getCompany().getComName();
		musicDao.evict(music);
		String lyric=music.getLyric();
		if(lyric!=null && !lyric.isEmpty()){
			lyric = lyric.replace("&#58;", ":")
					.replace("&#46;", ".")
					.replace("&#32;", " ")
					.replace("&#45;", " ")
					.replace("&#41;", " ")
					.replace("&#40;", " ")
					.replace("&#13;", " ")
					.replaceAll("\\[\\d\\d:\\d\\d\\.\\d\\d\\]", "")
					.replaceAll("\\]", "")
					.replace("[ti:", "")
					.replace("[ar:", "歌手：")
					.replace("[al:", "专辑：")
					.replace("[offset:0", "")
					.replace("[by:", "")
					.replaceAll("(&#10;)+", "<br/>");
		}
		music.setLyric(lyric);
		album.setDesc(album.getDesc().replace("\n", "<br>"));
		return music;
	}

	public MusicVideo getRelatedMV(String singerId, String musicName) {
		return musicVideoDao.getRelatedMV(singerId,musicName);
	}
	

}
