<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>我的音乐</title>
        <!-- 导库，开始 -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
        <!-- 导库，结束 -->
        <!-- 模板js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/template.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/template.js"></script>
        <!-- 模板js和css，结束 -->
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/logoIcon.png" />
        <!-- 本页js和css，开始 -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/listener.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/listener.js"></script>
        <!-- 本页js和css，开始 -->
        <!-- 模块css开始 -->
        <link rel="stylesheet" type="text/css" id="modelCss" href="" >
        <!-- 模块css结束 -->
    </head>
    <body >
    <!-- 用于快速回到开头 -->
    <a name="mao1"></a>
        <!-- 模板头，开始 -->
        <jsp:include page="/head.jsp"></jsp:include>
        <!-- 模板头，结束 -->
        <!-- 主体，开始 -->
        <s:if test="#session.listener==null">
        	<div class="unloginBox" align="center">
        		<div class="listenerCaption"><s:text name="listenerCaption"/></div>
        		<div class="manageMyMusic"><s:text name="manageMyMusic"/></div>
        		<input class="immediatelyLogin" type="button" value="<s:text name='immediatelyLogin'/>"/>
        	</div>
        </s:if>
        <s:else>
        	<div class="listenerProfile" align="center">
        		<img class="profileImg" alt="" src="<s:text name="img_repository_path"/>/listener_head/<s:property value='#session.listener.listenerHead==""?"default.jpg":#session.listener.listenerHead'/>">
	        	<div>
	        		<span class="profileUsername"><s:property value="#session.listener.username"/></span>
	        		<i class="profileIcon1 profileIcon"></i>
	        		<i class="profileIcon2 profileIcon"></i>
	        	</div>
	        	<div align="center">
	        		<ul class="profileItem">
	        			<li class="first">
	        				<span class="num">
	        					<s:a class="profileItemLink">
	        						<s:property value="#session.listener.creaSinger.size"/>
	        					</s:a></span><br/>
	        				<s:text name="care"/>
	        			</li>
	        			<li>
	        				<span class="num">
	        					<s:a  class="profileItemLink">
	        						<s:property value="#session.listener.fans.size"/>
	        					</s:a>
	        				</span><br/>
	        				<s:text name="fans"/>
	        			</li>
	        		</ul>
	        	</div>
	        	<div class="bottomItem">
	        		<ul>
	        			<li class="myLikeLi"><s:a class="bottomItemLink"><s:text name="myLike"/></s:a></li>
	        			<li><s:a class="bottomItemLink"><s:text name="menuMadeByMe"/></s:a></li>
	        			<li><s:a class="bottomItemLink"><s:text name="care"/></s:a></li>
	        			<li><s:a class="bottomItemLink"><s:text name="fans"/></s:a></li>
	        			<li><s:a class="bottomItemLink"><s:text name="videoUploadedByMe"/></s:a></li>
	        		</ul>
	        		<i class="publicIcon"></i>
	        	</div>
        	</div>
        	<div class="detailInfoContainer" align="center">
        		<div class="detailContent" align="center">
        			<img class="loading" alt="" src='<%=request.getContextPath() %>/img/loading.gif'>
        		</div>
        	</div>
        </s:else>
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