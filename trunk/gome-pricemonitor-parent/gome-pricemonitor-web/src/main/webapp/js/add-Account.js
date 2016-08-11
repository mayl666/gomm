/**
 * Created by hanpan on 2015/11/2.
 */
$(function() {

	$.formValidator.initConfig({
		theme : "baidu",
		mode : "AutoTip",
		submitOnce : true,
		formID : "account_form",
		submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
	});

	$("#accountNumber").formValidator({
		onEmpty : "请输入4~16位字符的帐号",
		onFocus : "4~16个字符，包括字母、数字、下划线，以字母开头，字母或数字结尾",
		onCorrect : "该用帐号可以使用",
		tipCss : {
			height : 36,
			width : 460
		}
	}).inputValidator({
		min : 4,
		max : 16,
		onError : "你输入的帐号名长度不正确,请确认"
	}).regexValidator({
		regExp : "username",
		dataType : "enum",
		onError : "请输入数字、26个英文字母或者下划线组成的字符串"
	}).ajaxValidator({
		dataType : "json",
		async : true,
		data : {
			'userName' : function() {
				return $("#accountNumber").val()
			}
		},
		url : "../manager/ajax/user/checkUserName",
		// url : "http://www.51gh.net/chkuser.aspx?act=ok",
		success : function(data) {
			//alert($("#accountNumber").val());
			if (data.code == 1)
				return true;
			if (data.code == 5)
				return false;
			// if( data.indexOf("此帐号可以使用!") > 0 ) return true;
			// if( data.indexOf("此帐号已存在,请填写其它帐号!") > 0 ) return false;
			return false;
		},
		buttons : $("#user_submit"),
		error : function(jqXHR, textStatus, errorThrown) {
			alert("服务器没有返回数据，可能服务器忙，请重试" + errorThrown);
		},
		onError : "该帐号不可用，请更换帐号",
		onWait : "正在进行合法性校验，请稍候..."
	});
	// $("#mail").formValidator({onEmpty:"请输入邮箱",onFocus:"请输入常用邮箱，通过验证后可用于登陆和找回密码",defaultValue:"@",tipCss:{height:36,width:320}}).inputValidator({min:6,max:60,onError:"请输入邮箱"}).regexValidator({regExp:"email",dataType:"enum",onError:"邮箱格式不正确"});
	$("#accountPwd").formValidator({
		onEmpty : "请输入密码",
		onFocus : "6~16个字符，包括字母、数字、特殊符号，区分大小写",
		tipCss : {
			height : 68,
			width : 320
		}
	}).inputValidator({
		min : 6,
		max : 16,
		empty : {
			leftEmpty : false,
			rightEmpty : false,
			emptyError : "密码两边不能有空符号"
		},
		onError : "密码长度错误,请确认"
	}).passwordValidator({
		compareID : "username"
	});
	$("#trueName").formValidator({
		onEmpty : "请输入4~16位的真实姓名",
		onFocus : "请输入真实姓名",
		tipCss : {
			height : 36,
			width : 320
		}
	}).inputValidator({
		min : 4,
		max : 16,
		onError : "你输入的帐号名长度不正确,请确认"
	});
	
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
	// 联系方式
//	var contVla;// 存放 联系方式的值
//	$("#contactWay").change(function() {
//		contVla = $("#contactWay").val();
//	})

	// var myEmailReg =
	// /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;//验证邮箱的正则
	// var mobile=
	// /^13[0-9]{9}|15[012356789][0-9]{8}|18[0256789][0-9]{8}|147[0-9]{8}$/;
	// if(myEmailReg.test(contVla)){
	// $("#contactWay").formValidator({
	// onEmpty:"请输入邮箱",
	// onFocus:"请输入正确的邮箱地址/手机号",
	// tipCss:{height:36,width:320}}).regexValidator({regExp:"email",dataType:"enum",onError:"邮箱格式不正确"});
	// }else if(mobile.test(contVla)){
	// $("#contactWay").formValidator({
	// onEmpty:"请输入手机号码",
	// tipCss:{height:36,width:320}}).regexValidator({regExp:"mobile",dataType:"enum",onError:"输入的格式有误"});
	//
	// }else{
	// $("#contactWay").formValidator({
	// onEmpty:"请输入邮箱",
	// onFocus:"请输入正确的邮箱地址/手机号",
	// tipCss:{height:36,width:320}}).regexValidator({regExp:"email",dataType:"enum",onError:"邮箱格式不正确"});
	// }

//	$("#contactWay").formValidator({
//		onEmpty : "请输入您的联系方式",
//		onFocus : "请输入您的联系方式",
//		tipCss : {
//			height : 36,
//			width : 320
//		}
//	}).functionValidator({
//		fun : checkContact
//	});
//
//	$.formValidator.reloadAutoTip();

});



function checkContactWay(){
	var contacWay=$("#contactWay").val();
	var mobilefilter = /^0?1[3|4|5|8][0-9]\d{8}$/;
	var emailfilter  = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	if (!mobilefilter.test(contacWay) && !emailfilter.test(contacWay)) {
	     return  "联系方式格式不正确";
	} 
}

function checkContact(val, elem) {
	var contactWayd = $("#contactWay").val();
	if (contactWayd == "") {
		return "您的联系方式不能为空";
	}
	var myEmailReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;// 验证邮箱的正则
	//var mobile = /^13[0-9]{9}|15[012356789][0-9]{8}|18[0256789][0-9]{8}|147[0-9]{8}$/;
	var mobile = /^1[0-9]{10}/;
	if (!mobile.test(contactWayd)) {
		if(!myEmailReg.test(contactWayd)){
			return "联系方式格式不正确";
		}

	}
    
	
	
}
