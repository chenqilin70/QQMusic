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
		$(".myChooser").attr("from","profile").trigger("click");
	});
	var globalFiles,isFake=false;
	function chooserChange(fileElement){
		console.log("dd")
		var file=fileElement.files[0];
		if(!file){
			console.log("如果没有选文件，则设置为全局");
			fileElement.files=globalFiles;
			return;
		}
		var fileName=file.name;
		var ext=fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
		if(ext!=='GIF' && ext!=='JPG' && ext!=='BMP' && ext!=='PNG' && ext!=='JPEG'){
//			if(globalFiles){
//				isFake=true;
//			}
			if("rechoose"==$(".myChooser").attr("from")){
				fileElement.files=globalFiles;
			}
			
			var $messageDiv=$(".fileMessage");
			var $messageBg=$(".messageBg");
			$messageDiv.css("display","block").animate({
				opacity:"1"
			},'fast',function(){
				setTimeout(function(){
					$messageDiv.animate({
						opacity:"0"
					},'fast',function(){
						$messageDiv.css("display","none");
					});
				}, 3000);
			});
			$messageBg.css("display","block").animate({
				opacity:"0.8"
			},'fast',function(){
				setTimeout(function(){
					$messageBg.animate({
						opacity:"0"
					},'fast',function(){
						$messageBg.css("display","none")
					});
				}, 3000);
			});
			
			return false;
		}else{
//			if(isFake){
//				alert("图片被拦截")
//				isFake=false;
//				return;
//			}
			var windowURL = window.URL || window.webkitURL;//windowURL相当于一个资源管理器
			var dataURL = windowURL.createObjectURL(fileElement.files.item(0));
			$(".mainArea").html("").prepend("<img class='image' onload='newHeadLoad()' alt='' src='"+dataURL+"'>");
			console.log("1111111111111111");
			$(".chooserMask").css("display","block");
			globalFiles=fileElement.files;
		}
		
		
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
    	$(".myChooser").attr("from","rechoose").trigger("click");
    });
    $(".chooserMask").click(function(e){
    	var $relatedElement=e.target || event.srcElement;
    	var flag=this.compareDocumentPosition($relatedElement);
    	if(!flag){
    		$(".chooserMask").css("display","none");
    		$(".myChooser").val("");
    	}
    });
    function submitHeadFunc(){
    	var offset=JSON.parse($("#imgOffset").val());
    	var $submitBtn=$(".submitHead");
    	var $uploadWaiting=$(".uploadWaiting");
    	$submitBtn.css("display","none");
    	$uploadWaiting.css("display","block");
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
                success: function (data,status)  //服务器成功响应处理函数
                {
                	var message=JSON.parse(data);
                	$submitBtn.css("display","block");
                	$uploadWaiting.css("display","none");
                	$(".chooserMask").css("display","none");
                	if(message.isSuccess){
                		$(".profileImg , .userHeadInHead , .lisenerHead").attr("src","/music_view/img/listener_head/"+message.head+"?time="+new Date().getTime());
                	}else{
                		alert("请检查网络");
                	}
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                	alert("请检查网络");
                }
            }
        );
        $(".myChooser").unbind("change").change(function(){
    		 chooserChange(this);
    	});
        $(".submitHead").one("click",submitHeadFunc);
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
    }
    $(".submitHead").one("click",submitHeadFunc);
});






