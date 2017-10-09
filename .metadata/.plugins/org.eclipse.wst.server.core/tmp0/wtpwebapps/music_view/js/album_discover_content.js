function initModel(){
	 /*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
    }
	$(".defaultStyleLink").mouseover(function() {
		$(this).css("color", "#31c27c");
	});
	$(".defaultStyleLink").mouseout(function() {
		$(this).css("color", "#333");
	});
	
	$(".likeAlbumSingerLink").mouseover(function() {
		$(this).css("color", "#31c27c");
	});
	$(".likeAlbumSingerLink").mouseout(function() {
		$(this).css("color", "#999999");
	});
	
	function updateBottonItemStyle(){
		if(window.location.hash.indexOf("MYLIKE")!=-1){
			$(".myLikeLi a").unbind();
			$(".myLikeLi a").attr("class","presentItem");
		}
	}
	function updateModelCssAndJs(){
		$("#modelCss").attr("href","/music_view/css/listener_model/"+window.location.hash.replace(/#/,"").toLowerCase()+".css");
		$.getScript("/music_view/js/listener_model/"+window.location.hash.replace(/#/,"").toLowerCase()+".js",function(){
			initModel();
		});
	}
	$(".gotoMusicPlace").click(function(){
		$(".musicPlace").trigger("click");
		return false;
	});
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
    $(".pageBtn").click(function(){
    	if($(this).attr("class").indexOf("nowPage")!=-1 || $(this).attr("class").indexOf("apostrophe")!=-1){
    		return;
    	}
    	var page=$(this).attr("page");
    	if(page==undefined){
    		return;
    	}else{
    		updateAlbumList(Number(page));
    	}
    });
    function updateAlbumList(index){
    	$(".detailContent").html("<img class='loadingIcon' src='/music_view/img/loading.gif' alt=''/>");
		var conditionJson={
			language:getValByOptionClass("language"),
			genres:getValByOptionClass("genres"),
			price:getValByOptionClass("price"),
			category:getValByOptionClass("category"),
			years:getValByOptionClass("years"),
			albumCompany:getValByOptionClass("albumCompany")
		};
		var conditionStr=JSON.stringify(conditionJson);
		$.ajax({
			url:"album_discover!updateAlbumList.action",
			type:"GET",
			data:{
				albumCondition:conditionStr,
				pageNo:index
			},
			error:function(){
				alert("请检查网络！");
			},
			success:function(data){
				$(".detailContent").html(data);
				updateJs();
			}
		});
	}
	function getValByOptionClass(optionClass){
		return $(".defaultUl[optionClass='"+optionClass+"'] .optionItem[selectedItem='true']").attr("val");
	}
	function updateJs(){
		$.getScript("/music_view/js/album_discover_content.js",function(){
			initModel();
		});
	}
    $(".pageBtn").mouseover(function(e){
    	if(isWrapElement(e, this)){
    		if($(this).attr("class").indexOf("nowPage")!=-1 || $(this).attr("class").indexOf("apostrophe")!=-1){
        		return;
        	}
    		$(this).css("color","#ffffff").css("background-color","#31c27c");
    	}
    });
    
    $(".pageBtn").mouseout(function(e){
    	if(isWrapElement(e, this)){
    		if($(this).attr("class").indexOf("nowPage")!=-1 || $(this).attr("class").indexOf("apostrophe")!=-1){
        		return;
        	}
    		$(this).css("color","#a2a2a2").css("background-color","#ffffff");
    	}
    });
    $(".albumBox").mouseover(function(e){
    	if(isWrapElement(e,this)){
    		var $optionIcon=$(this).find(".moreAlbumOption");
    		$optionIcon.stop(true,true);
    		$optionIcon.animate({
    			opacity:1
    		},'fast');
    	}
    });
    $(".albumBox").mouseout(function(e){
    	if(isWrapElement(e,this)){
    		var $optionIcon=$(this).find(".moreAlbumOption");
    		$optionIcon.stop(true,true);
    		$optionIcon.animate({
    			opacity:0
    		},'fast');
    	}
    });
    $(".moreAlbumOption").mouseover(function(){
    	$(this).css("background-position","-80px -60px");
    });
    $(".moreAlbumOption").mouseout(function(){
    	$(this).css("background-position","-80px -40px");
    });
};
	