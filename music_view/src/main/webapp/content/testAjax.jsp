<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <sx:head />
        <s:url  var="myurl" value="/ajax.action" >
        	<s:param name="param1">value11</s:param>
        </s:url>
    </head>
    <body>
        <s:submit value="Make Request" id="aa"/>
		<sx:bind afterNotifyTopics="after" sources="aa" events="onclick" href="%{#myurl}" />
    	<script type="text/javascript">
    		dojo.event.topic.subscribe("after",function(data){
    			console.log("/after:");
    			console.log(data);
    		});
    	</script>
    </body>
</html>
