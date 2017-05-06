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
    $(".topSingerName a , .singerTable a").mouseover(function(){
    	$(this).css("color","#31c27c");
    });
    $(".topSingerName a , .singerTable a").mouseout(function(){
    	$(this).css("color","#333");
    });
    $(".pageBtn").click(function(){
    	if($(this).attr("class").indexOf("nowPage")!=-1 || $(this).attr("class").indexOf("apostrophe")!=-1){
    		return;
    	}
    	var page=$(this).attr("page");
    	if(page==undefined){
    		return;
    	}else{
    		$(".singersBox").html("<img class='loading' alt='' src='/music_view/img/loading.gif'>");
    		updateSingerList(page
    				,$(".category li[selectedItem='true']").attr("val")
    				,$(".letter li[selectedItem='true']").attr("val"));
    	}
    });
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
    
    function updateSingerList(pageNo,category,letter){
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
    
}

