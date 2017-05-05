$(function(){
	$(".musicPlace , .singerNavItem").attr("selectedItem","true");
	$(".musicPlace").css("background-color","#31C27C").css("color","#fff");
	$(".singerNavItem").css("color","#31C27C");
	$(".tag:first").css("background-color","rgb(255,255,255)").attr("selectedItem","true");
	/*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
    }
    $(".defaultStyleLink").mouseover(function(){
    	$(this).css("color","#31c27c");
    });
    $(".defaultStyleLink").mouseout(function(){
    	$(this).css("color","#333");
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
	function updateSingerList(pageNo){
		$.ajax({
			url:"singer_discover!updateSingerList.action",
			type:"GET",
			data:{
				pageNo:pageNo,
			},
			error:function(){
				alert("请检查网络！");
			},
			success:function(data){
				$(".singersBox").html(data);
			}
		});
	}
	
	
	
});