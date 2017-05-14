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
	
	 /*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
    }
	
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
	//设置滚动条
	$(".mCS-my-theme").mCustomScrollbar({
		theme:"my-theme",
		scrollbarPosition:"outside",
		axis:"y"
	});
	
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
		var openNo=getCookie("playerIsOpen");
		
		if(playList!=null && nowPlay!=null){//cookie中有值
			if(pageScopePlayList!=playList){
				pageScopePlayList=playList;
				updateNowList(playList);
			}
			if(pageScopeNowPlay!=nowPlay){
				pageScopeNowPlay=nowPlay;
				updateNowPlay(nowPlay);
			}
		}else if(playList==null && nowPlay==null){//cookie中没值
			if(pageScopePlayList==undefined && pageScopeNowPlay==undefined){//cookie中没值  page中没值：用户第一次手动输入url打开player
				changeBg();
			}else if(pageScopePlayList!=undefined && pageScopeNowPlay!=undefined){//cookie中没值  page中有值：用户手动删除了cookie
				setCookie("playList", pageScopePlayList);
				setCookie("nowPlay", pageScopeNowPlay);
			}else{
				alert("page中的nowPlay和playList状态不一致");
			}
		}else{
			alert("cookie中的nowPlay和playList状态不一致");
		}
		if(openNo==null){//用户手动删除了cookie
			setCookie("playerIsOpen",pageScopeNowPlayerNo);
		}
	}
	var globalDuration;
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
				$(".albumCover").parent().attr("href","/music_view/album.action?albumId="+jsonData.albumId);
				$(".musicName").text(jsonData.musicName).parent().attr("href","/music_view/music.action?musicId="+jsonData.musicId);
				$(".singerName").text(jsonData.singerName).parent().attr("href","/music_view/singer.action?singerId="+jsonData.singerId);
				$(".albumName").text(jsonData.albumName).parent().attr("href","/music_view/album.action?albumId="+jsonData.albumId);
				$(".nowName").text(jsonData.musicName);
				globalDuration=document.getElementById("mp3Audio").duration;
			}
		});
	}
	
	function updateNowList(playList){
		var musicInCookieArr=playList.split(",");
		var musicInTableArr=new Array();
		$(".musicList tbody tr").each(function(index,element){
			var tempMusicId=$(element).attr("musicid");
			musicInTableArr.push(tempMusicId);
		});
		if(musicInTableArr.length==0){
			//如果界面中没有任何音乐信息，则发送请求，读取musicInCookieArr中所有音乐的信息
			addMusicByStr(playList);
		}else{
			//如果界面中已经存在音乐信息，则筛选出界面中没有的，但cookie中有的，再发送请求读取这些信息
			var willRequest=new Array();
			var cookieList=playList.split(",");
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
			
			console.log(willRequest.toString());
			addMusicByStr(willRequest.toString());
		}
	} 
	
	function addMusicByStr(playList){
		var musicsJson=getMusicInfoList(playList);
		for(var i in musicsJson){
			$(".musicList tbody").append("<tr  musicid='"+musicsJson[i].musicId+"' >" +
					"<td><div class='musicCheckbox' selecteditem='false'> <span class='right'>√</span> </div></td>" +
					"<td>" +
						"<a class='opacityLink' musicid='"+musicsJson[i].musicId+"'>"+musicsJson[i].musicName+"</a>" +
						"<div class='shareThisMusic thisMusicBtn'></div>" +
						"<div class='downLoadThisMusic thisMusicBtn'></div>" +
						"<div class='addToMenu thisMusicBtn'></div>" +
						"<div musicid="+musicsJson[i].musicId+" class='playThisMusic thisMusicBtn'></div>" +
					"</td>" +
					"<td><a class='opacityLink' href='/music_view/singer.action?singerId="+musicsJson[i].album.singer.singerId+"' target='_blank'>"+musicsJson[i].album.singer.singerName+"</a></td>" +
					"<td></td>" +
					"</tr>");
		}
		refreshListJs();
		var $headCheckBox=$(".musicList thead .musicCheckbox");
		if($headCheckBox.attr("selecteditem")=='true'){
			$headCheckBox.attr("selecteditem",'false').css("opacity","0.2").find("span").css("display","none");
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
		if(openNo!=null){
			pageScopeNowPlayerNo=(Number(openNo)-1);
			setCookie("playerIsOpen",(Number(openNo)-1)+"");
			console.log("此时player开启数量是："+getCookie("playerIsOpen"));
		}
		
	});
	
	$(".optBtn").mouseover(function(e){
		if(isWrapElement(e,this)){
			$(this).find("span , .btnBorder").css("opacity","1");
		}
	}).mouseout(function(e){
		if(isWrapElement(e,this)){
			$(this).find("span").css("opacity","0.8");
			$(this).find(".btnBorder").css("opacity","0.3");
		}
	});
	$(".musicList thead .musicCheckbox").mouseover(function(e){
		if(isWrapElement(e, this)){
			$(this).css("opacity","1");
		}
	}).mouseout(function(e){
		if(isWrapElement(e, this)){
			if($(this).attr("selecteditem")=='false'){
				$(this).css("opacity","0.2");
			}
		}
	}).click(function(){
		var $this=$(this);
		var oldflag=$this.attr("selecteditem");
		if(oldflag=='true'){
			$this.attr("selecteditem","false").find("span").css("display","none");
		}else{
			$this.attr("selecteditem","true").css("opacity","1").find("span").css("display","inline-block");
		}
		var newflag=$this.attr("selecteditem");
		var $allChecBox=$(".musicCheckbox");
		$allChecBox.attr("selecteditem",newflag);
		if(newflag=='true'){
			$allChecBox.css("opacity","1").find("span").css("display","inline-block");
		}else{
			$(".musicList tbody .musicCheckbox").css("opacity","0.2");
			$allChecBox.find("span").css("display","none");
		}
	});
	/*设定刷新列表时重新加载的时间--开始*/
	function opacityLinkMouseover(){
		$(this).css("opacity","1");
	}
	function opacityLinkMouseout(){
		$(this).css("opacity","0.8");
	}
	function bodyCheckBoxClick($this){
		var flag=$this.attr("selecteditem");
		if(flag=='true'){
			$this.attr("selecteditem","false").find("span").css("display","none");
		}else{
			$this.attr("selecteditem","true").css("opacity","1").find("span").css("display","inline-block");
		}
		var $headCheckBox=$(".musicList thead .musicCheckbox");
		if(isAllCheckBoxValOf("true")){
			$headCheckBox.attr("selecteditem",'true').css("opacity","1").find("span").css("display","inline-block");
		}else{
			$headCheckBox.attr("selecteditem",'false').css("opacity","0.2").find("span").css("display","none");
		}
		
	}
	function bodyCheckBoxMouseover(e,element){
		if(isWrapElement(e, element)){
			$(this).css("opacity","1");
		}
	}
	function bodyCheckBoxMouseout(e,element){
		if(isWrapElement(e, element)){
			if($(this).attr("selecteditem")=='false'){
				$(this).css("opacity","0.2");
			}
		}
	}
	function bodyTrMouseover(e,element){
		if(isWrapElement(e,element)){
			$(element).find(".thisMusicBtn").css("display","block");
		}
	}
	function bodyTrMouseout(e,element){
		if(isWrapElement(e,element)){
			$(element).find(".thisMusicBtn").css("display","none");
		}
	}
	function thisMusicBtnMouseover(element){
		var $this=$(element);
		var oldPosition=$this.css("background-position");
		var oldX=Number(oldPosition.split(" ")[0].replace(/px/ ,""));
		var oldY=Number(oldPosition.split(" ")[1].replace(/px/ ,""));
		$this.css("background-position",(oldX-40)+"px "+oldY+"px");
		
	}
	function thisMusicBtnMouseout(element){
		var $this=$(element);
		var oldPosition=$this.css("background-position");
		var oldX=Number(oldPosition.split(" ")[0].replace(/px/ ,""));
		var oldY=Number(oldPosition.split(" ")[1].replace(/px/ ,""));
		$this.css("background-position",(oldX+40)+"px "+oldY+"px");
		
	}
	function playThisMusicClick(element){
		var nowPlay=$(element).attr("musicid");
		setCookie("nowPlay", nowPlay);
		pageScopeNowPlay=nowPlay;
		updateNowPlay(nowPlay);
	}
	/*设定刷新列表时重新加载的时间--结束*/
	function refreshListJs(){
		$(".opacityLink").unbind().mouseover(opacityLinkMouseover).mouseout(opacityLinkMouseout);
		$(".musicList tbody .musicCheckbox")
			.unbind()
			.click(function(){bodyCheckBoxClick($(this));})
			.mouseover(function(e){bodyCheckBoxMouseover(e,this);})
			.mouseout(function(e){bodyCheckBoxMouseout(e,this);});
		$(".musicList tbody tr")
			.unbind()
			.mouseover(function(e){bodyTrMouseover(e,this);})
			.mouseout(function(e){bodyTrMouseout(e,this)});
		$(".thisMusicBtn")
			.unbind()
			.mouseover(function(){thisMusicBtnMouseover(this)})
			.mouseout(function(){thisMusicBtnMouseout(this)});
		$(".playThisMusic").click(function(){playThisMusicClick(this);});
	}
	
	function isAllCheckBoxValOf(val){
		var result=true;
		$(".musicList tbody .musicCheckbox").each(function(index,element){
			if($(element).attr("selecteditem")!=val){
				result=false;
				return false;
			}
		});
		return result;
	}
	$(".playBtn , .loopList , .likeBtn , .downLoadBtn ,.changeModel , .trumpetIcon")
				.mouseover(function(){
		$(this).css("opacity","1");
	}).mouseout(function(){
		$(this).css("opacity","0.8");
	});
	$(".defaultPlayerLink").mouseover(function(){
		$(this).css("opacity","1");
	}).mouseout(function(){
		$(this).css("opacity","0.6");
	});
	/*以下开始处理音频播放*/
	
	$(".playMusic").click(function(){
		var audio=document.getElementById("mp3Audio");
		if(audio.paused){
			$(this).css("background-position","-30px 0");
			audio.play();
		}else{
			$(this).css("background-position","0 0");
			audio.pause();
		}
	});
	(function(){
		var audio=document.getElementById("mp3Audio");
		var $progressBar=$(".progressBar");
		var $nowTime=$(".nowTime");
		var $circleInBar=$(".circleInBar");
		setInterval(function(){
			var percent=((audio.currentTime/globalDuration)*100)+"%";
			console.log("百分比："+percent);
			$circleInBar.css("left",percent);
			var currentMinite=Math.floor(audio.currentTime/60);
			var currentSecond=Math.floor(audio.currentTime-(currentMinite*60));
			var allMinite=Math.floor(globalDuration/60);
			var allSecond=Math.floor(globalDuration-(allMinite*60));
			$nowTime.text(getBothLetter(currentMinite)+":"+getBothLetter(currentSecond)+"/"+getBothLetter(allMinite)+":"+getBothLetter(allSecond));
			$progressBar.css("background","linear-gradient(to right,rgba(255,255,255,1) "+percent+",rgba(255,255,255,0.2) "+percent+")")
		}, 1000);
		function getBothLetter(num){
			return (num<10?("0"+num):(num+""));
		}
	})();
	$(".circleInBar").mousedown(function(){
		
	});
	document.onmousemove=function(e){
		var e=e || window.event;
		console.log(e.clientX)
	}
	
});






