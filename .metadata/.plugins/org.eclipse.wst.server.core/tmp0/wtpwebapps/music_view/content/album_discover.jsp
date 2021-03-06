<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="QQMusic"></s:text> - 专辑库</title>
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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/album_discover.css" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/album_discover_content.css" >
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/album_discover.js"></script>
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
         	<div class="albumDisvcoverContent">
         	<input type='hidden' value="<s:text name="all"></s:text>" id="allText">
         		<div class="languageBox">
         			<ul class="defaultUl languageUl" optionClass="language">
	         			<li class="optionClass"><s:text name="language"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="all"><s:text name="all"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="National"><s:text name="National"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="Cantonese"><s:text name="Cantonese"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="English"><s:text name="English"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="Korea"><s:text name="Korea"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="Japan"><s:text name="Japan"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="French"><s:text name="French"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="Spanish"><s:text name="Spanish"></s:text></li>
	         		</ul>
         		</div>
         		<div class="genresBox">
         			<ul class="defaultUl genresUl" optionClass="genres">
	         			<li class="optionClass"><s:text name="genres"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="all"><s:text name="all"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="fashion"><s:text name="fashion"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="classical"><s:text name="classical"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="jazz"><s:text name="jazz"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="rock"><s:text name="rock"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="electron"><s:text name="electron"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="latin"><s:text name="latin"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="light_music"><s:text name="light_music"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val=""><s:text name="world_music"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="world_music"><s:text name="Hip_hop"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="original_sound"><s:text name="original_sound"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="country"><s:text name="country"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="dance"><s:text name="dance"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="R&B"><s:text name="R&B"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="ballad"><s:text name="ballad"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="metal"><s:text name="metal"></s:text></li>
	         		</ul>
         		</div>
         		<div class="priceBox">
         			<ul class="defaultUl priceUl" optionClass="price">
	         			<li class="optionClass"><s:text name="price"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="all"><s:text name="all"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="true"><s:text name="free"></s:text></li>
	         			<li class="optionItem" selectedItem="false" val="false"><s:text name="pay"></s:text></li>
	         		</ul>
         		</div>
         		<div class="filtrateBox">
         			<ul class="foldingUl filtrateUl">
	         			<li class="foldingItemClass" ><s:text name="filtrate"></s:text></li>
	         			<li class="foldingItem" selectedItem="false" optionClass="category"><span><s:text name="category"></s:text></span><i class="downIcon"></i></li>
	         			<li class="foldingItem" selectedItem="false" optionClass="years" ><span><s:text name="years"></s:text></span><i class="downIcon"></i></li>
	         			<li class="foldingItem" selectedItem="false" optionClass="albumCompany" ><span><s:text name="albumCompany"></s:text></span><i class="downIcon"></i></li>
	         		</ul>
	         		<div class="unfoldBox" optionClass="category">
	         			<ul class="defaultUl categoryUl" optionClass="category">
		         			<li class="optionItem" selectedItem="false" val="all"><s:text name="all"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="album"><s:text name="album"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="EP"><s:text name="EP"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="Single"><s:text name="Single"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="concert"><s:text name="concert"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="cartoon"><s:text name="cartoon"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="game"><s:text name="game"></s:text></li>
		         		</ul>
	         		</div>
	         		<div class="unfoldBox" optionClass="years">
	         			<ul class="defaultUl yearsUl" optionClass="years">
		         			<li class="optionItem" selectedItem="false" val="all"><s:text name="all"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="2017.01.01-2017.12.31">2017</li>
		         			<li class="optionItem" selectedItem="false" val="2016.01.01-2016.12.31">2016</li>
		         			<li class="optionItem" selectedItem="false" val="2015.01.01-2015.12.31">2015</li>
		         			<li class="optionItem" selectedItem="false" val="2014.01.01-2014.12.31">2014</li>
		         			<li class="optionItem" selectedItem="false" val="2013.01.01-2013.12.31">2013</li>
		         			<li class="optionItem" selectedItem="false" val="2012.01.01-2012.12.31">2012</li>
		         			<li class="optionItem" selectedItem="false" val="2010.01.01-2019.12.31"><s:text name="yearsOf10"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="2000.01.01-2009.12.31"><s:text name="yearsOf00"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="1990.01.01-1999.12.31"><s:text name="yearsOf90"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="1980.01.01-1989.12.31"><s:text name="yearsOf80"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="1970.01.01-1979.12.31"><s:text name="yearsOf70"></s:text></li>
		         			<li class="optionItem" selectedItem="false" val="1960.01.01-1999.12.31"><s:text name="yearsOf60"></s:text></li>
		         		</ul>
	         		</div>
	         		<div class="unfoldBox" optionClass="albumCompany">
	         			<ul class="defaultUl albumCompanyUl" optionClass="albumCompany">
		         			<li class="optionItem" selectedItem="false" val="all"><s:text name="all"></s:text></li>
		         			<s:iterator value="top23Company" >
		         				<li class="optionItem" selectedItem="false" val="<s:property value='companyId'/>"><s:property value="comName"/></li>
		         			</s:iterator>
		         		</ul>
	         		</div>
	         		
         		</div>
         		<div class="haveSelectedBox" align="left">
         			<div class="allALbumText"><s:text name="all"/><s:text name="album"/></div>
         			<div category="language" class="haveSelectedItem"><span></span><i></i></div>
         			<div category="genres" class="haveSelectedItem"><span></span><i></i></div>
         			<div category="price" class="haveSelectedItem"><span></span><i></i></div>
         			<div category="category" class="haveSelectedItem"><span></span><i></i></div>
         			<div category="years" class="haveSelectedItem"><span></span><i></i></div>
         			<div category="albumCompany" class="haveSelectedItem"><span></span><i></i></div>
         			<div class="orderBy hottestBtn" align="center"><s:text name="hottest"/></div>
         			<div class="orderBy newestBtn" align="center"><s:text name="newest"/></div>
         		</div>
         		<div class="detailContent" align="center">
        			
        		</div>
         	</div>
         </div>
        <!-- 固定Div开始 -->
        <jsp:include page="/fixed_div.jsp"></jsp:include>
        <!-- 固定Div结束 -->
        <!-- 主体，结束 -->
        <!-- 模板尾，开始 -->
        <jsp:include page="/foot.jsp"></jsp:include>
        <!-- 模板尾，结束 -->
        
    </body>
</html>