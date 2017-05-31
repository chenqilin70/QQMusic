<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value='singer.singerName'/>-<s:text name="QQMusic"></s:text> - <s:text name='listenWhatIWanna'/>！</title>
        <!-- 导库，开始 -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
        <sx:head />
        <!-- 导库，结束 -->
        <!-- 模板js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/template.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/template.js"></script>
        <!-- 模板js和css，结束 -->
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/logoIcon.png" />
        <!-- 本页js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/singer.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/singer.js"></script>
        <!-- 本页js和css，结束 -->
		<script type="text/javascript">
			function changeErrorImg(element){
				element.src="/music_view/img/singer_300.png";
			}
		</script>
    </head>
    <body>
    <!-- 用于快速回到开头 -->
    <a name="mao1"></a>
        <!-- 模板头，开始 -->
        <jsp:include page="/head.jsp"></jsp:include>
        <!-- 模板头，结束 -->
        <!-- 主体，开始 -->
         <div class="container" align="center">
         	<div class="singerContent">
         		<div class="singerInfo"  align="left">
         			<img class="singerHead"   name="singerHead"   onerror="changeErrorImg(this)"
         				src="<s:text name='img_repository_path'></s:text>/singer_head/<s:property  value="singerId"/>.jpg"/>
         			<div class="singerBaseInfo">
         				<div class="singerName">
         					<s:property value="singer.singerName"/>
         				</div>
         				<div class="singerBriefIntro">
         					<input type="hidden" id='hiddenBriefIntro' value="<s:property value='singer.baseInfo'/>"/>
         					<div></div>
         					<a class="moreInfo">[<s:text name="more"></s:text>]</a>
         				</div>
         				<div class="singerWorks">
         					<ul>
         						<li class="singleMusicWork">
         							<span class="workType">单曲</span>
         							<i></i>
         							<span class="workNum"><s:property value="musicCount"/></span>
         						</li>
         						<li class="albumWork">
         							<div class="workLine leftLine"></div>
									<span class="workType">专辑</span>
									<i></i>
         							<span class="workNum"><s:property value="albumCount"/></span>
         							<div class="workLine rightLine"></div>
								</li>
         						<li class="mvWork">
									<span class="workType">MV</span>
									<i></i>
         							<span class="workNum"><s:property value="mvCount"/></span>
								</li>
         					</ul>
         				</div>
         				<div class="singerButton" align="left">
         					<button class="palyHotMusic" align="center">
         						<span>
         							<i></i>
         							<s:text name="playSinger_s_HotMusic"></s:text>
         						</span>
         					</button>
         					<button class="addCare" singerId="<s:property value='singerId'/>" align="center">
         						<span>
         							<s:if test="isCared">
         								<input type="hidden" id="isCared" value="true"/>
         								<i class="cared"></i>
         								<span class="careText">
         									<s:text name="haveCared"></s:text>
         								</span>
         								
         							</s:if>
         							<s:else>
         								<input type="hidden" id="isCared" value="false"/>
         								<i class="uncared"></i>
         								<span class="careText">
         									<s:text name="care"></s:text>
         								</span>
         							</s:else>
         						</span>
         					</button>
         				</div>
         			</div>
         		</div>
         		<!-- 热门歌曲  -->
         		<div class="modelTitle" align="left">
         			<s:text name="hotMusicTitle"></s:text>
         			<div class="toAll">
                        <i class="allIcon"></i>
                        <a class="allText" ><s:text name="All"></s:text></a>
                    </div>
         		</div>
         		<!-- 歌曲表格 -->
         		<table class="songsTable" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th class="songIndex"></th>
                                <th class="songName"><s:text name="songName"/></th>
                                <th class="songAlbum"><s:text name="album"/></th>
                                <th class="songTime"><s:text name="songTime"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="top10Music" status="status" var="music">
                                <tr class="songTr">
                                    <td class="songSetHeight songTd"><s:property value="#status.index+1" /></td>
                                    
                                        <td class="songNameTd songTd">
                                            <s:a action="song" class="defaultStyleLink">
                                                <s:param name="musicId" value="musicId"></s:param>
                                               <s:property value="musicName" />
                                            </s:a>
                                            <div class="songOption shareSong"></div>
                                            <div class="songOption downloadSong"></div>
                                            <div class="songOption addSong"></div>
                                            <div class="songOption playSong"></div>
                                        </td>
                                        <td class="albumTd songTd">
                                            <s:a action="singer"  class="defaultStyleLink">
                                                <s:param name="albumId" value="music.album.albumId"></s:param>
                                                 <s:property value="album.albumName" />
                                            </s:a>
                                        </td>
                                    <td class="songTimeTd songTd"><s:text name="notAvailable"/> </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                    <!-- 专辑 -->
	         		<div class="modelTitle" align="left">
	         			<s:text name="album"></s:text>
	         			<div class="toAll">
	                        <i class="allIcon"></i>
	                        <a class="allText" ><s:text name="All"></s:text></a>
	                    </div>
	         		</div>
	         		<!-- 专辑列表 -->
	         		<ul class="albumUl modelUl">
		         		<s:iterator value="top5Album" status="status">
		         			<s:if test="#status.last">
		         				<li special="lastModelLi">
		         			</s:if>
		         			<s:else>
		         				<li>
		         			</s:else>
		         				<div class="albumBox" align="left">
			         				<div class="imgContainer">
			         					<img alt="" class="albumHead"
			         						src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg">
	                                    <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
			         				</div>
		         					<div class="info1"><s:a action="album" class="defaultStyleLink"><s:param name="albumId" value="albumId"></s:param><s:property value="albumName"/></s:a></div>
		         					<div class="info2"><s:date format="yyyy-MM-dd" name="publishTime"/></div>
		         				</div>
		         			</li>
		         		</s:iterator>
	         		</ul>
	         		<!-- MV -->
	         		<div class="modelTitle" align="left">
	         			<s:text name="mv"></s:text>
	         			<div class="toAll">
	                        <i class="allIcon"></i>
	                        <a class="allText" ><s:text name="All"></s:text></a>
	                    </div>
	         		</div>
	         		<!-- MV列表 -->
	         		<ul class="modelUl mvUl">
		         		<s:iterator value="top5MV" status="status">
		         			<s:if test="#status.last">
		         				<li special="lastModelLi">
		         			</s:if>
		         			<s:else>
		         				<li>
		         			</s:else>
		         				<div class="mvBox" align="left">
			         				<div class="imgContainer">
			         					<img alt="" class="mvHead"
			         						src="<s:text name='img_repository_path'/>/mv_head/<s:property value='videoId'/>.jpg">
	                                    <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
			         				</div>
		         					<div class="info1"><s:a class="defaultStyleLink"><s:property value="videoName"/></s:a></div>
		         					<div class="info2"><s:date format="yyyy-MM-dd" name="watchTimes"/></div>
		         				</div>
		         			</li>
		         		</s:iterator>
	         		</ul>
	         		<!-- 相似歌手 -->
	         		<div class="modelTitle" align="left">
	         			<s:text name="similarSinger"></s:text>
	         			<div class="toAll">
	                        <i class="allIcon"></i>
	                        <a class="allText" ><s:text name="All"></s:text></a>
	                    </div>
	         		</div>
	         		<!-- 相似歌手列表 -->
	         		<ul class="modelUl similarSingerUl">
		         		<s:iterator value="top5SimilarSinger" status="status" var="s">
		         			<s:if test="#status.last">
		         				<li special="lastModelLi">
		         			</s:if>
		         			<s:else>
		         				<li>
		         			</s:else>
		         				<div class="similarBox" align="center">
		         					<s:a action="singer">
		         						<s:param name="<s:property value='#s.singerId' />"></s:param>
			         					<img 
			         						alt=""  class="similarSingerHead" name="singerHead"   onerror="changeErrorImg(this)" 
			         						src="<s:text name='img_repository_path'/>/singer_head/<s:property  value='#s.singerId'/>.jpg"">
		         					</s:a>
		         					<div class="similarSingerName">
		         						<s:a class="defaultStyleLink"  action="singer">
		         							<s:param name="singerId" value="#s.singerId"></s:param>
		         							<s:property  value="singerName"/>
		         						</s:a>
		         					</div>
		         				</div>
		         			</li>
		         		</s:iterator>
	         		</ul>
	         		
         	</div>
         </div>
        <!-- 固定Div开始 -->
        <jsp:include page="/fixed_div.jsp"></jsp:include>
        <!-- 固定Div结束 -->
        <!-- 主体，结束 -->
        <!-- 模板尾，开始 -->
        <jsp:include page="/foot.jsp"></jsp:include>
        <!-- 模板尾，结束 -->
        <s:debug></s:debug>
    </body>
</html>