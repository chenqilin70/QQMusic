$(function(){
	function setCookie(name,value)
	{
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	}
	function getCookie(name)
	{
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
		else
		return null;
	}
	function delCookie(name)
	{
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval=getCookie(name);
		if(cval!=null)
		document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	}
	/*以上定义操作cookies 的方法*/
	(function(){
		//每次打开此网页先在cookie中设置，本网页已打开
		alert(getCookie("playerIsOpen"))
		setCookie("playerIsOpen","true");
		alert(getCookie("playerIsOpen"))
		
		
//		$.ajax({
//			url:"player!updateMusicList.action",
//			type:"GET",
//			data:{
//				
//			},
//			error:function(){
//				
//			},
//			success:function(){
//				
//			}
//		});
	})();
	
	//在这个方法中不能写阻塞代码，比如alert，否则报错
	$(window).bind('beforeunload',function(){
		setCookie("playerIsOpen","false");
	});
	
	
	
	
	var img=$(".playerBg").css("background-image");
	img=img.substring(img.indexOf("\"")+1,img.lastIndexOf("\""));
	RGBaster.colors(img, {
	  success: function(payload) {
	      $("body").css("background-color",payload.dominant);
	  }
	});
	
	
	
});