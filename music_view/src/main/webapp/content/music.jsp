<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.huwl.oracle.qqmusic.music_model.Listener" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:property value="album.albumName"/>-QQ音乐 - 听我想听的歌！</title>
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
        
    </div>
    <!-- 主体，结束 -->
    <!-- 模板尾，开始 -->
    <jsp:include page="/foot.jsp" flush=""></jsp:include>
        <!-- 模板尾，结束 -->
        <s:debug></s:debug>
</body>
</html>