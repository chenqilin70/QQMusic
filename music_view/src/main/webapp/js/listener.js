$(function(){
	/*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
    }
	$(".myMusic").attr("selectedItem","true")
				.css("background-color","#31C27C").css("color","#fff");
	$(".immediatelyLogin").click(function(){
		console.log($(".login")[0]);
		$(".login").trigger("click");
	});
	$(".profileItem li").mouseover(function(){
		$(this).find(".profileItemLink").css("color","#31c27c");
	});
	$(".profileItem li").mouseout(function(){
		$(this).find(".profileItemLink").css("color","#ffffff");
	});
	
	$(".bottomItemLink").mouseover(function(){
		$(this).css("color","#31c27c");
	});
	$(".bottomItemLink").mouseout(function(){
		$(this).css("color","#ffffff");
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
	function changeModel(model,pageNo){
		if($("#isLogin").val()=="false"){
			return;
		}
		$.ajax({
			url:"listener!changeModel.action",
			type:"GET",
			data:{
				listener_model:model,
				pageNo:pageNo
			},
			error:function(){
				alert("请检查网络！");
			},
			success:function(data){
				window.location.hash="#"+model;
				updateBottonItemStyle();
				$(".detailContent").html(data);
				updateModelCssAndJs();
			}
		});
	}
	changeModel(window.location.hash==""?"MYLIKE_MUSIC":window.location.hash.replace(/#/,""),1);
	$(".profileImgBox").mouseover(function(e){
		if(isWrapElement(e, this)){
			$(".updateProfileImg").css("display","block");
		}
	});
	$(".profileImgBox").mouseout(function(e){
		if(isWrapElement(e, this)){
			$(".updateProfileImg").css("display","none");
		}
	});
	$(".updateProfileImg").click(function(){
		$(".myChooser").trigger("click");
	});
	$(".myChooser").change(function(){
		var windowURL = window.URL || window.webkitURL;//windowURL相当于一个资源管理器
		var dataURL = windowURL.createObjectURL(this.files.item(0));
		var $img=$(".image");
		$img.attr('src', dataURL);
		$(".chooserMask").css("display","block");
	});
	$(".closeEdit").click(function(){
		$(".chooserMask").css("display","none");
		$(".myChooser").val("");
	});
	 $(".closeEdit").mouseover(function(){
    	$(this).css("background-position","-14px -200px");
    });
    $(".closeEdit").mouseout(function(){
    	$(this).css("background-position","0px -200px");
    });
});






