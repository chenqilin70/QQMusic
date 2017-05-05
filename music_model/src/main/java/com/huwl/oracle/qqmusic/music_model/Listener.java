package com.huwl.oracle.qqmusic.music_model;

import java.util.Set;

public class Listener {
	private String listenerId;
	private String username;
	private String password;
	private String listenerHead;
	private Set<Listener> carePeople;
	private Set<Singer> creaSinger;
	private Set<Listener> fans;
	private Boolean isPublic;
	private Set<Music> likeMusic;
	private Set<MusicMenu> likeMusicMenu;
	private Set<Album> likeAlbum;
	private Set<MusicVideo> likeMusicVideo;
	private Set<MusicMenu> myMusicMenu;
	private Set<Video> myVideo;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getListenerHead() {
		return listenerHead;
	}
	public void setListenerHead(String listenerHead) {
		this.listenerHead = listenerHead;
	}
	public String getListenerId() {
		return listenerId;
	}
	public void setListenerId(String listenerId) {
		this.listenerId = listenerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Listener> getCarePeople() {
		return carePeople;
	}
	public void setCarePeople(Set<Listener> carePeople) {
		this.carePeople = carePeople;
	}
	public Set<Singer> getCreaSinger() {
		return creaSinger;
	}
	public void setCreaSinger(Set<Singer> creaSinger) {
		this.creaSinger = creaSinger;
	}
	public Set<Listener> getFans() {
		return fans;
	}
	public void setFans(Set<Listener> fans) {
		this.fans = fans;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Set<Music> getLikeMusic() {
		return likeMusic;
	}
	public void setLikeMusic(Set<Music> likeMusic) {
		this.likeMusic = likeMusic;
	}
	public Set<MusicMenu> getLikeMusicMenu() {
		return likeMusicMenu;
	}
	public void setLikeMusicMenu(Set<MusicMenu> likeMusicMenu) {
		this.likeMusicMenu = likeMusicMenu;
	}
	public Set<Album> getLikeAlbum() {
		return likeAlbum;
	}
	public void setLikeAlbum(Set<Album> likeAlbum) {
		this.likeAlbum = likeAlbum;
	}
	public Set<MusicVideo> getLikeMusicVideo() {
		return likeMusicVideo;
	}
	public void setLikeMusicVideo(Set<MusicVideo> likeMusicVideo) {
		this.likeMusicVideo = likeMusicVideo;
	}
	public Set<MusicMenu> getMyMusicMenu() {
		return myMusicMenu;
	}
	public void setMyMusicMenu(Set<MusicMenu> myMusicMenu) {
		this.myMusicMenu = myMusicMenu;
	}
	public Set<Video> getMyVideo() {
		return myVideo;
	}
	public void setMyVideo(Set<Video> myVideo) {
		this.myVideo = myVideo;
	}
	@Override
	public String toString() {
		return "Listener [listenerId=" + listenerId + ", username=" + username
				+ "]";
	}
	
	
}
