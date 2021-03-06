<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.huwl.oracle.qqmusic.music_model.Listener" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="album.albumName"/>-<s:text name="QQMusic"></s:text> - <s:text name='listenWhatIWanna'/>！</title>
        <!-- 导库，开始 -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
        <!-- 导库，结束 -->
        <!-- 模板js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/template.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/template.js"></script>
        <!-- 模板js和css，结束 -->
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/logoIcon.png" />
        <!-- 本页js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/album.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/album.js"></script>
        <!-- 本页js和css，开始 -->
    </head>
<body>
    <!-- 模板头，开始 -->
        <jsp:include page="/head.jsp"></jsp:include>
      <!-- 模板头，结束 -->
    <!-- 主体，开始 -->
    <div class="container" align="center">
        <div class="albumContent">
            <div class="albumBaseInfo" align="left">
                <div class="albumImgBox">
                    <img class="albumHead" src="<s:text name="img_repository_path"/>/album_head/T002R300x300M000<s:property value='album.albumId'/>.jpg"/>
                    <img class="albumHead" src="<%=request.getContextPath() %>/img/album_cover.png"/>
               </div>
               <!--专辑基本信息-->
                <div class="albumInfoBox">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                            <tr>
                                <td  colspan="2" class="albumName">
	                                <div>
	                                	<s:if test="album.albumName!=null">
	                                        <s:property value='album.albumName'/>
	                                    </s:if>
	                                    <s:else>
	                                        <s:text name="notAvailable"/>
	                                    </s:else>
	                                </div>
                                </td>
                            </tr>
                            <tr>
                                <td  colspan="2" class="albumSinger">
                                    <i class="singerIcon"></i>
                                    <div>
                                        <s:if test="album.singer.singerName!=null">
                                            <s:a class="defaultStyleLink"  action="singer">
                                                <s:param name="singerId" value="album.singer.singerId"/>
                                                <s:property value='album.singer.singerName'/>
                                            </s:a>
                                        </s:if>
                                        <s:else>
                                                <s:text name="notAvailable"/>
                                        </s:else>
                                    </div>
                                    
                                </td>
                            </tr>
                            <tr>
                                <td class="setWidth1 setHeight mainBody">
                                    <s:text name="genres"/>
                                    <s:if test="album.genres!=null">
                                        <s:property value='album.genres'/>
                                    </s:if>
                                    <s:else>
                                        <s:text name="notAvailable"/>
                                    </s:else>
                                </td>
                                <td class="setWidth2 mainBody">
                                    <s:text name="language"/>
                                    <s:if test="album.language!=null">
                                        <s:property value='album.language'/>
                                    </s:if>
                                    <s:else>
                                        <s:text name="notAvailable"/>
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td class="setHeight mainBody">
                                    <s:text name="publishTime"/>
                                    <s:if test="album.publishTime!=null">
                                        <s:date name="album.publishTime" format="yyyy年MM月dd日"/>
                                                                             
                                    </s:if>
                                    <s:else>
                                        <s:text name="notAvailable"/>
                                    </s:else>
                                </td>
                                <td class="mainBody">
                                    <s:text name="company"/>
                                    <s:if test="album.company!=null">
                                        <s:a class="defaultStyleLink"  action="company">
                                            <s:param name="companyId" value="album.company.companyId"/>
                                            <s:property value='album.company.comName'/>
                                        </s:a>
                                    </s:if>
                                    <s:else>
                                        <s:text name="notAvailable"/>
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td   colspan="2" class="setHeight mainBody">
                                    <s:text name="albumType"/>
                                    <s:if test="album.albumType!=null">
                                        <s:property value='album.albumType'/>
                                    </s:if>
                                    <s:else>
                                        <s:text name="notAvailable"/>
                                    </s:else>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                                    <!--三个按钮-->
                                    <button class="playAll albumBtn" align="center" >
                                                <i></i>
                                                 <s:text name='playAll'/>
                                    </button>
                                    <button class="collect albumBtn" albumId="<s:property value='album.albumId'/>">
                                        <s:if test="isCollected">
                                        	<i class="collected"></i>
                                        </s:if>
                                        <s:else>
                                        	<i class="uncollected"></i>
                                        </s:else>
                                        
                                        <span>
                                            <s:text name='collect'/>
                                        </span>
                                    </button>
                                    <button class="more albumBtn">
                                        <i></i>
                                        <span>
                                            <s:text name='more'/>
                                        </span>
                                    </button>
                </div>
            </div>
                 <!--歌曲表格-->
            <div class="albumSpecificInfo" align="left">
                    <table class="songsTable" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th class="songIndex"></th>
                                <th class="songName"><s:text name="songName"/></th>
                                <th class="singer"><s:text name="singer"/></th>
                                <th class="songTime"><s:text name="songTime"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="album.musics" status="status" var="music">
                                <tr class="songTr" musicid="<s:property value='musicId'/>">
                                    <td class="songSetHeight songTd"><s:property value="#status.index+1" /></td>
                                    
                                        <td class="songNameTd songTd">
                                            <s:a action="music" class="defaultStyleLink">
                                                <s:param name="musicId" value="musicId"></s:param>
                                               <s:property value="musicName" />
                                            </s:a>
                                            <div class="songOption shareSong"></div>
                                            <div class="songOption downloadSong"></div>
                                            <div class="songOption addSong"></div>
                                            <div class="songOption playSong" musicId='<s:property value='musicId'/>'></div>
                                        </td>
                                    
                                    
                                        <td class="singerTd songTd">
                                            <s:a action="singer"  class="defaultStyleLink">
                                                <s:param name="singerId" value="album.singer.singerId"></s:param>
                                                 <s:property value="album.singer.singerName" />
                                            </s:a>
                                        </td>
                                    
                                    <td class="songTimeTd songTd"><s:text name="notAvailable"/> </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                <!--右侧其他信息-->
                    <div class="otherDiv">
                        <div class="introTitle"><s:text name="birefIntro"/></div>
                        <input type="hidden" class="descValue" value="<s:property  value="album.desc"/>"/>
                        <p class="introP"></p>
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
                        <span class="moreBaseInfo">[<s:text name="more"/>]</span>
                        <div class="otherAlbumOfTheSinger"><s:text name="otherAlbumOfTheSinger"/></div>
                        <table class="otherAlbumTable"  border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                                <tr>
                                    <s:iterator value="otherAlbum" status="status">
                                        <s:if test="#status.index==3">
                                        </tr><tr>
                                        </s:if>
                                        <s:url action="album" var="albumUrl">
                                            <s:param name="albumId"><s:property value="otherAlbum[#status.index][0]"/></s:param>
                                        </s:url>
                                        <td>
                                            <div class="otherAlbumBox">
                                                <s:a href="%{albumUrl}" >
                                                <img 
                                                        class="otherAlbumHead"
                                                        src="<s:text name='img_repository_path' />/album_head/T002R300x300M000<s:property value="otherAlbum[#status.index][0]"/>.jpg"/>
                                                 </s:a>
                                                <div class="otherAlbumName"><s:property value="otherAlbum[#status.index][1]"/></div>
                                                    <div  class="otherAlbumTime">
                                                        <s:date format="yyyy-MM-dd" name="otherAlbum[#status.index][2]"/>
                                                    </div>
                                            </div>
                                        </td>
                                    </s:iterator>
                                </tr>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>
    </div>
    <!-- 主体，结束 -->
    <!-- 模板尾，开始 -->
    <jsp:include page="/foot.jsp" flush=""></jsp:include>
        <!-- 模板尾，结束 -->
</body>
</html>