package com.huwl.oracle.qqmusic.music_biz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_model.Listener;

@Controller("listenerBiz")
public class ListenerBiz extends BaseBiz{
	public static double BOX_WIDTH=600,BOX_HEIGHT=450,BOX_RATIO=BOX_WIDTH/BOX_HEIGHT;
	public Integer getLikeMusicCount(String loginedListenerId) {
		return listenerDao.getLikeMusicCount(Integer.parseInt(loginedListenerId));
	}

	public Integer getLikeMusicMenuCount(String loginedListenerId) {
		return listenerDao.getLikeMusicMenuCount(Integer.parseInt(loginedListenerId));
	}

	public Integer getLikeAlbumCount(String loginedListenerId) {
		return listenerDao.getLikeAlbumCount(Integer.parseInt(loginedListenerId));
	}

	public Integer getLikeMvCount(String loginedListenerId) {
		return listenerDao.getLikeMvCount(Integer.parseInt(loginedListenerId));
	}

	public List<Album> getLikeAlbumByPage(String loginedListenerId,
			Integer pageSize, Integer pageNo) {
		return listenerDao.getLikeAlbumByPage(Integer.parseInt(loginedListenerId),pageSize,pageNo);
	}

	public String uploadImg(String realPath,File tempFile,Listener listener
			,String imageName,String height,String width,String top,String left) {
		Map<String , Object> message=new HashMap<>();
		String ext=imageName.substring(imageName.lastIndexOf("."));
		File newFile=new File(realPath+"/img/listener_head/"+listener.getListenerId()+ext);
		File headRoot=new File(realPath+"/img/listener_head");
		File oldFile=null;
		for(File f:headRoot.listFiles()){
			String name=f.getName().substring(0, f.getName().lastIndexOf("."));
			String oldExt=f.getName().substring(f.getName().lastIndexOf("."));
			if(name.equals(listener.getListenerId())){
				oldFile=new File(headRoot+"/"+listener.getListenerId()+"_old"+oldExt);
				f.renameTo(oldFile);
				break;
			}
		}
	    try {
			FileUtils.copyInputStreamToFile(new FileInputStream(tempFile), newFile);
			BufferedImage read=ImageIO.read(newFile);
			double realWidth=read.getWidth();
			double realHeight=read.getHeight();
			double realRatio=realWidth/realHeight;
			double widthInBox,heightInBox,topInBox,leftInBox;
			double cutHeightRatio , cutWidthRatio;
			double realTop,realLeft;
			double cutHeightInBox=Double.parseDouble(height),cutWidthInBox=Double.parseDouble(width);
			double realCutHeight,realCutWidth;
			if(realRatio>BOX_RATIO){
				System.out.println("width is more longer");
				widthInBox=BOX_WIDTH;
				heightInBox=BOX_WIDTH/realRatio;
				leftInBox=Double.parseDouble(left);
				topInBox=Double.parseDouble(top)-((BOX_HEIGHT-heightInBox)/2);
			}else{
				System.out.println("height is more longer");
				heightInBox=BOX_HEIGHT;
				widthInBox=BOX_HEIGHT*realRatio;
				topInBox=Double.parseDouble(top);
				leftInBox=Double.parseDouble(left)-((BOX_WIDTH-widthInBox)/2);
			}
			cutHeightRatio=topInBox/heightInBox;
			cutWidthRatio=leftInBox/widthInBox;
			realTop=cutHeightRatio*realHeight;
			realLeft=cutWidthRatio*realWidth;
			realCutHeight=(cutHeightInBox/heightInBox)*realHeight;
			realCutWidth=(cutWidthInBox/widthInBox)*realWidth;
			System.out.println(cutWidthInBox+","+heightInBox);
//			System.out.println("realCutHeight="+realCutHeight+",realCutWidth="+realCutWidth+",realTop="+realTop+"realLeft="+realLeft);
			OperateImage o=new OperateImage((int)Math.round(realLeft),(int)Math.round(realTop), (int)Math.round(realCutWidth), (int)Math.round(realCutHeight));
			o.setSrcpath(newFile.getAbsolutePath());
			o.setSubpath(newFile.getAbsolutePath());
			o.setType(ext.replace(".",""));
			o.cut();
			boolean flag=listenerDao.updateListenerHead(newFile.getName(),listener.getListenerId());
			if(!flag){
				newFile.delete();
				if(oldFile!=null){
		    		oldFile.renameTo(new File(oldFile.getAbsolutePath().replace("_old.", ".")));
		    	}
				message.put("isSuccess", false);
				System.out.println("11111111111111false_______________");
			}else{
				if(oldFile!=null){
					System.out.println(oldFile.getName()+"删除状态："+oldFile.delete());;
				}
					
				listener.setListenerHead(newFile.getName());
				message.put("isSuccess", true);
				message.put("head", newFile.getName());
				System.out.println("2222222222222true_______________");
			}
			
	    }  catch (Exception e) {
	    	if(oldFile!=null){
	    		oldFile.renameTo(new File(oldFile.getAbsolutePath().replace("_old.", ".")));
	    	}
	    	message.put("isSuccess", false);
	    	System.out.println("333333333333333false_______________");
			e.printStackTrace();
		}
		try {
			return objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println("444444444false_______________");
			return "{\"isSuccess\":false}";
		}
	}

	

	

	

}
