package com.huwl.oracle.qqmusic.music_biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huwl.oracle.qqmusic.music_model.Singer;
@Service("singerDiscoverBiz")
public class SingerDiscoverBiz extends BaseBiz {

	public List<Singer> getTop15FocusSinger(String loginedListenerId) {
		if(loginedListenerId==null)
			return null;
		return listenerDao.getFocusSinger(Integer.parseInt(loginedListenerId),15);
	}

}
