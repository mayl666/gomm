$(function(){
	
/*	$("#imgurl").bind("input propertychange",function(){
		var imgurl=$("#imgurl").val();
		alert(imgurl);
		if(imgurl != ""){
			$(".up_image").find("span").hide();
		}
	});
	
	$('#imgurl').bind('input propertychange', function() {
		var imgurl=$("#imgurl").val();
		alert(imgurl);
	});
	
	*/

	
    $.formValidator.initConfig({theme:"baidu",mode:"AutoTip",submitOnce:true,formID:"editMyAccount",
        submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
    });
	
    $("#realName").formValidator({
        onEmpty:"请输入4~16位的真实姓名",
        onFocus:"请输入真实姓名",
        tipCss:{height:36,width:320}}).inputValidator({min:4,max:16,onError:"你输入的帐号名长度不正确,请确认"});
    //联系方式
    $("#contactWay").formValidator({
		onEmpty : "请输入您的联系方式",
		onFocus : "请输入您的联系方式",
		tipCss : {
			height : 36,
			width : 320
		}
	}).inputValidator({
		min : 4,
		max : 16,
		onError : "你输入的联系方式不正确,请确认"
	}).functionValidator({
		fun : checkContactWay
	});
	
/*   $("#imgurl").formValidator({
		tipCss : {
			height : 36,
			width : 320
		}
	}).functionValidator({
		fun : function(){
			if($("#imgurl").val() == ""){
				$(".up_image").find("span").show().text("头像不能为空");
				return false;
			}
			return true;
		}
	});*/

	
	 $.formValidator.reloadAutoTip();

	 
	 
	$("#savebtn").click(function(event){
		event.preventDefault();
		//	var resu=$.formValidator.isOneValid("roleForm")
		if(!$.formValidator.pageIsValid('1')){
			return;
		}
		var tmp = new Date();
		var content = {};
		content.realName=$("#realName").val();//真实名称
		content.contactWay=$("#contactWay").val();
		content.headPath=$("#imgurl").val();
/*		if($("#imgurl").val()==""){
			//alert("头像不能为空");
			$(".up_image").find("span").show().text("头像不能为空");
			return;
		}*/
		//console.info(JSON.stringify(content));
		$.ajax({
    		url:'../manager/ajax/user/editMyAccount',
    		type:'POST',
			dataType : 'json',
			async:true,
    		data:{'content':JSON.stringify(content),'tmp':tmp},
    		success:function(data){
    			if(data.code==1){
    				//alert("修改成功！");
    				window.location.href="../index/toIndex";
    			}else{
    				alert("修改失败！");
    			}
    		},
    		error:function(e){
    			alert("操作失败");
    		}
    		
    	});
	});

}) 



function hideHeadPathCheckError(){
	$(".up_image").find("span").hide();
	
}


function checkContactWay(){
	var contacWay=$("#contactWay").val();
	var mobilefilter = /^0?1[3|4|5|8][0-9]\d{8}$/;
	var emailfilter  = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	if (!mobilefilter.test(contacWay) && !emailfilter.test(contacWay)) {
	     return  "联系方式格式不正确";
	} 
}


//function iframeClick(){
//		$("#img").contents().find("#file").click();
//	}
// 
// 
////图片上传
// function upload() {
// 	if (!checkImg("file")) {
// 		divshow("图片格式错误！图片格式为jpg,png,gif,bmp");
// 		$("#mod-dialog").height(185);
//   		$("#altbtn").click(function(){
// 			 $("#mod-dialog").hide();
// 			 $("#mod-dialog-bg").hide();
// 		});
// 		return;
// 	}
// 	$("#img").contents().find("#imgform").submit();
// 	if($("#oneImage").attr("src")!=null){
// 	    divshow("上传成功");
// 		$("#altbtn").click(function(){
// 			 $("#mod-dialog").hide();
// 			 $("#mod-dialog-bg").hide();
// 		});
// 	}else{
// 		$("#mod-dialog").css({"top":"100%"});
// 		divshow("上传失败");
// 		$("#altbtn").click(function(){
// 			 $("#mod-dialog").hide();
// 			 $("#mod-dialog-bg").hide();
// 		});	
// 	}
// }
// 
// 
////检查文件格式
// function checkImg(fileId) {
// 	// $("#imgMsg").text('');//清空错误信息
// 	var fileName = $("#img").contents().find("#file").val(); // 文件名称
// 	fileType = [ "jpg", "png", "gif", "bmp" ]; // 图片类型
// 	fileExt = ""; // 图片拓展名
// 	fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
// 	for ( var i in fileType) {
// 		if (fileExt == fileType[i]) {
// 			return true;
// 		}
// 	}
// 	return false;
// }
//
// function divshow(msg){
// 	var divId=document.getElementById("mod-dialog");     
//     divId.style.top = (document.documentElement.scrollTop + (document.documentElement.clientHeight - divId.offsetHeight) / 2.5) + "px";
//     divId.style.left = (document.documentElement.scrollLeft + (document.documentElement.clientWidth - divId.offsetWidth) / 2.5) + "px";
// 	$("#tishi").text(msg);
// 	$("#mod-dialog").show();
// 	$("#mod-dialog-bg").show();	
// 	
// }
 