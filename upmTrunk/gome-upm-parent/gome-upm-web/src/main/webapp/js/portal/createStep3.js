$(function(){
	
	$("#btn-submit").click(function(){
		createPortal.controller.submit();
	});
	$("#previous-step").click(function(){
		createPortal.controller.previousStep();
	});
});

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
					var url = contextPath+"/portal/create/step2?portalAddress="+portalAddress+"&monitorType="+monitorType+"&timeOutNum="+overtimes+"&accFre="+frequency;
					window.location.href=url;
			 }  
		   }
		};