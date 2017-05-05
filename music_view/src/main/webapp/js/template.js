$(function () {
    /*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return false;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        if (flag == 0 || flag == 20) {
            return false;
        } else {
            return true;
        }
    }
    $(".model , .login , .navItem").mouseover(function () {
        if ($(this).attr("selectedItem") != "true") {
            $(this).css("color", "#31C27C");
        }
    });
    $(".model , .login , .navItem").mouseout(function () {
        if ($(this).attr("selectedItem") != "true") {
            $(this).css("color", "#333333");
        }
    });
    $(".login").click(function(){
    	$(".totalMask").css("display","block");
    });
    $("#greenJewel").mouseover(function () {
        $(this).css("background-color", "#2CAF6F");
    });
    $("#greenJewel").mouseout(function () {
        $(this).css("background-color", "#31C27C");
    });
    $("#payPackage").mouseover(function () {
        $(this).css("background-color", "#EDEDED");
    });
    $("#payPackage").mouseout(function () {
        $(this).css("background-color", "");
    });
    $("#searchBtn").mouseover(function (e) {
        if (isWrapElement(e, this)) {
            $("#searchIcon").css("background-position", "0px -60px");
        }
    });
    $("#searchBtn").mouseout(function (e) {
        if (isWrapElement(e, this)) {
            $("#searchIcon").css("background-position", "0px -40px");
        }
    });
    $(".searchRankUl li").mouseover(function (e) {
        if (isWrapElement(e, this)) {
            $(this).css("background-color", "#31C27C").css("color", "#ffffff");
            $(this).find(".searchCount").css("color", "#ffffff");
        }
    });
    $(".searchRankUl li").mouseout(function (e) {
        if (isWrapElement(e, this)) {
            $(this).css("background-color", "#ffffff").css("color", "#333");
            $(this).find(".searchCount").css("color", "#333");
        }
    });
    $(".trashCan").mouseover(function () {
        $(this).css("background-position", "-18px -58px");
    });
    $(".trashCan").mouseout(function () {
        $(this).css("background-position", "-98px 2px");
    });
    $(".searchInput").focus(function () {
        $(".searchList").css("display", "block").animate({height: '235px'}, 'fast');
    });
    $(".searchInput").blur(function (e) {
        window.setTimeout(function () {
            $(".searchList").animate({height: '0px'}, 'slow', function () {
                $(".searchList").css("display", "none");
            });
        }, '500');
    });
    $(".foot a").mouseover(function () {
        $(this).css("color", "rgb(49,194,124)");
    });
    $(".foot a").mouseout(function () {
        $(this).css("color", "#999999");
    });
    $(".facility").mouseover(function (e) {
        var tagName = e.target.tagName;
        if (tagName == "LI") {
            $(this).css("background-position-y", "-49px");
            $("." + $(this).attr("class").split(" ")[0] + "Text").find("a").css("color", "rgb(49,194,124)");
        } else if (tagName == "A") {
            $(this).css("color", "rgb(49,194,124)");
            var $icon = $("." + $(this).attr("class").split(" ")[0].replace(/Text/, ""));
            $icon.css("background-position-y", "-49px");
        }

    });
    $(".facility").mouseout(function (e) {
        var tagName = e.target.tagName;
        if (tagName == "LI") {
            $(this).css("background-position-y", "0px");
            $("." + $(this).attr("class").split(" ")[0] + "Text").find("a").css("color", "#999999");
        } else if (tagName == "A") {
            $(this).css("color", "#999999");
            var $icon = $("." + $(this).attr("class").split(" ")[0].replace(/Text/, ""));
            $icon.css("background-position-y", "0px");
        }
    });
    $(".product i").mouseover(function (e) {
        $(this).css("background-position-y", "-49px");
        $(this).parent().find("a").trigger("mouseover");
    });
    $(".product i").mouseout(function (e) {
        $(this).css("background-position-y", "0px")
        $(this).parent().find("a").trigger("mouseout");
    });
    $(".product a").mouseover(function (e) {
        $(this).parent().parent().find("i").css("background-position-y", "-49px");
    });
    $(".product a").mouseout(function (e) {
        $(this).parent().parent().find("i").css("background-position-y", "0px");
    });
    document.onmousewheel=updateScroll;
    function updateScroll(){
    	if(window.pageYOffset>=200){
    		$(".toTop").css("display","block");
    	}else{
    		$(".toTop").css("display","none");
    	}
    };
    updateScroll();
    $(".toTop").mouseover(function(){
    	$(this).css("background-position","-50px -120px");
    });
    $(".toTop").mouseout(function(){
    	$(this).css("background-position","0px -120px");
    });
    $(".toTop").click(function(){
    	$(".toTop").css("display","none");
    });
    function adviceEvent(){
    	$(".advice").mouseover(function(){
        	$(this).attr("class","fixDiv adviceActive").html("意见<br>反馈");
        	adviceEvent();
        });
    	$(".adviceActive").mouseout(function(){
        	$(this).attr("class","fixDiv advice").html("");
        	adviceEvent();
        });
    }
    adviceEvent();
    function playerEvent(){
    	$(".player").mouseover(function(){
    		$(this).attr("class","fixDiv playerActive").html("播放器");
    		 playerEvent();
    	});
    	$(".playerActive").mouseout(function(){
    		$(this).attr("class","fixDiv player").html("");
    		 playerEvent();
    	});
    }
    playerEvent();
    
    $(".defaultStyleLink").mouseover(function(){
    	$(this).css("color","#31c27c");
    });
    $(".defaultStyleLink").mouseout(function(){
    	$(this).css("color","#333");
    });
    $(".loginInput  input , .validateCode input").focus(function(){
    	$(this).parent().css("border-color","#a7d2fb");
    	$(this).parent().css("box-shadow","0px 0px 3px #a7d2fb");
    });
    $(".loginInput  input , .validateCode input").blur(function(){
    	$(this).parent().css("border-color","#d6d6d6");
    	$(this).parent().css("box-shadow","");
    });
    $(".nextAutoLogin , .loginCheckbox").click(function(){
    	$check=$(".loginCheckbox");
    	$check.attr("isSelected")==='true'?$check.attr("isSelected","false"):$check.attr("isSelected","true");
    });
    $(".closeIcon").click(function(){
    	$(".totalMask").css("display","none");
    });
    $(".closeIcon").mouseover(function(){
    	$(this).css("background-position","-14px -200px");
    });
    $(".closeIcon").mouseout(function(){
    	$(this).css("background-position","0px -200px");
    });
    $(".loginTail a").mouseover(function(){
    	$(this).css("text-decoration","underline");
    });
    $(".loginTail a").mouseout(function(){
    	$(this).css("text-decoration","none");
    });
    $(".signinOrLogin").click(function(){
    	var oldFor=$("input[name=forWhat]").val();
    	if(oldFor=="login"){
    		//显示注册
    		$(".comfirmPassword").css("display","block");
    		$("input[name=forWhat]").val("signin");
    		$(this).text($("#directLogin").val());
    		$(".accountAndPasswordLogin").css("display","none");
    		$(".new_user_sign_in").css("display","block");
    		$(".loginOrSiginSubmit input").val($("#signin").val());
    		$(".loginTitl div").text(($("#signin").val()));
    		if($("input[name=username]").val()!==""){
    			$("input[name=username]").trigger("blur");
    		}
    		$("input[name=comfirmPassword]").trigger("blur");
    	}else{
    		//显示登录
    		$(".comfirmPassword").css("display","none");
    		$("input[name=forWhat]").val("login");
    		$(this).text($("#new_user_sign_in").val());
    		$(".accountAndPasswordLogin").css("display","block");
    		$(".new_user_sign_in").css("display","none");
    		$(".loginOrSiginSubmit input").val($("#login").val());
    		$(".loginTitl div").text(($("#login").val()));
    		$("input[name=username]").parent().find(".inputMessage").text("");
    		$("input[name=comfirmPassword]").val("").parent().find(".inputMessage").text("");
    	}
    	return false;
    });
    
    $(".validateImg").click(function(){
    	var src=this.src;
    	this.src=src.substring(0,src.lastIndexOf("=")+1)+new Date().getTime();
    });
    $(".loginInput i").mouseover(function(){
    	$(this).css("background-position", "-140px -161px");
    });
    $(".loginInput i").mouseout(function(){
    	$(this).css("background-position", "-117px -161px");
    });
    $(".loginInput i").click(function(){
    	$(this).parent().find("input").val("");
    });
    function usernameBlur(){
    	if($("input[name=forWhat]").val()=="login"){
    		return ;
    	}
    	$this=$(this);
    		$.ajax({
    			url:"template!templateValidate.action",
    			type:"GET",
    			data:{
    				"username":$this.val()
    			},
    			error:function(){
    				alert("请检查网络");
    			},
    			success:function(data){
    				var json=JSON.parse(data);
    				if(json["username"]==="验证成功"){
    					$this.parent().find(".inputMessage").css("color","#86ce2f");
    				}else{
    					$this.parent().find(".inputMessage").css("color","#e84048");
    				}
    				$this.parent().find(".inputMessage").text(json["username"]);
    			}
    		});
    }
    function passwordBlur(){
    	if($("input[name=forWhat]").val()=="login"){
    		return ;
    	}
    	var message=isPwdLegal();
    	if("验证成功"===message){
    		$(this).parent().find(".inputMessage").css("color","#86ce2f").text(message);
    	}else{
    		$(this).parent().find(".inputMessage").css("color","#e84048").text(message);
    	}
    	$comfirmPassword=$(".loginBody form input[name=comfirmPassword]");
    	if($comfirmPassword.parent().find(".inputMessage").text()!=="" && $comfirmPassword.val()!==$(this).val()){
    		$comfirmPassword.parent().find(".inputMessage").css("color","#e84048").text("密码两次输入不一致");
    	}
    }
    function comfirmPasswordBlur(){
    	if($(".loginBody form input[name=password]").val()===""){
    		return;
    	}
    	if($(this).val()=== $(".loginBody form input[name=password]").val()){
    		$(this).parent().find(".inputMessage").css("color","#86ce2f").text("验证成功");
    	}else{
    		$(this).parent().find(".inputMessage").css("color","#e84048").text("两次密码输入不一致");
    	}
    }
    function validateCodeBlur(){
    	$this=$(this);
		$.ajax({
			url:"template!templateValidate.action",
			type:"GET",
			data:{
				"validateCode":$this.val()
			},
			error:function(){
				alert("请检查网络");
			},
			success:function(data){
				var json=JSON.parse(data);
				if(json["validateCode"]==="验证成功"){
					$this.parent()
						.find(".inputMessage")
						.css("color","#86ce2f")
						.text(json["validateCode"]);
				}else{
					$this.val("").parent()
						.find(".inputMessage")
						.css("color","#e84048")
						.text(json["validateCode"]);
				}
			}
		});
    }
    function isPwdLegal(){
		if($("#password").val()=="")
			return "密码不能为空";
		else if($("#password").val().length<6 || $("#password").val().length>30)
			return "密码必须在6-30位之间";
		else 
			return "验证成功";
	}
    $(".loginBody form input[name=username]").blur(usernameBlur);
    $(".loginBody form input[name=password]").blur(passwordBlur);
    $(".loginBody form input[name=comfirmPassword]").blur(comfirmPasswordBlur);
    $(".loginBody form input[name=validateCode]").blur(validateCodeBlur);
    $(".loginOrSiginSubmit input").click(function(){
    	var forWhat=$("input[name=forWhat]").val();
    	var username=$(".loginBody form input[name=username]").val();
    	var password=$(".loginBody form input[name=password]").val();
    	var comfirmPassword=$(".loginBody form input[name=comfirmPassword]").val();
    	var validateCode=$(".loginBody form input[name=validateCode]").val();
    	var remenberPwd=$(".loginCheckbox").attr("isSelected");
    	if("login"===forWhat){
    		$.ajax({
    			url:"template!login.action",
    			type:"POST",
    			data:{
    				"username":username,
    				"password":password,
    				"validateCode":validateCode,
    				"remenberPwd":(remenberPwd=='true')
    			},
    			error:function(){
    				alert("请检查网络");
    			},
    			success:function(data){
    				console.log(data);
    				var json=JSON.parse(data);
    				console.log(json);
    				if(json["isSuccess"]==="true"){
    					window.location.reload();
    				}else{
    					if(json["username"]!=="验证成功"){
    						$("input[name=username]").parent().find(".inputMessage").css("color","#e84048");
    					}else{
    						$("input[name=username]").parent().find(".inputMessage").css("color","#86ce2f");
    					}
    					if(json["validateCode"]!=="验证成功"){
    						$("input[name=validateCode]").parent().find(".inputMessage").css("color","#e84048");
    					}else{
    						$("input[name=validateCode]").parent().find(".inputMessage").css("color","#86ce2f");
    					}
    					$("input[name=username]").parent().find(".inputMessage").text(json["username"]);
    					$("input[name=validateCode]").parent().find(".inputMessage").text(json["validateCode"]);
    				}
    			}
    		});

    	}else{
    		$.ajax({
			url:"template!signin.action",
			type:"POST",
			data:{
				"username":username,
				"password":password,
				"comfirmPassword":comfirmPassword,
				"validateCode":validateCode
			},
			error:function(){
				alert("请检查网络");
			},
			success:function(data){
				var json=JSON.parse(data);
				if(json["isSuccess"]==="true"){
					window.location.reload();
				}else{
					$("input[name=username]").parent().find(".inputMessage").text(json["username"]);
					$("input[name=password]").parent().find(".inputMessage").text(json["password"]);
					$("input[name=comfirmPassword]").parent().find(".inputMessage").text(json["comfirmPassword"]);
					$("input[name=validateCode]").parent().find(".inputMessage").text(json["validateCode"]);
				}
			}
		});
    		
    	}
    	return false;
    });
    $(".lisenerHeadBox").mouseover(function(e){
    	if(isWrapElement(e,this)){
    		
    		$(".listenerInfoBox").stop(true,true)
    			.css("display","block")
    			.animate({
    				height:"205px"
    			},'fast');
    	}
    });
    $(".lisenerHeadBox").mouseout(function(e){
    	if(isWrapElement(e,this)){
    		window.setTimeout(function(){
    			
    			$(".listenerInfoBox").stop(true,true)
		    		.animate({
						height:"0px"
					},'fast',function(){
						$(".listenerInfoBox").css("display","none");
					});
    		}, 300);
    		
    	}
    });
    
    
});
