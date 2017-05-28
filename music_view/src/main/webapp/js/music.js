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
   
   
});