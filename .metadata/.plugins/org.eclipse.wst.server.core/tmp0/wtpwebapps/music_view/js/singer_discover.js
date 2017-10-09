$(function(){
	$(".musicPlace , .singerNavItem").attr("selectedItem","true");
	$(".musicPlace").css("background-color","#31C27C").css("color","#fff");
	$(".singerNavItem").css("color","#31C27C");
	$(".tag:first").css("background-color","rgb(255,255,255)").attr("selectedItem","true");
	changeItemToTrue($(".category li[val='"+getHashJson()['category']+"']"));
	changeItemToTrue($(".letter li[val='"+getHashJson()['letter']+"']"));
	/*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
    }
    $(".loginBtnInBg").click(function(){
    	$(".login").trigger("click");
    });
    $(".conditionBox li").mouseover(function(){
    	$this=$(this);
    	if($this.attr("selectedItem")=="true") return false;
    	$this.css("color","#31c27c");
    });
    $(".conditionBox li").mouseout(function(){
    	$this=$(this);
    	if($this.attr("selectedItem")=="true") return false;
    	$this.css("color","#333");
    });
	$(".loginBtnInBg").mouseover(function(){
		$(".lightBorder").stop(true,true);
		$(".lightBorder").animate({
			opacity:'1'
		},'fast');
	});
	$(".loginBtnInBg").mouseout(function(){
		$(".lightBorder").stop(true,true);
		$(".lightBorder").animate({
			opacity:'0'
		},'fast');
	});
	$(".toRight div").one("click",function(){
		moveFocusSingerUl(-1,$(this));
	});
	$(".toLeft div").one("click",function(){
		moveFocusSingerUl(1,$(this));
	});
	function moveFocusSingerUl(offset,$this){
		if($this){
			$this.unbind("click");
		}
		var oldLeft=Number($(".focusSingerUl").css("left").replace(/px/,""));
		var newLeft=oldLeft+(750*offset);
		if(newLeft<=-(750*($(".tag").length))){
			newLeft=0;
		}
		if(newLeft>=750){
			newLeft=-(750*($(".tag").length-1));
		}
		$(".focusSingerUl").animate({
			left:newLeft+"px"
		},500,function(){
			if($this){
				$this.one("click",function(){
					moveFocusSingerUl(offset,$this);
				});
			}
			changeTag(newLeft/750);
		});
	}
	function changeTag(index){
		$(".tag").css("background-color","rgba(255,255,255,0.3)").attr("selectedItem","false");
		$(".tag[index='"+(-index)+"']").css("background-color","rgb(255,255,255)").attr("selectedItem","true");
	}
	$(".tagsUl li").click(function(){
		var index=-Number($(this).find(".tag").attr("index"));
		var oldLeft=Number($(".focusSingerUl").css("left").replace(/px/,""));
		moveFocusSingerUl((index-(oldLeft/750)));
	});
	$(".toRight div").mouseout(function(){
		$(this).css("background-position","-25px 0px");
	});
	$(".toRight div").mouseover(function(){
		$(this).css("background-position","-25px -54px");
	});
	$(".toLeft div").mouseout(function(){
		$(this).css("background-position","0px 0px");
	});
	$(".toLeft div").mouseover(function(){
		$(this).css("background-position","0px -54px");
	});
	$(".focusSingerUl li").mouseover(function(e){
		if(isWrapElement(e,this)){
			$(this).find(".maskBorder").css("display","block");
			$(this).find(".focusSingerMask").stop(true,true).animate({
				opacity:"0"
			},200);
		}
	});
	$(".focusSingerUl li").mouseout(function(e){
		if(isWrapElement(e,this)){
			$(this).find(".maskBorder").css("display","none");
			$(this).find(".focusSingerMask").stop(true,true).animate({
				opacity:"0.5"
			},200);
		}
	});
	function changeItemToTrue($item){
		$item.attr("selectedItem","true").css({
			"background-color": "#31c27c",
			"color":"#ffffff"
		});
	}
	function changeItemToFalse($item){
		$item.attr("selectedItem","false").css({
			"background-color": "#fbfbfd",
			"color":"#333"
		});
	}
	$(".conditionBox li").click(function(){
		$(".singersBox").html("<img class='loading' alt='' src='/music_view/img/loading.gif'>");
		var $this=$(this);
		var $conditioinUl=$this.parent().parent();
		changeItemToFalse($conditioinUl.find("li[selectedItem='true']"));
		changeItemToTrue($this);
		updateSingerList(1
				,$(".category li[selectedItem='true']").attr("val")
				,$(".letter li[selectedItem='true']").attr("val"));
		return false;
	});
	
	var updateSingerList=(function fun(pageNo,category,letter){
		function innerFunc(){
			$.ajax({
				url:"singer_discover!updateSingerList.action",
				type:"GET",
				data:{
					pageNo:pageNo,
					category:category,
					letter:letter
				},
				error:function(){
					alert("请检查网络！");
				},
				success:function(data){
					window.location.hash="#pageNo%3D"+pageNo+"%26category%3D"+category+"%26letter%3D"+letter;
					$(".singersBox").html(data);
					$.getScript("/music_view/js/singer_discover_content.js",function(){
						initModel();
					});
				}
			});
		};
		innerFunc();
		return fun;
	})(getHashJson()['pageNo'],getHashJson()['category'],getHashJson()['letter']);
	function getHashJson(){
		var hash=window.location.hash;
		hash=hash.replace(/#/, "");
		if(hash=="" || hash==undefined || hash==null){
			return JSON.parse("{\"pageNo\":\"1\",\"category\":\"all\",\"letter\":\"hot\"}");
		}
		//以&分割
		var hashArr=hash.split("%26");
		var jsonStr="{";
		for(var i in hashArr){
			//以=分割
			var key=hashArr[i].split("%3D")[0];
			var val=hashArr[i].split("%3D")[1];
			jsonStr=jsonStr+"\""+unescape(key)+"\":\""+unescape(val)+"\",";
		}
		jsonStr=jsonStr.substring(0,jsonStr.lastIndexOf(","));
		jsonStr=jsonStr+"}";
		console.log("解码url后生成的json：");
		console.log(jsonStr);
		return JSON.parse(jsonStr);
	}
	function getJsonLength(json){
		var len=0;
		for(var i in json){
			len++;
		}
		return len;
	}
	function changeHashByJson(json){
		var hashStr="";
		for(var i in json){
			hashStr=hashStr+i+"="+json[i]+"&";
		}
		hashStr=hashStr.substring(0, hashStr.lastIndexOf("&"));
		hashStr=escape(hashStr);
		console.log("编码后的url：");
		console.log(hashStr);
		window.location.hash=hashStr;
	}
	
	
});