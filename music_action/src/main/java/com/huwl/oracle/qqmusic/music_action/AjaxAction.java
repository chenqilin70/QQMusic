package com.huwl.oracle.qqmusic.music_action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.BaseBiz;
import com.huwl.oracle.qqmusic.music_biz.IndexBiz;
import com.opensymphony.xwork2.ActionContext;
@Controller("ajaxAction")
@Scope("prototype")
public class AjaxAction extends BaseAction{
	@Autowired
	private IndexBiz indexBiz;
	private String result;
	private String language;
	private InputStream validateCodeImgInputStream;
	private String time;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public InputStream getValidateCodeImgInputStream() {
		return validateCodeImgInputStream;
	}
	public void setValidateCodeImgInputStream(InputStream validateCodeImgInputStream) {
		this.validateCodeImgInputStream = validateCodeImgInputStream;
	}
	public IndexBiz getIndexBiz() {
		return indexBiz;
	}
	public void setIndexBiz(IndexBiz indexBiz) {
		this.indexBiz = indexBiz;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String validateCode(){
		String code = BaseBiz.getRandomCode();
		getRequest().getSession().setAttribute("validateCode", code);
		validateCodeImgInputStream=BaseBiz.getValidateCodeImg(code);
		return "validateCode";
	}
	public String updateNewMusic() {
		result=indexBiz.getNewAlbums(language);
		return "updateNewMusic";
	}

	@Override
	public String execute() throws Exception {
		System.out.println("execute is running");
		return super.execute();
	}

	public HttpServletRequest getRequest(){
		return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	}

	

}
