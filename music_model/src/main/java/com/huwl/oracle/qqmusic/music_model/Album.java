package com.huwl.oracle.qqmusic.music_model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Album implements Serializable{
	private String albumId;
	private String albumName;
	private String genres;
	private String language;
	private Boolean isFree;
	private String albumType;
	private Company company;
	private Date publishTime;
	private Singer singer;
	private Set<Music> musics;
	private String desc;
	private String com;
	private Boolean isRecommened;
	/*	
	 	有一部分专辑，特征如下：
		日期大于2017年4月7日，语言为英语
		因其图片重复率过高且按日期排序排在前面，影响美观，特设置此字段将其屏蔽
	*/
	private Boolean isShield;
	
	public Boolean getIsShield() {
		return isShield;
	}
	public void setIsShield(Boolean isShield) {
		this.isShield = isShield;
	}
	public Boolean getIsRecommened() {
		return isRecommened;
	}
	public void setIsRecommened(Boolean isRecommened) {
		this.isRecommened = isRecommened;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Boolean getIsFree() {
		return isFree;
	}
	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}
	public String getAlbumType() {
		return albumType;
	}
	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	public Set<Music> getMusics() {
		return musics;
	}
	public void setMusics(Set<Music> musics) {
		this.musics = musics;
	}
	
	public Album(String albumId, String albumName, String genres,
			String language, Boolean isFree, String albumType, Company company,
			Date publishTime, Singer singer, Set<Music> musics, String desc) {
		super();
		this.albumId = albumId;
		this.albumName = albumName;
		this.genres = genres;
		this.language = language;
		this.isFree = isFree;
		this.albumType = albumType;
		this.company = company;
		this.publishTime = publishTime;
		this.singer = singer;
		this.musics = musics;
		this.desc = desc;
	}
	public Album() {}
	@Override
	public String toString() {
		return "Album [albumId=" + albumId + ", albumName=" + albumName+ "]";
	}
	
	
	
	
	
}
