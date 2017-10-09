package com.huwl.oracle.qqmusic.music_model;

import java.io.File;
import java.io.Serializable;

public class Music  implements Serializable{
	private String musicId;
	private Integer music;
	private String musicName;
	private String lyric;
	private Album album;
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public Integer getMusic() {
		return music;
	}
	public void setMusic(Integer music) {
		this.music = music;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public Music(String musicId, Integer music, String musicName, String lyric,
			String image, Album album) {
		super();
		this.musicId = musicId;
		this.music = music;
		this.musicName = musicName;
		this.lyric = lyric;
		this.album = album;
	}
	public Music() {}
	
	
}
