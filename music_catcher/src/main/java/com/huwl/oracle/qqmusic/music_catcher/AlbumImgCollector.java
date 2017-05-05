package com.huwl.oracle.qqmusic.music_catcher;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_model.Album;

public class AlbumImgCollector {
	public static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) {
		final AlbumDao dao = (AlbumDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("albumDao");
		Long count = dao.getCount();
		final Integer pageSize = (int) ((count / 5) + 1);
		for (int i = 1; i <= 5; i++) {
			final Integer pageNo = i;
			new Thread() {
				public void run() {
					List ids = dao.getIdBasePage( pageSize, pageNo);
					for (Object o : ids) {
						Serializable s = (Serializable) o;
						String uri = "https://y.gtimg.cn/music/photo_new/T002R300x300M000"
								+ s + ".jpg";
						HttpEntity entity = saveImg(uri,s);

					}
				};
			}.start();
		}

	}

	public static HttpEntity saveImg(String uri,Serializable id) {
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
				FileUtils
						.copyInputStreamToFile(
								in,
								new File(
										(String) (PropertiesUtil
												.getProperties("src/main/resources/file_download.properties")
												.get("albumImgPath"))
												+ "/T002R300x300M000"
												+ id.toString()
												+ ".jpg"));
				System.out.println("已存入一张照片！");
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

}
