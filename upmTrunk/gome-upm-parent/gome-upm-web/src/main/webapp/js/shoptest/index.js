$(function(){
	shop_index.service.index();
});

var shop_index = {
	service : {
		init : function(){
			shop_index.init.index();
		},
		index : function(){
			shop_index.controller.index();
		},
		dealHealthyNormalChart : function(total,error){
			var healthyIndex = ((total-error)/total).toPrecision(3)*100+"";
			normalHealthyChart.series[0].data[0].y=parseInt(healthyIndex);
			$('#network_health_index').highcharts(normalHealthyChart);
		},
		dealHealthySearchrChart : function(total,error){
			var healthyIndex = ((total-error)/total).toPrecision(3)*100+"";
			searchHealthyChart.series[0].data[0].y=parseInt(healthyIndex);
			$('#port_health_index').highcharts(searchHealthyChart);
		},
		dealHealthy : function(totalFailCount1,totalFailCount2,totalCount){
			$("#errorDeviceCount").text(totalFailCount1);
			$("#totalDeviceCount").text(totalCount-totalFailCount1);
			$("#errorSensorCount").text(totalFailCount2);
			$("#totalSensorCount").text(totalCount-totalFailCount2);
		},
		dealNormalNodesFailChart : function(data){
			normalNodesFailedChart.series[0].data=data;
			$('#cpu_use').highcharts(normalNodesFailedChart);
		},
		dealSearchNodesFailChart : function(data){
			searchNodesFailedChart.series[0].data=data;
			$('#memory_use').highcharts(searchNodesFailedChart);
		},
		dealTimeUsedChart : function(categories, dataNormal, dataSearch){
			timeUsedChart.xAxis.categories=categories;
			timeUsedChart.series[0].data=dataNormal;
			timeUsedChart.series[1].data=dataSearch;
			console.info(timeUsedChart);
			$('#time_use').highcharts(timeUsedChart);
		}
	},
	controller : {
		index : function(){
			$.ajax({
				url:contextPath+'/asp/index/ajax',
				type:'POST',
				dataType : 'json',	
				data:{},
				success:function(data){
					if(data.code == 1){
						//请求成功
						console.info(data.attach);
						var attach = data.attach;
						//var attach = JSON.parse(data.attach);
						//var attach = eval("(" + data.attach + ")");
						shop_index.service.dealHealthyNormalChart(attach.totalCount, attach.totalFailCount1);
						shop_index.service.dealHealthySearchrChart(attach.totalCount, attach.totalFailCount2);
						shop_index.service.dealHealthy(attach.totalFailCount1, attach.totalFailCount2, attach.totalCount);
						shop_index.service.dealNormalNodesFailChart(attach.nodeFailCount1);
						shop_index.service.dealSearchNodesFailChart(attach.nodeFailCount2);
						shop_index.service.dealTimeUsedChart(attach.createTime,attach.proTime1,attach.proTime2);
					}
				},
				error:function(){
					alert("操作失败");
				}
				
				
			});
		}
		
	}
}



//主流程通过率
var normalHealthyChart = {
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
                        return '<div>'+'<p style="color:#672795;font-size:28px;font-weight: 600;">'+this.y+'</p>'+'<br>'+'<p>'+'通过率'+'</p>'+'</div>'
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
    };


//搜索流程chart
var searchHealthyChart = {
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
                        return '<div>'+'<p style="color:#672795;font-size:28px;font-weight: 600;">'+this.y+'</p>'+'<br>'+'<p>'+'通过率'+'</p>'+'</div>'
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
    };


var timeUsedChart = {
        title: {
            text: '耗时'
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
                rotation: -120, 
                style: { 
                     fontSize: '13px', 
                     fontFamily: 'Verdana, sans-serif',
                     writingMode:'tb-rl'    //文字竖排样式
                }
             }
        },
        yAxis: {
            min: 0,
            title: {
                text: '分钟'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} 分钟</b></td></tr>',
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
            name: '主流程下单耗时',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        },
        {
            name: '搜索流程下单耗时',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };




var normalNodesFailedChart = {
		chart: {
            type: 'column',
            backgroundColor:"#FFF"
        },
        title: {
            text: '下单主流程'
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        xAxis: {
            categories: [
                '清除Cookie',
                '登录',
                '清空购物车',
                '加入购物车',
                '结算',
                '支付宝支付',
                '银联支付',
                '快钱支付',
                '取消订单'
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
                text: '次数'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} </b></td></tr>',
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
            name: '主流程节点失败次数',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            cursor: 'pointer'
          
        }]
};




var searchNodesFailedChart = {
		chart: {
            type: 'column',
            backgroundColor:"#FFF"
        },
        title: {
            text: '搜索下单主流程'
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        xAxis: {
            categories: [
                '清除Cookie',
                '登录',
                '清空购物车',
                '搜索',
                '加入购物车结算',
                '银联支付',
                '支付宝支付',
                '快钱支付',
                '取消订单'
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
                text: '次数'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} </b></td></tr>',
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
            name: '搜索下单流程节点失败次数',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            cursor: 'pointer'
          
        }]
};

