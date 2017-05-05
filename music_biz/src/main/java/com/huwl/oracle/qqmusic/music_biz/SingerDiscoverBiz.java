package com.huwl.oracle.qqmusic.music_biz;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.Singer;
@Service("singerDiscoverBiz")
public class SingerDiscoverBiz extends BaseBiz {

	public List<Singer> getTop15FocusSinger(String loginedListenerId) {
		if(loginedListenerId==null)
			return null;
		return listenerDao.getFocusSinger(Integer.parseInt(loginedListenerId),15);
	}

	public Long getSingersCount(String category, String letter) {
		List<String> categorys=new ArrayList<>();
		InputStream in= SingerDiscoverBiz.class.getClassLoader().getSystemResourceAsStream("singer_condition_mapping.properties");
		Properties p=new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
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
			if(!"hot".equals(letter)){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singerDao.getSingersCount(letter,categorys);
	}
	

}
