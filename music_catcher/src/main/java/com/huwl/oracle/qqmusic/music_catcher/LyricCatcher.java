package com.huwl.oracle.qqmusic.music_catcher;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

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
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_dao.MusicDao;
import com.huwl.oracle.qqmusic.music_model.Music;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;

public class LyricCatcher {
	public static CloseableHttpClient httpClient=HttpClients.createDefault();
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static void main(String[] args) {
		final MusicDao dao = (MusicDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("musicDao");
		Long count=dao.getNullLyricCount();
		int threadCount=10;
		final int pageSize=(int) ((count/threadCount)+1);
		for( int i=1;i<=threadCount;i++){
			final int pageNo=i;
			new Thread(){
				@Override
				public void run() {
					List<Serializable> ids=null;
					boolean flag=true;
					while(flag){
						flag=false;
						try {
							ids=dao.getNullLyricIdBasePage(pageSize, pageNo);
						} catch (Exception e) {
							System.out.println(pageNo+"线程出了错1 ，正在重复执行");
							flag=true;
						}
					}
					System.out.println("查到了线程"+pageNo+"负责的ids："+ids.size());
					for(Serializable id:ids){
						String html=getStr("https://y.qq.com/n/yqq/song/"+id.toString()+".html"
								, "null.properties", "null.properties", null);
						int start=html.indexOf("songid=")+7;
						int end=html.indexOf("&",start);
						if(start-7==-1 || end==-1){
							continue;
						}
						String result=html.substring(start, end);
						System.out.println(result);
						String jsonStr=getStr("https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg"
								, "LyricCatcher/lyric_header.properties"
								, "LyricCatcher/lyric_param.properties"
								, new BasicNameValuePair("musicid", result));
						String lyric=getRootNode(jsonStr).path("lyric").asText();
						int i=0;
						if(lyric !=null && !lyric.isEmpty())
							i=dao.updateByHql("UPDATE Music m SET m.lyric=? WHERE m.musicId=?",lyric,id);
						if(i!=0){
							System.out.println("修改了一个music的歌词");
						}
					}
				};
			}.start();
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
