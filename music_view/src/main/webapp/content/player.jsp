<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的音乐</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/player.css">
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/rgbaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/player.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.mCustomScrollbar.min.js"></script>

</head>
<body onselectstart="return false;">
<input type="hidden" name="img_repository_path" value="<s:text name='img_repository_path'/>" />
<div class="playerBg playerBg1">
	<div class="bgMask"></div>
</div>
<div class="contentContainer" align="center">
	<div class="QQMusicLink"></div>
	<div class="setting"><s:text name="setting"/></div>
	<div class="login"><s:text name="login"/></div>
</div>
<div class="centerBox">
	<div class="optBtnGroup">
		<div class="optBtn collect" align="center">
			<span><i></i><s:text name="collect"/></span>
			<div class="btnBorder"></div>
		</div>
		<div class="optBtn addTo" align="center">
			<span><i></i><s:text name="addTo"/></span>
			<div class="btnBorder"></div>
		</div>
		<div class="optBtn download" align="center">
			<span><i></i><s:text name="download"/></span>
			<div class="btnBorder"></div>
		</div>
		<div class="optBtn delete" align="center">
			<span><i></i><s:text name="delete"/></span>
			<div class="btnBorder"></div>
		</div>
		<div class="optBtn clearList" align="center">
			<span><i></i><s:text name="clearList"/></span>
			<div class="btnBorder"></div>
		</div>
	</div>
	<div class="rightColumn">
		<img class="albumHead" 
			src="<%=request.getContextPath() %>/img/player_cover.png" alt="">
		<a target="_blank"><img class="albumCover" src="<%=request.getContextPath() %>/img/album_cover.png"></a>
		<div class="musicInfoBox" align="center">
			<div><span class="infoTitle"><s:text name="musicName"></s:text>：</span><a target="_blank"  class="defaultPlayerLink"><span class="musicName"></span></a></div>
			<div><span class="infoTitle"><s:text name="singerName"></s:text>：</span><a target="_blank" class="defaultPlayerLink"><span class="singerName"></span></a></div>
			<div><span class="infoTitle"><s:text name="albumName"></s:text>：</span><a target="_blank"  class="defaultPlayerLink"><span class="albumName"></span></a></div>
		</div>
		<div class="lyricBox"></div>
	</div>
	<div class="musicList mCS-my-theme">
		<table  border="0" cellpadding="0" cellspacing="0" align="left">
			<thead>
				<tr>
					<th class="checkBoxTh">
						<div class="musicCheckbox" selecteditem="false">
							<span class="right">√</span>
						</div>
					</th>
					<th class="musicTh"><s:text name="music"/></th>
					<th class="singerTh"><s:text name="singer"/></th>
					<th class="songTimeTh"><s:text name="songTime"/></th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>
	<div class="musicConsole">
	<audio id="mp3Audio" preload="preload"  height="100" width="100">
	    <source src="<%=request.getContextPath() %>/mp3/1.mp3" type="audio/mp3" />
	</audio>
	
		<table cellpadding="0" cellspacing="0" border="0">
			<tbody>
				<tr>
					<td class="playBtnBox">
						<div class="prevMusic playBtn"></div>
						<div class="playMusic playBtn" ></div>
						<div class="nextMusic playBtn" ></div>
					</td>
					<td class="progressBox">
						<div class="progressBoxDiv">
							<div class="barBox">
								<div class="progressBar">
									<div class="circleInBar"></div>
								</div>
							</div>
							
							<div class="nowPlayMusic">
								<span class="nowName"></span>
								<span class="nowTime"></span>
							</div>
						</div>
					</td>
					<td class="otherBtnBox">
						<div class="otherBtnBoxDiv">
							<div class="loopList"></div>
							<div class="likeBtn"></div>
							<div class="downLoadBtn"></div>
							<div class="changeModel"></div>
							<div class="trumpetIcon"></div>
							<div class="volume"></div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
</div>
</body>
</html>

