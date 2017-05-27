package com.huwl.oracle.qqmusic.music_action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.MusicBiz;
import com.huwl.oracle.qqmusic.music_model.Music;

@Controller("musicAction")
@Scope("prototype")
public class MusicAction  extends BaseAction{
	private boolean siteNavEnable=true;
	private String musicId;
	private Music music;
	private MusicBiz musicBiz;
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public boolean getSiteNavEnable() {
		return siteNavEnable;
	}
	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	
	@Override
	public String execute() throws Exception {
		music=musicBiz.getMusicById(musicId);
		return super.execute();
	}
	

}
