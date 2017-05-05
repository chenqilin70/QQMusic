package com.huwl.oracle.qqmusic.music_action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.AlbumBiz;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.opensymphony.xwork2.ActionSupport;
@Controller("albumAction")
@Scope("prototype")
public class AlbumAction extends BaseAction{
	@Autowired
	private AlbumBiz albumBiz;
	private Album album;
	private String albumId;
	private List otherAlbum;
	private boolean siteNavEnable=true;
	private Map<String , Object> session;
	private InputStream inputStream;
	private boolean isCollected;
	
	public boolean getIsCollected() {
		return isCollected;
	}
	public void setIsCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public boolean isSiteNavEnable() {
		return siteNavEnable;
	}
	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	
	public String getAlbumId() {
		
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	public List getOtherAlbum() {
		return otherAlbum;
	}

	public void setOtherAlbum(List otherAlbum) {
		this.otherAlbum = otherAlbum;
	}

	@Override
	public String execute() throws Exception {
		album=albumBiz.queryMainAlbum(albumId);
		album.setDesc(album.getDesc().replace("\n", "<br>"));
		otherAlbum=albumBiz.queryOtherAlbum(album.getSinger().getSingerId());
		isCollected=albumBiz.judgeIsCollected(getLoginedListenerId(),albumId);
		injectUnAndPd();
		return SUCCESS;
	}
	public String collectAlbum(){
		inputStream=albumBiz.collectAlbum(getLoginedListenerId(),albumId);
		return "collectAlbum";
	}
	
	
	
}
