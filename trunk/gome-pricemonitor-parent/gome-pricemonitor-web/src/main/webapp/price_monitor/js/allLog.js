/**
 * Created by hanpan on 2016/4/5.
 */
$(function(){
	
	$("body").click(function(e){
		var className = e.target.className;
		$(".cs-options").hide();
		if(className == 'fa fa-angle-down'){
			$(e.target).siblings("div").show();
		}
	});
	
//	$(".cs-options").mouseleave(function(){
//		$(".cs-options").hide();
//	});

	
   // $('.select_row').delegate('.cs-options','click',function(){
   //     $(this).hide();
   // });

//自定义下拉框
   $(".fa.fa-angle-down").each(function(i,o){

       $(o).click(function(){

           $(this).siblings(".cs-options").slideToggle("fast");
       });
   });
   
   
/*   $(".fa.fa-angle-down").each(function(i,o){

       $(o).blur(function(o){
               $(this).siblings(".cs-options").slideToggle("fast");
          });
   });*/


    $(".cs-options ul li").each(function(i,o){

        $(this).click(function(){
         //父级阶段获得当前的ｖａｌ值
          //  隐藏ul
        	var spanVal=$(this).text();
            var titled = $(this).attr("titled");
            //console.log(spanVal);
            $(this).parent().parent().siblings("span").text(spanVal);
            $(this).parent().parent().hide();
            $(this).parent().parent().siblings("input").val(titled);
        });
    });



    //点击显示隐藏更多选项
    $(".operate").click(function(){
        $("#hight_select").toggle();
        hight_search_show();
    });
    
    
    $(".allLogSty").click(function(){
    	window.location.href=contextPath+"/price/monitor/all";
    });
    
    
    
    
    $(".recet_btn").click(function(){
    	$("#skuNo").val("");
    	$("#typeCode").val("");
    	$(".typeCode").text("价格类型");
    	$("#dResult").val("");
    	$(".dResult").text("处理结果");
    	$("#beginTime").val("");
    	$("#endTime").val("");
    	$("#dealStatus").val("");
    	$(".dealStatus").text("处理状态");
    	$("#areaCode").val("");
    	$("#currentNode").val("");
    	$(".currentNode").text("当前节点");
    	$("#currentAction").val("");
    	$(".currentAction").text("处理动作");
    	$("#pageNo").val("1");
    });
    
    
    $(".search_btn").click(function(){
    	$("#pageNo").val("1");
    	//queryList();
    	queryListForAjax("1");
     });
    

    initSearchTime();
    
});



function initSearchTime(){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	if(beginTime == "" || endTime == ""){
		$("#beginTime").val(getNowDate()+" 00:00:00");
		$("#endTime").val(getNowDate()+" 23:59:59");
		
	}
	
}

function getNowDate(){
	var date = new Date();
	return date.format("yyyy-MM-dd");
}





function isNum(num)
{
   var reNum =/^\d*$/;
   return (reNum.test(num));
}





var flag=true;
function hight_search_show(){
	if(flag){
		$("#superSearch b").text("收起");
		$("#superSearch i").attr("class","fa fa-angle-double-up");
		flag=false;
	}else{
		$("#superSearch b").text("高级搜索");
		$("#superSearch i").attr("class","fa fa-angle-double-down");
		flag=true;
	}
}

function doCompareTime(beginTime, endTime){
	if(beginTime=='' && endTime == ''){
		return true;
	}
	if(beginTime==''){
		alert("请正确输入开始时间");
		return false;
	}
	
	if(endTime == ''){
		alert("请正确输入截止时间");
		return false;
	}
	
	var startDate = beginTime.split(" ")[0];
	var endDate = endTime.split(" ")[0];
	if(startDate != endDate){
		alert("开始时间和截止时间必须是同一天");
		return false;
	}
	
	var beginDate = new Date(beginTime.replace(/-/g,"/"));
	var endDate = new Date(endTime.replace(/-/g,"/"));
	if(beginDate >= endDate){
		alert("截止时间必须大于开始时间");
		return false;
	}
	return true;
	
}


function getStartDate(){
	return "2016-06-18 12:12:12";
}


var queryListForAjax = function(pageNo){
	
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	if(!doCompareTime(beginTime, endTime)){
		return;
	}
	var content = {};
	content.skuNo=$("#skuNo").val();
	content.type=$("#typeCode").val();
	content.result=$("#dResult").val();
	content.beginTime=$("#beginTime").val();
	content.endTime=$("#endTime").val();
	content.areaCode=$("#areaCode").val();
	content.node=$("#currentNode").val();
	content.action=$("#currentAction").val();
	content.status=$("#dealStatus").val();
	//content.pageNo=$("#pageNo").val();
	content.pageNo=pageNo;
	$.ajax({
		url:contextPath+'/price/monitor/listTable',
		type:'POST',
		dataType : 'html',	
		data:{'content':JSON.stringify(content)},
		success:function(data){
			var bool = data.indexOf("login_warp");
			//alert("bool:"+bool);
			if(bool > 0){
				window.location.href=contextPath+"/login";
			}else{
				$(".log_data_warp").empty();
				$(".log_data_warp").append(data);
			}

		},
		error:function(){
			alert("操作失败");
			
		}
		
		
	});
	
}



var queryListForPageDetail = function(pageNo){
	
	var content = {};
	content.skuNo=$("#skuParam").val();
	content.type=$("#typeParam").val();
	content.result=$("#resultParam").val();
	content.beginTime=$("#beginTimeParam").val();
	content.endTime=$("#endTimeParam").val();
	content.areaCode=$("#areaCodeParam").val();
	content.node=$("#nodeParam").val();
	content.action=$("#actionParam").val();
	content.status=$("#statusParam").val();
	//content.pageNo=$("#pageNo").val();
	content.pageNo=pageNo;
	$.ajax({
		url:contextPath+'/price/monitor/listTable',
		type:'POST',
		dataType : 'html',	
		data:{'content':JSON.stringify(content)},
		success:function(data){
			var bool = data.indexOf("login_warp");
			//alert("bool:"+bool);
			if(bool > 0){
				window.location.href=contextPath+"/login";
			}else{
				$(".log_data_warp").empty();
				$(".log_data_warp").append(data);
			}

		},
		error:function(){
			alert("操作失败");
			
		}
		
		
	});
	
	
	
}