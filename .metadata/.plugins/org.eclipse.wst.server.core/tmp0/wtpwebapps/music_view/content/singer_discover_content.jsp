<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function changeErrorImg(element){
		element.src="/music_view/img/singer_300.png";
	}
</script>
<s:if test="singerList.size>0">
	<!-- 第一页才有大头像 -->
	<s:if test="pageBean.pageNo==1">
		<table class="topSingerTable"  border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<s:iterator status="status" value="singerList" begin="0" end="singerList.size>=10?9:(singerList.size-1)">
						<s:if test="#status.index==5">
							</tr><tr>
						</s:if>
						
							<s:if test="#status.index==4 || #status.index==9">
								<td class="lastTd">
							</s:if>
							<s:else>
								<td class="notLastTd">
							</s:else>
								<div class="topSingerBox" align="center">
								<img class="topSingerHead" onerror="changeErrorImg(this)" alt="" src="<s:text name='img_repository_path'/>/singer_head/<s:property value="singerId"/>.jpg">
								<div class="topSingerName">
									<s:a action="singer" class="defaultStyleLink">
										<s:param name="singerId" value="singerId"></s:param>
										<s:property value="singerName"/>
									</s:a>
								</div>
							</div>
						</td>
					</s:iterator>
				</tr>
			</tbody>
		</table>
		<s:if test="singerList.size>10">
			<table class="singerTable"  border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<s:iterator status="status" value="singerList" begin="10" >
							<s:if test="#status.index%5==0">
								</tr></tr>
							</s:if>
							<td>
								<s:a action="singer" class="defaultStyleLink">
									<s:param name="singerId" value="singerId"></s:param>
									<s:property value="singerName"/>
								</s:a>
							</td>
						</s:iterator>
					</tr>
				</tbody>
			</table>
		</s:if>
	</s:if>
	<!-- 如果不是第一页 -->
	<s:else>
		<table class="singerTable"  border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<s:iterator status="status" value="singerList" begin="0" >
							<s:if test="#status.index%5==0 && #status.index!=0">
								</tr></tr>
							</s:if>
							<td>
								<s:a action="singer" class="defaultStyleLink">
									<s:param name="singerId" value="singerId"></s:param>
									<s:property value="singerName"/>
								</s:a>
							</td>
						</s:iterator>
					</tr>
				</tbody>
			</table>
	</s:else>
	<!-- 数量大于30才会有翻页 -->
	<s:if test="pageBean.objCount > 30">
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
			<s:if test="pageBean.pageCount > 8">
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
</s:if>
<s:else>
	<div class="nullImg">
		<img src="<%=request.getContextPath() %>/img/symbol_none.png" alt=""/>
	</div>
	<div class="nothingBeFound">
  		<s:text name="nothingBeFound"/>
    </div>
</s:else>









