<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="singerList.size>0">
	<table class="topSingerTable"  border="1" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<s:iterator status="status" value="singerList" begin="0" end="singerList.size>=10?9:(singerList.size-1)">
					<s:if test="#status.index==5">
						</tr><tr>
					</s:if>
					<td>
						<div class="topSingerBox"></div>
					</td>
				</s:iterator>
			</tr>
		</tbody>
	</table>
	<s:if test="singerList.size>10">
		<table class="singerTable"  border="1" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<s:iterator status="status" value="singerList" begin="10" >
						<s:if test="#status.index%5==0">
							</tr></tr>
						</s:if>
						<td>
							<s:property value="singerName"/>
						</td>
					</s:iterator>
				</tr>
			</tbody>
		</table>
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









