package com.huwl.oracle.qqmusic.music_action;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.ListenerBiz;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;
@Controller("listenerAction")
@Scope("prototype")
public class ListenerAction   extends BaseAction{
	@Autowired
	private ListenerBiz listenerBiz;
	private boolean siteNavEnable=false;
	private String listener_model;
	private Integer likeMusicCount;
	private Integer likeMusicMenuCount;
	private Integer likeAlbumCount;
	private Integer likeMvCount;
	private InputStream inputStream;
	private List<Album> likeAlbum;
	private PageBean pageBean;
	private Integer pageNo;
	
	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public List<Album> getLikeAlbum() {
		return likeAlbum;
	}
	public void setLikeAlbum(List<Album> likeAlbum) {
		this.likeAlbum = likeAlbum;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public Integer getLikeMusicCount() {
		return likeMusicCount;
	}
	public void setLikeMusicCount(Integer likeMusicCount) {
		this.likeMusicCount = likeMusicCount;
	}
	public Integer getLikeMusicMenuCount() {
		return likeMusicMenuCount;
	}
	public void setLikeMusicMenuCount(Integer likeMusicMenuCount) {
		this.likeMusicMenuCount = likeMusicMenuCount;
	}
	public Integer getLikeAlbumCount() {
		return likeAlbumCount;
	}
	public void setLikeAlbumCount(Integer likeAlbumCount) {
		this.likeAlbumCount = likeAlbumCount;
	}
	public Integer getLikeMvCount() {
		return likeMvCount;
	}
	public void setLikeMvCount(Integer likeMvCount) {
		this.likeMvCount = likeMvCount;
	}
	public String getListener_model() {
		return listener_model;
	}
	public void setListener_model(String listener_model) {
		this.listener_model = listener_model;
	}
	public boolean isSiteNavEnable() {
		return siteNavEnable;
	}
	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	public String changeModel(){
		if(getLoginedListenerId()==null){
			inputStream=new ByteArrayInputStream("unLogin".getBytes());
			return "unLogin";
		}
		if(listener_model.startsWith("MYLIKE")){
			likeMusicCount=listenerBiz.getLikeMusicCount(getLoginedListenerId());
			likeMusicMenuCount=listenerBiz.getLikeMusicMenuCount(getLoginedListenerId());
			likeAlbumCount=listenerBiz.getLikeAlbumCount(getLoginedListenerId());
			likeMvCount=listenerBiz.getLikeMvCount(getLoginedListenerId());
			if(listener_model.equals(ListenerModel.MYLIKE_MUSIC.toString())){
				System.out.println(listener_model+"-<-");
			}else if(listener_model.equals(ListenerModel.MYLIKE_ALBUM.toString())){
				pageBean=new PageBean(30,pageNo,likeAlbumCount);
				likeAlbum=listenerBiz.getLikeAlbumByPage(getLoginedListenerId(),pageBean.getPageSize(),pageNo);
			}else if(listener_model.equals(ListenerModel.MYLIKE_MV.toString())){
				
			}else if(listener_model.equals(ListenerModel.MYLIKE_MENU.toString())){
				
			}
		}
		return listener_model;
	}
	@Override
	public String execute() throws Exception {
		injectUnAndPd();
		return SUCCESS;
	}
	

}
