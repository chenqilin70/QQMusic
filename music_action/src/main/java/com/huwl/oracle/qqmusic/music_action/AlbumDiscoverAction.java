package com.huwl.oracle.qqmusic.music_action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.AlbumDiscoverBiz;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_util_model.AlbumCondition;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;

@Controller("albumDiscoverAction")
public class AlbumDiscoverAction extends BaseAction {
	private boolean siteNavEnable=true;
	@Autowired
	private AlbumDiscoverBiz albumDiscoverBiz;
	private List<Company> top23Company;
	private Long albumCount;
	private List<Album> albumListOfThisPage;
	private PageBean pageBean;
	private Integer pageNo;
	private AlbumCondition albumCondition;
	
	public AlbumCondition getAlbumCondition() {
		return albumCondition;
	}
	public void setAlbumCondition(AlbumCondition albumCondition) {
		this.albumCondition = albumCondition;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public List<Album> getAlbumListOfThisPage() {
		return albumListOfThisPage;
	}
	public void setAlbumListOfThisPage(List<Album> albumListOfThisPage) {
		this.albumListOfThisPage = albumListOfThisPage;
	}
	public Long getAlbumCount() {
		return albumCount;
	}
	public void setAlbumCount(Long albumCount) {
		this.albumCount = albumCount;
	}
	public boolean getSiteNavEnable() {
		return siteNavEnable;
	}
	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}
	public List<Company> getTop23Company() {
		return top23Company;
	}
	public void setTop23Company(List<Company> top23Company) {
		this.top23Company = top23Company;
	}
	
	@Override
	public String execute() throws Exception {
		top23Company=albumDiscoverBiz.getTop23Company();
		injectUnAndPd();
		return SUCCESS;
	}
	public String updateAlbumList(){
		System.out.println(albumCondition);
		albumCount=albumDiscoverBiz.getAlbumCount(albumCondition);
		pageBean=new PageBean(20,pageNo,Integer.parseInt(albumCount.toString()));
		albumListOfThisPage=albumDiscoverBiz.getAlbumListByPage(albumCondition,pageBean.getPageSize(),pageBean.getPageNo());
		return "updateAlbumList";
	}
	
	
	
	
}
