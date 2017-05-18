package com.huwl.oracle.qqmusic.music_action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.CookiesAware;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import antlr.debug.ListenerBase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_biz.TemplateBiz;
import com.huwl.oracle.qqmusic.music_dao.ListenerDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller("templateAction")
@Scope("prototype")
public class TemplateAction  extends BaseAction {
	@Autowired
	private TemplateBiz templateBiz;
	@Autowired
	private ObjectMapper objectMapper;
	private String message;
	private String username;
	private String password;
	private String validateCode;
	private String comfirmPassword;
	private boolean remenberPwd;
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public boolean isRemenberPwd() {
		return remenberPwd;
	}
	public void setRemenberPwd(boolean remenberPwd) {
		this.remenberPwd = remenberPwd;
	}
	public String getComfirmPassword() {
		return comfirmPassword;
	}
	public void setComfirmPassword(String comfirmPassword) {
		this.comfirmPassword = comfirmPassword;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String logout(){
		getRequest().getSession().invalidate();
		inputStream=new ByteArrayInputStream("true".getBytes());
		return "logout";
	}
	public String signin(){
		HttpSession session=getRequest().getSession();
		String codeInSession=(String)session.getAttribute("validateCode");
		message=templateBiz.templateValidate(username,password,codeInSession,validateCode,comfirmPassword);
		message="{"+templateBiz.signin(username,password,message,session)+"}";
		return "signin";
	}
	public String login(){
		HttpSession session=getRequest().getSession();
		message="{"+templateBiz.login(username,password,validateCode,session)+"}";
		Cookie c1=null;
		Cookie c2=null;
		try {
			c1 = new Cookie("username", URLEncoder.encode(username,"UTF-8"));
			c2=new Cookie("password", URLEncoder.encode(password,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(remenberPwd){
			c1.setMaxAge(5*365*24*60*60);
			c2.setMaxAge(1728000);
		}else{
			c1.setMaxAge(1);
			c2.setMaxAge(1);
		}
		getResponse().addCookie(c1);
		getResponse().addCookie(c2);
		return "login";
	}
	public String templateValidate() {
		String codeInSession=(String) getRequest().getSession().getAttribute("validateCode");
		message="{"
				+templateBiz.templateValidate(username,password,codeInSession,validateCode,comfirmPassword)
				+"}";
		
		return "registerValidate";
	}
	
	
	
	
}
