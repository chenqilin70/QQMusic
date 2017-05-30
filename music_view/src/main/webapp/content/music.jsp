<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.huwl.oracle.qqmusic.music_model.Listener" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="music.musicName"/>-<s:property value="music.album.singer.singerName"/>-QQ音乐 - 听我想听的歌！</title>
        <!-- 导库，开始 -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
        <!-- 导库，结束 -->
        <!-- 模板js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/template.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/template.js"></script>
        <!-- 模板js和css，结束 -->
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/logoIcon.png" />
        <!-- 本页js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/music.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/music.js"></script>
        <!-- 本页js和css，开始 -->
    </head>
<body>
    <!-- 模板头，开始 -->
        <jsp:include page="/head.jsp"></jsp:include>
      <!-- 模板头，结束 -->
    <!-- 主体，开始 -->
    <div class="container" align="center">
        <div class="musicContent">
        	<div class="musicBaseInfoBox">
        		<img alt="" 
        			src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='music.album.albumId'/>.jpg" 
        			class="albumHead">
        		<div class="musicBaseInfoList">
        			<table class='musicBaseTable' border="0" cellspacing="0" cellpadding="0">
        				<tbody>
        					<tr class="controlTr">
        						<td class="controlTd1"></td>
        						<td class="controlTd2"></td>
        					</tr>
        					<tr>
        						<td colspan="2" class="musicNameTd">
        							<s:property value='music.musicName'/>
        						</td>
        					</tr>
        					<tr>
        						<td colspan="2" class="singerNameTd">
        							<i class="personIcon"></i>
        							<span><s:property value='music.album.singer.singerName'/></span>
        						</td>
        					</tr>
        					<tr>
        						<td class="albumNameTd"><s:text name='album'/>：<s:property value='music.album.albumName'/></td>
        						<td class="languageTd"><s:text name='language'/>：<s:property value='music.album.language'/></td>
        					</tr>
        					<tr>
        						<td class="genreTd"><s:text name="genre"></s:text>：<s:property value='music.album.genres'/></td>
        						<td class="companyTd"><s:text name="company"></s:text>：<s:property value='music.album.company.comName'/></td>
        					</tr>
        					<tr>
        						<td colspan="2" class="publishTimeTd">
        							<s:text name="publishTime"></s:text>：<s:date name="music.album.publishTime" format="yyyy-MM-dd"/>
        						</td>
        					</tr>
        				</tbody>
        			</table>
        			<button class="musicOptionBtn playBtn">
        				<i class="playIcon"></i><span><s:text name='play'/></span>
        			</button>
        			<button class="musicOptionBtn collectBtn">
        				<i class="collectIcon"></i><span><s:text name='collect'/></span>
        			</button>
        			<button class="musicOptionBtn moreBtn">
        				<i class="moreIcon"></i><span><s:text name='more'/></span>
        			</button>
        		</div>
        	</div>
        	<div class="bottomBox">
        		<div class="lyricBox" align="left">
        			<h2><s:text name="lyric"></s:text><i class="lyricIcon"></i></h2>
        			<div id="lyricMainBody">
        				
        			</div>
        			<input type="hidden" name='lyric' value="<s:property value='music.lyric'/>">
        		</div>
        		<div class="otherInfoBox">
        			<s:if test="music.album.desc!=null && music.album.desc.length!=0">
        				<div class="briefIntroTitle"><s:text name="briefIntro"/></div>
	        			<p class="briefIntroText"></p>
        				<input type="hidden" class="descValue" value="<s:property value="music.album.desc"/>"/>
        				<div class="otherAlbumDescBox">
                            <div class="bubbleIcon"></div>
                            <p class="albumMoreDesc">
                                <span class="albumDescTitle">
                                    <s:text name="albumDesc"/>
                                </span>
                                <br/>
                                <p class="descContent"></p>
                           </p>
                        </div>
                        <div align="left" class="moreInfoBox"><span class="moreInfo">[<s:text name="more"></s:text>]</span></div>
        			</s:if>
        			<s:if test="relatedMV!=null">
        				<div class="briefIntroTitle"><s:text name="relatedMV"/></div>
        				<div class="mvHeadBox">
        					<img class="mvHead" alt="" src="<s:text name='img_repository_path'/>/mv_head/<s:property value='relatedMV.videoId'/>.jpg">
        					<img class="playCover" src='<%=request.getContextPath()%>/img/cover_play.png'></img>
        				</div>
        				<div align="left"><s:a action=''  class="mvTextLink mvName" title='%{relatedMV.videoName }'><s:property value='relatedMV.videoName'/></s:a></div>
        				<div align="left">
        					<s:a action='singer' class="mvTextLink mvSinger" title='%{music.album.singer.singerName }'>
	        					<s:param name='singerId' value='music.album.singer.singerId'/>
	        					<s:property value='music.album.singer.singerName'/>
        					</s:a>
        				</div>
        			</s:if>
        		</div>
        		
        	</div>
        	
        </div>
    </div>
    <!-- 主体，结束 -->
    <!-- 模板尾，开始 -->
    <jsp:include page="/foot.jsp" flush=""></jsp:include>
        <!-- 模板尾，结束 -->
        <s:debug></s:debug>
</body>
</html>