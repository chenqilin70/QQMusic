package com.huwl.oracle.qqmusic.music_action;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.AsyncContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.PlayerBiz;
import com.huwl.oracle.qqmusic.music_model.Music;

@Controller("playerAction")
@Scope("prototype")
public class PlayerAction extends BaseAction{
	
	private List<Music> playList;
	private String updatedMusicList;
	private String nowMusicId;
	private String result;
	private String musicIds;
	@Autowired
	private PlayerBiz playerBiz;
	
	
	
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
	
	
	
	
	
}
