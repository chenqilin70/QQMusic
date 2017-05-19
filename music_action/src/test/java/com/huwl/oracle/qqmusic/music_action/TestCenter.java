package com.huwl.oracle.qqmusic.music_action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_biz.AlbumBiz;
import com.huwl.oracle.qqmusic.music_biz.IndexBiz;
import com.huwl.oracle.qqmusic.music_biz.ListenerBiz;
import com.huwl.oracle.qqmusic.music_biz.PlayerBiz;
import com.huwl.oracle.qqmusic.music_biz.SingerBiz;
import com.huwl.oracle.qqmusic.music_biz.SingerDiscoverBiz;
import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_dao.CompanyDao;
import com.huwl.oracle.qqmusic.music_dao.ListenerDao;
import com.huwl.oracle.qqmusic.music_dao.MusicDao;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;

public class TestCenter {
	public static ClassPathXmlApplicationContext ac;
	public static AlbumDao albumDao;
	public static CompanyDao companyDao;
	public static SingerDao singerDao;
	public static BaseDao baseDao;
	public static IndexBiz indexBiz;
	public static SingerBiz singerBiz;
	public static SessionFactory sessionFactory;
	public static ListenerDao listenerDao;
	private static AlbumBiz albumBiz;
	private static ListenerBiz listenerBiz;
	public static Query query;
	private static SingerDiscoverBiz singerDiscoverBiz;
	private static MusicDao musicDao;
	public static PlayerBiz playerBiz;
	private static ObjectMapper objectMapper;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
//		albumDao=(AlbumDao) ac.getBean("albumDao");
//		baseDao=(BaseDao) ac.getBean("baseDao");
//		singerDao=(SingerDao)ac.getBean("singerDao");
//		companyDao=(CompanyDao) ac.getBean("companyDao");
//		indexBiz=(IndexBiz) ac.getBean("indexBiz");
//		albumBiz=(AlbumBiz) ac.getBean("albumBiz");
//		listenerBiz=(ListenerBiz) ac.getBean("listenerBiz");
//		singerBiz=(SingerBiz) ac.getBean("singerBiz");
//		listenerDao=(ListenerDao) ac.getBean("listenerDao");
//		sessionFactory=(SessionFactory) ac.getBean("sessionFactory");
//		singerDiscoverBiz=(SingerDiscoverBiz) ac.getBean("singerDiscoverBiz");
//		musicDao=(MusicDao) ac.getBean("musicDao");
//		playerBiz=(PlayerBiz) ac.getBean("playerBiz");
//		objectMapper=(ObjectMapper) ac.getBean("objectMapper");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	@Test
	public void testMusic(){
		try {
			System.out.println(URLEncoder.encode(" ","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void testExtend(){
		try {
			System.out.println(new Character('f').getClass().getDeclaredField("TYPE"));
			System.out.println("是包装类");
		}catch (java.lang.NoSuchFieldException e) {
			System.out.println("不是包装类");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
