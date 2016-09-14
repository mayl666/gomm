$(function(){
	$("#defaultReturnCode").click(function(){
		createUrl.service.defaultReturnCode();
	});
	
	$("#customReturnCode").click(function(){
		createUrl.service.customReturnCode();
	});
	
	$("#step2-next").click(function(){
		createUrl.controller.toStep3();
	});
	
	$("#urlAddress").blur(function(){
		createUrl.controller.isUrlExists();
	});
	$("#returnCode").change(function(){
		regCode();
	});
	$(".form-control").focus(function(){
		$(this).siblings(".error_span").hide();
	});
	$("#accTimeOut").change(function(){
		isNumber();
	});
	$("#resContent").mouseout(function(){
		var resContent =$("#resContent").val();
		   if(resContent.length>0){
			   $("#include_id").show();
			   return ;
		   }
		   $("#include_id").hide();
	});
	$("#resContent").blur(function(){
		var resContent =$("#resContent").val();
		   if(resContent.length>0){
			   $("#include_id").show();
			   return ;
		   }
		   $("#include_id").hide();
	})
	
//	$("#previous-step").click(function(){
//		createUrl.controller.previousStep();
//	});
});
function change(count){
	if(count==1){
		$("#post_param").show();
	}else{
		$("#post_param").hide();
		$("#postParameter").text("");
	}
}
function isNumber(){
	var accTimeOut = $("#accTimeOut").val().trim();
	var re =/^[1-9]\d*$/;
	if(re.test(accTimeOut)){
		if(accTimeOut>0){
			$("#accTimeOut").siblings(".info_span").hide();
			return true;
		}else{
			$("#accTimeOut").siblings(".info_span").show().text("请输入正整数");
			$("#accTimeOut").focus();
			return false;
		}
	}else{
		$("#accTimeOut").siblings(".info_span").show().text("请输入正整数");
		$("#accTimeOut").focus();
		return false;
	}
}
function regCode(){
	var returnCode= $("#returnCode").val();
		if(returnCode.length==0){
			layer.msg("URL请求返回码不能为空");
			$("#returnCode").focus();
			return false;
		}else{
			var reg= /(([2-9]\d{2})+[,]?)+/;
			var out= reg.test(returnCode);
			if(!out){
				layer.msg("URL请求返回码格式错误");
				$("#returnCode").focus();
				return false;
			}
			var s =returnCode.split(",")
			if(s.length>0){
				for(var i=0;i<s.length;i++){
					if(s[i]>=200&&s[i]<=600){
						
					}else{
						layer.msg("URL请求返回码格式错误");
						$("#returnCode").focus();
						return false;
					}
				}
			}else{
				if(returnCode>=200&&returnCode<=600){
					
				}else{
					layer.msg("URL请求返回码格式错误");
						$("#returnCode").focus();
						return false;
				}
			}
			return true;
		}
}
function StandardPost (url,args) 
{
    var form = $("#form1");
    form.attr({"action":url});
    for (arg in args)
    {
        var input = $("<input type='hidden'>");
        input.attr({"name":arg});
        input.val(args[arg]);
        form.append(input);
    }
    $("#submit_id").click();
}

var createUrl = {
  service:{
	  defaultReturnCode : function(){
		  $("#returnCode").val("200");
		  $('#returnCode').attr("disabled","disabled");
	  },
	  customReturnCode : function(){
		  $("#returnCode").val("200"); 
		  $('#returnCode').attr("disabled",false);
	  },
	  requiredInputBlur : function(){
		  $("#urlAddress").focus(function(){
			  $("#urlAddress").find($(".info_span")).show().text("请输入监控地址");
		  });
	  }
  },	
  controller : {
	   nextStep : function(){
			var content = {};
			content.key = $("#urlKey").val();
			content.desc = $("#urlDesc").val();
			content.app = $("#urlApp").val();
			content.urlAddress = $("#urlAddress").val().trim();
			content.accFre = $("#accFre").val();
			content.accTimeOut = $("#accTimeOut").val();
			content.timeOutNum = $("#timeOutNum").val();
			content.alarmInter = $("#alarmInter").val();
			content.method = $("input[name='method']:checked").val();
			content.resContent = $("#resContent").val();
			content.isContainsCon = $("input[name='isContainsContent']:checked").val();
			content.isDefaultCode = $("input[name='isDefaultCode']:checked").val();
			content.returnCode = $("#returnCode").val();
			var returnCode= $("#returnCode").val();
			
			$.ajax({
				url:contextPath+'/url/create/step3',
				type:'POST',
				dataType : 'html',	
				data:{'content':JSON.stringify(content)},
				success:function(data){
					console.info(data);
					$(".wrapperr").empty();
					$(".wrapperr").append(data);
				},
				error:function(){
					layer.msg("操作失败");
					
				}
				
				
			});
	   },
	   toStep3 : function(){
		   $(".info_span.ele_hide").hide();
		   // var flag = true;
			var key = $("#urlKey").val();
			var desc = $("#urlDesc").val();
			var app = $("#urlApp").val();
			var urlAddress = $("#urlAddress").val().trim();
			var postParameter =$("#postParameter").val();
			var accFre = $("#accFre").val();
			var accTimeOut = $("#accTimeOut").val().trim();
			var timeOutNum = $("#timeOutNum").val();
			var alarmInter = 0;//$("#alarmInter").val();
			var method = $("input[name='method']:checked").val();
			var resContent = $("#resContent").val();
			var isContainsCon = $("input[name='isContainsContent']:checked").val();
			//var isDefaultCode = $("input[name='isDefaultCode']:checked").val();
			var isDefaultCode = "1";
			var returnCode = $("#returnCode").val();
			var alarmWay = $("#alarmWay").val();
			
			//var url = contextPath+"/url/create/step3?postParameter="+postParameter+"&urlAddress="+urlAddress+"&accFre="+accFre+"&accTimeOut="+accTimeOut+"&timeOutNum="+timeOutNum+"&alarmInter="+alarmInter+"&method="+method+"&resContent="+resContent+"&isContainsCon="+isContainsCon+"&isDefaultCode="+isDefaultCode+"&returnCode="+returnCode;
			var data ={
				'postParameter':postParameter,
				'urlAddress':urlAddress,
				'accFre':accFre,
				'accTimeOut':accTimeOut,
				'timeOutNum':timeOutNum,
				'alarmInter':alarmInter,
				'method':method,
				'resContent':resContent,
				'isContainsCon':isContainsCon,
				'isDefaultCode':isDefaultCode,
				'returnCode':returnCode,
				'alarmWay':alarmWay
			};
			
//			if(urlAddress == "" ){
//				$("#urlAddress").siblings(".error_span").show().text("*必填");
//				flag = false;
//			}
//			if(accTimeOut == "" ){
//				$("#accTimeOut").siblings(".error_span").show().text("*必填");
//				flag = false;
//			}
//			if(timeOutNum == "" ){
//				$("#timeOutNum").siblings(".error_span").show().text("*必填");
//				flag = false;
//			}
//			if(alarmInter == "" ){
//				$("#alarmInter").siblings(".error_span").show().text("*必填");
//				flag = false;
//			}
//
//			return false;

			
			
			
			
			/*if(isContainsCon == "include" && resContent == ""){
				layer.msg("请输入匹配内容");
				return false;
			}*/
			
			/*if(createUrl.controller.isUrlExists()){
				layer.msg("请输入必填项");
				return false;
			}
			
			if(accTimeOut*timeOutNum > accFre*60){
				layer.msg("超时时间*超时次数不能大于访问频率");
				return false;
			}*/
			
			
			
//			if(util.service.isNum(accFre)){
//				layer.msg("频率请输入正整数");
//				return false;
//			}
			
			var flag = createUrl.controller.isUrlExists();
			console.info("flagtoStep3:"+flag);
			var isNum = isNumber();
			if(!regCode()){
				return;
			}
			if(flag&&isNum){
				//window.location.href=url;
				StandardPost(contextPath+'/url/create/step3',data);
			}
			
	   },
	   previousStep : function(){
			var key = $("#urlKey").val();
			var desc = $("#urlDesc").val();
			var app = $("#urlApp").val();
			var url = contextPath+"/url/create/step1?key="+key+"&desc="+desc+"&app="+app;
			window.location.href=url;
	   },
	   isUrlExists : function(){ 
		   var flag = false;
		   var url = $("#urlAddress").val().trim();
		   if(url == ""){
			   $("#urlAddress").siblings(".info_span").show().text("监控地址不能为空");
			   $("#urlAddress").focus();
			   return flag;
		   }
		var check = /^(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/
						if(check.test(url)){
							$.ajax({
								url:contextPath+'/url/getByUrl',
								type:'POST',
								dataType : 'json',	
								data:{'url':url},
								async:false,
								success:function(data){
									console.info(data);
									if(data.code == 1){
										if(data.attach == 0){
											flag = true;
											$("#urlAddress").siblings(".info_span").show().text("监控地址可以使用")
											
											return flag;
										}else{
											$("#urlAddress").siblings(".info_span").show().text("监控地址已存在");
											$("#urlAddress").focus();
											return false;
										}
				
									}
								},
								error:function(){
									$("#urlAddress").siblings($(".info_span")).show().text("验证失败");
									$("#urlAddress").focus();
									return false;
								}
								
								
							});
						
						
					}else{
						$("#urlAddress").siblings($(".info_span")).show().text("监控地址输入不正确");
						$("#urlAddress").focus();
						return false;
					}
			return flag;
	   }
  }
};