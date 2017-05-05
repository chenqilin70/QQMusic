package com.huwl.oracle.qqmusic.music_action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.SingerBiz;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;
import com.huwl.oracle.qqmusic.music_model.Singer;
import com.opensymphony.xwork2.ActionSupport;
@Controller("singerAction")
@Scope("prototype")
public class SingerAction  extends BaseAction{
	@Autowired
	private SingerBiz singerBiz;
	@Autowired
	private SingerBiz baseBiz;
	private Singer singer;
	private String singerId;
	private List<Music> top10Music;
	private List<Album> top5Album;
	private List<MusicVideo> top5MV;
	private List<Singer> top5SimilarSinger;
	private Long albumCount;
	private Long musicCount;
	private Long mvCount;
	private Long fansCount;
	private boolean siteNavEnable=true;
	private Map<String, Object> session;
	private boolean isCared;
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public boolean getIsCared() {
		return isCared;
	}
	public void setIsCared(boolean isCared) {
		this.isCared = isCared;
	}
	public boolean isSiteNavEnable() {
		return siteNavEnable;
	}
	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	
	public List<Singer> getTop5SimilarSinger() {
		return top5SimilarSinger;
	}
	public void setTop5SimilarSinger(List<Singer> top5SimilarSinger) {
		this.top5SimilarSinger = top5SimilarSinger;
	}
	public List<MusicVideo> getTop5MV() {
		return top5MV;
	}
	public void setTop5MV(List<MusicVideo> top5mv) {
		top5MV = top5mv;
	}
	public List<Album> getTop5Album() {
		return top5Album;
	}
	public void setTop5Album(List<Album> top5Album) {
		this.top5Album = top5Album;
	}
	public List<Music> getTop10Music() {
		return top10Music;
	}
	public void setTop10Music(List<Music> top10Music) {
		this.top10Music = top10Music;
	}
	public Long getFansCount() {
		return fansCount;
	}
	public void setFansCount(Long fansCount) {
		this.fansCount = fansCount;
	}
	public Long getMusicCount() {
		return musicCount;
	}
	public void setMusicCount(Long musicCount) {
		this.musicCount = musicCount;
	}
	public Long getMvCount() {
		return mvCount;
	}
	public void setMvCount(Long mvCount) {
		this.mvCount = mvCount;
	}
	public Long getAlbumCount() {
		return albumCount;
	}
	public void setAlbumCount(Long albumCount) {
		this.albumCount = albumCount;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	public String getSingerId() {
		return singerId;
	}
	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}
	public String addCareSinger(){
		inputStream=singerBiz.addCareSinger(getLoginedListenerId(),singerId);
		reflushListenerInSession();
		return "addCareSinger";
	}
	
	@Override
	public String execute() throws Exception {
		singer=singerBiz.queryMainSinger(singerId);
		albumCount=singerBiz.getAlbumCountBySinger(singerId);
		musicCount=singerBiz.getMusicCountBySinger(singerId);
		mvCount=singerBiz.getMVCountBySinger(singerId);
		fansCount=singerBiz.getFansCount(singerId);
		top10Music=singerBiz.getTop10Music(singerId);
		top5Album=singerBiz.getTop5Album(singerId);
		top5MV=singerBiz.getTop5MusicVideo(singerId);
		top5SimilarSinger=singerBiz.getTop5SimilarSinger(singerId);
		isCared=singerBiz.judgeIsCared(getLoginedListenerId(),singerId);
		injectUnAndPd();
		return SUCCESS;
	}
	
	
	
	
}
