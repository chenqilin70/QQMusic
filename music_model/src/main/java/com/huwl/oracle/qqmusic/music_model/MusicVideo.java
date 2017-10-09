package com.huwl.oracle.qqmusic.music_model;

import java.io.Serializable;
import java.util.Date;

public class MusicVideo  implements Serializable{
	private String videoId;
	private String videoName;
	private Singer singer;
	private String video;
	private Date date;
	private Integer watchTimes;
	private String info;
	private String mvDistrict;
	private String mvType;
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getWatchTimes() {
		return watchTimes;
	}
	public void setWatchTimes(Integer watchTimes) {
		this.watchTimes = watchTimes;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getMvDistrict() {
		return mvDistrict;
	}
	public void setMvDistrict(String mvDistrict) {
		this.mvDistrict = mvDistrict;
	}
	public String getMvType() {
		return mvType;
	}
	public void setMvType(String mvType) {
		this.mvType = mvType;
	}
	public MusicVideo(String videoId, String videoName, Singer singer,
			String video, Date date, Integer watchTimes, String info,
			String mvDistrict, String mvType) {
		super();
		this.videoId = videoId;
		this.videoName = videoName;
		this.singer = singer;
		this.video = video;
		this.date = date;
		this.watchTimes = watchTimes;
		this.info = info;
		this.mvDistrict = mvDistrict;
		this.mvType = mvType;
	}
	public MusicVideo() {}
	
	
}
