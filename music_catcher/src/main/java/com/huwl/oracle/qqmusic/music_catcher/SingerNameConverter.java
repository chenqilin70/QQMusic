package com.huwl.oracle.qqmusic.music_catcher;

import java.io.Serializable;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_model.Singer;

public class SingerNameConverter {

	public static void main(String[] args) {
		final SingerDao dao = (SingerDao) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("singerDao");
		
		Long count=dao.getCount();
		int threadCount=2;
		final int pageSize=(int) ((count/threadCount)+1);
		for(int i=0;i<threadCount;i++){
			final int pageNo=i+1;
			new Thread(){
				public void run() {
					List<Serializable> list=dao.getIdBasePage(pageSize, pageNo);
					System.out.println("共有"+list.size()+"个id");
					for(Serializable id:list){
						try {
							Singer s=dao.getObjectById(id);
							String letter=getLetter(s.getSingerName());
							System.out.println(s.getSingerName()+"--"+letter);
							dao.updateByHql("update Singer s set s.letter=? where s.singerId=?", letter,s.getSingerId());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}

				private String getLetter(String singerName) {
					String result=null;
					String[] pinyinArr=PinyinHelper.toHanyuPinyinStringArray(singerName.toCharArray()[0]);
					if(pinyinArr==null){
						result=singerName.substring(0,1).toUpperCase();
					}else{
						String pinyin=pinyinArr[0];
						result= pinyin.substring(0,1).toUpperCase();
						
					}
					return result;
				};
			}.start();
		}
		
		
		
	}

}
