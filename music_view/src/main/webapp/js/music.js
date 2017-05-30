$(function(){
	$(".musicPlace").attr("selectedItem","true").css("background-color","#31C27C").css("color","#fff");
    /*判断是否为最外面的元素被移出了*/
   function isWrapElement(e, thisElement) {
       $relatedElement = e.relatedTarget;
       if ($relatedElement == null) {
           return true;
       }
       var flag = thisElement.compareDocumentPosition($relatedElement);
       return !(flag == 0 || flag == 20);
   }
   $("#lyricMainBody")[0].innerHTML=$("input[name='lyric']").val();
   var boxHeight=Number($(".lyricBox").css("height").replace("px",""));
   $(".bottomBox").css("height",(boxHeight+52.2)+"px");
   function linkStyleRegister(mouseoverStyle,mouseoutStyle){
	   this.mouseover(function(e){
		   if(isWrapElement(e,this)){
			   $(this).css(mouseoverStyle);
		   }
	   });
	   this.mouseout(function(e){
		   if(isWrapElement(e,this)){
			   $(this).css(mouseoutStyle);
		   }
	   });
   }
   linkStyleRegister.call($(".mvTextLink,.moreInfo"), {color:'#31C27C'},{color:'#333'});
   linkStyleRegister.call($(".playBtn"), {backgroundColor:'#2CAF6F'},{backgroundColor:'#31C27C'});
   linkStyleRegister.call($(".collectBtn,.moreBtn"), {backgroundColor:'#EDEDED'},{backgroundColor:'#ffffff'});
   function linkActionRegister(mouseoverFun,mouseoutFun){
	   this.mouseover(function(e){
		   if(isWrapElement(e, this)){
			   mouseoverFun(this);
		   }
	   });
	   this.mouseout(function(e){
		   if(isWrapElement(e, this)){
			   mouseoutFun(this);
		   }
	   });;
   }
   linkActionRegister.call($(".mvHeadBox"), function(element){
	   var $headImg=$(element).find(".mvHead");
	   var $cover=$(element).find(".playCover");
	   $headImg.stop(true,true);
	   $cover.stop(true,true);
	   var imgHeight=Number($headImg.css("height").replace("px",""));
	   var imgWidth=Number($headImg.css("width").replace("px",""));
	   $headImg.animate({
		   top:"-8px",
		   left:"-8px",
		   height:(imgHeight+16)+"px",
		   width:(imgWidth+16)+"px"
	   },500);
	   $cover.css("display",'block').animate({
		   	height: "70px",
			width:"70px",
			left:"110px",
			top:"46.844px",
			opacity:"1"
	   },500);
   },function(element){
	   var $headImg=$(element).find(".mvHead");
	   var $cover=$(element).find(".playCover");
	   $headImg.stop(true,true);
	   $cover.stop(true,true);
	   var imgHeight=Number($headImg.css("height").replace("px",""));
	   var imgWidth=Number($headImg.css("width").replace("px",""));
	   $headImg.animate({
		   top:"0",
		   left:"0",
		   height:(imgHeight-16)+"px",
		   width:(imgWidth-16)+"px"
	   },500);
	   $cover.animate({
		   height: "49px",
			width:"49px",
			left:"120.5px",
			top:"57.344px",
			opacity:"0"
	   },500,function(){
		   $cover.css("display","none");
	   });
   });
   
   var desc=$(".descValue").attr("value");
   $(".briefIntroText")[0].innerHTML=desc.replace(/<br>/,"");
   $(".descContent")[0].innerHTML=desc;
   
   var height=460;
   if(Number($(".otherAlbumDescBox").css("height").replace(/px/,""))>height){
       $(".otherAlbumDescBox").css("height",height+"px");
       $(".descContent").css("height",(height-87-32)+"px");
   }
   var isFireFox= window.navigator.userAgent.indexOf("FireFox")?true:false;
   $("body").click(function(e){
       var relatedElement =isFireFox?event.target:event.srcElement;
       var flag=$(".otherAlbumDescBox")[0].compareDocumentPosition(relatedElement);
       if($(relatedElement).attr("class")=='moreInfo'){
           $(".otherAlbumDescBox").css("display","block");
           return;
       }
       if(flag!=0 && flag!=20){
           $(".otherAlbumDescBox").css("display","none");
       }
   });

});