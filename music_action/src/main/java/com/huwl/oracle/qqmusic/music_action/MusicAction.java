package com.huwl.oracle.qqmusic.music_action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.MusicBiz;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.MusicMenu;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;

@Controller("musicAction")
@Scope("prototype")
public class MusicAction  extends BaseAction{
	private boolean siteNavEnable=true;
	private String musicId;
	private Music music;
	private List<MusicMenu> menus;
	private MusicVideo relatedMV;
	@Autowired
	private MusicBiz musicBiz;
	
	public List<MusicMenu> getMenus() {
		return menus;
	}
	public void setMenus(List<MusicMenu> menus) {
		this.menus = menus;
	}
	public MusicVideo getRelatedMV() {
		return relatedMV;
	}
	public void setRelatedMV(MusicVideo relatedMV) {
		this.relatedMV = relatedMV;
	}
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
	
	
	public Music getMusic() {
		return music;
	}
	public void setMusic(Music music) {
		this.music = music;
	}
	@Override
	public String execute() throws Exception {
		music=musicBiz.getMusicById(musicId);
//		menus=musicBiz.getRelatedMenu(musicId);
		relatedMV=musicBiz.getRelatedMV(music.getAlbum().getSinger().getSingerId(),music.getMusicName());
		return super.execute();
	}
	

}
