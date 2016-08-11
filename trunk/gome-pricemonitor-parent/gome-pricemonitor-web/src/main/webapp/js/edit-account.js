/**
 * Created by hanpan on 2015/11/2.
 */
/**
 * Created by hanpan on 2015/11/2.
 */
$(function(){

    $.formValidator.initConfig({theme:"baidu",mode:"AutoTip",submitOnce:true,formID:"edit-accountForm",
        submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
    });

    //$("#mail").formValidator({onEmpty:"请输入邮箱",onFocus:"请输入常用邮箱，通过验证后可用于登陆和找回密码",defaultValue:"@",tipCss:{height:36,width:320}}).inputValidator({min:6,max:60,onError:"请输入邮箱"}).regexValidator({regExp:"email",dataType:"enum",onError:"邮箱格式不正确"});
/*    $("#editPwd").formValidator({
    	onFocus:"6~16个字符，包括字母、数字、特殊符号，区分大小写",
        buttons: $("#user_submit"),
        tipCss:{height:68,width:320}}).inputValidator({min:6,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空符号"},
        onError:"密码长度错误,请确认"}).passwordValidator({compareID:"username"});*/
    $("#editName").formValidator({
        onEmpty:"请输入4~16位的真实姓名",
        onFocus:"请输入真实姓名",
        tipCss:{height:36,width:320}}).inputValidator({min:4,max:16,onError:"你输入的帐号名长度不正确,请确认"});
    //联系方式
    $("#editWay").formValidator({
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
//    var contVla;//存放 联系方式的值
//    $("#editWay").change(function(){
//        contVla= $("#editWay").val();
//    })

//    var myEmailReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;//验证邮箱的正则
//    var mobile= /^13[0-9]{9}|15[012356789][0-9]{8}|18[0256789][0-9]{8}|147[0-9]{8}$/;
//    if(myEmailReg.test(contVla)){
//        $("#editWay").formValidator({
//            onEmpty:"请输入邮箱",
//            onFocus:"请输入正确的邮箱地址/手机号",
//            tipCss:{height:36,width:320}}).regexValidator({regExp:"email",dataType:"enum",onError:"邮箱格式不正确"});
//    }else if(mobile.test(contVla)){
//        $("#editWay").formValidator({
//            onEmpty:"请输入手机号码", tipCss:{height:36,width:320}}).regexValidator({regExp:"mobile",dataType:"enum",onError:"输入的格式有误"});
//
//    }else{
//        $("#editWay").formValidator({
//            onEmpty:"请输入邮箱",
//            onFocus:"请输入正确的邮箱地址/手机号",
//            tipCss:{height:36,width:320}}).regexValidator({regExp:"email",dataType:"enum",onError:"邮箱格式不正确"});
//    }

    $.formValidator.reloadAutoTip();
    
    
    $("#editPwdBtn").click(function(){
    	$("#editPwd").removeAttr("disabled");
    	$("#editPwdBtn").hide();
    	$("#editPwd").focus();
    	$("#editPwd").val("");
    });
    
    
 });




function checkContactWay(){
	var contacWay=$("#editWay").val();
	var mobilefilter = /^0?1[3|4|5|8][0-9]\d{8}$/;
	var emailfilter  = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	if (!mobilefilter.test(contacWay) && !emailfilter.test(contacWay)) {
	     return  "联系方式格式不正确";
	} 
}

