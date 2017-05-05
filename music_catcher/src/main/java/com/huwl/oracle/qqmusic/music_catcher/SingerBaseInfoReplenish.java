package com.huwl.oracle.qqmusic.music_catcher;

import java.io.IOException;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;

public class SingerBaseInfoReplenish {
	public static CloseableHttpClient httpClient=HttpClients.createDefault();
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static void main(String[] args) {
		final SingerDao dao = (SingerDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("singerDao");
		List ids=dao.query("SELECT s.singerId FROM Singer s", null);
		for(Object o:ids){
			if(o==null || o.toString().isEmpty())
				continue;
			String jsonStr=getStr("https://c.y.qq.com/splcloud/fcgi-bin/fcg_get_singer_desc.fcg"
					, "SingerBaseInfoReplenish/singer_baseinfo_header.properties"
					, "SingerBaseInfoReplenish/singer_baseinfo_param.properties"
					, new BasicNameValuePair("singermid", o.toString()));
			Document document=Jsoup.parse(jsonStr);
			Elements es=document.getElementsByTag("info");
			int i=dao.updateByHql("UPDATE Singer s SET s.baseInfo=? WHERE s.singerId=?", es.toString(),o.toString());
			System.out.println(o.toString()+(i!=0?"的基本信息已经存入":"的信息未正常存入"));
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
