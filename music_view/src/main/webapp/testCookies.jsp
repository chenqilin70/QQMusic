<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
</head>
<body>
<h1>Test Cookies</h1>
<input type="text" name="message"><input type="button" id="btn" name="submit">
<script type="text/javascript">
$("#btn").click(function(){
	$.ajax({
		url:"/music_view/AsyncServlet",
		type:"GET",
		data:{
			method:"onMessage",
			message:$("input[name='message']").val()
		},
		error:function(){
			alert("请检查网络")
		},
		success:function(data){
		}
	});
});
function reverseAjax(){
	$.ajax({
	    url : "/music_view/AsyncServlet?method=onOpen",
	    cache : false,
	    dataType : "text",
	    success : function(data) {
	    	reverseAjax();
	        alert(data);
	    }
	});
}
reverseAjax();
</script>
</body>
</html>