<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/page/jqueryMaster.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=root%>/common/js/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=root%>/common/js/Date.js"></script>
<script type="text/javascript" src="<%=root%>/common/js/monito.js"></script>
<script type="text/javascript" src="<%=root%>/jquery-2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=root%>/highcharts/js/highcharts.js"></script>
<title>Insert title here</title>
</head>
<script type="text/javascript">
$(function () {
	initDragon();
	initDragonData();
});

function initDragonData(){
	//开始时间
	var startDate = new Date();
 	var startTimeStr=startDate.Format('yyyy-MM-dd HH:mm:ss');
	//结束时间
	var endDate=startDate.DateAdd('h',3);
 	var endTimeStr=endDate.Format('yyyy-MM-dd HH:mm:ss');
 	reloadDragonData(startTimeStr,endTimeStr);
}
var highChart_dragon;
var options_dragon;
var config_dragon={};

function initDragon(){
	options_dragon = {
        chart: {
            type: 'column',
            renderTo: 'highchart_DRAGON'
        },
        title: {
            text: 'DRAGON'
        },
        credits:{
            enabled:false
        },
        xAxis: {
            categories: [
                'G3PP真预留状态大量停在NA',
                'G3PP真预留状态停在DH',
                'G3PP订单状态大量停在PR',
                'G3PP订单状态停在DH',
                'G3PP订单状态停在CCO'
            ]
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} 个</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '订单',
            data: []
        }]
	}
	copy(config_dragon,options_dragon);
}
function searchDragonData(){
	var startTimeStr=$('#startTimeDragon').val();
	var endTimeStr=$('#endTimeDragon').val();
 	reloadDragonData(startTimeStr,endTimeStr);
}
function reloadDragonData(startTimeStr,endTimeStr){
	var url=root+"/getDragonData.do";
	$.ajax({
		url: url,
		type:"POST",
		data:{'startTime':startTimeStr,'endTime':endTimeStr},
		dataType:"text",
		cache:false,
		success: function(data){
			var dataList=eval(data);
			//copy(options_vali,config_tem);
			copy(options_dragon,config_dragon);
			options_dragon.series[0].data=dataList;
			highChart_dragon = new Highcharts.Chart(options_dragon);
     	},
     	error : function(){
     	}
	});
}
</script>
<body>
<dir>
开始时间：<input type="text" id="startTimeDragon" value="2016-07-10 09:55:39"/>
结束时间：<input type="text" id="endTimeDragon" value="2016-07-10 19:55:39"/>
<button onclick="searchDragonData()" value="查询"/></br>
</dir>
<div id="highchart_DRAGON" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>