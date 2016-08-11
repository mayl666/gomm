/**
 * Created by caowei on 2015/11/02
 */
var check = {
		
		//网址校验
		urlReg : /^((http|https):\/\/)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,4})*(\/[a-zA-Z0-9\&%_\.\/-~-]*)?$/,
		
		//名称校验：只含有汉字、数字、字母、下划线不能以下划线开头和结尾，长度为4-16位
		nameReg : /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
		
		//价格校验：只能为数字，小数部分最多两位，也可以不带小数位
		priceReg : /^\d{1,10}\.*\d{0,2}$/,
		
		//检查是否为空
		checkNull:function(fieldId){
			var fieldValue = $("#" + fieldId).val();
			if(fieldValue == null || fieldValue == ""){
				return true;
			}
			return false;
		},
		//检查格式
		checkFormat:function(reg, fieldId){
			var fieldValue = $("#" + fieldId).val();
			if(reg.test(fieldValue)){
				return true;
			}
			return false;
		},
		//提示错误信息
		showErrorMessage:function(fieldId, errorMessage){
			$("#" + fieldId + "ErrorSpan").html("<font color=red>" + errorMessage + "</font>");
			$("#" + fieldId).addClass("user_input_error user_input_focus");
			//$("#" + fieldId).focus();
		},
		
		//清除错误信息
		clearErrorMessage:function(fieldId){
			$("#" + fieldId + "ErrorSpan").html("");
			$("#" + fieldId).removeClass("user_input_error user_input_focus");
		}
}