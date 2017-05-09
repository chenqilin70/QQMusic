$(function(){
	var img=$(".playerBg").css("background-image");
	img=img.substring(img.indexOf("\"")+1,img.lastIndexOf("\""));
	RGBaster.colors(img, {
	  success: function(payload) {
		  var dominant=payload.dominant;
		  var rgb=getRgbArr(dominant);
		  if(isTint(rgb) || isDazzling(rgb)){
			  $(".bgMask").css("background-color","rgba(60,60,60,0.4)");
		  }else{
			  $(".bgMask").css("background-color","");
		  }
	    $("body").css("background-color",dominant);
	  }
	});
	function getRgbArr(rgbStr){
		var strArr=rgbStr.substring(rgbStr.indexOf("(")+1,rgbStr.lastIndexOf(")")).split(",");
		return new Array(Number(strArr[0]),Number(strArr[1]),Number(strArr[2]));
	}
	function isTint(rgb){
		if(rgb[0]>230 && rgb[1]>230 && rgb[2]>230){
			return true;
		}else{
			return false;
		}
	}
	function isDazzling(rgb){
		var r=rgb[0];
		var g=rgb[1];
		var b=rgb[2];
		if(Math.abs(r-g)>140 || Math.abs(r-b)>140 || Math.abs(b-g)>140){
			return true;
		}else{
			return false;
		}
	}
});