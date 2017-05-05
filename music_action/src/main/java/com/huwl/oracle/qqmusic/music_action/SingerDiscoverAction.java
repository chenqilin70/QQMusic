package com.huwl.oracle.qqmusic.music_action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.SingerDiscoverBiz;
import com.huwl.oracle.qqmusic.music_model.Singer;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;
@Controller("singerDiscoverAction")
@Scope("prototype")
public class SingerDiscoverAction extends BaseAction {
	private boolean siteNavEnable=true;
	private List<Singer> top15FocusSinger;
	private Integer pageNo;
	private PageBean pageBean;
	private String category;
	private String letter;
	@Autowired
	private SingerDiscoverBiz singerDiscoverBiz;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

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

	public List<Singer> getTop15FocusSinger() {
		return top15FocusSinger;
	}

	public void setTop15FocusSinger(List<Singer> top15FocusSinger) {
		this.top15FocusSinger = top15FocusSinger;
	}

	public boolean isSiteNavEnable() {
		return siteNavEnable;
	}

	public void setSiteNavEnable(boolean siteNavEnable) {
		this.siteNavEnable = siteNavEnable;
	}


	@Override
	public String execute() throws Exception {
		top15FocusSinger=singerDiscoverBiz.getTop15FocusSinger(getLoginedListenerId());
		return SUCCESS;
	}	
	public String updateSingerList() {
		Long SingerCount=singerDiscoverBiz.getSingersCount(category,letter);
//		pageBean=new PageBean(100, pageNo, objCount);
		return "updateSingerList";
	}
}
