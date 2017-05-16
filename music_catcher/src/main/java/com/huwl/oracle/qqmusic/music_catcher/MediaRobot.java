package com.huwl.oracle.qqmusic.music_catcher;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
/*
 * 控制台高383px   请求名字宽761px*/
public class MediaRobot {
	public static Robot robot;
	public static void main(String[] args) throws Exception {
		robot= new Robot();
		for(int i=0;i<10;i++){
			catchUrl(i);
		}
	}
	public static void catchUrl(int index) throws Exception {
		/*点开chrome*/
		moveto(615, 1060);
		click();
		Thread.sleep(500);
		/*点开chrome*/
		/*player获取焦点*/
		moveto(50, 400);
		click();
		/*player获取焦点*/
		/*下一首*/
		next();
		Thread.sleep(500);
		/*下一首*/
		/*复制请求*/
		moveto(200, 878);//移动第一个请求
		click();
		Thread.sleep(200);
		if(index>5){
			moveto(753, 876);//移动到滚动条
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(1500);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			moveto(200, 878);
			click();
		}
		for(int i=0;i<index;i++){
			input(KeyEvent.VK_DOWN);
			Thread.sleep(100);
		}
		moveto(875, 899);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		moveto(1400, 918);
		Thread.sleep(100);
		moveto(1420, 918);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		moveto(950, 906);
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);//打开复制菜单
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		moveto(980, 921);
		Thread.sleep(300);
		click();
		/*复制请求*/
		/*粘贴地址*/
		moveto(710, 1060);
		click();
		moveto(600, 18);
		Thread.sleep(500);
		past();
		input(KeyEvent.VK_ENTER);
		Thread.sleep(500);
		/*粘贴地址*/
	}
	public static void input(int key){
		robot.keyPress(key);
		robot.keyRelease(key);
	}
	public static void copy(){
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_C);
	}
	public static void past(){
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
	}
	public static void next(){
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
	}
	public static void moveto(int x,int y){
		robot.mouseMove(x, y);
	}
	public static void click(){
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

}
