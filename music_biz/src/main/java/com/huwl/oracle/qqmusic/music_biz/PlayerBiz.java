package com.huwl.oracle.qqmusic.music_biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.Singer;

@Service("playerBiz")
public class PlayerBiz extends BaseBiz {

	public List<Music> getPlayList(String musics) {
		List<Music> result=new ArrayList<Music>();
		if(musics!=null && (!musics.isEmpty())){
			String[] ids=musics.split(",");
			List<Object[]> objArr=musicDao.getPlayList(ids);
			for(Object[] arr:objArr){
				Music m=new Music();
				Album a=new Album();
				Singer s=new Singer();
				m.setMusicId(arr[0].toString());
				m.setMusicName(arr[1].toString());
				a.setAlbumId(arr[2].toString());
				a.setAlbumName(arr[3].toString());
				s.setSingerId(arr[4].toString());
				s.setSingerName(arr[5].toString());
				a.setSinger(s);
				m.setAlbum(a);
				result.add(m);
			}
		}
		
		return result;
	}

}
