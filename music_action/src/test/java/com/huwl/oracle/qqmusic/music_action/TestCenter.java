package com.huwl.oracle.qqmusic.music_action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	public void testMusic(){
		File file1=new File("D:/tempFile/1.txt");
		File file2=new File("D:/tempFile/2.txt");
		try {
			ZipOutputStream zout=new ZipOutputStream(new FileOutputStream("D:/1.zip"));
			ZipEntry entry1=new ZipEntry(file1.getName());
			zout.putNextEntry(entry1);
			FileInputStream in=new FileInputStream(file1);
			BufferedInputStream bin=new BufferedInputStream(in);
			byte[] barr=new byte[1024];
			int readSize=0;
			while((readSize=bin.read(barr))!=-1){
				zout.write(barr, 0, readSize);
			}
			ZipEntry entry2=new ZipEntry(file2.getName());
			zout.putNextEntry(entry2);
			FileInputStream in2=new FileInputStream(file2);
			BufferedInputStream bin2=new BufferedInputStream(in2);
			while((readSize=bin2.read(barr))!=-1){
				zout.write(barr, 0, readSize);
			}
			zout.flush();
			bin.close();
			in.close();
			bin2.close();
			in2.close();
			zout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testExtend(){
		playerBiz.getLyric("00004As11GHaoA");
	}
	
	
	

}
