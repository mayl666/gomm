/**
 * Created by caowei on 2016/07/21
 */
function detail(){
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
}

function changeStatus(){
	//操作
	$(".changeStatus").unbind("click");
	$(".changeStatus").bind("click",function(){
		var $_this = $(this);
		var id = $(this).attr("id");
		var status = $(this).attr("status");
		var content = {};
		content.id = id;
		content.status = status;
		$.ajax({
			url:'../alarm/update',
			type:'POST',
			async:true,
			dataType:'json',
			data:{
				content:JSON.stringify(content)
			},
			success:function(data){
				if(data.code==1){
					layer.msg("操作成功！", {shade: [0.5, '#000'],scrollbar: false,offset: '50%', time:1000});
					if(status == 1){
						$_this.parent().siblings(".status").empty().append("处理中");
						$_this.parent().empty().append("<a href='javascript:void(0);' class='changeStatus' id='" + id + "' status='2' style='font-size:12px;color:#9c8ade;text-decoration:underline'>已处理</a>&nbsp;" 
			        	+ "<a href='javascript:void(0);' class='changeStatus' id='" + id + "' status='3' style='font-size:12px;color:#9c8ade;text-decoration:underline'>忽略</a>");
					}else if(status == 2){
						$_this.parent().siblings(".status").empty().append("已处理");
						$_this.parent().empty().append("<a href='javascript:void(0);' disabled='disabled' style='cursor:default;font-size:12px;color:gray;text-decoration:underline'>处理</a>&nbsp;" 
						+ "<a href='javascript:void(0);' disabled='disabled' style='cursor:default;font-size:12px;color:gray;text-decoration:underline'>忽略</a>");
					}else if(status == 3){
						$_this.parent().siblings(".status").empty().append("忽略");
						$_this.parent().empty().append("<a href='javascript:void(0);' class='changeStatus' id='" + id + "' status='1' style='font-size:12px;color:#9c8ade;text-decoration:underline'>处理</a>&nbsp;" 
			        	+ "<a href='javascript:void(0);' disabled='disabled' style='cursor:default;font-size:12px;color:gray;text-decoration:underline'>忽略</a>");
					}
					//重新绑定事件
					detail();	
					changeStatus();
				  }else{
					  layer.msg(attach);
				  }
			},
			error:function(){
//				alert("服务器异常");
				layer.msg('服务器异常');
			}
			
		});
	});
}

$(function(){
	//搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime.length != 0 && endTime.length == 0){
//			alert("请输入结束时间！");
			layer.msg('请输入结束时间！');
			return;
		} else if(startTime.length == 0 && endTime.length != 0){
//			alert("请输入开始时间！");
			layer.msg('请输入开始时间！');
			return;
		}
		var content = {};
		if(startTime.length > 0 && endTime.length > 0){
			var startDate = new Date(startTime.replace(/-/g,"/"));
			var endDate = new Date(endTime.replace(/-/g,"/"));
			if(startDate >= endDate){
//				alert("截止时间必须大于开始时间");
				layer.msg('开始时间不能晚于结束时间！');
				return;
			}
			content.startTime = startTime;
			content.endTime = endTime;
		}
		var type = $("#type").val();
		if(type != "" && type != null){
			content.type=type;
		}
		var level = $("#level").val();
		if(level != "" && level != null){
			content.level=level;
		}
		var status = $("#status").val();
		if(status != "" && status != null){
			content.status=status;
		}
		content.search=true;
		$.ajax({
			url:'../alarm/businessList',
			type:'POST',
			async:true,
			dataType:'html',
			data:{
				content:JSON.stringify(content)
			},
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
				$("#searchConditions").val(JSON.stringify(content));
				//重新绑定事件
				detail();	
				changeStatus();
			},
			error:function(){
//				alert("服务器异常");
				layer.msg('服务器异常');
			}
			
		});
	});
	
	detail();
	
	changeStatus();
	
	//导出
	$(".export-btn").click(function(){
		var searchConditions = $("#searchConditions").val();
		window.location.href = '../alarm/exportExcel?conditions='+searchConditions;
	});
	
	laydate({
	    elem: '#startTime',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    //min: laydate.now(), //设定最小日期为当前日期
	    //max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: true,
	    choose: function(date){
	         //end.min = datas; //开始日选好后，重置结束日的最小日期
	         //end.start = datas //将结束日的初始值设定为开始日
	    	//alert("startTime:" + date);
	    }
	});
	
	laydate({
	    elem: '#endTime',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    //min: laydate.now(), //设定最小日期为当前日期
	    //max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: true,
	    choose: function(date){
	         //end.min = datas; //开始日选好后，重置结束日的最小日期
	         //end.start = datas //将结束日的初始值设定为开始日
	    	//alert("endTime:" + date);
	    }
	});
	
});