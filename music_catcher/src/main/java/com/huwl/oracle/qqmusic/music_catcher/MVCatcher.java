package com.huwl.oracle.qqmusic.music_catcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_biz.SingerBiz;
import com.huwl.oracle.qqmusic.music_dao.MusicVideoDao;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_model.MusicVideo;

public class MVCatcher {
	public static CloseableHttpClient httpClient=HttpClients.createDefault();
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	public static final SingerBiz singerBiz=(SingerBiz) ac.getBean("singerBiz");
	public static void main(String[] args) {
		
		final SingerDao singerDao = (SingerDao) ac.getBean("singerDao");
		final MusicVideoDao musicVideoDao = (MusicVideoDao) ac.getBean("musicVideoDao");
		Long count=singerDao.getCount();
		int threadCount=9;
		final int pageSize=(int) ((count/threadCount)+1);
		for(int i=1;i<=threadCount;i++){
			final List<Serializable> ids=singerDao.getIdBasePage(pageSize, i);
			new Thread(){
				public void run() {
					for(Serializable sid:ids){
						try {
							//循环每个singer的id
							Set<MusicVideo> mvs=new HashSet<>();
							for(int j=0;true;j++){//对每个singer都进行死循环翻页
								try {
									String str=getStr("https://c.y.qq.com/mv/fcgi-bin/fcg_singer_mv.fcg"
											, "MVCatcher/mv_list_head.properties"
											, "MVCatcher/mv_list_param.properties"
											, new BasicNameValuePair("singermid", sid.toString())
											,new BasicNameValuePair("begin", (j*52)+""));
									if(str.contains("\"list\":[]")){
										break;
									}else{
										JsonNode rootNode=getRootNode(str);
										JsonNode listNode=rootNode.path("data").path("list");
										for(JsonNode mvNode:listNode){//遍历每一个mv的信息
											mvs.add(createMV(mvNode));
										}
									}
								} catch (Exception e) {
									System.out.println(e.getMessage());
									continue;
								}
							}
							int flag=0;
							while(flag<5){
								flag++;
								try {
									singerBiz.addAlbumForSinger(mvs, sid.toString());
									break;
								} catch (Exception e) {
									System.out.println(e.getMessage());
									continue;
								}
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
							continue;
						}
					}
				};
			}.start();
		}
		
	}
	public static MusicVideo createMV(JsonNode mvNode){
		String videoId=mvNode.path("vid").asText();
		String videoName=mvNode.path("title").asText();
		Date date=null;
		try {
			date=new SimpleDateFormat("yyyy-MM-dd").parse(mvNode.path("date").asText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String headUrl=mvNode.path("pic").asText();
		MusicVideo mv=new MusicVideo();
		mv.setVideoId(videoId);
		mv.setVideoName(videoName);
		mv.setDate(date);
		saveImg(headUrl, videoId);
		return mv;
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
				FileUtils
						.copyInputStreamToFile(
								in,
								new File(
										(String) (PropertiesUtil
												.getProperties("src/main/resources/file_download.properties")
												.get("mvImgPath"))
												+ "/"
												+ id.toString()
												+ ".jpg"));
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
	//获得json的根节点
		public static JsonNode getRootNode(String jsonStr){
			JsonNode root=null;
			jsonStr=jsonStr.substring(jsonStr.indexOf("(")+1, jsonStr.lastIndexOf(")"));
			try {
				root= objectMapper.readTree(jsonStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return root;
		}
	//根据传入的类和文件获取配置文件中的值组成的List
		public static <T> List<T> getPropertiesList(Class<T> clazz,String file){
			if(clazz!=BasicHeader.class &&   clazz!=BasicNameValuePair.class)
				return null;
			Properties single_music_list=PropertiesUtil.getProperties("src/main/resources/"+file);
			List<T> list=new ArrayList<>();
			Object item=null;
			for(Object key:single_music_list.keySet()){
				if(clazz==BasicHeader.class)
					item=new BasicHeader(key.toString(),single_music_list.get(key).toString());
				else if(clazz==BasicNameValuePair.class)
					item=new BasicNameValuePair(key.toString(),single_music_list.get(key).toString());
				list.add((T)item);
			}
			return list;
		}
		//获取请求的返回字符串
		public static String getStr(String uri,String headerFile,String paramFile,BasicNameValuePair... nameValuePairs){
			List<BasicHeader> headers=getPropertiesList(BasicHeader.class, headerFile);
			List<BasicNameValuePair> params=getPropertiesList(BasicNameValuePair.class,paramFile );
			CloseableHttpResponse  response=null;
			try {
				NameValuePair n;
				URIBuilder uriBuilder=new URIBuilder(uri);
				
				if(nameValuePairs!=null && nameValuePairs.length!=0){
					for(BasicNameValuePair p:nameValuePairs){
						params.add(p);
					}
				}
					
				List<NameValuePair> paramsList=new ArrayList<>();
				for(BasicNameValuePair bnvp:params)
					paramsList.add(bnvp);
				Header[] headerArray=new Header[headers.size()];
				for(int i=0;i<headers.size();i++)
					headerArray[i]=headers.get(i);
				
				uriBuilder.setParameters(paramsList);
				HttpGet httpGet=new HttpGet(uriBuilder.build());
				
				httpGet.setHeaders(headerArray);
				response =httpClient.execute(httpGet);
				HttpEntity entity=response.getEntity();
				if(entity!=null){
					return EntityUtils.toString(entity, "utf-8");
				}else{
					return "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					response.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return "";
		}

}
