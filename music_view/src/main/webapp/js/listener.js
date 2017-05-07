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
	function chooserChange(fileElement){
		var windowURL = window.URL || window.webkitURL;//windowURL相当于一个资源管理器
		var dataURL = windowURL.createObjectURL(fileElement.files.item(0));
		$(".mainArea").html("").prepend("<img class='image' onload='newHeadLoad()' alt='' src='"+dataURL+"'>");
		$(".chooserMask").css("display","block");
	}
	$(".myChooser").change(function(){
		chooserChange(this);
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
    $(".submitHead , .rechoose").mouseover(function(){
    	$(this).css("background-color","#2caf6f");
    });
    $(".submitHead , .rechoose").mouseout(function(){
    	$(this).css("background-color","#31c27c");
    });
    $(".rechoose").click(function(){
    	$(".myChooser").trigger("click");
    });
    $(".chooserMask").click(function(e){
    	var $relatedElement=e.target || event.srcElement;
    	var flag=this.compareDocumentPosition($relatedElement);
    	if(!flag){
    		$(".chooserMask").css("display","none");
    		$(".myChooser").val("");
    	}
    });
    $(".submitHead").click(function(){
    	var offset=JSON.parse($("#imgOffset").val());
    	console.log();
    	$.ajaxFileUpload
        (
            {
                url:'listener!uploadHead.action',//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:'myChooser',//文件上传空间的id属性  <input type="file" id="file" name="file" />
                data:{
                	height:offset.height,
                	width:offset.width,
                	top:offset.top,
                	left:offset.left,
                	imageName:document.getElementById("myChooser").files[0].name
                },
                dataType: 'json',//返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    var json=JSON.parse(data);
                    console.log(json)
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("network is wrong");
                }
            }
        )
        $(".myChooser").unbind("change").change(function(){
    		chooserChange(this);
    	});
      
//    	var xhr = new XMLHttpRequest();
//    	var fileInput = $(".myChooser")[0];
//    	var file = fileInput.files[0];
//    	var formData = {
//        		'upload': file
//        	};
//    	xhr.open("POST", "listener!uploadHead.action");
//    	xhr.setRequestHeader("Content-Type", "multipart/form-data");
//    	xhr.onload = function(){
//    	    if(this.status === 200){
//    	       alert("发送成功");
//    	    }else{
//    	    	alert("发送失败");
//    	    }
//    	}
//    	xhr.send(formData);
//    	xhr = null;
    });
});






