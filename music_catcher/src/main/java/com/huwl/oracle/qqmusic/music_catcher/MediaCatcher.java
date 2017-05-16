package com.huwl.oracle.qqmusic.music_catcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huwl.oracle.qqmusic.music_dao.MusicDao;

public class MediaCatcher {
	public static HttpClient httpClient=HttpClients.createDefault();
	public static void main(String[] args) {
		final MusicDao dao = (MusicDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("musicDao");
		File urlFile=new File("src/main/resources/MediaCatcher/music_url.txt");
		FileReader reader=null;
		BufferedReader br=null;
		try {
			reader=new FileReader(urlFile);
			br=new BufferedReader(reader);
			while(true){
				String url=br.readLine();
				if(url==null){
					break;
				}else{
					int startIndex=url.indexOf("/", 10)+1;
					int endIndex=url.indexOf("?");
					String fileName=url.substring(startIndex, endIndex);
					File file=new File("/usr/local/tomcat/webapps/qqmusic_img_repository/music_m4a/"+fileName);
					if(file.exists()){
						System.out.println("出现重复值，跳过");
					}else{
						String id=fileName.substring(4,fileName.indexOf("."));
						if(dao.exists(id)){
							saveMedia(url,file);
							dao.updateByHql("update Music m set m.music=1 where m.musicId=?", id);
						}
						
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public static void saveMedia(String url,File file){
		HttpGet get=new HttpGet(url);
		InputStream in=null;
		try {
			HttpResponse response=httpClient.execute(get);
			HttpEntity  entity=response.getEntity();
			in=entity.getContent();
			FileUtils.copyInputStreamToFile(in, file);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
