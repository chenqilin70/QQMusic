function initModel(){
	$(".defaultStyleLink").mouseover(function() {
		$(this).css("color", "#31c27c");
	});
	$(".defaultStyleLink").mouseout(function() {
		$(this).css("color", "#333");
	});
	$(".gotoMusicPlace").click(function(){
		$(".musicPlace").trigger("click");
		return false;
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
		$(".detailContent").html("<img class='loading' alt='' src='/music_view/img/loading.gif'>");
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
				if(data==="unLogin"){
					location.reload();
					return;
				}
				updateBottonItemStyle();
				$(".detailContent").html(data);
				updateModelCssAndJs();
			}
		});
	}
	$(".myLikeUl a").click(function(){
		changeModel($(this).attr("model"),1);
		return false;
	});
	
}
	