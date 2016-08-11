$(function(){
	
//获取文本框焦点
$("#originalPwd").focus(originalPwdFocus);
$("#newPwd").focus(newPwdFocus);
$("#verifyPwd").focus(verifyPwdFocus);
//文本框失去焦点	
$("#originalPwd").blur(originalPwdBlur);	
$("#newPwd").blur(newPwdBlur);	
$("#verifyPwd").blur(verifyPwdBlur);

$("#saveBtn").click(function(event){
		
		event.preventDefault();
	// var resu=$.formValidator.isOneValid("roleForm")
		// alert($.formValidator.pageIsValid('1'));
		if(!checkSubmit()){
			return false;
		}
		
			var content={};
			content.oldPasswd=$.md5($("#originalPwd").val(), 'gome.com');
			content.passwd=$.md5($("#newPwd").val(), 'gome.com');
			content.confirmPasswd=$.md5($("#verifyPwd").val(), 'gome.com');
			$.ajax({
	    		url:'../manager/ajax/user/upPasswd',
	    		type:'POST',
				dataType : 'json',
	    		data:{content:JSON.stringify(content)},
	    		success:function(data){
	    			if(data.code==1){
	    				alert("修改成功！");
	    				window.location.href="../index/toIndex";
	    			}else{
	    				alert(data.attach);
	    				$("#originalPwd").val("");
	    			}
	    			//$('#upPasswdForm')[0].reset();
	    		},
	    		error:function(){
	    			alert("操作失败");
	    			
	    		}
	    		
	    	});
			
      });

	
});


function checkSubmit(){
	var flag=true;
	var originalPwd=$("#originalPwd").val();
	var newPwd=$("#newPwd").val();
	var verifyPwd=$("#verifyPwd").val();
	var pattern=/^[a-zA-Z0-9_]+$/;
	
	if(originalPwd=="" || originalPwd.length<6 || originalPwd.lenth>20){
		$(".old-passwd").find("span").show().text("请输入6~20位密码");
		flag=false;
	}else if(!pattern.test(originalPwd)){
		$(".old-passwd").find("span").show().text("密码格式错误(数字字母下划线)");
		flag=false;
	}
	if(newPwd=="" || newPwd.length<6 || newPwd.lenth>20){
		$(".new-passwd").find("span").show().text("请输入6~20位密码");
		flag=false;
	}else if(newPwd == originalPwd){
		$(".new-passwd").find("span").show().text("新密码和原密码相同");
		flag=false;
	}else if(!pattern.test(newPwd)){
		$(".new-passwd").find("span").show().text("密码格式错误(数字字母下划线)");
		flag=false;
	}
	
	if(verifyPwd=="" || verifyPwd.length<6 || verifyPwd.lenth>20){
		$(".confirm-passwd").find("span").show().text("请输入6~20位密码");
		flag=false;
	}else if(verifyPwd != newPwd){
		$(".confirm-passwd").find("span").show().text("确认密码和新密码不一致");
		flag=false;
	}else if(!pattern.test(verifyPwd)){
		$(".confirm-passwd").find("span").show().text("密码格式错误(数字字母下划线)");
		flag=false;
	}
	
	return flag;
	
}




function originalPwdFocus(){
	$(".old-passwd").find("span").hide().text("");
}


function originalPwdBlur(){
	var originalPwd=$("#originalPwd").val();
	var newPwd=$("#newPwd").val();
	var pattern=/^[a-zA-Z0-9_]+$/;
	if(originalPwd=="" || originalPwd.length<6 || originalPwd.lenth>20){
		$(".old-passwd").find("span").show().text("请输入6~20位密码");
	}else if(!pattern.test(originalPwd)){
		$(".old-passwd").find("span").show().text("密码格式错误(数字字母下划线)");
	}
	
	if(newPwd != originalPwd){
	$(".new-passwd").find("span").hide();
	}
	
}


function newPwdFocus(){
	$(".new-passwd").find("span").hide().text("");
}


function newPwdBlur(){
	var originalPwd=$("#originalPwd").val();
	var verifyPwd=$("#verifyPwd").val();
	var newPwd=$("#newPwd").val();
	var pattern=/^[a-zA-Z0-9_]+$/;
	if(newPwd=="" || newPwd.length<6 || newPwd.lenth>20){
		$(".new-passwd").find("span").show().text("请输入6~20位密码");
	}else if(newPwd == originalPwd){
		$(".new-passwd").find("span").show().text("新密码和原密码相同");
	}else if(!pattern.test(newPwd)){
		$(".new-passwd").find("span").show().text("密码格式错误(数字字母下划线)");
	}
	
	if(verifyPwd == newPwd){
	$(".confirm-passwd").find("span").hide();
	}
	
}


function verifyPwdFocus(){
	$(".confirm-passwd").find("span").hide().text("");
}


function verifyPwdBlur(){
	var verifyPwd=$("#verifyPwd").val();
	var newPwd=$("#newPwd").val();
	var pattern=/^[a-zA-Z0-9_]+$/;
	if(verifyPwd=="" || verifyPwd.length<6 || verifyPwd.lenth>20){
		$(".confirm-passwd").find("span").show().text("请输入6~20位密码");
	}else if(verifyPwd != newPwd){
		$(".confirm-passwd").find("span").show().text("确认密码和新密码不一致");
	}else if(!pattern.test(verifyPwd)){
		$(".confirm-passwd").find("span").show().text("密码格式错误(数字字母下划线)");
	}
	
}



function checkConfirm(){
	var newPwd=$("#newPwd").val();
	var verifyPwd=$("#verifyPwd").val();
	if(newPwd!=verifyPwd){
		return "新密码和确认密码不一致";
		}
	
	}



//文本框默认提示文字
function textFocus(el) {
    if (el.defaultValue == el.value) { el.value = ''; el.style.color = '#333'; }
}
function textBlur(el) {
    if (el.value == '') { el.value = el.defaultValue; el.style.color = '#999'; }
}

