$(function(){
	
	 
	
	$("#user_submit").click(function(event){
		//alert("ddd");
		event.preventDefault();
		//	var resu=$.formValidator.isOneValid("roleForm")
		if(!$.formValidator.pageIsValid('1')){
			return;
		}
		var content = {};
		content.userName=$("#accountNumber").val();
		content.passwd=$.md5($("#accountPwd").val(), 'gome.com');
		content.roleId=$(".add_account_select").val();
		content.realName=$("#trueName").val();
		content.contactWay=$("#contactWay").val();
		content.state=$('input:radio[name=newWork_type]:checked').val();
		content.headPath=$("#imgurl").val();
		
		$.ajax({
    		url:'../manager/ajax/user/save',
    		type:'POST',
			dataType : 'json',
    		data:{content:JSON.stringify(content)},
    		success:function(data){
    			if(data.code==1){
    				alert("添加用户成功！");
    				window.location.href="../user/list";
    			}else{
    				alert("添加用户失败！");
    			}
    		},
    		error:function(){
    			alert("操作失败");
    			
    		}
    		
    	});
	
	});
	
	
	/*$("#userName").blur(function(){
		isNum($("#contactWay").val());
		checkUserName();
	});
	$("#passwd").blur(function(){
		checkPasswd();
	});
	$("#realName").blur(function(){
		checkRealName();
	});*/
	
//	$("#contactWay").blur(function(){
//		checkContactWay();
//	});
	
	
	
	
	

	
	
})

function getCurrentTime(){
	var date = new Date();
	return date.getYear()+"."+date.get
}


 function checkUserName(){
	 var userName = $("#userName").val();
		if(userName.length<4 || userName.length > 16){
			alert("账号长度不合法");
		}
 }
 
 function checkPasswd(){
	 var passwd = $("#passwd").val();
		if(passwd.length<6 || passwd.length > 16){
			alert("账号长度不合法");
		}
 }
 
 function checkRealName(){
	 var realName = $("#realName").val();
		if(realName.length<4 || realName.length > 16){
			alert("真实名称长度不合法");
		}
 }
 
 
 function checkContactWay(){
	 var contactWay = $("#contactWay").val();
	 if(!isNum(contactWay) && !isEmail(contactWay)){
		 alert("联系方式不合法");
	 }
		
 }
 
 
//电话号码验证
 function isNum(obj){
   var reg=/^1[0-9]{10}/;
   if(!reg.test(obj)){
     alert("请正确填写手机号！");
	   return false;
   }else{
	   return true;
   }
 }
    
 //验证邮件格式
 function isEmail(obj){
   var reg=/[a-zA-Z0-9]{1,10}@[a-zA-Z0-9]{1,5}\.[a-zA-Z0-9]{1,5}/;
   if(!reg.test(obj)){
     alert("请正确填写邮箱！");
	   return false;
   }else{
	   return true;
   }
 }
 
 
 function iframeClick(){
		$("#img").contents().find("#file").click();
	}
 
 
//图片上传
 function upload() {
 	if (!checkImg("file")) {
 		divshow("图片格式错误！图片格式为jpg,png,gif,bmp");
 		$("#mod-dialog").height(185);
   		$("#altbtn").click(function(){
 			 $("#mod-dialog").hide();
 			 $("#mod-dialog-bg").hide();
 		});
 		return;
 	}
 	$("#img").contents().find("#imgform").submit();
 	if($("#oneImage").attr("src")!=null){
 	    divshow("上传成功");
 		$("#altbtn").click(function(){
 			 $("#mod-dialog").hide();
 			 $("#mod-dialog-bg").hide();
 		});
 	}else{
 		$("#mod-dialog").css({"top":"100%"});
 		divshow("上传失败");
 		$("#altbtn").click(function(){
 			 $("#mod-dialog").hide();
 			 $("#mod-dialog-bg").hide();
 		});	
 	}
 }
 
 
//检查文件格式
 function checkImg(fileId) {
 	// $("#imgMsg").text('');//清空错误信息
 	var fileName = $("#img").contents().find("#file").val(); // 文件名称
 	fileType = [ "jpg", "png", "gif", "bmp" ]; // 图片类型
 	fileExt = ""; // 图片拓展名
 	fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
 	for ( var i in fileType) {
 		if (fileExt == fileType[i]) {
 			return true;
 		}
 	}
 	return false;
 }

 function divshow(msg){
 	var divId=document.getElementById("mod-dialog");     
     divId.style.top = (document.documentElement.scrollTop + (document.documentElement.clientHeight - divId.offsetHeight) / 2.5) + "px";
     divId.style.left = (document.documentElement.scrollLeft + (document.documentElement.clientWidth - divId.offsetWidth) / 2.5) + "px";
 	$("#tishi").text(msg);
 	$("#mod-dialog").show();
 	$("#mod-dialog-bg").show();	
 	
 }
 