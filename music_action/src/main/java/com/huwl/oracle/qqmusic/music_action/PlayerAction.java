package com.huwl.oracle.qqmusic.music_action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.PlayerBiz;
import com.huwl.oracle.qqmusic.music_model.Music;

@Controller("playerAction")
@Scope("prototype")
public class PlayerAction extends BaseAction {
	private List<Music> playList;
	private String updatedMusicList;
	@Autowired
	private PlayerBiz playerBiz;
	
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
	public String updateMusicList(){
		
		return "updateMusicList";
	}
	
	
	
	
	
}
