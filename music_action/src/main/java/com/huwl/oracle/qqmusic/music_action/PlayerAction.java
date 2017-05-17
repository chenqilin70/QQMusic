package com.huwl.oracle.qqmusic.music_action;

import java.io.File;
import java.io.InputStream;
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
	@Autowired
	private PlayerBiz playerBiz;
	
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
		result=playerBiz.getMusicInfo(nowMusicId);
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
	
	
	
	
	
}
