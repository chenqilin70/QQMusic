package com.huwl.oracle.qqmusic.music_biz;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.Singer;

@Service("playerBiz")
public class PlayerBiz extends BaseBiz {

	public List<Music> getPlayList(String musics) {
		List<Music> result = new ArrayList<Music>();
		if (musics != null && (!musics.isEmpty())) {
			String[] ids = musics.split(",");
			List<Object[]> objArr = musicDao.getPlayList(ids);
			for (Object[] arr : objArr) {
				Music m = new Music();
				Album a = new Album();
				Singer s = new Singer();
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

	public String getMusicInfo(String listenerId, String nowMusicId) {
		Object[] musicInfo = musicDao.getSimpleMusicInfo(nowMusicId);
		Map<String, String> map = new HashMap<>();
		map.put("musicId", musicInfo[0].toString());
		map.put("musicName", musicInfo[1].toString());
		map.put("albumId", musicInfo[2].toString());
		map.put("albumName", musicInfo[3].toString());
		map.put("singerId", musicInfo[4].toString());
		map.put("singerName", musicInfo[5].toString());
		map.put("music", musicInfo[6] + "");
		if (listenerId != null) {
			boolean isLike = listenerDao.isLikeMusic(
					Integer.parseInt(listenerId), nowMusicId);
			map.put("isLike", isLike + "");
		}
		String result = null;
		try {
			result = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getMusicInfoList(String musicIds) {
		final String[] idsArr = musicIds.split(",");
		List<Object[]> olist = musicDao.getMusicInfoList(idsArr);
		Collections.sort(olist, new Comparator<Object[]>() {
			private List<String> idsList = Arrays.asList(idsArr);

			@Override
			public int compare(Object[] arg0, Object[] arg1) {
				String id1 = arg0[0].toString();
				String id2 = arg1[0].toString();
				return idsList.indexOf(id1) - idsList.indexOf(id2);
			}

		});
		List<Music> result = new ArrayList<>();
		for (Object[] oarr : olist) {
			Music m = new Music();
			m.setMusicId(oarr[0].toString());
			m.setMusicName(oarr[1].toString());
			Album a = new Album();
			a.setAlbumId(oarr[2].toString());
			a.setAlbumName(oarr[3].toString());
			Singer s = new Singer();
			s.setSingerId(oarr[4].toString());
			s.setSingerName(oarr[5].toString());
			a.setSinger(s);
			m.setAlbum(a);
			result.add(m);
		}
		String resultStr = null;
		try {
			resultStr = objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	public InputStream getRandomMusicId(String path) {
		File musicDir = new File(new File(path).getParent()
				+ "/qqmusic_img_repository/music_m4a");
		String[] files = musicDir.list();
		int ran = new Random().nextInt(files.length);
		return new ByteArrayInputStream(files[ran].getBytes());
	}

	public InputStream downloadMusic(String file_dir, String musicFile) {
		File file = new File(new File(file_dir).getParent()
				+ "/qqmusic_img_repository/music_m4a" + File.separator
				+ musicFile);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public InputStream likeMusic(String loginedListenerId, String musics) {
		String result = listenerDao.likeMusic(
				Integer.parseInt(loginedListenerId), musics.split(","));
		return new ByteArrayInputStream(result.getBytes());
	}

	public InputStream dislikeMusic(String loginedListenerId, String musics) {
		String result = listenerDao.dislikeMusic(
				Integer.parseInt(loginedListenerId), musics.split(","));
		return new ByteArrayInputStream(result.getBytes());
	}

	public InputStream batchDownload(String file_dir, String musics) {
		List<Object[]> result = musicDao.getBatchInfo(musics.split(","));
		ZipOutputStream zout = null;
		String zipFileName = UUID.randomUUID().toString();
		File tempDir = null;
		try {
			File tempRoot = new File(new File(file_dir).getParent()
					+ "/tempFile");
			if (!tempRoot.exists())
				tempRoot.mkdirs();
			tempDir = new File(tempRoot.getAbsolutePath() + "/" + zipFileName
					+ ".zip");
			zout = new ZipOutputStream(new FileOutputStream(tempDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Object[] oarr : result) {

			InputStream in = null;
			String musicId = oarr[0].toString();
			String musicName = oarr[1].toString();

			if (oarr[2] == null) {
				ZipEntry zipEntry = new ZipEntry("(随机音乐)" + musicName + ".m4a");
				try {
					zout.putNextEntry(zipEntry);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					File musicDir = new File(new File(file_dir).getParent()
							+ "/qqmusic_img_repository/music_m4a");
					String[] files = musicDir.list();
					int ran = new Random().nextInt(files.length);
					in = new FileInputStream(new File(file_dir).getParent()
							+ "/qqmusic_img_repository/music_m4a/" + files[ran]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (1 == (Integer) oarr[2]) {
				ZipEntry zipEntry = new ZipEntry(musicName + ".m4a");
				try {
					zout.putNextEntry(zipEntry);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					in = new FileInputStream(new File(file_dir).getParent()
							+ "/qqmusic_img_repository/music_m4a/C400"
							+ musicId + ".m4a");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			int readSize = 0;
			byte[] bytes = new byte[1024];
			try {
				while ((readSize = in.read(bytes)) != -1) {
					zout.write(bytes, 0, readSize);
				}
				zout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			zout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream zipInput = null;
		try {
			zipInput = new FileInputStream(tempDir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		final File willDelete = tempDir;
		new Thread() {
			@Override
			public void run() {
				while (true) {
					if (!willDelete.exists()) {
						break;
					} else {
						boolean flag = willDelete.delete();
						if (flag) {
							break;
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			};
		}.start();
		return zipInput;
	}

	public InputStream downloadThisMusic(String downloadMusicId, String path) {
		boolean flag = musicDao.hasMusicFile(downloadMusicId);
		File musicFile = null;
		if (flag) {
			musicFile = new File(new File(path).getParent()
					+ "/qqmusic_img_repository/music_m4a/C400"
					+ downloadMusicId + ".m4a");
		} else {
			File musicDir = new File(new File(path).getParent()
					+ "/qqmusic_img_repository/music_m4a");
			String[] files = musicDir.list();
			int ran = new Random().nextInt(files.length);
			musicFile = new File(new File(path).getParent()
					+ "/qqmusic_img_repository/music_m4a/" + files[ran]);
		}
		InputStream input = null;
		try {
			input = new FileInputStream(musicFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input;
	}

	public InputStream getLyric(String nowMusicId) {
		String lyric = musicDao.getLyric(nowMusicId);
		if(lyric==null || lyric.isEmpty()){
			return new ByteArrayInputStream("false".getBytes());
		}
		lyric = lyric.replace("&#58;", ":").replace("&#46;", ".")
				.replace("&#10;", "\r\n").replace("&#32;", " ")
				.replace("&#45;", " ").replace("&#41;", " ")
				.replace("&#40;", " ").replace("&#13;", " ");
		System.out.println(lyric);
		Pattern p = Pattern.compile("\\[\\d\\d:\\d\\d\\.\\d\\d\\]");
		Matcher matcher = p.matcher(lyric);
		int index = 0;
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			String str = matcher.group();
			Integer minite = Integer.parseInt(str.substring(1, 3));
			Integer second = Integer.parseInt(str.substring(4, 6));
			Integer millisecond = Integer.parseInt(str.substring(7, 9));
			lyric = lyric.replace(str, "["
					+ (minite * 60 + second + (millisecond / 100.00)) + "]");
			index++;
		}
		String[] lyrics = lyric.split("\r\n");
		Comparator comparator=new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				Double i0 = Double.parseDouble(arg0);
				Double i1 = Double.parseDouble(arg1);
				return (i0 - i1) > 0 ? 1 : -1;
			}
		};
		Map resultMap = new TreeMap<>(comparator);
		for (String s : lyrics) {
			if (s.indexOf("[ti:") == -1 && s.indexOf("[by:") == -1
					&& s.indexOf("[al:") == -1 && s.indexOf("[ar:") == -1
					&& s.indexOf("[offset:") == -1) {
						Pattern p2 = Pattern.compile("\\[\\d{0,3}\\.\\d{0,2}\\]");
						Matcher m2 = p2.matcher(s);
						if (m2.find()) {
							String line=s.substring(m2.end());
							if(!line.isEmpty()){
								resultMap.put(
										s.substring(m2.start() + 1, m2.end() - 1),
										line);
							}
							
						}
			}
		}
		String result="";
		try {
			result=objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		InputStream resultInput=null;
		try {
			resultInput = new ByteArrayInputStream(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
		return resultInput;

	}
}
