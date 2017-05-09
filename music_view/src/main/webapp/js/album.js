$(function(){
	$(".musicPlace").attr("selectedItem","true");
	$(".musicPlace").css("background-color","#31C27C").css("color","#fff");
	
     /*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
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
	$(".playAll").click(function(){
		
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});
