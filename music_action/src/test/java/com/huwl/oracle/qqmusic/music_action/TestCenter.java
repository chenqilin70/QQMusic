package com.huwl.oracle.qqmusic.music_action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
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
import com.huwl.oracle.qqmusic.music_biz.MusicBiz;
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
	public static MusicBiz musicBiz;
	private static ObjectMapper objectMapper;
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
		musicDao=(MusicDao) ac.getBean("musicDao");
		playerBiz=(PlayerBiz) ac.getBean("playerBiz");
		objectMapper=(ObjectMapper) ac.getBean("objectMapper");
		musicBiz=(MusicBiz) ac.getBean("musicBiz");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	
	@Test
	public void testExtend(){
		musicBiz.getMusicById("000002a33Eqj3D");
	}
	
	
	

}
