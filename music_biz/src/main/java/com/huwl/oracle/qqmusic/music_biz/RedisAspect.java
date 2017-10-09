package com.huwl.oracle.qqmusic.music_biz;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huwl.oracle.qqmusic.music_dao.AlbumDao;
import com.huwl.oracle.qqmusic.music_model.Album;
import com.huwl.oracle.qqmusic.music_util.RedisCache;

@Component
@Aspect
public class RedisAspect {
	@Autowired 
	private RedisCache redisCache;
	@Around("execution(* com.huwl.oracle.qqmusic.music_biz.IndexBiz.getChineseAlbums(..))")
     public List<Album> vlidateArgs(ProceedingJoinPoint  joinPoint) {
		List<Album> result=null;
		result=(List<Album>) redisCache.getDataFromRedis("inland");
		if(result==null){
			try {
				result=(List<Album>) joinPoint.proceed();
				redisCache.setDataToRedis("inland", result);
				System.out.println("redis 中没有国语数据  正在存入");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("redis 中有国语数据  直接用缓存");
		}
         return result;
     }
	
	
	
	
	
	
	
	
	
}
