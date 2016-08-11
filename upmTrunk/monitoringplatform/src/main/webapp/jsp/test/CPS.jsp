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
function getSearchHourCps(){
	var obj=new Object();
	var endTime=new Date();
	obj.endTime=endTime.Format('yyyy-MM-dd HH:mm:ss');
	var startTime=endTime.DateDiff('h',3);
	obj.startTime=startTime.Format('yyyy-MM-dd HH:mm:ss');
	return obj;
}
//每XX时间后加载新节点
var waitCpsTime=1000*60*1;
var waitCpsRun;
//var timeout = false; //启动及关闭按钮  
function timeCpsRun(){
	addCpsData();
	waitCpsRun=setTimeout(timeCpsRun,waitCpsTime); //time是指本身,延时递归调用自己,100为间隔调用时间,单位毫秒
}
//等待时间----时间价格为intervalTime分钟
function waitTimeCps(){
	//等待时间差
	setTimeout(function(){
		timeCpsRun();
	},waitCpsTime);
}

var cpsDate;
var cpsHighChart;
//初始化默认查询intervalTime  小时
var intervalHour=3;
//默认每5分钟查询查询一次
var intervalMinute=5;
var timeCpsCon=false;
function initCpsData(){
	var obj=getSearchHourCps(intervalHour);
    var startTimeStr=obj.startTime;
    var endTimeStr=obj.endTime;
    reloadCpsData(startTimeStr,endTimeStr,false);
}
$(function () {
	initCps();
	initCpsData();
});
function addCpsData(){
	var url=root+"/getCpsDataForTime.do";
	var data='';
	$.ajax({ 
		url: url,
		type:"POST",
		data:{startTime:cpsDate},
		dataType:"text",
		cache:false,
		success: function(data){
			var obj=eval(data);
			cpsDate=obj[0];
			cpsHighChart.series[0].addPoint([obj[0], obj[1]], false, true);
			cpsHighChart.redraw();
     	},
     	error : function(){
     	}
	});
}
var options_cps;
var config_cps={};
function initCps(){
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	options_cps = {
		chart: {
            type: 'spline',
            renderTo: 'container_cps',  //图表放置的容器，一般在html中放置一个DIV，获取DIV的id属性值
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10, //对容器的右内边距
            shadow:true, //外框阴影
            animation:true,
            events: {
                load: function () {
                	waitTimeCps();
                }
            }
        },
        title: {
            text: '五分钟登录'
        },
        xAxis: {
        	type: 'datetime',
            tickPixelInterval: 150,
            dateTimeLabelFormats: {
                second: '%H:%M:%S'
            },
            //categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
	        title: {
	            text: 'Value'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }],
	        title: {
    			style: {
    				color: '#AAA',
    				font: 'bolder 12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
                }
            }
        },
        //显示头
        tooltip: {
            formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                    Highcharts.numberFormat(this.y, 0);
            },
            backgroundColor: {
    			linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
    			stops: [
    				[0, 'rgba(96, 96, 96, .8)'],
    				[1, 'rgba(16, 16, 16, .8)']
    			]
    		},
    		borderWidth: 0,
    		style: {
    			color: '#FFF'
    		}
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series:[{
        	name: '用户',
        	data:[]
        }]
	}
	copy(config_cps,options_cps);
}
function searchCpsData(){
	//停止5分钟查询
	clearTimeout(waitCpsRun);
	var startTimeStr=$('#startTimeCps').val();
	var endTimeStr=$('#endTimeCps').val();
	
	startTimeStr=startTimeStr.replace(/-/g,"/");
 	var oDate1 = new Date(startTimeStr);
 	startTimeStr=oDate1.Format('yyyy-MM-dd HH:m5:s0');
 	
 	endTimeStr=endTimeStr.replace(/-/g,"/");
 	var oDate2 = new Date(endTimeStr);
 	endTimeStr=oDate2.Format('yyyy-MM-dd HH:m5:s0');
 	
 	reloadCpsData(startTimeStr,endTimeStr,true);
}
function reloadCpsData(startTimeStr,endTimeStr,waitTimeReload){
	var payUrl=root+"/findCpsDataList.do";
	$.ajax({
		url: payUrl,
		type:"POST",
		data:{'startTime':startTimeStr,'endTime':endTimeStr},
		dataType:"text",
		cache:false,
		success: function(data){
			var dataList=eval(data);
			cpsDate=dataList[dataList.length-1][0];
			copy(options_cps,config_cps);
			options_cps.series[0].data=dataList;
			if(waitTimeReload){
				options_cps.chart.events.load=function(){};
			}
			cpsHighChart = new Highcharts.Chart(options_cps);
     	},
     	error : function(){
     	}
	});
}

</script>
<body>
<dir>
开始时间：<input type="text" id="startTimeCps" value="2016-07-10 09:55:39"/>
结束时间：<input type="text" id="endTimeCps" value="2016-07-10 19:55:39"/>
<button onclick="searchCpsData()" value="查询"/></br>
</dir>
<div id="container_cps" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>