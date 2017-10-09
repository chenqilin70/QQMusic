package com.huwl.oracle.qqmusic.music_model;

import java.io.Serializable;
import java.util.Set;

public class Singer  implements Serializable{
	private String singerId;
	private String singerName;
	private String baseInfo;
	private Set<Album> albums;
	private Set<MusicVideo> musicVideos;
	private String head;
	private String singerLabel;
	private String language;
	private String letter;
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public String getSingerId() {
		return singerId;
	}
	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}
	public String getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}
	public Set<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
	public Set<MusicVideo> getMusicVideos() {
		return musicVideos;
	}
	public void setMusicVideos(Set<MusicVideo> musicVideos) {
		this.musicVideos = musicVideos;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getSingerLabel() {
		return singerLabel;
	}
	public void setSingerLabel(String singerLabel) {
		this.singerLabel = singerLabel;
	}

	public Singer(String singerId, String singerName, String baseInfo,
			Set<Album> albums, Set<MusicVideo> musicVideos, String head,
			String singerLabel) {
		super();
		this.singerId = singerId;
		this.singerName = singerName;
		this.baseInfo = baseInfo;
		this.albums = albums;
		this.musicVideos = musicVideos;
		this.head = head;
		this.singerLabel = singerLabel;
	}
	public Singer() {}
	
	
	

}
