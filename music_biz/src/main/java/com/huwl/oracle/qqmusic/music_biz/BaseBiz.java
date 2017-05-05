package com.huwl.oracle.qqmusic.music_biz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_dao.BaseDao;
import com.huwl.oracle.qqmusic.music_dao.CompanyDao;
import com.huwl.oracle.qqmusic.music_dao.ListenerDao;
import com.huwl.oracle.qqmusic.music_dao.MusicDao;
import com.huwl.oracle.qqmusic.music_dao.MusicVideoDao;
import com.huwl.oracle.qqmusic.music_dao.SingerDao;
import com.huwl.oracle.qqmusic.music_model.Listener;

@Service("baseBiz")
public class BaseBiz {
	@Autowired
	protected BaseDao baseDao;
	@Autowired
	protected AlbumDao albumDao;
	@Autowired
	protected CompanyDao companyDao;
	@Autowired 
	protected MusicDao musicDao;
	@Autowired
	protected SingerDao singerDao;
	@Autowired
	protected MusicVideoDao musicVideoDao;
	@Autowired
	protected ListenerDao listenerDao;
	@Autowired
	protected ObjectMapper objectMapper;
	private static List<Integer> options=new ArrayList<>();
	private static Random random=new Random();
	static{
		for(int i=65;i<=90;i++){
			options.add(i);
		}
		for(int i=97;i<=122;i++){
			options.add(i);
		}
		for(int i=48;i<=57;i++){
			options.add(i);
		}
	}
	public Listener reGetListener(String listenerId){
		Listener l=listenerDao.getObjectById(listenerId);
		l.getMyMusicMenu().size();
		l.getCreaSinger().size();
		l.getFans().size();
		return l;
	}
	public static InputStream getValidateCodeImg(String code){
		System.out.println(code);
		BufferedImage codeImg=new  BufferedImage(105, 40, BufferedImage.TYPE_INT_RGB);
		Graphics g=codeImg.getGraphics();
		g.setFont(new Font("微软雅黑", Font.ITALIC, 30));
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, 105, 40);
		for(int i=0;i<=3;i++){
			g.setColor(getRandomColor());
			g.drawString(code.substring(i,i+1), i*25, 30);
		}
		for(int i=0;i<=10;i++){
			g.setColor(getRandomColor());
			int roundRectWidth=random.nextInt(8);
			int roundRectHight=random.nextInt(8);
			g.fillRoundRect(random.nextInt(105), random.nextInt(40)
					, roundRectWidth, roundRectHight
					, roundRectWidth/2, roundRectHight/2);
			g.drawLine(random.nextInt(105), random.nextInt(40), random.nextInt(105), random.nextInt(40));
		}
		
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		try {
			ImageIO.write(codeImg, "jpg", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
	private static Color getRandomColor(){
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
	}
	public static String getRandomCode(){
		List<Integer> indexs=new ArrayList<>();
		for(int i=0;i<=3;i++){
			indexs.add(random.nextInt(options.size()-1));
		}
		StringBuffer sb=new StringBuffer();
		for(Integer index:indexs){
			sb.append(((char)(options.get(index).intValue())));
		}
		return sb.toString();
	}
	public static List getRandomList(List list,Integer limit){
		int size=list.size();
		if(size<limit)
			return list;
		List result=new ArrayList();
		List<Integer> indexList=new ArrayList<Integer>();
		for(int i=0;i<limit;i++){
			int tempIndex=0;
			while(true){
				tempIndex=random.nextInt(size);
				if(!indexList.contains(tempIndex)){
					break;
				}else{
					System.out.println("相同了，开始循环下一次");
				}
			}
			indexList.add(tempIndex);
		}
		for(Integer i:indexList){
			result.add(list.get(i));
		}
		return result;
	}
	
	
}
