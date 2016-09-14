$(function(){
	
	$("#btn-submit").click(function(){
		createPortal.controller.submit();
	});
	$("#previous-step").click(function(){
		createPortal.controller.previousStep();
	});
	$("input[name='alarmWay']:radio").change(function(){
		var alarmWay =$("input[name='alarmWay']:checked").val();
		if(alarmWay=='no'){
			$("#inlineCheckbox1").removeAttr("checked");
		}else{
			$("#inlineCheckbox1").prop("checked",true);
		}
	});
});
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
var createPortal = {
		   service : {
			   getCheckedAlarm : function(){
				   var ids = "";
				   $("input[name='alarmWay']:radio").each(function(){
					   if($(this).is(":checked")){
						   ids += $(this).val()+",";
					   }
				   });
				   if(ids.length==0){
					   layer.msg("请选择报警方式");
					   return "";
				   }
				  return ids.substring(0, ids.length-1);
			   }
		   },
		   controller : {
			   submit : function(){
					var monitorType = $("#monitorType").val();
					var overtimes = $("#overtimes").val();
					var portalAddress = $("#portalAddress").val();
					var frequency = $("#frequency").val();
					var alarmMethod = createPortal.service.getCheckedAlarm();
					
					if(alarmMethod==""){
						layer.msg("请选择报警方式");
						return false;
					}
					$("#btn-submit").unbind("click");
					$.ajax({
						url:contextPath+'/portal/save',
						type:'POST',
						dataType : 'json',	
						data:{
							'monitorType':monitorType,
							'overtimes':overtimes,
							'port':portalAddress,
							'frequency':frequency,
							'alarmMethod':alarmMethod
						},
						success:function(data){
							if(data.code == 1){
								layer.msg("保存成功", {shade: [0.5, '#000'],scrollbar: false,offset: '50%', time:1000},function(){
									window.location.href=contextPath+"/portal/get";
								});
							}else if(data.code == 2){
								layer.msg("监控地址已经存在,保存失败,请修改监控地址");
								$("#btn-submit").click(function(){
									createPortal.controller.submit();
								});
							}else{
								layer.msg("系统异常");
								$("#btn-submit").click(function(){
									createPortal.controller.submit();
								});
							}
//							$(".content-wrapper").empty();
//							$(".content-wrapper").append(data);
						},
						error:function(){
							layer.msg("操作失败");
							
						}
						
						
					});
			   },
			 previousStep : function(){
					var portalAddress = $("#portalAddress").val();
					var monitorType = $("#monitorType").val();
					var overtimes = $("#overtimes").val();
					var frequency = $("#frequency").val();
					var alarmWay =$("input[name='alarmWay']:checked").val();
					var data ={
						'portalAddress':portalAddress,
						'monitorType':monitorType,
						'timeOutNum':overtimes,
						'accFre':frequency,
						'alarmWay':alarmWay
					}
					StandardPost(contextPath+'/portal/create/step2',data);
			 }  
		   }
		};