package com.huwl.oracle.qqmusic.music_aspact;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.huwl.oracle.qqmusic.music_action.BaseAction;
import com.huwl.oracle.qqmusic.music_model.Listener;

@Aspect
public class DefaultAspect implements ApplicationContextAware{
	private ApplicationContext applicationContext;
	@AfterReturning(value="execution(public com.huwl.oracle.qqmusic.music_model.Listener com.huwl.oracle.qqmusic.music_daoimpl.ListenerDaoImpl.getListenerByUsernameAndPassword(java.lang.String, java.lang.String))"
			,returning="result")
	public void shieldPassword(JoinPoint joinpoint,Object result){
		System.out.println("this is "+joinpoint.getThis());
		SessionFactory sessionFactory=(SessionFactory) applicationContext.getBean("sessionFactory");
        if(result!=null){
        	sessionFactory.getCurrentSession().evict(result);
            ((Listener)result).setPassword("********");
        }
	}
//	@AfterReturning(value="execution(public String com.huwl.oracle.qqmusic.music_action.*.execute())"
//			,returning="result")
//	public void setUsernameAndPasswordByCookie(JoinPoint joinpoint,Object result){
//		System.out.println("切面已经被触发了1111111112222222222222—");
////		if("success".equals(result)){
////			System.out.println("切面已经被触发了333333333");
////			BaseAction a=(BaseAction)joinpoint.getTarget();
////			System.out.println("(((((((((((("+joinpoint.getTarget());
////			a.setUsernameInCookie(a.getCookieValue("username"));
////			System.out.println("切面已经被触发了33444444444"+a.getCookieValue("username"));
////			a.setPasswordInCookie(a.getCookieValue("password"));
////			System.out.println("通过切面将cookie中的值存入值栈！！"+a.getCookieValue("password"));
////		}
//	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
}
