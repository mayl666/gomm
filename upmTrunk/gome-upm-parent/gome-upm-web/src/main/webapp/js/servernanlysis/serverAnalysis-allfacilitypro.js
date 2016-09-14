
$(function(){
	//搜索
	$("#searchbtn").unbind("click");
	$("#searchbtn").bind("click", function(){
		var content = {};
		var hostName = $("#host").val();
		if(hostName != "" && hostName != null){
			content.name=encodeURIComponent(hostName);
		}
		content.search=true;
		content.status = status;
		$.ajax({
			url:'../serverAnalysis/queryHostAllPro',
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
			},
			error:function(){
				//pop_up("系统异常",false);
				layer.msg("服务器异常");
			}
		});
	});
});


//导出
$("#export").click(function(){
	var searchConditions = $("#searchConditions").val();
	window.location.href = '../serverAnalysis/exportExcelPro?content='+searchConditions+"&vType=3";;
});
