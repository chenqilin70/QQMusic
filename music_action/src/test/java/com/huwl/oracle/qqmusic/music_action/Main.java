package com.huwl.oracle.qqmusic.music_action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huwl.oracle.qqmusic.music_biz.PlayerBiz;

public class Main {

	public static void main(String[] args) {
		String test="bababalskdfjldskfjldskfjbabaskljfdldsbalkj";
		System.out.println(test.replaceAll("(ba)+", "-"));

	}

}
