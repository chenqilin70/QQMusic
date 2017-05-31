package com.huwl.oracle.qqmusic.music_action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.MVBiz;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;
@Controller("mvAction")
@Scope("prototype")
public class MVAction extends BaseAction{
	private boolean siteNavEnable=true;
	private MusicVideo musicVideo;
	private String mvId;
	@Autowired
	private MVBiz mvBiz;
	
	public String getMvId() {
		return mvId;
	}

	public void setMvId(String mvId) {
		this.mvId = mvId;
	}

	public MusicVideo getMusicVideo() {
		return musicVideo;
	}

	public void setMusicVideo(MusicVideo musicVideo) {
		this.musicVideo = musicVideo;
	}

	public boolean isSiteNavEnable() {
		return siteNavEnable;
	}

	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	@Override
	public String execute() throws Exception {
		musicVideo=mvBiz.getMusicVideo(mvId);
		return SUCCESS;
	}
	
}
