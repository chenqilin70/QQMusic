package com.huwl.oracle.qqmusic.music_action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_biz.BaseBiz;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.opensymphony.xwork2.ActionSupport;
@Controller("baseAction")
@Scope("prototype")
public class BaseAction extends ActionSupport implements SessionAware{
	protected Map<String, Object> session;
	protected String loginedListenerId;
	protected Listener loginedListener;
	private String usernameInCookie,passwordInCookie;
	@Autowired
	private BaseBiz baseBiz;
	
	public String getUsernameInCookie() {
		return usernameInCookie;
	}

	public void setUsernameInCookie(String usernameInCookie) {
		this.usernameInCookie = usernameInCookie;
	}

	public String getPasswordInCookie() {
		return passwordInCookie;
	}

	public void setPasswordInCookie(String passwordInCookie) {
		this.passwordInCookie = passwordInCookie;
	}

	public Listener getLoginedListener() {
		return (Listener) session.get("listener");
	}

	public void setLoginedListener(Listener loginedListener) {
		this.loginedListener = loginedListener;
	}
	/**
	 * 如果登陆了 则返回登陆的用户的id ， 否则返回null
	 * @return
	 */
	public String getLoginedListenerId() {
		if(getLoginedListener()==null){
			return null;
		}
		return getLoginedListener().getListenerId();
	}

	public void setLoginedListenerId(String loginedListenerId) {
		this.loginedListenerId = loginedListenerId;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	protected void reflushListenerInSession(){
		Listener l=baseBiz.reGetListener(getLoginedListenerId());
		session.put("listener", l);
	}
	protected HttpServletResponse getResponse(){
		return (HttpServletResponse) ServletActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	}
	public HttpServletRequest getRequest(){
		return (HttpServletRequest) ServletActionContext
							.getContext()
							.get(ServletActionContext.HTTP_REQUEST);
	}
	public String getCookieValue(String key){
		Cookie[] cs=getRequest().getCookies();
		if(cs==null){
			return "";
		}
		for(Cookie c:cs){
			if(c.getName().equals(key)){
				return c.getValue();
			}
		}
		return "";
	}
	public void injectUnAndPd(){
		setUsernameInCookie(getCookieValue("username"));
		setPasswordInCookie(getCookieValue("password"));
	}
	/**
	 * 根据cookie的名字返回相应的值
	 */
	protected String getCookieValueByName(String name){
		if(name==null || name.isEmpty()) return null;
		Cookie[] cookies=getRequest().getCookies();
		for(Cookie c:cookies){
			if(name.equals(c.getName())){
				return c.getValue();
			}
		}
		return null;
	}
	
	

}
