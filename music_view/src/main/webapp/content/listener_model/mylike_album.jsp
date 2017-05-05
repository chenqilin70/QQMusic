<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<ul class="myLikeUl">
	<li>
		<s:a class="defaultStyleLink" model="MYLIKE_MUSIC">
			<s:text name="music" />&nbsp;<s:property value="likeMusicCount" />
		</s:a>
	</li>
	<li>
		<s:a class="defaultStyleLink" model="MYLIKE_MENU">
			<s:text name="musicMenu" />&nbsp;<s:property value="likeMusicMenuCount" />
		</s:a>
	</li>
	
	<li class="presentItem">
		<s:text name="album" />&nbsp;<s:property value="likeAlbumCount" />
	</li>
	<li>
		<s:a class="defaultStyleLink" model="MYLIKE_MV">
			<s:text name="mv" />&nbsp;<s:property value="likeMvCount" />
		</s:a>
	</li>
</ul>
<s:if test="likeAlbumCount==0">
	<div class="nullImg">
		<img src="<%=request.getContextPath() %>/img/symbol_none.png" alt=""/>
	</div>
	<div class="nothingBeFound">
  		<s:text name="nothingBeFound1"/><s:a href="#" class="gotoMusicPlace"><s:text name="musicPlace"/></s:a><s:text name="nothingBeFound2"/>
    </div>
</s:if>
<s:else>
	<table class="likeAlbumTable" border="0" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<s:iterator value="likeAlbum" status="status">
					<s:if test="#status.index%5==0">
						</tr><tr>
						<td>
					</s:if>
					<s:else>
						<td class="notFirstAlbumTd">
					</s:else>
							<div class="albumBox">
								<div class="imgContainer">
		         					<img alt="" class="albumHead"
		         						src="<s:text name='img_repository_path'/>/album_head/T002R300x300M000<s:property value='albumId'/>.jpg">
                                    <img class="cover_play" src="<%=request.getContextPath()%>/img/cover_play.png" />
		         				</div>
	         					<div class="info1"><s:a class="defaultStyleLink"><s:property value="albumName"/></s:a></div>
	         					<div class="info2"><s:a class="likeAlbumSingerLink"><s:property value="singer.singerName"/></s:a></div>
								<div class="moreAlbumOption"></div>
							</div>
						</td>
				</s:iterator>
			</tr>
		</tbody>
	</table>
	
	<!-- 数量大于30才会有翻页 -->
	<s:if test="likeAlbumCount > 30">
		<div class="pageBtnBox">
			<!-- 为首页时没有“上一页” -->
			<s:if test="pageBean.prevPage >= 1">
				<div class="pageBtn" align="center" page="<s:property value='pageBean.prevPage'/>"><span>&#60;</span></div>
			</s:if>
			
			
			
			<!-- 总页数小于等于8,则全部展示 -->
			<s:if test="pageBean.pageCount <= 8">
				<s:iterator begin="1" end="pageBean.pageCount" var="p">
					<s:if test="pageBean.pageNo==#p">
						<div class="pageBtn  nowPage" align="center"  page="<s:property/>"><span><s:property/></span></div>
					</s:if>
					<s:else>
						<div class="pageBtn" align="center"  page="<s:property/>"><span><s:property/></span></div>
					</s:else>
				</s:iterator>
			</s:if>
			<!-- 总页数大于8,则部分用省略号代替 -->
			<s:if test="pageBean.pageCount >= 8">
				<!-- 如果当前页小于3，则从1直接罗列到当前页 -->
				<s:if test="pageBean.pageNo-1 <= 3">
					<s:iterator begin="1" end="pageBean.pageNo"   var="p">
						<s:if test="pageBean.pageNo==#p">
							<div class="pageBtn  nowPage" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:if>
						<s:else>
							<div class="pageBtn" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:else>
					</s:iterator>
				</s:if>
				<!-- 如果当前页大于3，则罗列1和…，再罗列当前页前几页到当前页 -->
				<s:else>
					<div class="pageBtn" align="center"   page="1"><span>1</span></div>
					<div class="pageBtn apostrophe" align="center"><span>…</span></div>
					<s:iterator begin="pageBean.pageNo==pageBean.pageCount?pageBean.pageNo-3:pageBean.pageNo-2" end="pageBean.pageNo"   var="p">
						<s:if test="pageBean.pageNo==#p">
							<div class="pageBtn  nowPage" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:if>
						<s:else>
							<div class="pageBtn" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:else>
					</s:iterator>
				</s:else>
				<!-- 如果当前页和尾页相差小于3，则罗列当前页到尾页 -->
				<s:if test="pageBean.pageCount-pageBean.pageNo <= 3">
					<s:iterator begin="pageBean.pageNo+1" end="pageBean.pageCount"   var="p">
						<s:if test="pageBean.pageNo==#p">
							<div class="pageBtn  nowPage" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:if>
						<s:else>
							<div class="pageBtn" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:else>
					</s:iterator>
				</s:if>
				<!-- 如果当前页和尾页相差大于3，则罗列当前页的后两位 -->
				<s:else>
					<s:iterator begin="pageBean.pageNo+1" end="pageBean.pageNo==1?pageBean.pageNo+3:pageBean.pageNo+2"   var="p">
						<s:if test="pageBean.pageNo==#p">
							<div class="pageBtn  nowPage" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:if>
						<s:else>
							<div class="pageBtn" align="center"  page="<s:property/>"><span><s:property/></span></div>
						</s:else>
					</s:iterator>
					<div class="pageBtn apostrophe" align="center"><span>…</span></div>
					<div class="pageBtn" align="center"   page="<s:property value="pageBean.pageCount"/>"><span><s:property value="pageBean.pageCount"/></span></div>
				</s:else>
			</s:if>
			
			
			<!-- 为尾页时没有“下一页” -->
			<s:if test="pageBean.nextPage <= pageBean.pageCount">
				<div class="pageBtn" align="center" page="<s:property value='pageBean.nextPage'/>"><span>&#62;</span></div>
			</s:if>
		</div>
	</s:if>
	
	
</s:else>