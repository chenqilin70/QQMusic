<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QQ音乐 - 中国最新最全免费正版高品质音乐平台！</title>
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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/index.js"></script>
        <!-- 本页js和css，开始 -->
    </head>
    <body>
    <!-- 用于快速回到开头 -->
    <a name="mao1"></a>
        <!-- 模板头，开始 -->
        <jsp:include page="/head.jsp"></jsp:include>
            <!-- 模板头，结束 -->
            <!-- 主体，开始 -->
            <div class="container">
                <div class="newMusic" align="center">
                    <div class="left">
                        <div class="toLeft slideBtn" align="left">
                            <i></i>
                        </div>
                    </div>
                    <div class="right">
                        <div class="toRight slideBtn" align="left">
                            <i></i>
                        </div>
                    </div>
                    <div class="newMusicContainer">
                        <div class="newMusicTitle modelTitle" >
                            <div class="leftLine newMusicLeftLine"></div>
                            <div class="rightLine newMusicRightLine"></div>
                            <div><s:text name="newMusicFirstPublish"></s:text></div>
                        </div>
                        <!-- 选择地区语言 -->
                        <div class="district" align="center">
                            <ul>
                                <li selectedItem="true" language="inland">
                                	<s:text name="inland"/>
                                </li>
                                <li selectedItem="false" language="HongKong_And_TaiWan">
                                	<a><s:text name="HongKong_And_TaiWan"/></a>
                                </li>
                                <li selectedItem="false" language="Europe_And_America" >
                                	<a><s:text name="Europe_And_America"></s:text></a>
                                </li>
                                <li selectedItem="false" language="Korea">
                                	<a><s:text name="Korea"></s:text></a>
                                </li>
                                <li selectedItem="false" language="Japan">
                                	<a><s:text name="Japan"></s:text></a>
                                </li>
                            </ul>
                            <div class="toAll">
                                <i class="allDistrictIcon"></i>
                                <a class="allDistrict" ><s:text name="All"></s:text></a>
                            </div>
                        </div>
                        <!--推荐专辑的表格-->
                        <div class="showNewMusicBox">
                            <table class="showNewMusicTable" border="0" cellspacing="0" cellpadding="0">
                                <tbody>
                                    <tr>
                                    	<s:iterator value="chineseAlbums" begin="12" end="15" var="album" status="status">
                                            <s:if test="#status.odd">
                                                <td albumId="<s:property value='albumId'/>" class="contralWidth contralHeight" >
                                                    <div>
                                                        <img src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg" 
                                                        	class="mostLeft albumImgInNewMusic">
                                                        <div class="imgMask" align="center"> </div>
                                                        <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
                                                    </div>
                                                </td>
                                                </s:if>
                                                <s:else>
                                                <td  albumId="<s:property value='albumId'/>" class="contralWidth" >
                                                    <div >
                                                        <img src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg" 
                                                        	class="mostLeft albumImgInNewMusic">
                                                        <div class="imgMask" align="center"></div>
                                                        <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
                                                    </div>
                                                </td>
                                                </s:else>
                                                
                                    </s:iterator>
                                        <!--开始正式的for循环-->
                                     <s:iterator value="chineseAlbums" begin="0" end="15" var="album" status="status">
                                            <s:if test="#status.odd">
                                                <td albumId="<s:property value='albumId'/>" class="contralWidth contralHeight" >
                                                    <div>
                                                        <img   src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg" 
                                                        	class="mostCenter albumImgInNewMusic">
                                                        <div class="imgMask" align="center"> </div>
                                                        <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
                                                    </div>
                                                </td>
                                                </s:if>
                                                <s:else>
                                                <td  albumId="<s:property value='albumId'/>" class="contralWidth" >
                                                    <div >
                                                        <img   src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg" 
                                                        	class="mostCenter albumImgInNewMusic">
                                                        <div class="imgMask" align="center"></div>
                                                        <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
                                                    </div>
                                                </td>
                                                </s:else>
                                    </s:iterator>
                                     <!--forEach循环结束后还需要加一个和前四列一样的td，这样保证滑动是连续的-->
                                     <s:iterator value="chineseAlbums" begin="0" end="3" var="album" status="status">
                                            <s:if test="#status.odd">
                                                <td albumId="<s:property value='albumId'/>" class="contralWidth contralHeight" >
                                                    <div>
                                                        <img   src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg" 
                                                        	class="mostRight albumImgInNewMusic">
                                                        <div class="imgMask" align="center"> </div>
                                                        <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
                                                    </div>
                                                </td>
                                                </s:if>
                                                <s:else>
                                                <td  albumId="<s:property value='albumId'/>" class="contralWidth" >
                                                    <div >
                                                        <img   src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg"
                                                        	 class="mostRight albumImgInNewMusic">
                                                        <div class="imgMask" align="center"></div>
                                                        <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
                                                    </div>
                                                </td>
                                                </s:else>
                                    </s:iterator>
                                </tr>
                                <tr>
                                    <!--***************************************************************************************************-->
                                    <!--以下forEach循环开始前还需要加一个和后四列一样的tds，这样保证滑动是连续的-->
                                    <s:iterator value="chineseAlbums"  begin="12" end="15" var="album" status="status">
                                        <s:url action="album" var="albumUrl">
                                            <s:param name="albumId"><s:property value='albumId'/></s:param>
                                        </s:url>
                                        <s:if test="#status.odd">
                                                <td  albumId="<s:property value='albumId'/>" class="singularMask musicMask" maskPos='leftMask' align="center">
                                                    
                                                    <s:a href="%{albumUrl}"><span class="musicName"><s:property value='albumName'/></span></s:a><i class="moreIcon"></i>
                                                    <br>
                                                    <a><span class="singerName"><s:property value='singer.singerName'/></span></a>
                                                </td>
                                         </s:if>
                                         <s:else>
                                         <td  albumId="<s:property value='albumId'/>" class="evenMask  musicMask"  maskPos='leftMask'  align="center">
                                             <s:a href="%{albumUrl}"><span class="musicName"><s:property value='albumName'/></span></s:a><i class="moreIcon"></i>
                                                <br>
                                                <a><span class="singerName"><s:property value='singer.singerName'/></span></a>
                                            </td>
                                         </s:else>
                                    </s:iterator>
                                      <!--正式开始正常内容的forEach-->
                                      <s:iterator value="chineseAlbums"   var="album" status="status">
                                     <s:url action="album" var="albumUrl">
                                            <s:param name="albumId"><s:property value='albumId'/></s:param>
                                        </s:url>
                                            <s:if test="#status.odd">
                                                <td  albumId="<s:property value='albumId'/>" class="singularMask musicMask" maskPos='centerMask' align="center">
                                                    <s:a href="%{albumUrl}"><span class="musicName"><s:property value='albumName'/></span></s:a><i class="moreIcon"></i>
                                                    <br>
                                                    <a><span class="singerName"><s:property value='singer.singerName'/></span></a>
                                                </td>
                                            </s:if>
                                            <s:else>
                                                <td  albumId="<s:property value='albumId'/>" class="evenMask  musicMask" maskPos='centerMask' align="center">
                                                    <s:a href="%{albumUrl}"><span class="musicName"><s:property value='albumName'/></span></s:a><i class="moreIcon"></i>
                                                    <br>
                                                    <a><span class="singerName"><s:property value='singer.singerName'/></span></a>
                                                </td>
                                            </s:else>
                                         </s:iterator>
                                     <!--以上forEach循环结束后还需要加一个和前四列一样的tds，这样保证滑动是连续的-->
                                     <s:iterator value="chineseAlbums"  begin="0" end="3" var="album" status="status">
                                         <s:url action="album" var="albumUrl">
                                            <s:param name="albumId"><s:property value='albumId'/></s:param>
                                        </s:url>
                                         <s:if test="#status.odd">
                                             <td  albumId="<s:property value='albumId'/>" class="singularMask musicMask" maskPos='rightMask' align="center">
                                                 <s:a href="%{albumUrl}"><span class="musicName"><s:property value='albumName'/></span></s:a><i class="moreIcon"></i>
                                                 <br>
                                                 <a><span class="singerName"><s:property value='singer.singerName'/></span></a>
                                             </td>
                                         </s:if>
                                         <s:else>
                                             <td  albumId="<s:property value='albumId'/>" class="evenMask  musicMask"  maskPos='rightMask'  align="center">
                                                 <s:a href="%{albumUrl}"><span class="musicName"><s:property value='albumName'/></span></s:a><i class="moreIcon"></i>
                                                 <br>
                                                 <a><span class="singerName"><s:property value='singer.singerName'/></span></a>
                                             </td>
                                         </s:else>
                                    </s:iterator>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                        <ul class="newMusicTab">
                            <li selectedItem='true'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                            <li  selectedItem='false'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                        </ul>
                 </div>
                    
                </div>
                <div class="hotRecommended"  isRetain="true" align="center">
                    <div class="leftBtn hotRecomLeftBtn"   isRetain="true"  align="left">
                        <i   isRetain="true" ></i>
                    </div>
                    <div class="rightBtn hotRecomRightBtn"   isRetain="true"  align="left">
                        <i   isRetain="true" ></i>
                    </div>
                    <div class="contentContainer">
                        <div class="hotRecomTitle  modelTitle" >
                            <div class="leftLine  hotRecomLeftLine"></div>
                            <div class="rightLine  hotRecomRightLine"></div>
                            <div>精彩推荐</div>
                        </div>
                        <div class="carouselPic" align="left">
                            <s:iterator begin="9" end="10" step="1" var="pic">
                                     <img class="hotRecomSinglePic" src="<%=request.getContextPath() %>/img/hotRecommendedMVHead/<s:property/>.jpg" alt="" />
                            </s:iterator>
                            <s:iterator begin="1" end="10" step="1" var="pic">
                                     <img class="hotRecomSinglePic" src="<%=request.getContextPath() %>/img/hotRecommendedMVHead/<s:property/>.jpg" alt="" />
                            </s:iterator>
                             <s:iterator begin="1" end="2" step="1" var="pic">
                                     <img class="hotRecomSinglePic" src="<%=request.getContextPath() %>/img/hotRecommendedMVHead/<s:property/>.jpg" alt="" />
                            </s:iterator>
                        </div>
                        <ul class="hotRecomTab">
                            <li selectedItem='true'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                            <li  selectedItem='false'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                            <li  selectedItem='false'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                            <li  selectedItem='false'><div></div></li>
                            <li selectedItem='false'><div></div></li>
                        </ul>
                    </div>
                </div>
            <div class="rankingList"></div>
            <div class="hotMusicMenu"   isRetain="true" >
                <div class="leftBtn hotMusicLeftBtn"   isRetain="true"  align="left">
                        <i   isRetain="true" ></i>
                    </div>
                    <div   isRetain="true"  class="rightBtn hotMusicRightBtn" align="left">
                        <i   isRetain="true" ></i>
                    </div>
            </div>
            <div class="MVFirstRun"></div>
        </div>
        <!-- 主体，结束 -->
        <!-- 固定Div开始 -->
        <jsp:include page="/fixed_div.jsp"></jsp:include>
        <!-- 固定Div结束 -->
        <!-- 模板尾，开始 -->
        <jsp:include page="/foot.jsp"></jsp:include>
        <!-- 模板尾，结束 -->
        <s:debug></s:debug>
    </body>
</html>