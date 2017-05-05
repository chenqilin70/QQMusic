$(function(){
	$(".musicPlace , .indexNavItem").attr("selectedItem","true");
	$(".musicPlace").css("background-color","#31C27C").css("color","#fff");
	$(".indexNavItem").css("color","#31C27C");
	
     /*判断是否为最外面的元素被移出了*/
    function isWrapElement(e, thisElement) {
        $relatedElement = e.relatedTarget;
        if ($relatedElement == null) {
            return true;
        }
        var flag = thisElement.compareDocumentPosition($relatedElement);
        return !(flag == 0 || flag == 20);
    }
    $(".district li[selectedItem=false]").mouseover(function(){
        $(this).attr("selectedItem","true");
    });
    $(".district li[selectedItem=false]").mouseout(function(){
        $(this).attr("selectedItem","false");
    });
    
    
    $(".toAll").mouseover(function(){
        $(".allDistrict").css("color","#31C27C");
        $(".allDistrictIcon").css("background-position","-116px -58px");
    });
     $(".toAll").mouseout(function(){
        $(".allDistrict").css("color","#ffffff");
        $(".allDistrictIcon").css("background-position","-36px -258px");
    });
    
    function musicMaskOver($this){
        $this.css("background-color","#31C27C");
            $this.find(".singerName").css("color","#C1E9D5");
            $(".moreIcon").stop(true,true);
            $this.find("i").animate({
                opacity:'1'
            },'fast');
    }
    $(".musicMask").mouseover(function(e){
        var $this=$(this);
        if(isWrapElement(e,this)){
            musicMaskOver($this);
        }
    });
    function musicMaskOut($this){
    	if($this.attr("class")==undefined){
    		return;
    	}
        if($this.attr("class").indexOf("singularMask")==-1){
            $this.css("background-color","rgba(0,0,0,0.8)");
        }else{
            $this.css("background-color","rgba(0,0,0,0.6)");
        }
        $this.find(".singerName").css("color","#999999");
         $(".moreIcon").stop(true,true);
        $this.find("i").animate({
            opacity:'0'
        },'fast');
    }
    $(".musicMask").mouseout(function(e){
        var $this=$(this);
        if(isWrapElement(e,this) && ($(e.relatedTarget).attr("class")==undefined  || $(e.relatedTarget).attr("class").indexOf("imgMask")==-1)){
            musicMaskOut($this);
        }
    });
    $(".contralWidth").mouseover(function(e){
        if(isWrapElement(e,this)){
            $(".imgMask").stop(true,true);
            $(".cover_play").stop(true,true);
            $(this).find(".albumImgInNewMusic").stop(true,false);
            $(this).find(".imgMask").animate({
                opacity:'.5'
            });
            $(this).find(".cover_play").animate({
                top: '115px',
                left: '115px',
                height:'70px',
                opacity:'1'
            });
            var albumId=$(this).attr("albumId");
            var $targetMusicMask=  $(".musicMask[albumId="+albumId+"]");
            musicMaskOver($targetMusicMask);
            $(this).find(".albumImgInNewMusic").animate({
                top:'-10px',
                left:'-10px',
                height:'320px',
                width:'320px'
            },'fast');
        }
    });
    $(".contralWidth").mouseout(function(e){
        if(isWrapElement(e,this)){
            $(".imgMask").stop(true,true);
            $(".cover_play").stop(true,true);
            $(this).find(".albumImgInNewMusic").stop(true,false);
            $(this).find(".imgMask").animate({
                opacity:'0'
            });
            $(this).find(".cover_play").animate({
                top: '125px',
                left: '125px',
                height:'50px',
                opacity:'0'
            });
            if($(e.relatedTarget).attr("class")!=undefined && $(e.relatedTarget).attr("class").indexOf("musicMask")==-1){
                var albumId=$(this).attr("albumId");
                var $targetMusicMask=  $(".musicMask[albumId="+albumId+"]");
                musicMaskOut($targetMusicMask);
            }
             
            $(this).find(".albumImgInNewMusic").animate({
                top:'0',
                left:'0',
                height:'300px',
                width:'300px'
            },'fast');
        }
    });
    
    function active(e,element){
        if(isWrapElement(e, element)){
            var $btn=$(element).find(".slideBtn");
            $btn.stop(true,false);
           $btn.animate({
                width:'80px'
            });
            $btn.css("animation" ,"active 1s").css("background","rgb(49,194,124)");
        }
    }
    
    function shadow(e,element){
        if(isWrapElement(e, element)){
            var $btn=$(element).find(".slideBtn");
            $btn.stop(true,true);
            $btn.animate({
                width:'70px'
            });
            $btn.css("animation" ,"shadow 1s").css("background","rgba(255,255,255,0.3)");
        }
    }
    $(".left , .right").mouseover(function(e){
        active(e,this);
    });
    $(".left , .right").mouseout(function(e){
        shadow(e,this);
    });
    $(".newMusic").mouseout(function(e){
        var thisElement=this;
            if(isWrapElement(e, thisElement)){
                $(".slideBtn").stop(true,true);
                $(".toLeft").animate({
                    left:'-72px'
                },'fast');
                $(".toRight").animate({
                    right:'-72px'
                },'fast');
              }
        
    });
    $(".newMusic").mouseover(function(e){
        var thisElement=this;
            if(isWrapElement(e, thisElement)){
                $(".slideBtn").stop(true,true);
                $(".toLeft").animate({
                    left:'0px'
                },'fast');
                $(".toRight").animate({
                    right:'0px'
                },'fast');
            }

    });
    function changeTab(toWhere){
        $('.newMusicTab li[selectedItem=true]').find("div").css("background-color","rgba(255,255,255,0.5)");
        var $target=eval("$('.newMusicTab li[selectedItem=true]').attr('selectedItem','false')."+toWhere+"('.newMusicTab li')");
        if($target.length==0 && toWhere=='next'){
            $(".newMusicTab li:first").attr("selectedItem","true").find("div").css("background-color","rgb(255,255,255)");
        }else if($target.length==0 && toWhere=='prev'){
            $(".newMusicTab li:last").attr("selectedItem","true").find("div").css("background-color","rgb(255,255,255)");
        }else{
            $target.attr("selectedItem","true").find("div").css("background-color","rgb(255,255,255)");
        }
    }
    //移动推荐专辑
    function moveTable(offset){
        if(offset>0){
            $(".toLeft").unbind();
        }else{
            $(".toRight").unbind();
        }
        $table=$(".showNewMusicTable");
        var now=Number($table.css("left").replace(/px/,""));
        if(offset<0 && now==-4800){
            now=0;
            $table.css("left",now+"px");
        }
        if(offset>0 && now==-1200){
            now=-6000;
            $table.css("left",now+"px");
        }
        var to=(now+offset)+"px";
        $table.animate({
            left:to
        },800,registerClick);
    }
    
    function registerClick(){
        $(".toRight").unbind().click(function(e){
                moveTable(-1200);
                changeTab("next");
          });
         $(".toLeft").unbind().click(function(e){
            moveTable(1200);
            changeTab("prev");
        });
        $(".newMusicTab li").unbind().click(function(){
            $(".newMusicTab li").unbind();
            tabTrigger(this);
        });
    }
    registerClick();
    function tabTrigger(thisElement){
        $current=$(".newMusicTab li[selectedItem=true]");
        if($(thisElement).attr("selectedItem")=='false'){
            var offset=($(thisElement).index()-$current.index())*1200;
            moveTable(-offset);
            $current.attr("selectedItem","false").find("div").css("background-color","rgba(255,255,255,0.5)");
            $(thisElement).attr("selectedItem","true").find("div").css("background-color","rgb(255,255,255)");
        }
    }
    var nowPicIndex=1;
    changePicWithoutAnimate(2);
    function changePic(index){
        console.log(index);
        nowPicIndex=index;
        var picLeft=0-303.75*(index-1);
        $(".hotRecomSinglePic").each(function(i,thisElement){
         if(i===index){
              $(thisElement).animate({
                    height:'300px',
                    width:'750px',
                    left:(picLeft-71.25)+"px",
                    top:121.5+"px",
                    opacity:'1'
                },480);
         }else if(i===(index-1) || i===(index+1)){
             $(thisElement).animate({
                    height:'243px',
                    width:'607.5px',
                    left:picLeft+"px",
                    top:150+"px",
                    opacity:'0.1'
                },500,function(){
                    $(thisElement).css("opacity","1");
                });
         }else{
             $(thisElement).animate({
                    height:'243px',
                    width:'607.5px',
                    left:picLeft+"px",
                    top:150+"px",
                    opacity:'0.2'
                },500);
         }
         window.setTimeout(function(){
             if(i===(index-1) || i===(index+1)){
                $(thisElement).css("z-index","2");
            }else if(i===index){
                $(thisElement).css("z-index","3");
            }else{
                $(thisElement).css("z-index","1");
            }
            
         },500);
        
        picLeft+=303.75;
        var t;
        t=window.setInterval(function(){
            if((!$(thisElement).is(":animated")) && index===12){ 
                changePicWithoutAnimate(2);
                $(".hotRecomTab li").attr("selectedItem","false").find("div").css("background-color","rgb(185,185,185)");
               $(".hotRecomTab li:first").attr("selectedItem","true").find("div").css("background-color","rgb(0,0,0)");
                window.clearInterval(t);
            }else if((!$(thisElement).is(":animated")) && index===1){
                changePicWithoutAnimate(11);
                $(".hotRecomTab li").attr("selectedItem","false").find("div").css("background-color","rgb(185,185,185)");
                $(".hotRecomTab li:last").attr("selectedItem","true").find("div").css("background-color","rgb(0,0,0)");
                window.clearInterval(t);
            }else{
                $(".hotRecomTab li").attr("selectedItem","false").find("div").css("background-color","rgb(185,185,185)");
                $($(".hotRecomTab li")[nowPicIndex-2]).attr("selectedItem","true").find("div").css("background-color","rgb(0,0,0)");
            }
        },100);
        
    });
    window.setTimeout(function(){
            $(".hotRecomLeftBtn").unbind("click").one("click",function(){
                nowPicIndex-=1;
                changePic(nowPicIndex);
            });
            $(".hotRecomRightBtn").unbind("click").one('click',function(){
                nowPicIndex+=1;
                changePic(nowPicIndex);
            });
    },500);
    }
    function changePicWithoutAnimate(index){
        nowPicIndex=index;
        var picLeft=0-303.75*(index-1);
        $(".hotRecomSinglePic").each(function(i,thisElement){
         if(i===index){
              $(thisElement).css({
                    height:'300px',
                    width:'750px',
                    left:(picLeft-71.25)+"px",
                    top:121.5+"px",
                    opacity:'1',
                    zIndex:'3'
                });
         }else if(i===(index-1) || i===(index+1)){
             $(thisElement).css({
                    height:'243px',
                    width:'607.5px',
                    left:picLeft+"px",
                    top:150+"px",
                    opacity:'1',
                    zIndex:'2'
                });
         }else{
             $(thisElement).css({
                    height:'243px',
                    width:'607.5px',
                    left:picLeft+"px",
                    top:150+"px",
                    opacity:'1',
                    zIndex:'1'
                });
         }
        picLeft+=303.75;
    });

    }
    $(".hotRecomLeftBtn").one("click",function(){
        nowPicIndex-=1;
        changePic(nowPicIndex);
    });
    $(".hotRecomRightBtn").one('click',function(){
        nowPicIndex+=1;
        changePic(nowPicIndex);
    });
    $(".hotRecomTab li").click(function(){
        nowPicIndex=$(this).index()+2;
        $(".hotRecomTab li").attr("selectedItem","false");
        $(this).attr("selectedItem","true");
        changePic(nowPicIndex);
    });
    
    function activeSlideBtn(e,element){
        var $btn=$(element);
        if(isWrapElement(e, element)){
            $btn.stop(true,false);
           $btn.animate({
                width:'80px'
            });
            $btn.css("animation" ,"activeSlideBtn 1s").css("background","rgb(49,194,124)");
        }
    }
    function shadowSlideBtn(e,element){
        var $btn=$(element);
        if(isWrapElement(e, element)){
            $btn.stop(true,true);
            $btn.animate({
                width:'70px'
            });
            $btn.css("animation" ,"shadowSlideBtn 1s").css("background","rgb(214,214,214)");
        }
    }
    
   var isFireFox= window.navigator.userAgent.indexOf("FireFox")?true:false;
   var witchIsActive1="none";
    $(".hotRecomRightBtn").parent().mousemove(function(e){
        var $this=$(this);
        var targetEle=isFireFox?event.target:event.srcElement;
        if($(targetEle).attr("isRetain") !=undefined && $(targetEle).attr("isRetain")=='true'){
            var halfWidth=Number($this.css("width").replace(/px/,""))/2;
           if(e.offsetX<halfWidth){
               if("hotRecomLeftBtn"!==witchIsActive1){
                   witchIsActive1="hotRecomLeftBtn";
                   activeSlideBtn(e,$this.find(".leftBtn")[0]);
               }
           }else{
                if("hotRecomRightBtn"!==witchIsActive1){
                   witchIsActive1="hotRecomRightBtn";
                   activeSlideBtn(e,$this.find(".rightBtn")[0]);
               }
           }
        }else{
            if(witchIsActive1!=="none"){
                witchIsActive1="none";
                shadowSlideBtn(e,$this.find(".hotRecomRightBtn")[0]);
                shadowSlideBtn(e,$this.find(".hotRecomLeftBtn")[0]);
            }
        }
    });
    $(".hotRecomRightBtn").parent().mouseout(function(e){
        if(isWrapElement(e, this)){
            witchIsActive1="none";
            shadowSlideBtn(e,$(this).find(".hotRecomRightBtn")[0]);
            shadowSlideBtn(e,$(this).find(".hotRecomLeftBtn")[0]);
        }
    });
    
    var witchIsActive2="none";
    $(".hotMusicLeftBtn").parent().mousemove(function(e){
        var $this=$(this);
        var targetEle=isFireFox?event.target:event.srcElement;
        if($(targetEle).attr("isRetain") !=undefined && $(targetEle).attr("isRetain")=='true'){
            var halfWidth=Number($this.css("width").replace(/px/,""))/2;
           if(e.offsetX<halfWidth){
               if("hotMusicLeftBtn"!==witchIsActive2){
                   witchIsActive2="hotRecomLeftBtn";
                   activeSlideBtn(e,$this.find(".leftBtn")[0]);
               }
           }else{
                if("hotMusicRightBtn"!==witchIsActive2){
                   witchIsActive2="hotMusicRightBtn";
                   activeSlideBtn(e,$this.find(".rightBtn")[0]);
               }
           }
        }else{
            if(witchIsActive2!=="none"){
                witchIsActive2="none";
                shadowSlideBtn(e,$this.find(".hotMusicRightBtn")[0]);
                shadowSlideBtn(e,$this.find(".hotMusicLeftBtn")[0]);
            }
        }
    });
    $(".hotMusicLeftBtn").parent().mouseout(function(e){
        if(isWrapElement(e, this)){
            witchIsActive2="none";
            shadowSlideBtn(e,$(this).find(".hotMusicRightBtn")[0]);
            shadowSlideBtn(e,$(this).find(".hotMusicLeftBtn")[0]);
        }
    });
    
    //将推荐的专辑还原到最初状态
    function restoreTable(){
         $(".showNewMusicTable").css("left","-1200px");
         $(".newMusicTab li").attr("selectedItem","false").find("div").css("background-color","rgb(185,185,185)");
         $(".newMusicTab li:first").attr("selectedItem","true").find("div").css("background-color","rgb(255,255,255)");
    }
    function updateNewMuic(json){
//    	console.log(json[i].albumId+","+json[i].albumName);
//		console.log(json[i].singer.singerId+","+json[i].singer.singerName);
    	for(var i=12;i<15;i++){
    		//修改图片
    		var $img=$($(".mostLeft").get(i-12));
    		var $td=$img.parent().parent();
    		var oldId=$td.attr("albumId");
    		var oldSrc=$img.attr("src");
    		$img.attr("src","");
    		$img.attr("src",oldSrc.replace(oldId,json[i].albumId));
    		$td.attr("albumId",json[i].albumId);
    		//修改文字
    		var $mask=$($("[maskPos=leftMask]").get(i-12));
    		$mask.attr("albumId",json[i].albumId);
    		$mask.find(".musicName").text(json[i].albumName);
    		$mask.find(".singerName").text(json[i].singer.singerName);
    		//修改超链接
    		$mask.find(".musicName").parent().attr("href","/music_view/album.action?albumId="+json[i].albumId);
    	}
    	for(var i=0;i<15;i++){
    		//修改图片
    		var $img=$($(".mostCenter").get(i));
    		var $td=$img.parent().parent();
    		var oldId=$td.attr("albumId");
    		var oldSrc=$img.attr("src");
    		$img.attr("src","");
    		$img.attr("src",oldSrc.replace(oldId,json[i].albumId));
    		$td.attr("albumId",json[i].albumId);
    		//修改文字
    		var $mask=$($("[maskPos=centerMask]").get(i));
    		$mask.attr("albumId",json[i].albumId);
    		$mask.find(".musicName").text(json[i].albumName);
    		$mask.find(".singerName").text(json[i].singer.singerName);
    		//修改超链接
    		$mask.find(".musicName").parent().attr("href","/music_view/album.action?albumId="+json[i].albumId);
    	}
    	for(var i=0;i<3;i++){
    		//修改图片
    		var $img=$($(".mostRight").get(i));
    		var $td=$img.parent().parent();
    		var oldId=$td.attr("albumId");
    		var oldSrc=$img.attr("src");
    		$img.attr("src","");
    		$img.attr("src",oldSrc.replace(oldId,json[i].albumId));
    		$td.attr("albumId",json[i].albumId);
    		//修改文字
    		var $mask=$($("[maskPos=rightMask]").get(i));
    		$mask.attr("albumId",json[i].albumId);
    		$mask.find(".musicName").text(json[i].albumName);
    		$mask.find(".singerName").text(json[i].singer.singerName);
    		//修改超链接
    		$mask.find(".musicName").parent().attr("href","/music_view/album.action?albumId="+json[i].albumId);
    	}
    }
    function registerDistrictLi($this){
        //修改表格内容
        $.ajax({
        	url:"ajax!updateNewMusic.action",
        	type:"GET",
        	data:{
        		"language":$this.attr("language")
        	},
        	error:function(){
        		alert("加载失败，请检查网络");
        	},
        	success:function(data){
        		//将表格还原到最初状态
                restoreTable();
                //修改地区li的状态
                $(".district li").attr("selectedItem","false").unbind("click");
                $this.attr("selectedItem","true").unbind();
                $(".district li[selectedItem=false]").click(function(){
                	registerDistrictLi($(this));
                });
                
                updateNewMuic(JSON.parse(data));
        	}
        });
    }
    $(".district li").click(function(e){
    	 registerDistrictLi($(this),e);
    });

    
    
    
    
    
    
});
