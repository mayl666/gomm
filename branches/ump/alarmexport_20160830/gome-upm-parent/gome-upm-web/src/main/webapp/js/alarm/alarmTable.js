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
		if(searchConditions == ""){
			var content = {};
			content.search=true;
			searchConditions = JSON.stringify(content);
		}
		
		$.ajax({
			url:'../alarm/list',
			type:'POST',
			dataType:'html',
			async:false,
			data:{
				content:searchConditions,
				page:page
			},
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
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
	
	
	
});
