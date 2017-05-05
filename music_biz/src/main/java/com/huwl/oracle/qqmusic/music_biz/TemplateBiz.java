package com.huwl.oracle.qqmusic.music_biz;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_model.Listener;

@Service("templateBiz")
public class TemplateBiz extends BaseBiz {
	public String isUsernameLegal(String username) {
		if(username==null){
			return "\"username\":\"\"";
		}
		username=username.trim();
		if(username.isEmpty()){
			return "\"username\":\"用户名不能为空\"";
		}
		if(listenerDao.existUsername(username)){
			return "\"username\":\"此用户名已存在\"";
		}
		return "\"username\":\"验证成功\"";
	}

	public String templateValidate(String username, String password, String codeInSession, String validateCode, String comfirmPassword) {
		String usernameResult=isUsernameLegal(username);
		String passwordResult=isPasswordLegal(password);
		String validateResult=isValidateCodeLegal(codeInSession,validateCode);
		String comfirmPasswordResult=isComfirmPasswordLegal(password,comfirmPassword);
		return usernameResult+","+passwordResult+","+validateResult+","+comfirmPasswordResult;
	}
	
	private String isComfirmPasswordLegal(String password,
			String comfirmPassword) {
		String message="";
		if(comfirmPassword!=null){
			if(comfirmPassword.equalsIgnoreCase(password)){
				message="\"comfirmPassword\":\"验证成功\"";
			}else{
				message="\"comfirmPassword\":\"两次密码输入不正确\"";
			}
		}else{
			message="\"comfirmPassword\":\"\"";
		}
		return message;
	}

	private String isValidateCodeLegal(String codeInSession, String validateCode) {
		String message="";
		if(validateCode!=null){
			if(validateCode.equalsIgnoreCase(codeInSession)){
				message="\"validateCode\":\"验证成功\"";
			}else if("".equals(validateCode)){
				message="\"validateCode\":\"验证码不能为空\"";
			}else{
				message="\"validateCode\":\"验证码不正确\"";
			}
		}else{
			message="\"validateCode\":\"\"";
		}
		return message;
	}

	private  String isPasswordLegal(String password) {
		if(password==null){
			return "\"password\":\"\"";
		}
		if(password.isEmpty()){
			return "\"password\":\"密码不能为空\"";
		}
		if(password.length()<6 || password.length()>30){
			return "\"password\":\"密码长度应在6-30之间\"";
		}
		return "\"password\":\"验证成功\"";
	}

	public String signin(String username, String password, String message,HttpSession session) {
		JsonNode messageNode=null;
		try {
			messageNode = objectMapper.readTree("{"+message+"}");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean flag=true;
		for(JsonNode node:messageNode){
			if((!node.asText().equals("验证成功")) && (!node.asText().equals(""))){
				flag=false;
			}
		}
		if(flag){
			Listener listener=new Listener();
			listener.setUsername(username);
			listener.setPassword(password);
			listener.setIsPublic(true);
			listener.setListenerHead("default.jpg");
			Serializable s=listenerDao.save(listener);
			if(s!=null){
				listener=listenerDao.getListenerByUsernameAndPassword(username, password);
				session.setAttribute("listener", listener);
				System.out.println("已放入session");
				try {
					String listenStr=objectMapper.writeValueAsString(listener);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return message+","+"\"isSuccess\":\""+flag+"\"";
	}

	public String login(String username, String password,String validateCode, HttpSession session) {
		String codeInSession=(String)session.getAttribute("validateCode");
		String message="";
		boolean flag=true;
		if(!validateCode.equalsIgnoreCase(codeInSession)){
			flag=false;
			message=message+ "\"validateCode\":\"验证码不正确\"";
		}else{
			message=message+ "\"validateCode\":\"验证成功\"";
		}
		Listener l=listenerDao.getListenerByUsernameAndPassword(username, password);
		if(l==null){
			flag=false;
			message=message+ ",\"username\":\"用户名或密码不正确\"";
		}else{
			session.setAttribute("listener", l);
		}
		if(flag){
			session.removeAttribute("validateCode");
		}
		return message+ ",\"isSuccess\":\""+flag+"\"";
	}

}
