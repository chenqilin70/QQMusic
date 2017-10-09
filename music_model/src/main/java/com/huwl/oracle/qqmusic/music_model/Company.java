package com.huwl.oracle.qqmusic.music_model;

import java.io.Serializable;
import java.util.Set;

public class Company  implements Serializable{
	private String companyId;
	private String comName;
	private String comHead;
	private Set<Album> albums;
	private Set<MusicVideo> musicVideos;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getComHead() {
		return comHead;
	}
	public void setComHead(String comHead) {
		this.comHead = comHead;
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
	public Company(String comName, String comHead) {
		super();
		this.comName = comName;
		this.comHead = comHead;
	}
	public Company(String companyId, String comName, String comHead,
			Set<Album> albums, Set<MusicVideo> musicVideos) {
		super();
		this.companyId = companyId;
		this.comName = comName;
		this.comHead = comHead;
		this.albums = albums;
		this.musicVideos = musicVideos;
	}
	public Company() {}
	
	
	
	

}
