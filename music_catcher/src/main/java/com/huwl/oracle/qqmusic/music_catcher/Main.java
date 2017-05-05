package com.huwl.oracle.qqmusic.music_catcher;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_daoimpl.SingerDaoImpl;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.AlbumType;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.huwl.oracle.qqmusic.music_model.Singer;

public class Main {
	public static CloseableHttpClient httpClient=HttpClients.createDefault();
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	public static ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	public static SingerDao singerDao=(SingerDao) ac.getBean("singerDao");
	public static void main(String[] args) {
		for(int p=1;p<=100;p++){
			String singerListJson=getStr("https://c.y.qq.com/v8/fcg-bin/v8.fcg",
					"singer_list_header.properties", 
					"singer_list_param.properties",
					new BasicNameValuePair("pagenum", ""+p));
			
			JsonNode singerListNode=getRootNode(singerListJson).path("data").path("list");
			
			int count=0;
			for(JsonNode singerJson:singerListNode){
				try {
					Singer singer=new Singer();
					singer.setSingerName(singerJson.path("Fsinger_name").textValue());
					singer.setSingerId(singerJson.path("Fsinger_mid").textValue());
					singer.setSingerLabel(singerJson.path("Fsinger_tag").textValue());
					fullSinger(singer);
					System.out.println(singer.getSingerName());
					//此处的写法啰嗦，历史原因后期改了dao代码，此处写法未测试
					Singer result;
					String id= (String) singerDao.save(singer);
					singer.setSingerId(id);
					result=singer;
					System.out.println("**************************************************************");
					System.out.println("*                                                            *");
					if(result==null){
						System.out.println("    第"+p+"页，第"+count+"个：未获取到singer");
					}else{
						System.out.println("    第"+p+"页，第"+count+"个："+result.getSingerId()+"\t"+result.getSingerName()+"\t已存入！！！");
					}
					System.out.println("*                                                            *");
					System.out.println("**************************************************************");
				} catch (Exception e) {
					System.out.println("    第"+p+"页，第"+count+"个：报错："+e.getMessage());
				}
				count++;
			}
		}
		
		
		
	}
	
	
	
	
	
	public static void fullSinger(Singer singer) {
		String thirtyMusicJson=getStr("https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_album.fcg",
				"album_list_header.properties", 
				"album_list_param.properties",
				new BasicNameValuePair("singermid", singer.getSingerId()));
		JsonNode rootNode=getRootNode(thirtyMusicJson);
		
		//获取singer信息
		String singerId=rootNode.get("data").get("singer_mid").textValue();
		String singerName=rootNode.get("data").get("singer_name").textValue();
		singer.setSingerId(singerId);
		singer.setSingerName(singerName);
		
		JsonNode list=rootNode.path("data").path("list");
		Set<Album> albums=new HashSet<>();
		for(JsonNode singleAlbum:list){
			Album album=getAlbumByJson(singleAlbum);
			album.setSinger(singer);
			
			//进入当前专辑
			String nowAlbumJson=getStr("https://c.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg",
					"music_list_header.properties", 
					"music_list_param.properties",
					new BasicNameValuePair("albummid", album.getAlbumId()));
			JsonNode albumRoot=getRootNode(nowAlbumJson);
			//Company com=getCompanyByJson(albumRoot);
			
			album.setCom(albumRoot.path("data").path("company_new").path("id").textValue());
			
			Set<Music> musics=getMusicListByJson(albumRoot,album);
			album.setMusics(musics);
			
			albums.add(album);
		}
		singer.setAlbums(albums);
		
		
	}
	
	public static Set<Music> getMusicListByJson(JsonNode albumRoot,Album album){
		String musicId,musicName;
		JsonNode musicListNode=albumRoot.path("data").path("list");
		Set<Music> musics=new HashSet<>();
		for(JsonNode singleMusic:musicListNode){
			musicId=singleMusic.path("songmid").textValue();
			musicName=singleMusic.path("songname").textValue();
			Music music=new Music(musicId, null, musicName, null, null, album);
			music.setAlbum(album);
			musics.add(music);
		}
		return musics;
	}
	//鉴于主键约束冲突问题，此方法暂时不适用，Company也不存入数据库
	public static Company getCompanyByJson(JsonNode albumRoot){
		String comName,comId,comHead;
		comId=albumRoot.path("data").path("company_new").path("id").textValue();
		comHead=albumRoot.path("data").path("company_new").path("headPic").textValue();
		comName=albumRoot.path("data").path("company_new").path("name").textValue();
		return new Company(comId, comName, comHead, null, null);
	}
	public static Album getAlbumByJson(JsonNode singleAlbum){
		String albumId,albumName,albumtype,albumDesc,albumLanguage,albumPubTime;
		albumId=singleAlbum.path("albumMID").textValue();
		albumName=singleAlbum.path("albumName").textValue();
		albumtype=singleAlbum.path("albumtype").textValue();
		albumDesc=singleAlbum.path("desc").textValue();
		albumLanguage=singleAlbum.path("lan").textValue();
		albumPubTime=singleAlbum.path("pubTime").textValue();
		return new Album(albumId, albumName, null, albumLanguage, true, getAlbumType(albumtype), null, getAlbumDate(albumPubTime), null, null, albumDesc);
	}
	//将来自网页的AlbumType字符串转化成本系统标准的Type字符串
	public static String getAlbumType(String type){
		for(AlbumType t:AlbumType.values()){
			String tempStr=t.toString();
			if(type.contains(tempStr)){
				return tempStr;
			}
		}
		return null;
	}
	//将日期字符串转化为日期类型
	public static Date getAlbumDate(String date){
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
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
	public static String getStr(String uri,String headerFile,String paramFile,BasicNameValuePair nameValuePair){
		List<BasicHeader> headers=getPropertiesList(BasicHeader.class, headerFile);
		List<BasicNameValuePair> params=getPropertiesList(BasicNameValuePair.class,paramFile );
		CloseableHttpResponse  response=null;
		try {
			NameValuePair n;
			URIBuilder uriBuilder=new URIBuilder(uri);
			
			if(nameValuePair!=null)
				params.add(nameValuePair);
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
