$(function(){
	$(".musicPlace").attr("selectedItem","true").css("background-color","#31C27C").css("color","#fff");
	
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
    if(Number($(".container").css("height").replace(/px/,""))<840){
        $(".container").css("height","865px");
    }
    var desc=$(".descValue").attr("value");
    $(".introP")[0].innerHTML=desc.replace(/<br>/,"");
    $(".descContent")[0].innerHTML=desc;
    
    var height=460;
    if(Number($(".otherAlbumDescBox").css("height").replace(/px/,""))>height){
        $(".otherAlbumDescBox").css("height",height+"px");
        $(".descContent").css("height",(height-87-32)+"px");
    }
    var isFireFox= window.navigator.userAgent.indexOf("FireFox")?true:false;
    $("body").click(function(e){
        var $relatedElement =isFireFox?event.target:event.srcElement;
        var flag=$(".otherAlbumDescBox")[0].compareDocumentPosition($relatedElement);
        if($($relatedElement).attr("class")=='moreBaseInfo'){
            $(".otherAlbumDescBox").css("display","block");
            return;
        }
        if(flag!=0 && flag!=20){
            $(".otherAlbumDescBox").css("display","none");
        }
    });
    $(".defaultStyleLink").mouseover(function(){
        $(this).css("color","#2CAF6F");
    });
    $(".defaultStyleLink").mouseout(function(){
        $(this).css("color","rgb(51,51,51)");
    });
    $(".playAll").mouseover(function(e){
        if(isWrapElement(e,this)){
            $(this).css("background-color","#2CAF6F");
        }
    });
    $(".playAll").mouseout(function(e){
        if(isWrapElement(e,this)){
            $(this).css("background-color","#31C27C");
        }
    });
    $(".collect , .more").mouseover(function(e){
        if(isWrapElement(e,this)){
            $(this).css("background-color","#EDEDED");
        }
    });
    $(".collect , .more").mouseout(function(e){
        if(isWrapElement(e,this)){
            $(this).css("background-color","#ffffff");
        }
    });
    $(".songOption").mouseover(function(){
    	var nowPos=$(this).css("background-position");
    	var nowY=nowPos.split(" ")[1];
    	$(this).css("background-position","-40px"+" "+nowY);
    });
    $(".songOption").mouseout(function(){
    	var nowPos=$(this).css("background-position");
    	var nowY=nowPos.split(" ")[1];
    	$(this).css("background-position","0px"+" "+nowY);
    });
    $(".songTr").mouseover(function(e){
    	if(isWrapElement(e,this)){
    		$(this).find(".songOption").css("display","block");
    	}
    	
    });
	$(".songTr").mouseout(function(e){
		if(isWrapElement(e,this)){
			$(this).find(".songOption").css("display","none");
		}
    });
	$(".collect").click(function(){
		var isLogin=$("#isLogin").val();
		$this=$(this);
		if(isLogin=='false'){
			$(".login").trigger("click");
		}else{
			$.ajax({
				url:"album!collectAlbum.action",
				type:"GET",
				data:{
					albumId:$(this).attr("albumId")
				},
				error:function(){
					alert("请检查网络！");
				},
				success:function(data){
					if(data=='true'){
						$this.find("i").attr("class","collected");
					}else{
						alert("请检查网络！");
					}
				}
			});
		}
	});
	function playerIsOpen(){
		var openNoStr=getCookie("playerIsOpen");
		if(openNoStr!=null){
			var openNo=Number(openNoStr);
			if(openNo>0){
				return true;
			}else{
				return false;
			};
		}else{
			return false;
		};
	}
	/*判断数组中是否存在某个值*/
	function arrayContain(arr,val){
		if(arr==undefined || val==undefined || arr==null || arr.length==0)
			return false;
		for(var o in arr){
			if(val==o){
				return true;
			}
		}
		return false;
	}
	$(".playAll").click(function(e){
		var oldList=getCookie("playList");
		var resultMids="";
		var firstMid="";
		$(".songsTable tbody tr").each(function(index,element){
			var mid=$(element).attr("musicid");
			if(index==0){
				firstMid=mid;
			}
			if(oldList==null || !arrayContain(oldList.split(","),mid) ){
				resultMids=resultMids+mid+",";
			}
		});
		resultMids=resultMids.substring(0, resultMids.lastIndexOf(","));
		setCookie("playList",oldList+","+resultMids);
		setCookie("nowPlay",firstMid);
		if(!playerIsOpen()){
			window.open("http://"+window.location.host+"/music_view/player.action");
		}
	});
	$(".playSong").click(function(){
		if(playerIsOpen()){
			var oldList=getCookie("playList");
			if(oldList!=null){
				var oldListArr=oldList.split(",");
				for(var i in oldListArr){
					if($(this).attr("musicId")==oldListArr[i]){
						setCookie("nowPlay",$(this).attr("musicId"));
						return;
					}
				}
			}
			setCookie("nowPlay",$(this).attr("musicId"));
			setCookie("playList",((oldList==null || oldList=="")?"":(oldList+","))+$(this).attr("musicId"));
		}else{
			alert("没有打开");
		};
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});
