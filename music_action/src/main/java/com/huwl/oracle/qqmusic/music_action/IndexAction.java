package com.huwl.oracle.qqmusic.music_action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.CookiesAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.IndexBiz;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.opensymphony.xwork2.ActionSupport;
@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends BaseAction{
	@Autowired
	private IndexBiz indexBiz;
	private List<Album> chineseAlbums=null;
	private boolean siteNavEnable=true;
	private Map<String, String> cookies;
	
	public boolean isSiteNavEnable() {
		return siteNavEnable;
	}
	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	public String execute() throws Exception {
		chineseAlbums=indexBiz.getChineseAlbums("国语");
		injectUnAndPd();
		return SUCCESS;
	}
	public List<Album> getChineseAlbums() {
		return chineseAlbums;
	}
	public void setChineseAlbums(List<Album> chineseAlbums) {
		this.chineseAlbums = chineseAlbums;
	}
	
	
	
	
	
	
	
    
}
