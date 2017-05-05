package com.huwl.oracle.qqmusic.music_util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");

	}

}
