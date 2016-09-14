$(function(){
//	$('#cpu_use').highcharts(cpuChart);
//	$('#memory_use').highcharts(memoryChart);
//	$('#port_flow').highcharts(flowChart);
//	$('#network_health_index').highcharts(healthyChart);
//	$('#port_health_index').highcharts(portHealthyChart);
    // 去掉这里的注释就是类似 Apple Watch 上的效果了
	//highChartInit();
	networkMonitor.controller.index();
	
});

var categoryLinks = {"10.58.22.252":"/prtg/device/detail?deviceId=2712","10.58.90.78":"/prtg/device/detail?deviceId=4362"};

var networkMonitor = {
		service : {
			init : function(){
				networkMonitor.service.dealHealthyIndexChart();
			},
			dealHealthy : function(errorDeviceCount,totalDeviceCount,errorSensorCount,totalSensorCount,alarmPortCount){
				$("#errorDeviceCount").text(errorDeviceCount);
				$("#totalDeviceCount").text(totalDeviceCount);
				$("#errorSensorCount").text(errorSensorCount);
				$("#totalSensorCount").text(totalSensorCount);
				$("#healthyDeviceIndex").text(((totalDeviceCount-errorDeviceCount)/totalDeviceCount).toPrecision(2)*100);
				$("#healthySensorIndex").text(((totalSensorCount-errorSensorCount)/totalSensorCount).toPrecision(2)*100);
				$("#alarmPortCount").text(alarmPortCount);
				//$(".health_status_count span:first-child a").text(error);
			   // $(".health_status_count span:last-child a").text(total);
			   // $(".topView h2 span").text(total-error);
			},
			dealChartCommon : function(data, chart){
				var seriesData = [];//柱形图
				var category = [];
				for(var top in data){
					var sensor = data[top];
					var yText = {};
					yText.name = sensor.sensorName;
					yText.y = sensor.lastvalueUse;
					yText.url = contextPath+'/prtg/sensor/detail?sensorId='+sensor.sensorId+'&deviceId='+sensor.deviceId;
				  seriesData.push(yText);
				  var xurl = 'http://'+window.location.host+contextPath+'/prtg/device/detail?deviceId='+sensor.deviceId;
				  category.push('<a href="'+xurl+'">'+sensor.host+'</a>');
				} 
				chart.series[0].data=seriesData;
				chart.xAxis.categories=category;
				//console.info(JSON.stringify(chart));
				return chart;
			},
			dealCpuChart : function(data, chart){
				var cpuChart = networkMonitor.service.dealChartCommon(data, chart);
				$('#cpu_use').highcharts(cpuChart);
			},
			dealMemoryChart : function(data, chart){
				var memoryChart = networkMonitor.service.dealChartCommon(data, chart);
				$('#memory_use').highcharts(memoryChart);
			},
			dealFlowChart : function(data, chart){
				var flowChart = networkMonitor.service.dealChartCommon(data, chart);
				$('#port_flow').highcharts(flowChart);
			},
			dealHealthyDeviceChart : function(total,error){
				deviceHealthyChart.series[0].data[0].y=total;
				deviceHealthyChart.series[0].data[1].y=error;
				$('#network_health_index').highcharts(deviceHealthyChart);
			},
			dealHealthySensorChart : function(total,error){
				portHealthyChart.series[0].data[0].y=total;
				portHealthyChart.series[0].data[1].y=error;
				$('#port_health_index').highcharts(portHealthyChart);
			},
			dealHealthyDeviceChart2 : function(total,error){
				var healthyIndex = ((total-error)/total).toPrecision(3)*100+"";
				deviceHealthyChart2.series[0].data[0].y=parseInt(healthyIndex);
				$('#network_health_index').highcharts(deviceHealthyChart2);
			},
			dealHealthySensorChart2 : function(total,error){
				var healthyIndex = ((total-error)/total).toPrecision(3)*100+"";
				portHealthyChart2.series[0].data[0].y=parseInt(healthyIndex);
				$('#port_health_index').highcharts(portHealthyChart2);
			}
		},
		controller : {
			index : function(){
				$.ajax({
					url:contextPath+'/prtg/index/ajax',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							//console.info(data.attach);
							var attach = data.attach;
							//var attach = JSON.parse(data.attach);
							//var attach = eval("(" + data.attach + ")");
							networkMonitor.service.dealHealthy(attach.errorDeviceCount,attach.totalDeviceCount,attach.errorSensorCount,attach.totalSensorCount,attach.alarmPortCount);
							networkMonitor.service.dealCpuChart(attach.cpu,cpuChart);
							networkMonitor.service.dealMemoryChart(attach.memory,memoryChart);
							networkMonitor.service.dealFlowChart(attach.flow,flowChart);
							networkMonitor.service.dealHealthyDeviceChart2(attach.totalDeviceCount,attach.errorDeviceCount);
							networkMonitor.service.dealHealthySensorChart2(attach.totalSensorCount,attach.errorSensorCount);
						}
					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
			}
		}
};





var cpuChart = {
		chart: {
            type: 'column',
            backgroundColor:"#FFF"
        },
        title: {
            text: 'CPU使用率TOP5'
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        xAxis: {
            categories: [
				'0.0.0.0',
				'0.0.0.0',
				'0.0.0.0',
				'0.0.0.0',
				'0.0.0.0'
            ],
            labels: {
                rotation: -45, 
                style: { 
                     fontSize: '13px', 
                     margin:'auto',
                     fontFamily: 'Verdana, sans-serif'
                     /*writingMode:'tb-rl'  */  //文字竖排样式  //by wangxiaye 样式调整，谷歌浏览器无法打开链接问题
                }
//                formatter: function () {
//                    return '<a href="' + categoryCpuLinks[this.value] + '">' +
//                        this.value + '</a>';
//                }

             }
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
                '<td style="padding:0"><b>{point.y} %</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                            location.href = this.options.url;
                        }
                    }
                }
            }
        },
        series: [{
            name: 'cpu使用率',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            
        }]
};

var memoryChart = {
		chart: {
            type: 'column',
            backgroundColor:"#FFF"
        },
        title: {
            text: '内存使用率TOP5'
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        xAxis: {
            categories: [
                '0.0.0.0',
                '0.0.0.0',
                '0.0.0.0',
                '0.0.0.0',
                '0.0.0.0'
            ],
            labels: {
                rotation: -45, 
                style: { 
                     fontSize: '13px', 
                     fontFamily: 'Verdana, sans-serif'
//                     writingMode:'tb-rl'    //文字竖排样式
                }
             }
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
                '<td style="padding:0"><b>{point.y} %</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                            location.href = this.options.url;
                        }
                    }
                }
            }
        },
        series: [{
            name: '内存使用率',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            cursor: 'pointer'
          
        }]
};


var flowChart = {
		chart: {
            type: 'column',
            backgroundColor:"#FFF"
        },
        title: {
            text: '端口流量TOP5'
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        xAxis: {
            categories: [
				'0.0.0.0',
				'0.0.0.0',
				'0.0.0.0',
				'0.0.0.0',
				'0.0.0.0'
            ],
            labels: {
                rotation: -45, 
                style: { 
                     fontSize: '13px', 
                     fontFamily: 'Verdana, sans-serif',
//                     writingMode:'tb-rl'    //文字竖排样式
                }
             }
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
                '<td style="padding:0"><b>{point.y} KB/秒</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                            location.href = this.options.url;
                        }
                    }
                }
            }
        },
        series: [{
            name: '端口流量',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            cursor: 'pointer'
            
        }]
};

var deviceHealthyChart = {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        title: {
            text: ''
        },
        tooltip: {
    	    pointFormat: ' <b>{point.percentage:.1f} %</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                size : 200,
                dataLabels: {
                    enabled: false,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '',
            data: [
                {
                 	name:'正常率',   
                    y:100,
                    color:'#9c8ade'
                },
                {
                    name: '错误率',
                    y: 0,
                    sliced: true,
                    selected: true,
                    color:'#CFCFCF'
                }
            ]
        }]
    };


var portHealthyChart = {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        title: {
            text: ''
        },
        tooltip: {
    	    pointFormat: '<b>{point.percentage:.1f} %</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                size : 200,
                dataLabels: {
                    enabled: false,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '健康指数',
            data: [
                {
                 	name:'正常率',   
                    y:100,
                    color:'#9c8ade'
                },
                {
                    name: '错误率',
                    y: 0,
                    sliced: true,
                    selected: true,
                    color:'#CFCFCF'
                }
            ]
        }]
    };



var portHealthyChart2 = {
        chart: {
            type: 'solidgauge',
            marginTop: 50
        },
        credits: {
            text: '',
            //href: 'http://www.hcharts.cn'
        },
        title: {
            text: '',
            style: {
                fontSize: '24px'
            }
        },
        tooltip: {
            enabled: false
            /*borderWidth: 0,
            backgroundColor: 'none',
            shadow: false,
            style: {
                fontSize: '16px'
            },
            pointFormat: '{series.name}<br><span style="font-size:2em; color: {point.color}; font-weight: bold">{point.y}%</span>',
            positioner: function (labelWidth, labelHeight) {
                return {
                    x: 200 - labelWidth / 2,
                    y: 180
                };
            }*/
        },
        pane: {
            startAngle: 0,
            endAngle: 360,
            background: [{ // Track for Move
                outerRadius: '112%',
                innerRadius: '88%',
                backgroundColor:"#fff",
                //backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.3).get(),
                borderWidth: 0
            }]
        },
        yAxis: {
            min: 0,
            max: 100,
            lineWidth: 0,
            tickPositions: []
        },
        plotOptions: {
            solidgauge: {
                borderWidth: '18px',
                dataLabels: {
                    enabled: true,
                    borderRadius:'none',
                    borderWidth:'0',
                    //verticalAlign:'center',
                    rotation: -360,
                    x: 2,
                    y: -10,
                    style:{
                        fontSize:'14px',
                        color:"#999",
                        textShadow:'',
                        textAlign:'center',
                        fontWeight: "0",
                        fontFamily:"Microsoft Yahei"
                    },
                    // y:'15',
                    formatter:function(){
                        return '<div>'+'<p style="color:#672795;font-size:28px;font-weight: 600;">'+this.y+'</p>'+'<br>'+'<p>'+'健康指数'+'</p>'+'</div>'
                    }
                },
                linecap: 'round',
                stickyTracking: false
            }
        },
        series: [{
            name: 'Move',
            borderColor: "#9c8ade",
            data: [{
                color: "#9c8ade",
                radius: '100%',
                innerRadius: '100%',
                y: 59
            }]
        }]
    }

var deviceHealthyChart2 = {
        chart: {
            type: 'solidgauge',
            marginTop: 50
        },
        credits: {
            text: '',
            //href: 'http://www.hcharts.cn'
        },
        title: {
            text: '',
            style: {
                fontSize: '24px'
            }
        },
        tooltip: {
            enabled: false
            /*borderWidth: 0,
            backgroundColor: 'none',
            shadow: false,
            style: {
                fontSize: '16px'
            },
            pointFormat: '{series.name}<br><span style="font-size:2em; color: {point.color}; font-weight: bold">{point.y}%</span>',
            positioner: function (labelWidth, labelHeight) {
                return {
                    x: 200 - labelWidth / 2,
                    y: 180
                };
            }*/
        },
        pane: {
            startAngle: 0,
            endAngle: 360,
            background: [{ // Track for Move
                outerRadius: '112%',
                innerRadius: '88%',
                backgroundColor:"#fff",
                //backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.3).get(),
                borderWidth: 0
            }]
        },
        yAxis: {
            min: 0,
            max: 100,
            lineWidth: 0,
            tickPositions: []
        },
        plotOptions: {
            solidgauge: {
                borderWidth: '18px',
                dataLabels: {
                    enabled: true,
                    borderRadius:'none',
                    borderWidth:'0',
                    //verticalAlign:'center',
                    rotation: -360,
                    x: 2,
                    y: -10,
                    style:{
                        fontSize:'14px',
                        color:"#999",
                        textShadow:'',
                        textAlign:'center',
                        fontWeight: "0",
                        fontFamily:"Microsoft Yahei"
                    },

                    // y:'15',
                    formatter:function(){
                        return '<div>'+'<p style="color:#672795;font-size:28px;font-weight: 600;">'+this.y+'</p>'+'<br>'+'<p>'+'健康指数'+'</p>'+'</div>'
                    }
                },
                linecap: 'round',
                stickyTracking: false
            }
        },
        series: [{
            name: 'Move',
            borderColor: "#9c8ade",
            data: [{
                color: "#9c8ade",
                radius: '100%',
                innerRadius: '100%',
                y: 59
            }]
        }]
    }

