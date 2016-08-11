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
	initEc();
	initEcData();
});

function initEcData(){
	//开始时间
	var startDate = new Date();
 	var startTimeStr=startDate.Format('yyyy-MM-dd HH:mm:ss');
	//结束时间
	var endDate=startDate.DateAdd('h',3);
 	var endTimeStr=endDate.Format('yyyy-MM-dd HH:mm:ss');
 	reloadEcData(startTimeStr,endTimeStr);
}
var highChart_Ec;
var options_Ec;
var config_Ec={};
function initEc(){
	options_Ec = {
			chart: {
	            type: 'column',
	            renderTo: 'highchart_ec'
	        },
	        title: {
	            text: 'EC'
	        },
	        credits:{
	            enabled:false
	        },
	        xAxis: {
	            categories: [
	                '正向单停在CO的订单<br>-待客服处理',
	                '正向单停在CO的订单<br>-未发送SO（其他）',
	                '正向单订单数量',
	                '逆向单订单数量',
	                '正向单停在CO的订单<br>-总数'
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
	copy(config_Ec,options_Ec);
}
function searchEcData(){
	var startTimeStr=$('#startTimeEc').val();
	var endTimeStr=$('#endTimeEc').val();
 	reloadEcData(startTimeStr,endTimeStr);
}
function reloadEcData(startTimeStr,endTimeStr){
	var url=root+"/getEcData.do";
	$.ajax({
		url: url,
		type:"POST",
		data:{'startTime':startTimeStr,'endTime':endTimeStr},
		dataType:"text",
		cache:false,
		success: function(data){
			var dataList=eval(data);
			copy(options_Ec,config_Ec);
			options_Ec.series[0].data=dataList;
			highChart_Ec = new Highcharts.Chart(options_Ec);
     	},
     	error : function(){
     	}
	});
}
</script>
<body>
<dir>
开始时间：<input type="text" id="startTimeEc" value="2016-07-10 09:55:39"/>
结束时间：<input type="text" id="endTimeEc" value="2016-07-10 19:55:39"/>
<button onclick="searchEcData()" value="查询"/></br>
</dir>
<div id="highchart_ec" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>