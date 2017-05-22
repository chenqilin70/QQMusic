package com.huwl.oracle.qqmusic.music_action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huwl.oracle.qqmusic.music_biz.PlayerBiz;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		PlayerBiz playerBiz=(PlayerBiz) ac.getBean("playerBiz");
		InputStream in=playerBiz.batchDownload("C:/Users/aierxuan/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/music_view"
				,"000000t62JXXEh,000000U31BLK6Z,000002a33Eqj3D,000005FX0vVDAw");
		try {
			FileUtils.copyInputStreamToFile(in, new File("D:/1.zip"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
