package com.huwl.oracle.qqmusic.music_biz;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	public String getMusicInfo(String listenerId,String nowMusicId) {
		Object[] musicInfo=musicDao.getSimpleMusicInfo(nowMusicId);
		Map<String,String> map=new HashMap<>();
		map.put("musicId", musicInfo[0].toString());
		map.put("musicName", musicInfo[1].toString());
		map.put("albumId", musicInfo[2].toString());
		map.put("albumName", musicInfo[3].toString());
		map.put("singerId", musicInfo[4].toString());
		map.put("singerName", musicInfo[5].toString());
		map.put("music", musicInfo[6]+"");
		if(listenerId!=null){
			boolean isLike=listenerDao.isLikeMusic(Integer.parseInt(listenerId),nowMusicId);
			map.put("isLike", isLike+"");
		}
		String result=null;
		try {
			result=objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getMusicInfoList(String musicIds) {
		final String[] idsArr=musicIds.split(",");
		List<Object[]> olist=musicDao.getMusicInfoList(idsArr);
		Collections.sort(olist, new Comparator<Object[]>() {
			private List<String> idsList=Arrays.asList(idsArr);
			@Override
			public int compare(Object[] arg0, Object[] arg1) {
				String id1=arg0[0].toString();
				String id2=arg1[0].toString();
				return idsList.indexOf(id1)-idsList.indexOf(id2);
			}
			
		} );
		List<Music> result=new ArrayList<>();
		for(Object[] oarr:olist){
			Music m=new Music();
			m.setMusicId(oarr[0].toString());
			m.setMusicName(oarr[1].toString());
			Album a=new Album();
			a.setAlbumId(oarr[2].toString());
			a.setAlbumName(oarr[3].toString());
			Singer s=new Singer();
			s.setSingerId(oarr[4].toString());
			s.setSingerName(oarr[5].toString());
			a.setSinger(s);
			m.setAlbum(a);
			result.add(m);
		}
		String resultStr=null;
		try {
			resultStr = objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	public InputStream getRandomMusicId(String path) {
		File musicDir=new File(new File(path).getParent()+"/qqmusic_img_repository/music_m4a");
		System.out.println("/n/n/n/n/n/n/n/n"+musicDir.getAbsolutePath()+"/n/n/n/n/n/n/n/n");
		String[] files=musicDir.list();
		int ran=new Random().nextInt(files.length);
		return new ByteArrayInputStream(files[ran].getBytes());
	}

	public InputStream downloadMusic(String file_dir,String musicFile) {
        File file = new File(new File(file_dir).getParent()+"/qqmusic_img_repository/music_m4a"+ File.separator + musicFile);
        try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public InputStream likeMusic(String loginedListenerId, String musics) {
		String result=listenerDao.likeMusic(Integer.parseInt(loginedListenerId),musics.split(","));
		return new ByteArrayInputStream(result.getBytes());
	}

	public InputStream dislikeMusic(String loginedListenerId, String musics) {
		String result=listenerDao.dislikeMusic(Integer.parseInt(loginedListenerId),musics.split(","));
		return new ByteArrayInputStream(result.getBytes());
	}

}
