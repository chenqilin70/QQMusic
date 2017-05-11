$(function(){
	//以下是采用反向ajax的方式，暂时无法实现功能
//	(function connect() {
//		alert(21)
//        $.ajax({
//            url : "/music_view/ReverseAjaxServlet",
//            data:{
//            	method:"onOpen"
//            },
//            dataType : "text",
//            error:function(){
//            	alert("error");
//            },
//            success : function(data) {
//                connect();
//                alert(data);
//            }
//        });
//    })();
	

	
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
	
	
	
	
	var pageScopePlayList;
	var pageScopeNowPlay;
	var pageScopeNowPlayerNo;
	var img_repository_path=$("input[name='img_repository_path']").val();
	(function(){
		//每次打开此网页先在cookie中设置，本网页已打开
		var openNo=getCookie("playerIsOpen");
		if(openNo==null){
			pageScopeNowPlayerNo=1;
			setCookie("playerIsOpen","1");
		}else{
			pageScopeNowPlayerNo=(Number(openNo)+1);
			setCookie("playerIsOpen",(Number(openNo)+1)+"");
		}
		console.log("此时player开启数量是："+getCookie("playerIsOpen"));
	})();
	(function(){
		setInterval(function(){
			updatePlayer();
		}, 1000);
	})();
	function updatePlayer(playList){
		var playList=getCookie("playList");
		var nowPlay=getCookie("nowPlay");
		var openNo=getCookie("nowPlay");
		//如果两者都为空，表示手动输入url第一次进入了播放器，或者用户手动删除了所有cookie
		if(playList!=null && nowPlay!=null){
			if(pageScopePlayList!=playList){
				pageScopePlayList=playList;
				updateNowList(playList);
			}
			if(pageScopeNowPlay!=nowPlay){
				pageScopeNowPlay=nowPlay;
				updateNowPlay(nowPlay);
			}
		}else{
			changeBg();
		}
		if(openNo==null){
			pageScopeNowPlayerNo=1;
			setCookie("playerIsOpen","1");
		}
	}
	function updateNowPlay(nowPlay){
		$.ajax({
			url:"player!changeNowPlay.action",
			type:"GET",
			data:{
				nowMusicId:nowPlay
			},
			error:function(){
				alert("请检查网络！");
			},
			success:function(data){
				var jsonData=JSON.parse(data);
				var $playerBg=$(".playerBg");
				var imgUrl=img_repository_path+"/album_head/T002R300x300M000"+jsonData.albumId+".jpg";
				changeBg(imgUrl);
				$(".albumHead").attr("src","").attr("src",imgUrl);
				$playerBg.css("background-image"
						,"url("+imgUrl+")");
				$(".musicName").text(jsonData.musicName);
				$(".singerName").text(jsonData.singerName);
				$(".albumName").text(jsonData.albumName);
				
			}
		});
	}
	
	function updateNowList(playList){
		var musicInCookieArr=playList.split(",");
		var musicInTableArr;
		$(".musicList tbody tr").each(function(index,element){
			var tempMusicId=$(element).attr("musicid");
			console.log(index+","+tempMusicId)
			musicInTableArr[index+1]=tempMusicId;
		});
		if(musicInTableArr==undefined){
			//如果界面中没有任何音乐信息，则发送请求，读取musicInCookieArr中所有音乐的信息
			var musicsJson=getMusicInfoList(playList);
			for(var i in musicsJson){
				$(".musicList tbody").append("<tr  musicid='"+musicsJson[i].musicId+"' >" +
						"<td><div class='musicCheckbox'></div></td>" +
						"<td><a musicid='"+musicsJson[i].musicId+"'>"+musicsJson[i].musicName+"</a></td>" +
						"<td><a albumid='"+musicsJson[i].album.albumId+"'>"+musicsJson[i].album.albumName+"</a></td>" +
						"<td></td>" +
						"</tr>");
			}
		}else{
			//如果界面中已经存在音乐信息，则筛选出界面中没有的，但cookie中有的，再发送请求读取这些信息
			var willRequest=new Array();
			var cookieList=playList.split;
			for(var c=0 ;c<cookieList.length;c++){
				var flag=true;
				for(var u=0;u<musicInTableArr.length;u++){
					if(musicInTableArr[u]==cookieList[c]){
						flag=false;
						break;
					}
				}
				if(flag){
					willRequest.push(cookieList[c]);
				}
			}
			for(var k in willRequest){
				alert(willRequest[k]);
			}
		}
	} 
	function getMusicInfoList(musicIds){
		var result;
		$.ajax({
			url:"player!getMusicInfoList.action",
			type:"GET",
			data:{
				musicIds:musicIds
			},
			async:false,
			error:function(){
				alert("请检查网络");
			},
			success:function(data){
				result=JSON.parse(data);
			}
		});
		return result;
	}
	
	function changeBg(url){
		if(url){
			$(".playerBg").attr("class","playerBg playerBg2").css("background-image","url("+url+")");
			RGBaster.colors($(".albumHead")[0], {
			  success: function(payload) {
			      $("body").css("background-color",payload.dominant);
			  }
			});
		}else{
			$(".playerBg").attr("class","playerBg playerBg1");
		}
	}
	//在这个方法中不能写阻塞代码，比如alert，否则报错
	$(window).bind('beforeunload',function(){
		var openNo=getCookie("playerIsOpen");
		pageScopeNowPlayerNo=(Number(openNo)-1);
		setCookie("playerIsOpen",(Number(openNo)-1)+"");
		console.log("此时player开启数量是："+getCookie("playerIsOpen"));
	});
	
	
	
	
	
	
});