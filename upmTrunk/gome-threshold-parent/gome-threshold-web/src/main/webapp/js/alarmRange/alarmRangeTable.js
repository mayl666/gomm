/**
 * Created by wangxiaye on 2016/08/31
 */
$(function() {
	// 分页搜索
	$(".pageNumber").unbind("click");
	$(".pageNumber").bind("click", function() {
		var page = $(this).attr("pageNo");
		if (typeof (page) == "undefined") {
			page = null;
		}
		var searchConditions = $("#searchConditions").val();
		if (searchConditions == "") {
			var content = {};
			content.search = true;
			searchConditions = JSON.stringify(content);
		}
		$.ajax({
			url : '../alarmRange/list',
			type : 'POST',
			dataType : 'html',
			async : false,
			data : {
				content : searchConditions,
				page : page
			},
			success : function(data) {
				$(".list_table").empty();
				$(".list_table").append(data);
			},
			error : function() {
				layer.msg('服务器异常');
			}
		});
	});

	$("a[name='edit_table']").unbind("click");
	$(".list_table").on("click", "a[name='edit_table']", function() {
		$("#rangeValue").css("color",'#d2d6de');
		var rangeId = $(this).attr("rangeId");
		$("#hiddenRangeId").val(rangeId);
		var rangeValue = $(this).parent().parent().find("td[name=rangeValue]").html();
		$("#rangeValue").val(rangeValue);
	});
	
	$("#rangeValue").unbind("focus");
	$("#rangeValue").focus(function(){
		var color = $(this).css("color");
		if(color=='rgb(210, 214, 222)'){
			$(this).css("color","#555");
			$(this).val("");
		}
	});
	
});
