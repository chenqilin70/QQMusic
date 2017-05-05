$(function(){
	$(".musicPlace , .albumNavItem").attr("selectedItem","true");
	$(".musicPlace").css("background-color","#31C27C").css("color","#fff");
	$(".albumNavItem").css("color","#31C27C");
	
	//初始化条件
	var allText=$("#allText").val();
	var categoryText=$(".foldingItem[optionClass='category']").text();
	var yearsText=$(".foldingItem[optionClass='years']").text();
	var albumCompanyText=$(".foldingItem[optionClass='albumCompany']").text();
	changeCondition(getHashJson());
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
	$(".haveSelectedItem i").mouseover(function(e){
		$(this).css("background-color","#69cb9a");
	});
	$(".haveSelectedItem i").mouseout(function(e){
		$(this).css("background-color","");
	});
	$(".haveSelectedItem").mouseover(function(e){
		if(isWrapElement(e,this)){
			$(this).css("color","#ffffff")
					.css("background-color","#31c27c")
					.css("border-color","#31c27c");
			$(this).find("i").css("background-position","-160px -80px");
		}
	});
	$(".haveSelectedItem").mouseout(function(e){
		if(isWrapElement(e,this)){
			$(this).css("color","#333")
					.css("background-color","")
					.css("border-color","#c9c9c9");
			$(this).find("i").css("background-position","-160px -120px");
		}
	});
	$(".haveSelectedItem i").click(function(e){
		var optionClass=$(this).parent().attr("category");
		$(getOptionItemByText(allText,optionClass)).trigger("click");
		$(this).parent().css("display","none");
	});
	function getHashJson(){
		var hash=window.location.hash;
		hash=hash.replace(/#/, "");
		if(hash=="" || hash==undefined || hash==null){
			return JSON.parse("{}");
		}
		//以&分割
		var hashArr=hash.split("%26");
		var jsonStr="{";
		for(var i in hashArr){
			//以=分割
			var key=hashArr[i].split("%3D")[0];
			var val=hashArr[i].split("%3D")[1];
			jsonStr=jsonStr+"\""+unescape(key)+"\":\""+unescape(val)+"\",";
		}
		jsonStr=jsonStr.substring(0,jsonStr.lastIndexOf(","));
		jsonStr=jsonStr+"}";
		console.log("解码url后生成的json：");
		console.log(jsonStr);
		return JSON.parse(jsonStr);
	}
	function getJsonLength(json){
		var len=0;
		for(var i in json){
			len++;
		}
		return len;
	}
	function getOptionItemByText(text,optionClass){
		var result;
		$(".defaultUl[optionClass='"+optionClass+"']")
				.find(".optionItem").each(function(index,element){
					if($(element).text()==text){
						result=element;
						return false;
					}
		});
		return result;
	}
	function changeHashByJson(json){
		var hashStr="";
		for(var i in json){
			hashStr=hashStr+i+"="+json[i]+"&";
		}
		hashStr=hashStr.substring(0, hashStr.lastIndexOf("&"));
		hashStr=escape(hashStr);
		console.log("编码后的url：");
		console.log(hashStr);
		window.location.hash=hashStr;
	}
	$(".optionItem").click(function(){
		var optionClass=$(this).parent().attr("optionClass");
		var text=$(this).text();
		var hashJson=getHashJson();
		hashJson[optionClass]=text;
		changeHashByJson(hashJson);
		selectOptionByText(text,optionClass);
	});
	function selectedItemToTrue($ele){
		$ele.attr("selectedItem","true")
			.css("color","#ffffff")
			.css("background-color","#31c27c");
		$downIcon=$ele.find(".downIcon");
		if($downIcon!=undefined){
			$downIcon.css("background-position","-100px -260px");
		}
	}
	function selectedItemToFalse($ele){
		$ele.attr("selectedItem","false")
			.css("color","#333")
			.css("background-color","");
		$downIcon=$ele.find(".downIcon");
		if($downIcon!=undefined){
			$downIcon.css("background-position","-120px -260px");
		}
	}
	//处理选择条件后的显示层
	function selectOptionByText(text,optionClass){
		var element=getOptionItemByText(text,optionClass);
		//此处加判断，否则Firefox等浏览器无法使用
		if(element==undefined){
			return ;
		}
		selectedItemToFalse($(element).parent().find(".optionItem"));
		selectedItemToTrue($(element));
		//处理下方选中标签
		if(text!==allText){
			$(".haveSelectedItem[category='"+optionClass+"']")
				.css("display","block").find("span").text(text);
		}else{
			$(".haveSelectedItem[category='"+optionClass+"']")
				.css("display","none").find("span").text(text);
		}
		selectiveShowAllAlbumText();
		//如果在可折叠式的选项卡中进行操作
		var flag=element.compareDocumentPosition($(".filtrateBox")[0]);
		if(flag==10){
			var optClazz=$(element).parent().attr("optionClass");
			$(".foldingUl li[optionClass='"+optClazz+"']").trigger("click");
		}
		
	}
	//有选择性的展示“全部专辑”
	function selectiveShowAllAlbumText(){
		var displayVal="block";
		$(".haveSelectedItem").each(function(){
			if($(this).css("display")=="block"){
				displayVal="none";
				return false;
			}
		});
		$(".allALbumText").css("display",displayVal);
	}
	
	//初始化条件
	function changeCondition(conditionJson){
		selectedItemToFalse($(".optionItem"));
		selectOptionByText(conditionJson.language==undefined?allText:conditionJson.language,"language");
		selectOptionByText(conditionJson.genres==undefined?allText:conditionJson.genres,"genres");
		selectOptionByText(conditionJson.price==undefined?allText:conditionJson.price,"price");
		if(conditionJson.category==undefined || conditionJson.category==allText){
			selectOptionByText(allText,"category");
		}else{
			selectOptionByText(conditionJson.category,"category");
			selectedItemToTrue($(".foldingItem[optionClass='category']"));
			$(".foldingItem[optionClass='category'] span").text(conditionJson.category);
		}
		if(conditionJson.years==undefined || conditionJson.years==allText){
			selectOptionByText(allText,"years");
		}else{
			selectOptionByText(conditionJson.years,"years");
			selectedItemToTrue($(".foldingItem[optionClass='years']"));
			$(".foldingItem[optionClass='years'] span").text(conditionJson.years);
		}
		if(conditionJson.albumCompany==undefined || conditionJson.albumCompany==allText){
			selectOptionByText(allText,"albumCompany");
		}else{
			selectOptionByText(conditionJson.albumCompany,"albumCompany");
			selectedItemToTrue($(".foldingItem[optionClass='albumCompany']"));
			$(".foldingItem[optionClass='albumCompany'] span").text(conditionJson.albumCompany);
		}
	}
	function unfoldFun(){
		var optionClass=$(this).attr("optionClass");
		//关闭已经展开的选项卡
		$(".unfoldBox").each(function(){
			if($(this).css("display")=="block"){
				var optClazz=$(this).attr("optionClass");
				$(".foldingItem[optionClass='"+optClazz+"']").trigger("click");
			}
		});
		
		$(".unfoldBox[optionClass='"+optionClass+"']").css("display","block");
		
		selectedItemToTrue($(this));
		
		$(this).one("click",foldFun);
	}
	function foldFun(){
		$(".unfoldBox").css("display","none");
		
		var selectedText=$(".defaultUl[optionClass='"+$(this).attr("optionClass")+"'] li[selectedItem='true']").text();
		if(selectedText==allText){
			selectedItemToFalse($(this));
		}else{
			selectedItemToTrue($(this));
		}
		
		$(this).one("click",unfoldFun);
	}
	$(".foldingItem").one("click",unfoldFun);
	var isFireFox= window.navigator.userAgent.indexOf("FireFox")?true:false;
	$("body").one("click",function(e){
		bodyClickFun(e);
	});
	function bodyClickFun(e){
		 var targetEle=isFireFox?e.target:event.srcElement;
		 var flag=targetEle.compareDocumentPosition($(".filtrateBox")[0]);
		 //如不在Item的容器内
		 if(flag!=10){
			 var $this;
			 $(".unfoldBox").each(function(){
				 if($(this).css("display")=="block"){
					 $this=$(this);
					 $(".foldingItem[optionClass='"+$this.attr("optionClass")+"']").trigger("click");
				 }
			 });
		 }
		 $("body").one("click",function(e){
				bodyClickFun(e);
		});
	}
	
	//用于改变筛选Item的Text
	$(".unfoldBox .optionItem").click(function(){
		var selectedText=$(this).text();
		var $span=$(".foldingItem[optionClass='"+$(this).parent().attr("optionClass")+"'] span");
		if(selectedText!==allText){
			$span.text(selectedText);
		}else{
			var optClazz=$span.parent().attr("optionClass");
			if(optClazz=="category"){
				$span.text(categoryText);
			}else if(optClazz=="years"){
				$span.text(yearsText);
			}else if(optClazz=="albumCompany"){
				$span.text(albumCompanyText);
			}
		}
	});
	//点击条件后刷新数据
	$(".optionItem").click(function(){
		updateAlbumList(1);
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
	updateAlbumList(1);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});