$(function(){
	$(".musicPlace").attr("selectedItem","true");
	$(".musicPlace").css("background-color","#31C27C").css("color","#fff");
	
	var singerHeads=document.getElementsByName("singerHead");
	for(var h in singerHeads){
		singerHeads[h].onerror=function(){
			this.src="/music_view/img/singer_300.png";
		};
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
	$briefIntroXml=$($("#hiddenBriefIntro").val());
	var str="";
	$briefIntroXml.find("basic").find("item").each(function(index,item){
		var key=$(item).find("key").text().replace(/ /, "");
		var value=$(item).find("value").text().replace(/ /, "");
		str=str+key+":"+value+"&nbsp;";
	});
	$(".singerBriefIntro").find("div").append(str);	
	
	var animateTime=400;
	$(".albumBox .imgContainer").mouseover(function(e){
        if(isWrapElement(e,this)){
            $(".cover_play").stop(true,true);
            $(this).find(".albumHead").stop(true,false);
            $(this).find(".cover_play").animate({
                top: '77px',
                left: '77px',
                height:'70px',
                opacity:'1'
            },animateTime);
            $(this).find(".albumHead").animate({
                top:'-10px',
                left:'-10px',
                height:'244px',
                width:'244px'
            },animateTime);
        }
    });
    $(".albumBox .imgContainer").mouseout(function(e){
        if(isWrapElement(e,this)){
            $(".cover_play").stop(true,true);
            $(this).find(".albumHead").stop(true,false);
            $(this).find(".cover_play").animate({
                top: '87px',
                left: '87px',
                height:'50px',
                opacity:'0'
            },animateTime);
            $(this).find(".albumHead").animate({
                top:'0',
                left:'0',
                height:'224px',
                width:'224px'
            },animateTime);
        }
    });
    $(".mvBox .imgContainer").mouseover(function(e){
        if(isWrapElement(e,this)){
            $(".cover_play").stop(true,true);
            $(this).find(".mvHead").stop(true,false);
            $(this).find(".cover_play").animate({
                top: '28.3px',
                left: '77px',
                height:'70px',
                opacity:'1'
            },animateTime);
            $(this).find(".mvHead").animate({
                top:'-10px',
                left:'-10px',
                height:'146.66px',
                width:'244px'
            },animateTime);
        }
    });
    $(".mvBox .imgContainer").mouseout(function(e){
        if(isWrapElement(e,this)){
            $(".cover_play").stop(true,true);
            $(this).find(".mvHead").stop(true,false);
            $(this).find(".cover_play").animate({
                top: '38px',
                left: '87px',
                height:'50px',
                opacity:'0'
            },animateTime);
            $(this).find(".mvHead").animate({
                top:'0',
                left:'0',
                height:'126.66px',
                width:'224px'
            },animateTime);
        }
    });
    $(".addCare").click(function(){
    	var isLogin=$("#isLogin").val();
    	var isCared=$("#isCared").val();
    	var $this=$(this);
    	if(isLogin=="false"){
    		$(".login").trigger("click");
    		return;
    	}
    	if(isCared=="true"){
    		//取消关注
    		alert("cancel");
    	}else{
    		//添加关注
    		
    		$.ajax({
    			url:"singer!addCareSinger.action",
    			type:"GET",
    			data:{
    				singerId:$(this).attr("singerId")
    			},
    			error:function(){
    				alert("请检查网络！");
    			},
    			success:function(data){
    				if(data=="true"){
    					$(".uncared").attr("class","cared");
    					$("#isCared").val("true");
    					var oldText=$(".careText").text();
    					var newNum=Number(oldText.replace(/\D/g,""))+1;
    					$(".careText").text(oldText.replace(/\d/g,""+newNum));
    				}else{
    					alert("请检查网络！");
    				}
    			}
    			
    		});
    		
    	}
    });
    
});