$(function(){
	aspListHistory.service.init();
});


var aspListHistory = {
		service : {
			init : function(){
				aspListHistory.service.pageInit();
				aspListHistory.service.dealSearch();
				aspListHistory.service.dealPageClick();
				aspListHistory.service.dealOperatorLog();
			},
			pageInit : function(){
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				aspListHistory.controller.getPage("all", startTime, endTime, 1);
			},
			dealSearch : function(){
				$("#searchBtn").click(function(){
					var state = $("#statusSelect").val();
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var firstStartTime = "2016-09-03 00:00:00";
					var beginDate = new Date(startTime.replace(/-/g,"/"));
					var endDate = new Date(endTime.replace(/-/g,"/"));
					var firstStartDate = new Date(firstStartTime.replace(/-/g,"/"));
					if(beginDate < firstStartDate){
						alert("开始时间必须大于等于2016-09-03 00:00:00");
						return false;
					}
					if(endDate < firstStartDate){
						alert("截止时间必须大于等于2016-09-03 00:00:00");
						return false;
					}
					if(beginDate >= endDate){
						alert("截止时间必须大于开始时间");
						return false;
					}
					aspListHistory.controller.getPage(state, startTime, endTime, 1);
				})

			},
			dealPageClick : function(){
			    $(".list_table").on("click",".pageNumber", function(){
			    	var pageNo = $(this).attr("pageNo");
			    	var state = $("#hiddenState").val();
					var startTime = $("#hiddenStartTime").val();
					var endTime = $("#hiddenEndTime").val();
					aspListHistory.controller.getPage(state, startTime, endTime, pageNo);
			    });
			},
			dealOperatorLog : function(){
				$(".list_table").on("click",".logDetail",function(){
					var logId = $(this).attr("logAddress");
					var logUrl = "ftp://10.126.59.1/reports/"+logId+"/surefire-reports/html/index.html";
					window.open(logUrl);
				})
			}
		},
		controller : {
		    getPage : function(state, startTime, endTime, pageNo){
		    	var aspType = $("#aspType").val();
		    	var pageSize = 15;
		    	$.ajax({
					url:contextPath+'/asp/history/list/table',
					type:'POST',
					dataType : 'html',	
					data:{type:aspType,state:state,startTime:startTime,endTime:endTime,pageNo:pageNo,pageSize:pageSize},
					success:function(data){
						//console.info(data);
						$(".list_table").empty();
						$(".list_table").append(data);
						
					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
		    }
		}
};