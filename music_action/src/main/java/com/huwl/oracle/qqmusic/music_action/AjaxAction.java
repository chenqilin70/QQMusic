package com.huwl.oracle.qqmusic.music_action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.ParameterAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_biz.BaseBiz;
import com.huwl.oracle.qqmusic.music_biz.IndexBiz;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
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
