package com.huwl.oracle.qqmusic.music_converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.struts2.util.StrutsTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.SingleDataSourceLookup;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_util_model.AlbumCondition;
@Component("albumConditionConverter")
public class AlbumConditionConverter extends StrutsTypeConverter{
	@Autowired
	private ObjectMapper objectMapper;
	private SimpleDateFormat sf=new SimpleDateFormat("yyyy.MM.dd");
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		AlbumCondition albumCondition=new AlbumCondition();
		System.out.println("have into converter");
		if(toClass==AlbumCondition.class){
			if(values!=null && values.length>0){
				String value=values[0];
				JsonNode jsonNode;
				try {
					jsonNode = objectMapper.readTree(value);
					String language=jsonNode.path("language").asText();
					String genres=jsonNode.path("genres").asText();
					String category=jsonNode.path("category").asText();
					String price=jsonNode.path("price").asText();
					String albumCompany=jsonNode.path("albumCompany").asText();
					String years=jsonNode.path("years").asText();
					if(!"all".equals(language)){
						albumCondition.setLanguage(AlbumCondition.conditionMapping.getProperty(language));
					}
					if(!"all".equals(genres)){
						albumCondition.setGenres(AlbumCondition.conditionMapping.getProperty(genres));				
					}
					if(!"all".equals(category)){
						albumCondition.setCategory(AlbumCondition.conditionMapping.getProperty(category));
					}
					if(!"all".equals(price)){
						albumCondition.setIsfree("true".equals(price));
					}
					if(!"all".equals(albumCompany)){
						albumCondition.setAlbumCompany(Integer.parseInt(albumCompany));
					}
					if(!"all".equals(years)){
						String[] strArr=years.split("-");
						try {
							
							albumCondition.setMinYear(sf.parse(strArr[0]));
							albumCondition.setMaxYear(sf.parse(strArr[1]));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else{
				System.out.println("values is null or length <=0");
			}
		}else{
			System.out.println("不是这个类型");
		}
		return albumCondition;
	}

	@Override
	public String convertToString(Map context, Object o) {
		System.out.println("对象转化为String目前还没必要");
		return null;
	}


}
