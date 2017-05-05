package com.huwl.oracle.qqmusic.music_catcher;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import com.huwl.oracle.qqmusic.music_model.Album;

public class ALbumReplenish {
	public static CloseableHttpClient httpClient=HttpClients.createDefault();
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static void main(String[] args) {
		final AlbumDao dao = (AlbumDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("albumDao");
		Long count = dao.getCount();
		final Integer pageSize = (int) ((count / 5) + 1);
		for (int i = 1; i <= 5; i++) {
			final Integer pageNo = i;
			new Thread() {
				public void run() {
					List<Serializable> ids = dao.getIdBasePage(pageSize, pageNo);
					for(Serializable id:ids){
						String str=getStr("https://c.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg"
								, "AlbumReplenish/album_detail_header.properties"
								, "AlbumReplenish/album_detail_param.properties"
								, new BasicNameValuePair("albummid", id.toString()));
						JsonNode rootNode=getRootNode(str);
						String desc=rootNode.path("data").path("desc").asText();
						String genres=rootNode.path("data").path("genre").asText();
						String comId=rootNode.path("data").path("company_new").path("id").asText();
						int result=dao.updateByHql("UPDATE Album a set a.genres=? ,a.desc=?,a.com=? WHERE a.albumId=?", genres,desc,comId,id);
						if(result!=0){
							System.out.println("\nid为"+id+"的album数据已更改\n");
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
