<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<ul class="myLikeUl">
	<li class="presentItem">
		<s:text name="music" />&nbsp;<s:property value="likeMusicCount" />
	</li>
	<li>
		<s:a class="defaultStyleLink" model="MYLIKE_MENU">
			<s:text name="musicMenu" />&nbsp;<s:property value="likeMusicMenuCount" />
		</s:a>
	</li>
	<li> 
		<s:a class="defaultStyleLink" model="MYLIKE_ALBUM">
			<s:text name="album" />&nbsp;<s:property value="likeAlbumCount" />
		</s:a>
	</li>
	<li>
		<s:a class="defaultStyleLink" model="MYLIKE_MV">
			<s:text name="mv" />&nbsp;<s:property value="likeMvCount" />
		</s:a>
	</li>
</ul>
<s:if test="likeMusicCount==0">
	<div class="nullImg">
		<img src="<%=request.getContextPath() %>/img/symbol_none.png" alt=""/>
	</div>
	<div class="nothingBeFound">
  		<s:text name="nothingBeFound1"/><s:a href="#" class="gotoMusicPlace"><s:text name="musicPlace"/></s:a><s:text name="nothingBeFound2"/>
    </div>
</s:if>
