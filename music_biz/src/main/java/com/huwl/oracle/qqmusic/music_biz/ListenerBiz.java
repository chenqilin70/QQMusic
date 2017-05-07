package com.huwl.oracle.qqmusic.music_biz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;

import com.huwl.oracle.qqmusic.music_daoimpl.HibernateConfigurationUtil;
import com.huwl.oracle.qqmusic.music_model.Album;

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

	public String uploadImg(String realPath,File tempFile,String listenerId
			,String imageName,String height,String width,String top,String left) {
		System.out.println("uploadHead");
		String ext=imageName.substring(imageName.lastIndexOf("."));
		System.out.println(tempFile.getName()+"\n"+tempFile.getAbsolutePath());
	    File newFile=new File(realPath+"/img/listener_head/"+listenerId+ext);
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
				widthInBox=BOX_WIDTH;
				heightInBox=BOX_WIDTH/realRatio;
				leftInBox=Double.parseDouble(left);
				topInBox=Double.parseDouble(top)-((BOX_HEIGHT-heightInBox)/2);
			}else{
				widthInBox=BOX_HEIGHT;
				heightInBox=BOX_HEIGHT*realRatio;
				topInBox=Double.parseDouble(top);
				leftInBox=Double.parseDouble(left)-((BOX_WIDTH-widthInBox)/2);
			}
			cutHeightRatio=topInBox/BOX_HEIGHT;
			cutWidthRatio=leftInBox/BOX_WIDTH;
			realTop=cutHeightRatio*realHeight;
			realLeft=cutWidthRatio*realWidth;
			realCutHeight=(cutHeightInBox/heightInBox)*realHeight;
			realCutWidth=(cutWidthInBox/widthInBox)*realWidth;
			System.out.println("realCutHeight="+realCutHeight+",realCutWidth="+realCutWidth+",realTop="+realTop+"realLeft="+realLeft);
			OperateImage o=new OperateImage((int)Math.round(realLeft),(int)Math.round(realTop), (int)Math.round(realCutWidth), (int)Math.round(realCutHeight));
			o.setSrcpath(realPath+"/img/listener_head/"+listenerId+ext);
			o.setSubpath(realPath+"/img/listener_head/"+listenerId+"_CutImg"+ext);
			o.cut();
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    
		return "{\"message\":\"true\"}";
	}

	

	

	

}
