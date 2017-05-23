package com.huwl.oracle.qqmusic.music_action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.PlayerBiz;
import com.huwl.oracle.qqmusic.music_model.Music;

@Controller("playerAction")
@Scope("prototype")
public class PlayerAction extends BaseAction{
	
	private List<Music> playList;
	private String nowMusicId;
	private String result;
	private String musicIds;
	private InputStream inputStream;
	private String musicFile;
	private String musicName;
	private String musics;
	private String batchFileName;
	private String downloadMusicId;
	private String time;
	@Autowired
	private PlayerBiz playerBiz;
	public String getDownloadMusicId() {
		return downloadMusicId;
	}
	public void setDownloadMusicId(String downloadMusicId) {
		this.downloadMusicId = downloadMusicId;
	}
	public String getBatchFileName() {
		return batchFileName;
	}
	public void setBatchFileName(String batchFileName) {
		this.batchFileName = batchFileName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMusics() {
		return musics;
	}
	public void setMusics(String musics) {
		this.musics = musics;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getMusicFile() {
		return musicFile;
	}
	public void setMusicFile(String musicFile) {
		this.musicFile = musicFile;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getMusicIds() {
		return musicIds;
	}
	public void setMusicIds(String musicIds) {
		this.musicIds = musicIds;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getNowMusicId() {
		return nowMusicId;
	}
	public void setNowMusicId(String nowMusicId) {
		this.nowMusicId = nowMusicId;
	}
	public List<Music> getPlayList() {
		return playList;
	}
	public void setPlayList(List<Music> playList) {
		this.playList = playList;
	}
	@Override
	public String execute() throws Exception {
		String musics=getCookieValue("playList");
		playList=playerBiz.getPlayList(musics);
		return SUCCESS;
	}
	public String changeNowPlay(){
		result=playerBiz.getMusicInfo(getLoginedListenerId(),nowMusicId);
		return "changeNowPlay";
	}
	public String getMusicInfoList(){
		result=playerBiz.getMusicInfoList(musicIds);
		return "getMusicInfoList";
	}
	public String getRandomMusicId(){
		HttpServletRequest request=getRequest();
		inputStream=playerBiz.getRandomMusicId(request.getServletContext().getRealPath(""));
		 return "getRandomMusicId";
	}
	public String downloadMusic(){
		try {
			musicName=new String(musicName.getBytes(),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		inputStream=playerBiz.downloadMusic(getRequest().getServletContext().getRealPath(""),musicFile);
		return "downloadMusic";
	}
	public String likeMusic(){
		inputStream=playerBiz.likeMusic(getLoginedListenerId(),musics);
		return "likeMusic";
	}
	public String dislikeMusic(){
		inputStream=playerBiz.dislikeMusic(getLoginedListenerId(),musics);
		return "likeMusic";
	}
	public String batchDownload(){
		try {
			batchFileName=new String(("批量下载音乐"+new Date().getTime()+".zip").getBytes(),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		inputStream=playerBiz.batchDownload(getRequest().getServletContext().getRealPath(""),musics);
		return "batchDownload";
	}
	public String downloadThisMusic(){
		inputStream=playerBiz.downloadThisMusic(downloadMusicId,getRequest().getServletContext().getRealPath(""));
		musicName=musicName+".m4a";
		return "downloadThisMusic";
	}
	public String getLyric(){
		inputStream=playerBiz.getLyric(nowMusicId);
		return "getLyric";
	}
	
	
	
	
}
