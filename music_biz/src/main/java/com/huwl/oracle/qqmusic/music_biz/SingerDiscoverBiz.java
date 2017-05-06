package com.huwl.oracle.qqmusic.music_biz;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.Singer;
import com.huwl.oracle.qqmusic.music_util_model.PageBean;
@Service("singerDiscoverBiz")
public class SingerDiscoverBiz extends BaseBiz {
	private static Properties p=null;
	static{
		InputStream in= SingerDiscoverBiz.class.getClassLoader().getResourceAsStream("singer_condition_mapping.properties");
		p=new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<Singer> getTop15FocusSinger(String loginedListenerId) {
		if(loginedListenerId==null)
			return null;
		return listenerDao.getFocusSinger(Integer.parseInt(loginedListenerId),15);
	}

	public Long getSingersCount(String category, String letter) {
		List<String> categorys=categoryConverter(category);
		List<String> letters=letterConverter(letter);
		return singerDao.getSingersCount(letters,categorys);
	}

	public List<Singer> getSingerListByCondition(PageBean pageBean,
			String category, String letter) {
		List<String> categorys=categoryConverter(category);
		List<String> letters=letterConverter(letter);
		List<Object[]> objList=singerDao.getSingerListByCondition(pageBean,categorys,letters);
		List<Singer> result=new ArrayList<>();
		for(Object[] arr: objList){
			String singerId=(String) arr[0];
			String singerName=(String) arr[1];
			Singer singer=new Singer();
			singer.setSingerId(singerId);
			singer.setSingerName(singerName);
			result.add(singer);
		}
		return result;
	}
	private static List<String> letterConverter(String letter){
		List<String> letters=new ArrayList<>();
		if(!"hot".equals(letter)){
			letters.add(letter);
		}
		return letters;
	}
	private static  List<String> categoryConverter(String category){
		List<String> categorys=new ArrayList<>();
		if(!"all".equals(category)){
			if("otherCategory".equals(category)){
				String[] lans=((String)p.get(category)).split(",");
				for(String lan:lans){
					categorys.add((String) p.get(lan));
				}
			}else{
				categorys.add((String) p.get(category));
			}
		}
		return categorys;
		
	}
	

}
