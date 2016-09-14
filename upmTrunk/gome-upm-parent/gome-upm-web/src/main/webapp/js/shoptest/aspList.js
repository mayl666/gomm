$(function(){
	aspList.service.init();

});

var aspList = {
	dao : {
		dealFlowChartSm : function(data, type){
			var html1='';
			var error_code=' error_code';
			html1 += '<tr class="flowTr"><td colspan="6"><div id="flowChartSmWClone"><div class="flowChart"><div>';
			html1 += '<div class="lookFlow';
			if(data[0].result == "fail"){
				html1 += error_code;
			}
			html1 += '">清除Cookie<span>（'+data[0].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			html1 += '<div class="lookFlow';
			if(data[1].result == "fail"){
				html1 += error_code;
			}
			html1 += '">登录<span>（'+data[1].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			html1 += '<div class="lookFlow';
			if(data[2].result == "fail"){
				html1 += error_code;
			}
			html1 += '">清空购物车<span>（'+data[2].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			if(type=="normal"){
				html1 += '<div class="lookFlow';
				if(data[3].result == "fail"){
					html1 += error_code;
				}
				html1 += '">加入购物车<span>（'+data[3].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html1 += '<div class="lookFlow';
				if(data[4].result == "fail"){
					html1 += error_code;
				}
				html1 += '">结算<span>（'+data[4].usedTime+'s）</span></div></div>';
				html1 += '<img src="'+contextPath+'/image/next-turn.png"></img>';
				html1 += '<div style="float: left;margin-left: 60px;">';
				html1 += '<div class="lookFlow';
				if(data[5].result == "fail"){
					html1 += error_code;
				}
				html1 += '">支付宝支付<span>（'+data[5].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html1 += '<div class="lookFlow';
				if(data[6].result == "fail"){
					html1 += error_code;
				}
				html1 += '">银联支付<span>（'+data[6].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			}else{
				html1 += '<div class="lookFlow';
				if(data[3].result == "fail"){
					html1 += error_code;
				}
				html1 += '">搜索<span>（'+data[3].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html1 += '<div class="lookFlow';
				if(data[4].result == "fail"){
					html1 += error_code;
				}
				html1 += '">加入购物车结算<span>（'+data[4].usedTime+'s）</span></div></div>';
				html1 += '<img src="'+contextPath+'/image/next-turn.png"></img>';
				html1 += '<div style="float: left;margin-left: 60px;">';
				html1 += '<div class="lookFlow';
				if(data[5].result == "fail"){
					html1 += error_code;
				}
				html1 += '">银联支付<span>（'+data[5].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html1 += '<div class="lookFlow';
				if(data[6].result == "fail"){
					html1 += error_code;
				}
				html1 += '">支付宝支付<span>（'+data[6].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			}

			html1 += '<div class="lookFlow';
			if(data[7].result == "fail"){
				html1 += error_code;
			}
			html1 += '">快钱支付<span>（'+data[7].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			html1 += '<div class="lookFlow';
			if(data[8].result == "fail"){
				html1 += error_code;
			}
			html1 += '">取消订单<span>（'+data[8].usedTime+'s）</span></div>';
			html1 += '</div></div></div></td></tr>';
			return html1;
		},
		dealFlowChartBg : function(data,type){
			var html2='';
			var error_code=' error_code';
			html2 += '<tr class="flowTr"><td colspan="6"><div id="flowChartBigWClone"><div class="flowChartBigW"><div>';
			html2 += '<div class="lookFlow';
			if(data[0].result == "fail"){
				html2 += error_code;
			}
			html2 += '">清除Cookie<span>（'+data[0].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			html2 += '<div class="lookFlow';
			if(data[1].result == "fail"){
				html2 += error_code;
			}
			html2 += '">登录<span>（'+data[1].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			html2 += '<div class="lookFlow';
			if(data[2].result == "fail"){
				html2 += error_code;
			}
			html2 += '">清空购物车<span>（'+data[2].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			if(type=="normal"){
				html2 += '<div class="lookFlow';
				if(data[3].result == "fail"){
					html2 += error_code;
				}
				html2 += '">加入购物车<span>（'+data[3].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html2 += '<div class="lookFlow';
				if(data[4].result == "fail"){
					html2 += error_code;
				}
				html2 += '">结算<span>（'+data[4].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html2 += '<div class="lookFlow';
				if(data[5].result == "fail"){
					html2 += error_code;
				}
				html2 += '">支付宝支付<span>（'+data[5].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html2 += '<div class="lookFlow';
				if(data[6].result == "fail"){
					html2 += error_code;
				}
				html2 += '">银联支付<span>（'+data[6].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			}else{
				html2 += '<div class="lookFlow';
				if(data[3].result == "fail"){
					html2 += error_code;
				}
				html2 += '">搜索<span>（'+data[3].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html2 += '<div class="lookFlow';
				if(data[4].result == "fail"){
					html2 += error_code;
				}
				html2 += '">加入购物车结算<span>（'+data[4].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html2 += '<div class="lookFlow';
				if(data[5].result == "fail"){
					html2 += error_code;
				}
				html2 += '">银联支付<span>（'+data[5].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
				html2 += '<div class="lookFlow';
				if(data[6].result == "fail"){
					html2 += error_code;
				}
				html2 += '">支付宝支付<span>（'+data[6].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			}

			html2 += '<div class="lookFlow';
			if(data[7].result == "fail"){
				html2 += error_code;
			}
			html2 += '">快钱支付<span>（'+data[7].usedTime+'s）</span></div><img src="'+contextPath+'/image/next.png"></img>';
			html2 += '<div class="lookFlow';
			if(data[8].result == "fail"){
				html2 += error_code;
			}
			html2 += '">取消订单<span>（'+data[8].usedTime+'s）</span></div>';
			html2 += '</div></div></div></td></tr>';
			return html2;
		},
	},
	service : {
		init : function(){
			aspList.service.pageInit();
			aspList.service.dealSearch();
			aspList.service.dealOperatorLog();
			aspList.service.goToHistory();
			aspList.service.dealViewFlow();
		},
		pageInit : function(){
			var state = $("#statusSelect").val();
			aspList.controller.getList(state);
		},
		dealSearch : function(){
			$(".searchBtn").click(function(){
				var state = $("#statusSelect").val();
				aspList.controller.getList(state);
			});
		},
		dealOperatorLog : function(){
			$(".list_table").on("click",".logDetail",function(){
				var logId = $(this).attr("logAddress");
				var logUrl = "ftp://10.126.59.1/reports/"+logId+"/surefire-reports/html/index.html";
				window.open(logUrl);
			})
		},
		goToHistory : function(){
			$(".search_history_btn").click(function(){
				var aspType = $("#aspType").val();
				window.location.href=contextPath+"/asp/history/list/"+aspType;
			})
		},
		dealViewFlow : function(){
			$(".list_table").on("click",".viewFlow",function(){
				var logId = $(this).attr("logId");
				var aspType = $("#aspType").val();
				var data = aspList.controller.getFlow(logId);
				if(data.length != 0){
					if($(this).parents('tr').next().hasClass('flowTr')){
						$(this).parents('tr').next().remove();
					} else {
						if($(window).width() > 1900) {
							var html = aspList.dao.dealFlowChartBg(data,aspType);
							//console.info("html:"+html);
							$(this).parents('tr').after(html);
							//$(this).parents('tr').after('<tr class="flowTr"><td colspan="6">'+$('#flowChartBigWClone').clone(true)[0].innerHTML+'</td></tr>');
						} else {
							var html = aspList.dao.dealFlowChartSm(data,aspType);
							//console.info("html:"+html);
							$(this).parents('tr').after(html);
							//$(this).parents('tr').after('<tr class="flowTr"><td colspan="6">'+$('#flowChartSmWClone').clone(true)[0].innerHTML+'</td></tr>');
						}
						$(this).parents('tr').next().siblings('.flowTr').remove();
					}
				}
			
			})
		}
		
	},
	controller : {
	    getList : function(state){
	    	var aspType = $("#aspType").val();
	    	$.ajax({
				url:contextPath+'/asp/list/table',
				type:'POST',
				dataType : 'html',	
				data:{type:aspType,state:state},
				success:function(data){
					//console.info(data);
					$(".list_table").empty();
					$(".list_table").append(data);
					
				},
				error:function(){
					alert("操作失败");
				}
				
				
			});
	    },
	  getFlow : function(id){
    	var aspType = $("#aspType").val();
    	var flowDatas = [];
    	$.ajax({
			url:contextPath+'/asp/getFlow',
			type:'POST',
			dataType : 'json',	
			async:false,
			data:{type:aspType,id:id},
			success:function(data){
				console.info(data);
                if(data.code == 1){
                	flowDatas = data.attach;
                }
				
			},
			error:function(){
				alert("操作失败");
			}
			
			
		});
    	
    	return flowDatas;
	  }


	}
};
