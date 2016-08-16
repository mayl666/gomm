$(function(){
	//分页搜索
	$(".pageNumber").unbind("click");
	$(".pageNumber").bind("click", function(){
		var page = $(this).attr("pageNo");
		if(typeof(page) == "undefined"){
			page = null;
		}
		var url="../monitoBusiness/getAlarmDate.do";
		var alarmType=$('#alarmType').val();
		$.ajax({
			url:url,
			type:'POST',
			dataType:'html',
			async:false,
			async:false,
			data:{
				type:alarmType,
				page:page
			},
			cache:false,
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
					//查看详情
				$(".detail").unbind("click");
				$(".detail").bind("click",function(){
					var url = $(this).attr("url");
					layer.open({
						  type: 2,
						  title: '详情',
						  shadeClose: true,
						  shade: 0.8,
						  area: ['60%', '70%'],
						  content: url //iframe的url
						}); 
				});		
			},
			error:function(){
				alert("服务器异常");
			}
			
		});
	});
});
function searchAlarmList(){
	var url="../monitoBusiness/getAlarmDate.do";
	var alarmType=$('#alarmType').val();
	var startTime_alarm=$('#startTime_alarm').val();
	var endTime_alarm=$('#endTime_alarm').val();
	if((startTime_alarm != null && startTime_alarm != '') && (endTime_alarm == null || endTime_alarm == '')){
//		alert("请输入结束时间！");
		layer.msg('请输入结束时间！');
		return;
	}else if((endTime_alarm != null && endTime_alarm != '') && (startTime_alarm == null || startTime_alarm == '')){
//		alert("请输入开始时间！");
		layer.msg('请输入开始时间！');
		return;
	}
	$.ajax({
		url:url,
		type:'POST',
		async:true,
		dataType:'html',
		data:{
			type:alarmType,
			startTime:startTime_alarm,
			endTime:endTime_alarm
		},
		success:function(data){
			$(".list_table").empty();
			$(".list_table").append(data);
			//查看详情
			$(".detail").unbind("click");
			$(".detail").bind("click",function(){
				var url = $(this).attr("url");
				layer.open({
					  type: 2,
					  title: '详情',
					  shadeClose: true,
					  shade: 0.8,
					  area: ['60%', '70%'],
					  content: url //iframe的url
					}); 
			});	
		},
		error:function(){
			alert("服务器异常");
		}
		
	});
}
