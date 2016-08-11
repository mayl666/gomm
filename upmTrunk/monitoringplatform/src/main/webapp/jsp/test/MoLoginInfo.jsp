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

var waitLoginInfoRun;
//var timeout = false; //启动及关闭按钮  
function timeLoginInfoRun(){
	addLoginInfoData();
	waitLoginInfoRun=setTimeout(timeLoginInfoRun,1000*60*5); //time是指本身,延时递归调用自己,100为间隔调用时间,单位毫秒
}
//等待时间----时间价格为intervalTime分钟
function waitTimeLoginInfo(){
	//获取结束时间
	var endTimeNow=new Date();
	var endTimeStr=endTimeNow.Format('yyyy/MM/dd HH:m5:s0');
	var wwww=new Date(endTimeStr);
	//下个触发查询时间--5分钟后
	wwww=wwww.DateAdd('n',5);
	//alert(wwww.Format('yyyy-MM-dd HH:mm:ss'));
	//计算当前时间和下个查询时间的时间差
	var waitTime=wwww-new Date();
	//等待时间差
	setTimeout(function(){
		timeLoginInfoRun();
	},waitTime);
}


var loginInfoDate;
var loginInfoHighChart;
//初始化默认查询intervalTime  小时
var intervalHour=3;
//默认每5分钟查询查询一次
var intervalMinute=5;
var timeLoginInfoCon=false;
function initLoginInfoData(){
	var obj=getSearchHour(intervalHour);
    var startTimeStr=obj.startTime;
    var endTimeStr=obj.endTime;
    reloadLoginInfoData(startTimeStr,endTimeStr,false);
}
$(function () {
	initLoginInfo();
	initLoginInfoData();
});
function addLoginInfoData(){
	var url=root+"/getLoginInfoForTime.do";
	var data='';
	$.ajax({ 
		url: url,
		type:"POST",
		data:{startTime:loginInfoDate},
		dataType:"text",
		cache:false,
		success: function(data){
			var obj=eval(data);
			loginInfoDate=obj[0];
			loginInfoHighChart.series[0].addPoint([obj[0], obj[1]], false, true);
			loginInfoHighChart.redraw();
     	},
     	error : function(){
     	}
	});
}
var options_loginInfo;
var config_loginInfo={};
function initLoginInfo(){
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	options_loginInfo = {
		chart: {
            type: 'spline',
            renderTo: 'container_loginInfo',  //图表放置的容器，一般在html中放置一个DIV，获取DIV的id属性值
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10, //对容器的右内边距
            shadow:true, //外框阴影
            animation:true,
            events: {
                load: function () {
                	waitTimeLoginInfo();
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
	copy(config_loginInfo,options_loginInfo);
}
function searchLoginInfoData(){
	//停止5分钟查询
	clearTimeout(waitLoginInfoRun);
	var startTimeStr=$('#startTimeLoginInfo').val();
	var endTimeStr=$('#endTimeLoginInfo').val();
	
	startTimeStr=startTimeStr.replace(/-/g,"/");
 	var oDate1 = new Date(startTimeStr);
 	startTimeStr=oDate1.Format('yyyy-MM-dd HH:m5:s0');
 	
 	endTimeStr=endTimeStr.replace(/-/g,"/");
 	var oDate2 = new Date(endTimeStr);
 	endTimeStr=oDate2.Format('yyyy-MM-dd HH:m5:s0');
 	
 	reloadLoginInfoData(startTimeStr,endTimeStr,true);
}
function reloadLoginInfoData(startTimeStr,endTimeStr,waitTimeReload){
	var payUrl=root+"/findLoginInfoList.do";
	$.ajax({
		url: payUrl,
		type:"POST",
		data:{'startTime':startTimeStr,'endTime':endTimeStr},
		dataType:"text",
		cache:false,
		success: function(data){
			var dataList=eval(data);
			loginInfoDate=dataList[dataList.length-1][0];
			copy(options_loginInfo,config_loginInfo);
			options_loginInfo.series[0].data=dataList;
			if(waitTimeReload){
				options_loginInfo.chart.events.load=function(){};
			}
			loginInfoHighChart = new Highcharts.Chart(options_loginInfo);
     	},
     	error : function(){
     	}
	});
}

</script>
<body>
<dir>
开始时间：<input type="text" id="startTimeLoginInfo" value="2016-07-10 09:55:39"/>
结束时间：<input type="text" id="endTimeLoginInfo" value="2016-07-10 19:55:39"/>
<button onclick="searchLoginInfoData()" value="查询"/></br>
</dir>
<div id="container_loginInfo" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>