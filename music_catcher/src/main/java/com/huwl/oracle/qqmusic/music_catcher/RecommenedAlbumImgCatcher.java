package com.huwl.oracle.qqmusic.music_catcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.CompanyDao;

public class RecommenedAlbumImgCatcher {
	public static CloseableHttpClient httpClient=HttpClients.createDefault();
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static void main(String[] args) {
		final AlbumDao dao = (AlbumDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("albumDao");
		List list=dao.query("SELECT a.albumId FROM Album a WHERE a.isRecommened=true ", null);
        for(Object o:list){
        	saveImg("http://120.24.244.103:8080/qqmusic_img_repository/album_head/T002R300x300M000"+o.toString()+".jpg", o.toString());
        }
	}
	public static void saveImg(String uri,Serializable id) {
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(uri);
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			response = httpClient.execute(httpGet);
			if (response != null)
				entity = response.getEntity();
			if (entity != null)
				in = entity.getContent();
			if (in != null) {
				FileUtils.copyInputStreamToFile(in, new File("D:/tempFile/"+"T002R300x300M000"+id.toString()+".jpg"));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in!=null)
					in.close();
				if(response!=null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
