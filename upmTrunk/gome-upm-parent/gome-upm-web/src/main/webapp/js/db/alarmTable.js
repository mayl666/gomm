/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//分页搜索
	$(".pageNumber").unbind("click");
	$(".pageNumber").bind("click", function(){
		var page = $(this).attr("pageNo");
		if(typeof(page) == "undefined"){
			page = null;
		}
		var searchConditions = $("#searchConditions").val();
		var dataType = $("#dataType").val();
		var alarmLevel = $("#alarmLevel").val();
		if(searchConditions == ""){
			var content = {};
			content.search=true;
			searchConditions = JSON.stringify(content);
		}
		
		$.ajax({
			url:'../db/alarm',
			type:'POST',
			dataType:'html',
			async:false,
			data:{
				dataType:dataType,
				alarmLevel:alarmLevel,
				content:searchConditions,
				page:page
			},
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
					
			},
			error:function(){
//				alert("服务器异常");
				layer.msg('服务器异常');
			}
			
		});
	});
});
