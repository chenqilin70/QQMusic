$(function(){
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
});