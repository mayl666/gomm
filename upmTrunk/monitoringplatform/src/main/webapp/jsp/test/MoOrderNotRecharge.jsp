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

var waitNotPayRun;
//var timeout = false; //启动及关闭按钮  
function timeNotPayRun(){
	addNotPayData();
	waitNotPayRun=setTimeout(timeNotPayRun,1000*60*5); //time是指本身,延时递归调用自己,100为间隔调用时间,单位毫秒
}
//等待时间----时间价格为intervalTime分钟
function waitTimeNotPay(){
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
		timeNotPayRun();
	},waitTime);
}


var notPayDate;
var notPayHighChart;
//初始化默认查询intervalTime  小时
var intervalHour=3;
//默认每5分钟查询查询一次
var intervalMinute=5;
var timeNotPayCon=false;
function initNotPayData(){
	var obj=getSearchHour(intervalHour);
    var startTimeStr=obj.startTime;
    var endTimeStr=obj.endTime;
    reloadNotPayData(startTimeStr,endTimeStr,false);
}
$(function () {
	initNotPay();
	initNotPayData();
});
function addNotPayData(){
	var url=root+"/getNotRechargeOrderForTime.do";
	var data='';
	$.ajax({ 
		url: url,
		type:"POST",
		data:{startTime:notPayDate},
		dataType:"text",
		cache:false,
		success: function(data){
			var obj=eval(data);
			notPayDate=obj[0][0];
			notPayHighChart.series[0].addPoint([obj[0][0], obj[0][1]], false, true);
			notPayHighChart.series[1].addPoint([obj[1][0], obj[1][1]], false, true);
			notPayHighChart.series[2].addPoint([obj[2][0], obj[2][1]], false, true);
			notPayHighChart.redraw();
     	},
     	error : function(){
     	}
	});
}
var options_notPay;
var config_notPay={};
function initNotPay(){
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	options_notPay = {
		colors: ["red","#00EC00","blue","#DDDF0D", "#7798BF", "#55BF3B", "#DF5353", "#aaeeee", "#ff0066", "#eeaaee","#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
        chart: {
            type: 'spline',
            renderTo: 'container_notPay',  //图表放置的容器，一般在html中放置一个DIV，获取DIV的id属性值
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10, //对容器的右内边距
            shadow:true, //外框阴影
            animation:true,
            events: {
                load: function () {
                	waitTimeNotPay();
                }
            }
        },
        title: {
            text: '五分钟非充值订单',
            style:{
                color: 'red',
                font: 'bolder 20px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
            }
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            labels:{ //x轴刻度显示间隔
                step:1,
                style: {
                				//color: '#999',
                				fontWeight: 'bold'
    			      }
            },
            //gridLineWidth:1,
            dateTimeLabelFormats: {
                second: '%H:%M:%S'
            },
            gridLineWidth: 0,
    		//lineColor: '#999',
    		//tickColor: '#999',
    		title: {
    			style: {
    				color: '#AAA',
    				font: 'bolder 12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
                }
            }

        },
        yAxis: {
            /*plotLines: [{  //设置警戒线
                value: 100,
                width: 2,
                color: 'red'
            }]*/
            //gridLineColor: 'rgba(255, 255, 255, .1)',
    		minorGridLineColor: 'rgba(255,255,255,0.07)',
    		lineWidth: 0,
    		tickWidth: 0,
    		labels: {
    			style: {
    				color: '#666',
    				fontWeight: 'bold'
    			}
    		},
    		title: {
    		    text: 'OrderNum',
    			style: {
    				color: '#AAA',
    				font: 'bold 12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
    			}
    		}
        },
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
        credits:{ //右下角超链接设置
            enabled: false
        },
        legend: {//设置图例
            enabled:true,
            layout : 'horizontal',//显示形式，支持水平horizontal(默认)和垂直vertical
            align : 'center',// left/right/center/
            verticalAlign : 'bottom',//bottom  top
            itemStyle: {
    			color: '#666'
    		},
    		itemHoverStyle: {
    			color: 'blue'
    		},
    		itemHiddenStyle: {
    			color: '#333'
    		}
        },
        labels: {
    		style: {
    			color: '#CCC'
    		}
    	},
        exporting: {
            enabled: true
            
        },
        series:[{
        	name: '当前5分钟非充值订单',
        	data:[]
        },{
        	name: '昨天5分钟非充值订单',
        	data:[]
        },{
        	name: '7天前5分钟非充值订单',
        	data:[]
        }]
	}
	copy(config_notPay,options_notPay);
}
function searchNotPayData(){
	//停止5分钟查询
	clearTimeout(waitNotPayRun);
	var startTimeStr=$('#startTimeNotPay').val();
	var endTimeStr=$('#endTimeNotPay').val();
	
	startTimeStr=startTimeStr.replace(/-/g,"/");
 	var oDate1 = new Date(startTimeStr);
 	startTimeStr=oDate1.Format('yyyy-MM-dd HH:m5:s0');
 	
 	endTimeStr=endTimeStr.replace(/-/g,"/");
 	var oDate2 = new Date(endTimeStr);
 	endTimeStr=oDate2.Format('yyyy-MM-dd HH:m5:s0');
 	
 	reloadNotPayData(startTimeStr,endTimeStr,true);
}
function reloadNotPayData(startTimeStr,endTimeStr,waitTimeReload){
	var payUrl=root+"/findNotRechargeOrderList.do";
	$.ajax({
		url: payUrl,
		type:"POST",
		data:{'startTime':startTimeStr,'endTime':endTimeStr},
		dataType:"text",
		cache:false,
		success: function(data){
			var dataList=eval(data);
			notPayDate=dataList[0][dataList[0].length-1][0];
			copy(options_notPay,config_notPay);
			options_notPay.series[0].data=dataList[0];
			options_notPay.series[1].data=dataList[1];
			options_notPay.series[2].data=dataList[2];
			if(waitTimeReload){
				options_notPay.chart.events.load=function(){};
			}
			notPayHighChart = new Highcharts.Chart(options_notPay);
     	},
     	error : function(){
     	}
	});
}

</script>
<body>
<dir>
开始时间：<input type="text" id="startTimeNotPay" value="2016-07-10 09:55:39"/>
结束时间：<input type="text" id="endTimeNotPay" value="2016-07-10 19:55:39"/>
<button onclick="searchNotPayData()" value="查询"/></br>
</dir>
<div id="container_notPay" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>