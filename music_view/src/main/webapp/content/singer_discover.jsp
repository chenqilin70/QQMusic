<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>歌手列表-<s:text name="QQMusic"></s:text>-<s:text name='listenWhatIWanna'/>！</title>
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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/singer_discover.css" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/singer_discover_content.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/singer_discover.js"></script>
        <!-- 本页js和css，结束 -->
    </head>
    <body>
    <!-- 用于快速回到开头 -->
    <a name="mao1"></a>
        <!-- 模板头，开始 -->
        <jsp:include page="/head.jsp"></jsp:include>
        <!-- 模板头，结束 -->
        <!-- 主体，开始 -->
         <div class="container" align="center">
	         <div class="singersBg" align="center">
	         	<s:if test="#session.listener==null">
	         		<div class="TitleInSingerBg" align="center">
	         			<s:text name="ThousandsOfSingerAllAroundTheCorner"/>
	         		</div>
	         		<div class="TipInSingerBg" align="center">
	         			<s:text name="LoginToViewYourFocusSinger"/>
	         		</div>
	         		<div class="loginBtnInBg">
	         			<s:text name="loginNow"></s:text>
	         			<div class="lightBorder"></div>
	         		</div>
	         	</s:if>
	         	
	         	<s:else>
		         	<s:if test="top15FocusSinger==null || top15FocusSinger.size==0 || top15FocusSinger.get(0)==null">
		         		<div class="yourFocusSingerWillDisplayHear">
		         			<s:text name='yourFocusSingerWillDisplayHear'/>
		         		</div>
		         		<div class="quicklyEnjoinSong">
		         			<s:text name="quicklyEnjoinSong"></s:text>
		         		</div>
		         	</s:if>
		         	<s:else>
			         	<div class="focusSingerHeads">
		         			<div class="myFocus" align="center">
			         			<s:text name="myFocusSinger"></s:text>
			         		</div>
			         		<s:if test="top15FocusSinger.size>5">
				         		<div class="moveUl toLeft">
				         			<div></div>
				         		</div>
			         		</s:if>
			         		<div class="myFocusBox">
			         			<ul class="focusSingerUl">
			         				<s:if test="top15FocusSinger.size==1 || top15FocusSinger.size==2">
				         				<li></li>
				         				<li></li>
			         				</s:if>
			         				<s:if test="top15FocusSinger.size==3 || top15FocusSinger.size==4">
			         					<li></li>
			         				</s:if>
			         				<s:iterator value="top15FocusSinger" status="status">
			         					<li>
				         					<img alt="" 
				         						src="<s:text name='img_repository_path'/>/singer_head/<s:property value='singerId'/>.jpg"/>
			         						<div class="focusSingerMask" align="center">
			         						</div>
			         						<s:a action="singer">
			         							<s:param name="singerId" value="singerId"></s:param>
			         							<div class="maskBorder"></div>
			         							<s:if test="#status.last">
				         							<s:a action="listener" anchor="CARE_SINGER">
					         							<span class="showAll"><s:text name="showAll"></s:text></span>
					         						</s:a>
				         						</s:if>
			         						</s:a>
			         					</li>
			         				</s:iterator>
			         			</ul>
			         		</div>
			         		<s:if test="top15FocusSinger.size>5">
				         		<div class="moveUl toRight">
				         			<div></div>
				         		</div>
			         		</s:if>
		         		</div>
		         	</s:else>
	         		
	         		<s:if test="top15FocusSinger.size>5">
	         			<ul class="tagsUl">
		         			<s:iterator begin="1" end="top15FocusSinger.size/5+(top15FocusSinger.size%5==0?0:1)" status="status">
		         				<li>
		         					<div class="tag" index="<s:property value='#status.index'/>"></div>
		         				</li>
		         			</s:iterator>
		         		</ul>
	         		</s:if>
	         	</s:else>
	         </div>
	         <div class="singerDiscoverContent">
	         <div class="conditionBox">
	         	<div class="categoryBox">
	         		<ul class="category">
	         			<s:a ><li selectedItem="false" class="AllItem" val="all"><s:text name="all"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="Chinese"><s:text name="Chinese"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="English"><s:text name="English"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="Japanese"><s:text name="Japanese"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="Korean"><s:text name="Korean"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="Cantonese"><s:text name="Cantonese"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="Taiwanese"><s:text name="Taiwanese"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="German"><s:text name="German"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="otherCategory"><s:text name="other"/></li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="AbsoluteMusic"><s:text name="AbsoluteMusic"/></li></s:a>
		         	</ul>
	         	</div>
	         	<div class="letterBox">
	         		<ul class="letter">
	         			<s:a ><li selectedItem="false" class="hotItem" val="hot"><s:text name="hot"/></li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="A">A</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="B">B</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="C">C</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="D">D</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="E">E</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="F">F</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="G">G</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="H">H</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="I">I</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="J">J</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="K">K</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="L">L</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="M">M</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="N">N</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="O">O</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="P">P</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="Q">Q</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="R">R</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="S">S</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="T">T</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="U">U</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="V">V</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="W">W</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="X">X</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="Y">Y</li></s:a>
	         			<s:a ><li selectedItem="false" class="optionItem" val="Z">Z</li></s:a>
		         		<s:a ><li selectedItem="false" class="optionItem" val="#">#</li></s:a>
		         	</ul>
	         	</div>
	         </div>
	         <div class="singersBox" align="center">
	         	<img class="loading" alt="" src='/music_view/img/loading.gif'>
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