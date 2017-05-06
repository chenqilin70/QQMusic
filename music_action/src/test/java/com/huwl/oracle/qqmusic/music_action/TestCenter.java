package com.huwl.oracle.qqmusic.music_action;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huwl.oracle.qqmusic.music_biz.AlbumBiz;
import com.huwl.oracle.qqmusic.music_biz.IndexBiz;
import com.huwl.oracle.qqmusic.music_biz.ListenerBiz;
import com.huwl.oracle.qqmusic.music_biz.SingerBiz;
import com.huwl.oracle.qqmusic.music_biz.SingerDiscoverBiz;
import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_dao.CompanyDao;
import com.huwl.oracle.qqmusic.music_dao.ListenerDao;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_model.LanOfSinger;
import com.huwl.oracle.qqmusic.music_model.Listener;
import com.huwl.oracle.qqmusic.music_model.Singer;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;

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
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		albumDao=(AlbumDao) ac.getBean("albumDao");
		baseDao=(BaseDao) ac.getBean("baseDao");
		singerDao=(SingerDao)ac.getBean("singerDao");
		companyDao=(CompanyDao) ac.getBean("companyDao");
		indexBiz=(IndexBiz) ac.getBean("indexBiz");
		albumBiz=(AlbumBiz) ac.getBean("albumBiz");
		listenerBiz=(ListenerBiz) ac.getBean("listenerBiz");
		singerBiz=(SingerBiz) ac.getBean("singerBiz");
		listenerDao=(ListenerDao) ac.getBean("listenerDao");
		sessionFactory=(SessionFactory) ac.getBean("sessionFactory");
		singerDiscoverBiz=(SingerDiscoverBiz) ac.getBean("singerDiscoverBiz");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	@Test
	public void testMusic(){
		List<Singer> list=singerDiscoverBiz.getSingerListByCondition(new PageBean(1000, 1, 10000), "Chinese", "Z");
		System.out.println(list.size());
		for(Singer s: list){
			System.out.println(s.getSingerId()+","+s.getSingerName());
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
