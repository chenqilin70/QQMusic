<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="head" align="center">
	<div class="content">
		<div class="siteModel" align="center">
           <s:a action="index">
                    <img class="logoImg" alt="" src="<%=request.getContextPath() %>/img/logo.png">
           </s:a>
           <s:a action="index">
                    <div class="musicPlace model" selectedItem="false"><s:text name="musicPlace"/></div>
           </s:a>
            <s:a action="listener">
				<div class="myMusic model"  selectedItem="false"><s:text name="myMusic"/></div>
			</s:a>
			<div class="downloadClient model" selectedItem="false"><s:text name="downloadClient"/></div>
			<div class="vip model" selectedItem="false"><s:text name="vip"/></div>
			<div class="searchBox">
				<input class="searchInput" placeholder="<s:text name='searchPlaceholder'></s:text>" type="text" >
				<div id="searchBtn" class="searchBtn">
					<div id="searchIcon" class="searchIcon"></div>
				</div>
				<div class="searchList">
					<ul class="searchRankUl">
						<li><span class="searchSeq">1</span>&nbsp;&nbsp;张国荣<span class="searchCount">128.1万</span></li>
						<li><span class="searchSeq">2</span>&nbsp;&nbsp;动物世界 薛之谦<span class="searchCount">87.3万</span></li>
						<li><span class="searchSeq">3</span>&nbsp;&nbsp;男孩<span class="searchCount">49.1万</span></li>
						<li><span class="searchSeq">4</span>&nbsp;&nbsp;庄心妍<span class="searchCount">41.8万</span></li>
						<li><span class="searchSeq">5</span>&nbsp;&nbsp;五月天<span class="searchCount">35.8万</span></li>
					</ul>
					<div class="historySearch" align="left">
						<span><s:text name='historySearch'></s:text></span>
						<div class="trashCan"></div>
					</div>
				</div>
			</div>
			<input type="hidden" name="img_repository_path" value="<s:text name='img_repository_path'/>">
				<s:if test="#session.listener==null">
					<input type="hidden" id="isLogin" value="false"/>
					<div class="login" align="center">
						<s:text name='login'></s:text>
					</div>
				</s:if>
				<s:else>
				<input type="hidden" id="isLogin" value="true"/>
					<div class="lisenerHeadBox" align="center">
						<img src="<%=request.getContextPath() %>/img/listener_head/<s:property value='#session.listener.listenerHead==""?"default.jpg":#session.listener.listenerHead'/>?time=<%=new Date().getTime() %>" alt='' class='userHeadInHead'>
						<div class="listenerInfoBox" align="left">
							<img class="lisenerHead" alt="" src="<%=request.getContextPath() %>/img/listener_head/<s:property value='#session.listener.listenerHead'/>?time=<%=new Date().getTime() %>">
							<div class="lisenerNameInfo"><s:a class="defaultStyleLink"><s:property value='#session.listener.username'/></s:a></div>
							<img class="IconInfo1" src="<%=request.getContextPath() %>/img/svip_g.png" alt="" >
							<img  class="IconInfo2" src="<%=request.getContextPath() %>/img/sui_g.png" alt="" >
							<s:a  href="#" class="defaultStyleLink logoutLink">[<s:text name="logoutAccount"></s:text>]</s:a>
							<div class="listenerItemsDiv" align="center">
								<ul class="listenerItem">
									<li>
										<s:a class="defaultStyleLink"><s:property value="#session.listener.myMusicMenu==null?0:#session.listener.myMusicMenu.size()"/></s:a>
										<br/>
										<span><s:text name="musicMenu"/></span>
									</li>
									<li class="centerListenerItem">
										<s:a class="defaultStyleLink"><s:property value="#session.listener.creaSinger==null?0:#session.listener.creaSinger.size()"/></s:a>
										<br/>
										<span><s:text name="care"/></span>
									</li>
									<li>
										<s:a class="defaultStyleLink"><s:property value="#session.listener.fans==null?0:#session.listener.fans.size()"/></s:a>
										<br/>
										<span><s:text name="fans"/></span>
									</li>
								</ul>
							</div>
							
						</div>
					</div>
				</s:else>
			<button id="greenJewel" class="greenJewel"><s:text name='openGreenJewel'></s:text></button>
			<button id="payPackage" class="payPackage"><s:text name='payPackage'></s:text></button>
		</div>
		<s:if test="siteNavEnable">
			<div class="siteNav">
				<ul class="navItems">
					<s:a action="index"  class="noneJsDefaultLink">
						<li class="navItem indexNavItem"  selectedItem="false"><s:text name="indexPage"/></li>
					</s:a>
					<s:a action="singer_discover"  class="noneJsDefaultLink">
						<li class="navItem singerNavItem"  selectedItem="false"><s:text name="singer"/></li>
					</s:a>
					<s:a action="album_discover"  class="noneJsDefaultLink">
						<li class="navItem albumNavItem"  selectedItem="false"><s:text name="album"/></li>
					</s:a>
					<li class="navItem"  selectedItem="false"><s:text name="rank"/></li>
					<li class="navItem"  selectedItem="false"><s:text name="classifyMenu"/></li>
					<li class="navItem"  selectedItem="false"><s:text name="mv"/></li>
				</ul>
			</div>
		</s:if>
	</div>
	<div class="totalMask" align="center">
		<div class="loginWin">
			<div class="loginTitl" align="left">
				<div align="center"><s:text name="login"/></div>
				<i class="closeIcon"></i>
			</div>
			<div class="loginBody">
				<div class="accountAndPasswordLogin"><s:text name="accountAndPasswordLogin"/></div>
				<div class="new_user_sign_in"><s:text name="new_user_sign_in"/></div>
				<input type="hidden" value="<s:text name='new_user_sign_in'/>" id="new_user_sign_in">
				<input type="hidden" value="<s:text name='directLogin'/>" id="directLogin">
				<input type="hidden" value="<s:text name='signin'/>" id="signin">
				<input type="hidden" value="<s:text name='login'/>" id="login">
				<form>
					<input type="hidden" name="forWhat" value="login">
					<div class="loginInput" align="left">
						<s:textfield 
							name="username" 
							placeholder='%{getText("pleaceInputAccount")}' 
							value="%{usernameInCookie}"></s:textfield>
						<i></i>
						<div class="inputMessage" ></div>
					</div>
					<div class="loginInput" align="left">
						<s:password 
							name="password" 
							placeholder='%{getText("password")}'
							value="%{passwordInCookie}"></s:password>
						<div class="inputMessage" ></div>
					</div>
					<div class="loginInput comfirmPassword" align="left">
						<s:password name="comfirmPassword" placeholder='%{getText("comfirmPassword")}'></s:password>
						<div class="inputMessage" ></div>
					</div>
					<div class="validateCode" align="left">
						<s:textfield name="validateCode" placeholder='%{getText("validateCode")}'></s:textfield>
						<img alt="" src='<s:url action='ajax!validateCode'><s:param name="time">time</s:param></s:url>' class="validateImg">
						<div class="inputMessage" ></div>
					</div>
					<div class="loginOrSiginSubmit" align="left">
						<input type="submit" value="<s:text name='login'/>">
					</div>
				</form>
			</div>
			<div class="loginTail" align="left">
				<s:if test="usernameInCookie==null || usernameInCookie==''">
					<a isSelected="false" class="loginCheckbox"></a>
				</s:if>
				<s:else>
					<a isSelected="true" class="loginCheckbox"></a>
				</s:else>
				<div class="nextAutoLogin"><s:text name="nextAutoLogin"></s:text></div>
				<ul>
					<li><s:a><s:text name="feedback"/></s:a></li>
					<li class="centerLi"><s:a class="signinOrLogin"><s:text name="new_user_sign_in"/></s:a></li>
					<li><s:a><s:text name="forgetPassword"/></s:a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
