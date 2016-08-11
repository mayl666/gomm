// 显示提示信息
function pop_up(message,bool,time){
	if(bool){//成功
		$("#loading p").removeClass().addClass("loading-success-p loading-error-p").html("<i class='fa fa-smile-o'></i>" + message);
	} else {//失败
		$("#loading p").removeClass().addClass("loading-error-p").html("<i class='fa fa-smile-o'></i>" + message);
	}
	if(!time){
		time = 1000;
	}
	show_notify(time);
}

function show_notify(time){
   $(".loading-mask").show();
   $("#loading").show();
    setTimeout("atuohide()",time);//2秒，可以改动
}

function atuohide(){
    $(".loading-mask").fadeOut("slow");
    $("#loading").fadeOut("slow");
}