package com.huwl.oracle.qqmusic.music_model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MusicMenu  implements Serializable{
	private String menuId;
	private Listener creator;
	private String menuName;
	private String menuType;
	private Set<Music> musics;
	private String labels;
	private Integer listenTimes;
	private Date createDate;
	private Boolean isRecommend;
	private String image;
	private String info;
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public Listener getCreator() {
		return creator;
	}
	public void setCreator(Listener creator) {
		this.creator = creator;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public Set<Music> getMusics() {
		return musics;
	}
	public void setMusics(Set<Music> musics) {
		this.musics = musics;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public Integer getListenTimes() {
		return listenTimes;
	}
	public void setListenTimes(Integer listenTimes) {
		this.listenTimes = listenTimes;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public MusicMenu(String menuId, Listener creator, String menuName,
			String menuType, Set<Music> musics, String labels,
			Integer listenTimes, Date createDate, Boolean isRecommend,
			String image, String info) {
		super();
		this.menuId = menuId;
		this.creator = creator;
		this.menuName = menuName;
		this.menuType = menuType;
		this.musics = musics;
		this.labels = labels;
		this.listenTimes = listenTimes;
		this.createDate = createDate;
		this.isRecommend = isRecommend;
		this.image = image;
		this.info = info;
	}
	public MusicMenu() {}
	
	

}
