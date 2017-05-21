$(function(){
    //判断访问终端
    var browser={
        versions:function(){
            var u = navigator.userAgent, app = navigator.appVersion;
            return {
            	edge: u.indexOf('Edge') > -1, //Edge内核
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
                qq: u.match(/\sQQ/i) == " qq" //是否QQ
            };
        }(),
        language:(navigator.browserLanguage || navigator.language).toLowerCase()
    }
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
	
	var switchoverModel=Number(getCookie("switchoverModel"));
	var switchoverModelArr=[{
		"model":"circle",
		"position":"0 -205px"
	},
	{
		"model":"single",
		"position":"0px -232px"
	},
	{
		"model":"sequence",
		"position":"0px -260px"
	},
	{
		"model":"random",
		"position":"0 -73px"
	}];
	if(switchoverModel==null)
		switchoverModel=0;
	$(".loopList").css("background-position",switchoverModelArr[switchoverModel].position).css("display","block");
	function nextMusicDispatcher(){
		var audio=document.getElementById("mp3Audio");
		if(switchoverModel==0){//循环
			$(".nextMusic").trigger("click");
		}else if(switchoverModel==2){//顺序
			var $oldPlayingTr=$(".musicList tbody").find("tr[playing='true']");
			var $nextPlyingTr=$oldPlayingTr.next();
			console.log($nextPlyingTr.length)
			if($nextPlyingTr.length<=0){
				$(".playMusic").css("background-position","0px 0px");
				audio.pause();
			}else{
				nextPlayingId=$nextPlyingTr.attr("musicid");
				setCookie("nowPlay", nextPlayingId);
				updateNowPlay(nextPlayingId);
			}
		}else if(switchoverModel==3){//随机
			var $tbody=$(".musicList tbody");
			var length=$tbody.find("tr").length;
			if(length<=0){
				return;
			}else{
				var nextPlayingId =$tbody.find("tr:eq("+Math.floor(Math.random()*length)+")").attr("musicid");
				setCookie("nowPlay", nextPlayingId);
				updateNowPlay(nextPlayingId);
			}
		}else{//single单曲循环
			audio.play();
		}
	}
	$(".loopList").click(function(){
		var $this=$(this);
		if(switchoverModel==switchoverModelArr.length-1){
			switchoverModel=0;
			
		}else{
			switchoverModel++;
		}
		setCookie("switchoverModel", switchoverModel+"");
		$this.css("background-position",switchoverModelArr[switchoverModel].position);
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
		updatePlayer();
		setInterval(function(){
			updatePlayer();
		}, 1000);
	})();
	function updatePlayer(){
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
		pageScopeNowPlay=nowPlay;
		var audio=document.getElementById("mp3Audio");
		var $playBtn=$(".playMusic");
		var $nextTr;
		var $oldPlayTr=$(".musicList tr[playing='true']");
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
				var audioRoot=$("input[name='img_repository_path']").val()+"/music_m4a";
				var imgUrl=img_repository_path+"/album_head/T002R300x300M000"+jsonData.albumId+".jpg";
				var $nowName=$(".nowName");
				$(".albumHead").attr("src","").attr("src",imgUrl);
				changeBg(imgUrl);
				$(".albumCover").parent().attr("href","/music_view/album.action?albumId="+jsonData.albumId);
				$(".musicName").text(jsonData.musicName).parent().attr("href","/music_view/music.action?musicId="+jsonData.musicId);
				$(".singerName").text(jsonData.singerName).parent().attr("href","/music_view/singer.action?singerId="+jsonData.singerId);
				$(".albumName").text(jsonData.albumName).parent().attr("href","/music_view/album.action?albumId="+jsonData.albumId);
				$nextTr=$(".musicList tbody tr[musicid='"+jsonData.musicId+"']");
				if(jsonData.isLike=="true"){
					$(".likeBtn").attr("class","likeBtn like");
				}else{
					$(".likeBtn").attr("class","likeBtn dislike");
				}
				if(jsonData.music==1){
					afterGetSrc(audioRoot+"/C400"+jsonData.musicId+".m4a")
					$nowName.text(jsonData.musicName);
				}else{
					getRandomMusic(audioRoot);
					$nowName.html(jsonData.musicName+"&nbsp;&nbsp;&nbsp;&nbsp;(服务器查无此音乐，当前播放随机音乐)");
				}
				
			}
		});
		function getRandomMusic(audioRoot){
			$.ajax({
				url:"player!getRandomMusicId.action",
				type:"GET",
				data:{
					time:new Date().getTime()
				},
				error:function(){
					alert("请检查网络！");
				},
				success:function(data){
					afterGetSrc(audioRoot+"/"+data);
				}
			
			});
		}
		function updateGlobalDuration(){
			var durationInterval=window.setInterval(function(){
				var duration=audio.duration;
				if(duration){
					globalDuration=duration;
					window.clearInterval(durationInterval);
				}
			}, 100);
		}
		function afterGetSrc(src){
			if(!audio.paused){
				$playBtn.trigger("click");
			}
			$(audio).attr("src",src).find("source").attr("src",src);
			var $oldNum=$oldPlayTr.attr("playing","false").find(".num");
			$oldNum.css("background-image","").text($oldNum.attr("num"));
			$nextTr.attr("playing","true").find(".num").text("").css("background-image","url(http://"+window.location.host+"/music_view/img/wave.gif)");
			$nextTr.find(".opacityLink").unbind().css("opacity","1");
			registerOpacityLink($oldPlayTr.find(".opacityLink"));
			updateGlobalDuration();
			$(".downLoadBtn").parent().attr("href",src);
			if(audio.paused){
				$playBtn.trigger("click");
			}
		}
		
	}
	
	function updateNowList(playList){
		var musicInTableArr=new Array();
		$(".musicList tbody tr").each(function(index,element){
			var tempMusicId=$(element).attr("musicid");
			musicInTableArr.push(tempMusicId);
		});
		if(musicInTableArr.length==0){
			//如果界面中没有任何音乐信息，则发送请求，读取playList中所有音乐的信息
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
			
			addMusicByStr(willRequest.toString());
		}
	} 
	
	function addMusicByStr(playList){
		var musicsJson=getMusicInfoList(playList);
		var $tbody=$(".musicList tbody");
		var oldLength=$tbody.find("tr").length;
		for(var i in musicsJson){
			$tbody.append("<tr  musicid='"+musicsJson[i].musicId+"' playing='false' > " +
					"<td><div  musicid='"+musicsJson[i].musicId+"' class='musicCheckbox' selecteditem='false'> <span class='right'>√</span> </div></td>" +
					"<td>" +
						"<a class='opacityLink' musicid='"+musicsJson[i].musicId+"' >" +
								" <span class='num' num='"+(oldLength+Number(i)+1)+"'>"+(oldLength+Number(i)+1)+"</span>"+
								"<span>"+musicsJson[i].musicName+"</span>"+
						"</a>" +
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
		var $playerBg=$(".playerBg");
		if(url){
			if(browser.versions.trident || browser.versions.edge){
				$playerBg.css("background-color","rgba(255,255,255,0)");
			}else{
				$playerBg.attr("class","playerBg playerBg2").css("background-image","url("+url+")");
			}
			RGBaster.colors(url, {
			  success: function(payload) {
				  var colorArr=payload.palette;
				  var resultColor;
				  for(var i in colorArr){
					  var rgb=colorArr[i].replace(/rgb/,"").replace("(","").replace(")","").split(",");
					  var r=Number(rgb[0]),g=Number(rgb[1]),b=Number(rgb[2]);
					  if(r<230 || g<230 || b<230){
						  var  average=(r+g+b)/3;
						  var  gap=(Math.abs(r-average)+Math.abs(g-average)+Math.abs(b-average))/3;
						  if(resultColor!=undefined){
							  if(resultColor.gap>=gap){
								  continue;
							  }
						  }
						  resultColor={
								  "color":"rgb("+r+","+g+","+b+")",
								  "gap":gap
						  };
					  }
				  }
				  
				  if(resultColor==undefined){
					  resultColor={
							  "color":"#333",
							  "gap":0
					  };
				  }
			      $("body").css("background-color",resultColor.color);
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
	function registerOpacityLink($element){
		$element.css("opacity","0.6")
		$element.mouseover(opacityLinkMouseover).mouseout(opacityLinkMouseout);
	}
	/*设定刷新列表时重新加载的时间--开始*/
	function opacityLinkMouseover(){
		$(this).css("opacity","1");
	}
	function opacityLinkMouseout(){
		$(this).css("opacity","0.6");
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
			$(element).css("opacity","1");
		}
	}
	function bodyCheckBoxMouseout(e,element){
		if(isWrapElement(e, element)){
			if($(element).attr("selecteditem")=='false'){
				$(element).css("opacity","0.2");
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
		$(".musicList tbody tr[playing='false'] .opacityLink").unbind().mouseover(opacityLinkMouseover).mouseout(opacityLinkMouseout);
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
	var t1;
	var continuallyUpdateCircle=(function thisFun(){
		var audio=document.getElementById("mp3Audio");
		var $progressBar=$(".progressBar");
		var $nowTime=$(".nowTime");
		var $circleInBar=$(".circleInBar");
		t1=setInterval(function(){
			var percent=((audio.currentTime/globalDuration)*100)+"%";
			$circleInBar.css("left",percent);
			var currentMinite=Math.floor(audio.currentTime/60);
			var currentSecond=Math.floor(audio.currentTime-(currentMinite*60));
			var allMinite=Math.floor(globalDuration/60);
			var allSecond=Math.floor(globalDuration-(allMinite*60));
			var denominator=getBothLetter(allMinite)+":"+getBothLetter(allSecond);
			var numerator=getBothLetter(currentMinite)+":"+getBothLetter(currentSecond);
			$nowTime.text(numerator+"/"+denominator);
			$progressBar.css("background","linear-gradient(to right,rgba(255,255,255,0.8) "+percent+",rgba(255,255,255,0.2) "+percent+")");
			if(numerator==denominator){
				nextMusicDispatcher();
			}
		}, 1000);
		function getBothLetter(num){
			return (num<10?("0"+num):(num+""));
		}
		return thisFun;
	})();
	
	
	$(".circleInBar").mousedown(function(e){
		var audio=document.getElementById("mp3Audio");
		if(audio.ended){
			return;
		}
		var barWidth=Number($(".progressBar").css("width").replace(/px/,""));
		var oldMouseX=e.clientX;
		var $this=$(this);
		var oldLeft=Number($this.css("left").replace(/px/,""));
		var $progressBar=$(".progressBar");
		var newPercent;
		window.clearInterval(t1);
		$(document).mousemove(function(e){
			var newMouseX=e.clientX;
			var offsetX=oldMouseX-newMouseX;
			var newLeft=oldLeft-offsetX;
			if(newLeft>=0 && newLeft<=barWidth){
				newPercent=newLeft/barWidth;
				var percentLeft=(newPercent*100)+"%";
				$this.css("left",percentLeft);
				$progressBar.css("background","linear-gradient(to right,rgba(255,255,255,0.8) "+percentLeft+",rgba(255,255,255,0.2) "+percentLeft+")")
				
			}
		});
		$(document).mouseup(function(){
			if(newPercent!=undefined){
				audio.currentTime=globalDuration*(newPercent);
			}
			continuallyUpdateCircle();
			$(document).unbind('mousemove').unbind('mouseup');
		});
	});
	
	
	$(".nextMusic").click(function(){
		var $oldPlayingTr=$(".musicList tbody").find("tr[playing='true']");
		var $nextPlyingTr=$oldPlayingTr.next();
		var nextPlayingId;
		if($nextPlyingTr.length<=0){
			nextPlayingId=$(".musicList tbody").find("tr:first").attr("musicid");
		}else{
			nextPlayingId=$nextPlyingTr.attr("musicid");
		}
		setCookie("nowPlay", nextPlayingId);
		updateNowPlay(nextPlayingId);
	});
	$(".prevMusic").click(function(){
		var $oldPlayingTr=$(".musicList tbody").find("tr[playing='true']");
		var $prevPlyingTr=$oldPlayingTr.prev();
		var prevPlayingId;
		if($prevPlyingTr.length<=0){
			prevPlayingId=$(".musicList tbody").find("tr:last").attr("musicid");
		}else{
			prevPlayingId=$prevPlyingTr.attr("musicid");
		}
		setCookie("nowPlay", prevPlayingId);
		updateNowPlay(prevPlayingId);
	});
	
	$(".barBox").click(function(e){
		var srcElement=browser.versions.gecko?e.target:window.event.srcElement;
		if($(srcElement).attr("class")!=undefined  && $(srcElement).attr("class").indexOf("circleInBar")>-1){
			return;
		}
		var newOffsetX=e.offsetX-5;
		var allX=Number($(this).css("width").replace(/px/,""));
		var percentLeft=(100*newOffsetX/allX)+"%";
		$(".circleInBar").css("left",percentLeft);
		$(this).find(".progressBar").css("background","linear-gradient(to right,rgba(255,255,255,0.8) "+percentLeft+",rgba(255,255,255,0.2) "+percentLeft+")")
		$("#mp3Audio")[0].currentTime=globalDuration*(newOffsetX/allX);
	});
	$(".downLoadBtn").click(function(){
        var form = $("<form>");   //定义一个form表单
        form.attr('style', 'display:none');   //在form表单中添加查询参数
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', "player!downloadMusic.action");
        var fileSrc=$("#mp3Audio").attr("src");
        var fileName=fileSrc.substring(fileSrc.lastIndexOf("/")+1);
        var input1 = $('<input>');
        input1.attr('type', 'hidden').attr('name', 'musicFile').attr('value', fileName);
        var input2 = $('<input>');
        input2.attr('type', 'hidden').attr('name', 'musicName').attr('value', $(".musicName").text()+fileSrc.substring(fileSrc.lastIndexOf(".")));
        $('body').append(form);  //将表单放置在web中 
        form.append(input1);   //将查询参数控件提交到表单上
        form.append(input2);
        form.submit();
        form.remove();
	});
	$(".circleOfVolume").mousedown(function(e){
		var audio=document.getElementById("mp3Audio");
		var oldMouseX=e.clientX;
		var $circle=$(this);
		var oldLeft=Number($circle.css("left").replace(/px/,""))
		var $document=$(document);
		var barWidth=Number($circle.parent().css("width").replace(/px/,""));
		$document.mousemove(function(e){
			var newMouseX=e.clientX;
			var offsetX=oldMouseX-newMouseX;
			var newLeft=oldLeft-offsetX;
			var $volumeBar=$circle.parent().find(".volume");
			if(barWidth>newLeft && newLeft>=0){
				$circle.css("left",newLeft+"px");
				audio.volume=newLeft/barWidth;
				$volumeBar.css("background","linear-gradient(to right,rgba(255,255,255,0.8) "+((newLeft/barWidth)*100)+"%,rgba(255,255,255,0.2) "+((newLeft/barWidth)*100)+"%)");
			}
			
		});
		$document.one("mouseup",function(e){
			$document.unbind("mousemove");
		});
	});
	$(".volumeBox").click(function(e){
		var $volumeBox=$(this);
		var $circle=$(".circleOfVolume");
		var srcElement=browser.versions.gecko?e.target:window.event.srcElement;
		var barWidth=Number($circle.parent().css("width").replace(/px/,""));
		if($(srcElement).attr("class")!=undefined && $(srcElement).attr("class").indexOf("circleOfVolume")==-1){
			var newLeft=e.offsetX-5;
			$circle.css("left",newLeft+"px");
			document.getElementById("mp3Audio").volume=(newLeft/barWidth)<0?0:newLeft/barWidth;
			$volumeBox.find(".volume").css("background","linear-gradient(to right,rgba(255,255,255,0.8) "+((newLeft/barWidth)*100)+"%,rgba(255,255,255,0.2) "+((newLeft/barWidth)*100)+"%)");
		}
	});
	$(".collect").click(function(){
		var musics=getSelectedMusic().join(",");
		likeMusic(musics);
	});
	function getSelectedMusic(){
		var $tbody=$(".musicList tbody");
		var musicList=new Array();
		$tbody.find(".musicCheckbox[selecteditem='true']").each(function(){
			musicList.push($(this).attr("musicid"));
		});
		return musicList;
	}
	function likeMusic(musics){
		if($("input[name='isLogin']").val()=='true'){
			$.ajax({
				url:"player!likeMusic.action",
				data:{
					musics:musics
				},
				type:"GET",
				error:function(){
					alert("请检查网络!");
				},
				success:function(data){
					if(data=='true'){
						var $successLike=$(".successLike");
						$successLike.css("display","block");
						$(".likeBtn").attr("class","likeBtn like");
						window.setTimeout(function(){
							$successLike.animate({
								opacity:"0"
							},'fast',function(){
								$successLike.css({
									"display":"none",
									"opacity":"1"
								});
							});
						}, 1000);
						
					}else{
						alert("请检查网络!");
					}
				}
				
			});
		}else{
			$(".login").trigger("click");
		}
		
	}
	function dislikeMusic(musics){
		if($("input[name='isLogin']").val()=='true'){
			$.ajax({
				url:"player!dislikeMusic.action",
				data:{
					musics:musics
				},
				type:"GET",
				error:function(){
					alert("请检查网络!");
				},
				success:function(data){
					if(data=='true'){
						$(".likeBtn").attr("class","likeBtn dislike");
					}else{
						alert("请检查网络!");
					}
				}
				
			});
		}else{
			$(".login").trigger("click");
		}
	}
	$(".login").click(function(){
		$(".totalMask").css("display","block");
	});
	/************************************************************************************************/
	$(".loginInput  input , .validateCode input").focus(function(){
    	$(this).parent().css("border-color","#a7d2fb");
    	$(this).parent().css("box-shadow","0px 0px 3px #a7d2fb");
    });
    $(".loginInput  input , .validateCode input").blur(function(){
    	$(this).parent().css("border-color","#d6d6d6");
    	$(this).parent().css("box-shadow","");
    });
    $(".nextAutoLogin , .loginCheckbox").click(function(){
    	$check=$(".loginCheckbox");
    	$check.attr("isSelected")==='true'?$check.attr("isSelected","false"):$check.attr("isSelected","true");
    });
    $(".closeIcon").click(function(){
    	$(".totalMask").css("display","none");
    });
    $(".closeIcon").mouseover(function(){
    	$(this).css("background-position","-14px -200px");
    });
    $(".closeIcon").mouseout(function(){
    	$(this).css("background-position","0px -200px");
    });
    $(".loginTail a").mouseover(function(){
    	$(this).css("text-decoration","underline");
    });
    $(".loginTail a").mouseout(function(){
    	$(this).css("text-decoration","none");
    });
    $(".signinOrLogin").click(function(){
    	var oldFor=$("input[name=forWhat]").val();
    	if(oldFor=="login"){
    		//显示注册
    		$(".comfirmPassword").css("display","block");
    		$("input[name=forWhat]").val("signin");
    		$(this).text($("#directLogin").val());
    		$(".accountAndPasswordLogin").css("display","none");
    		$(".new_user_sign_in").css("display","block");
    		$(".loginOrSiginSubmit input").val($("#signin").val());
    		$(".loginTitl div").text(($("#signin").val()));
    		if($("input[name=username]").val()!==""){
    			$("input[name=username]").trigger("blur");
    		}
    		$("input[name=comfirmPassword]").trigger("blur");
    	}else{
    		//显示登录
    		$(".comfirmPassword").css("display","none");
    		$("input[name=forWhat]").val("login");
    		$(this).text($("#new_user_sign_in").val());
    		$(".accountAndPasswordLogin").css("display","block");
    		$(".new_user_sign_in").css("display","none");
    		$(".loginOrSiginSubmit input").val($("#login").val());
    		$(".loginTitl div").text(($("#login").val()));
    		$("input[name=username]").parent().find(".inputMessage").text("");
    		$("input[name=comfirmPassword]").val("").parent().find(".inputMessage").text("");
    	}
    	return false;
    });
    
    $(".validateImg").click(function(){
    	var src=this.src;
    	this.src=src.substring(0,src.lastIndexOf("=")+1)+new Date().getTime();
    });
    $(".loginInput i").mouseover(function(){
    	$(this).css("background-position", "-140px -161px");
    });
    $(".loginInput i").mouseout(function(){
    	$(this).css("background-position", "-117px -161px");
    });
    $(".loginInput i").click(function(){
    	$(this).parent().find("input").val("");
    });
    function usernameBlur(){
    	if($("input[name=forWhat]").val()=="login"){
    		return ;
    	}
    	$this=$(this);
    		$.ajax({
    			url:"template!templateValidate.action",
    			type:"GET",
    			data:{
    				"username":$this.val()
    			},
    			error:function(){
    				alert("请检查网络");
    			},
    			success:function(data){
    				var json=JSON.parse(data);
    				if(json["username"]==="验证成功"){
    					$this.parent().find(".inputMessage").css("color","#86ce2f");
    				}else{
    					$this.parent().find(".inputMessage").css("color","#e84048");
    				}
    				$this.parent().find(".inputMessage").text(json["username"]);
    			}
    		});
    }
    function passwordBlur(){
    	if($("input[name=forWhat]").val()=="login"){
    		return ;
    	}
    	var message=isPwdLegal();
    	if("验证成功"===message){
    		$(this).parent().find(".inputMessage").css("color","#86ce2f").text(message);
    	}else{
    		$(this).parent().find(".inputMessage").css("color","#e84048").text(message);
    	}
    	$comfirmPassword=$(".loginBody form input[name=comfirmPassword]");
    	if($comfirmPassword.parent().find(".inputMessage").text()!=="" && $comfirmPassword.val()!==$(this).val()){
    		$comfirmPassword.parent().find(".inputMessage").css("color","#e84048").text("密码两次输入不一致");
    	}
    }
    function comfirmPasswordBlur(){
    	if($(".loginBody form input[name=password]").val()===""){
    		return;
    	}
    	if($(this).val()=== $(".loginBody form input[name=password]").val()){
    		$(this).parent().find(".inputMessage").css("color","#86ce2f").text("验证成功");
    	}else{
    		$(this).parent().find(".inputMessage").css("color","#e84048").text("两次密码输入不一致");
    	}
    }
    function validateCodeBlur(){
    	$this=$(this);
		$.ajax({
			url:"template!templateValidate.action",
			type:"GET",
			data:{
				"validateCode":$this.val()
			},
			error:function(){
				alert("请检查网络");
			},
			success:function(data){
				var json=JSON.parse(data);
				if(json["validateCode"]==="验证成功"){
					$this.parent()
						.find(".inputMessage")
						.css("color","#86ce2f")
						.text(json["validateCode"]);
				}else{
					$this.val("").parent()
						.find(".inputMessage")
						.css("color","#e84048")
						.text(json["validateCode"]);
				}
			}
		});
    }
    function isPwdLegal(){
		if($("#password").val()=="")
			return "密码不能为空";
		else if($("#password").val().length<6 || $("#password").val().length>30)
			return "密码必须在6-30位之间";
		else 
			return "验证成功";
	}
    $(".loginBody form input[name=username]").blur(usernameBlur);
    $(".loginBody form input[name=password]").blur(passwordBlur);
    $(".loginBody form input[name=comfirmPassword]").blur(comfirmPasswordBlur);
    $(".loginBody form input[name=validateCode]").blur(validateCodeBlur);
    $(".loginOrSiginSubmit input").click(function(){
    	var forWhat=$("input[name=forWhat]").val();
    	var username=$(".loginBody form input[name=username]").val();
    	var password=$(".loginBody form input[name=password]").val();
    	var comfirmPassword=$(".loginBody form input[name=comfirmPassword]").val();
    	var validateCode=$(".loginBody form input[name=validateCode]").val();
    	var remenberPwd=$(".loginCheckbox").attr("isSelected");
    	if("login"===forWhat){
    		$.ajax({
    			url:"template!login.action",
    			type:"POST",
    			data:{
    				"username":username,
    				"password":password,
    				"validateCode":validateCode,
    				"remenberPwd":(remenberPwd=='true')
    			},
    			error:function(){
    				alert("请检查网络");
    			},
    			success:function(data){
    				console.log(data);
    				var json=JSON.parse(data);
    				console.log(json);
    				if(json["isSuccess"]==="true"){
    					window.location.reload();
    				}else{
    					if(json["username"]!=="验证成功"){
    						$("input[name=username]").parent().find(".inputMessage").css("color","#e84048");
    					}else{
    						$("input[name=username]").parent().find(".inputMessage").css("color","#86ce2f");
    					}
    					if(json["validateCode"]!=="验证成功"){
    						$("input[name=validateCode]").parent().find(".inputMessage").css("color","#e84048");
    					}else{
    						$("input[name=validateCode]").parent().find(".inputMessage").css("color","#86ce2f");
    					}
    					$("input[name=username]").parent().find(".inputMessage").text(json["username"]);
    					$("input[name=validateCode]").parent().find(".inputMessage").text(json["validateCode"]);
    				}
    			}
    		});

    	}else{
    		$.ajax({
			url:"template!signin.action",
			type:"POST",
			data:{
				"username":username,
				"password":password,
				"comfirmPassword":comfirmPassword,
				"validateCode":validateCode
			},
			error:function(){
				alert("请检查网络");
			},
			success:function(data){
				var json=JSON.parse(data);
				if(json["isSuccess"]==="true"){
					window.location.reload();
				}else{
					$("input[name=username]").parent().find(".inputMessage").text(json["username"]);
					$("input[name=password]").parent().find(".inputMessage").text(json["password"]);
					$("input[name=comfirmPassword]").parent().find(".inputMessage").text(json["comfirmPassword"]);
					$("input[name=validateCode]").parent().find(".inputMessage").text(json["validateCode"]);
				}
			}
		});
    		
    	}
    	return false;
    });
    $(".login , .setting1, .setting2, .listenerName , .logout").mouseover(function(){
    	$(this).css("opacity","1");
    });
    $(".login , .setting1, .setting2, .listenerName , .logout").mouseout(function(){
    	$(this).css("opacity","0.3");
    });
    $(".topRightCorner").mouseover(function(e){
    	if(isWrapElement(e, this)){
    		$(this).find(".logout").css("visibility","visible");
    	}
    });
    $(".topRightCorner").mouseout(function(e){
    	if(isWrapElement(e,this)){
    		$(this).find(".logout").css("visibility","hidden");
    	}
    });
    $(".logout").click(function(){
    	$.ajax({
    		url:"template!logout.action",
    		type:"GET",
    		error:function(){
    			alert("请检查网路！");
    		},
    		success:function(data){
    			window.location.reload(true);
    		}
    	});
    	return false;
    });
    $(".likeBtn").click(function(){
    	var musicid=$(".musicList tbody").find("tr[playing='true']").attr("musicid");
    	if($(this).attr("class").indexOf("dislike")>-1){
        	likeMusic(musicid);
    	}else{
    		dislikeMusic(musicid);
    	}
    	
    });
});






