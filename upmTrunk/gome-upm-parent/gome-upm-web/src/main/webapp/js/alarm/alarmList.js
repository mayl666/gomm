/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//搜索
	$(".newbtn").unbind("click");
	$(".newbtn").bind("click", function(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime.length != 0 && endTime == 0){
//			alert("请输入结束时间！");
			layer.msg('请输入结束时间！');
			return;
		} else if(startTime.length == 0 && endTime != 0){
//			alert("请输入开始时间！");
			layer.msg('请输入开始时间！');
			return;
		}
		var content = {};
		if(startTime.length > 0 && endTime.length > 0){
			content.startTime = startTime;
			content.endTime = endTime;
		}
		var type = $("#type").val();
		if(type != "" && type != null){
			content.type=type;
		}
		content.search=true;
		$.ajax({
			url:'../alarm/list',
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
//				alert("服务器异常");
				layer.msg('服务器异常');
			}
			
		});
	});
	
	//查看详情
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