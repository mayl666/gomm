$(function(){
	var hostid = $('#hostid').val();
	$('#cpu').highcharts(cpuChart);
	$('#memory').highcharts(memoryChart);
	networkMonitor.controller.cpu(hostid);
	networkMonitor.controller.memory(hostid);
});
var categoryLinks = {};

var networkMonitor = {
		service : {
			dealDeviceHealthy : function(error,total){
				$(".health_status_count span:first-child a").text(error);
				$(".health_status_count span:last-child a").text(error);
			},
			memoryChart : function(data){
				var categoryLinksCpu = {};
				var categoryAll = [];
				var series = [];
				for(var serverItem in data){
					var category = [];
					var dataV = [];
					var seriesData = {};//柱形图
					var detailArr = data[serverItem].itemDetailList;
					if(detailArr==null || detailArr==''){
						dataV=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
					}
					for(var detail in detailArr){
						dataV.push(parseFloat(detailArr[detail].value_avg));
						category.push(detailArr[detail].showTime);
					}
					var t = new Date().getTime();
					for(var timeD in dataV){
						d=new Date(t-1000*60*60+1000*timeD*60);
						var m = d.getMinutes();
						if(m>0 && m<10){
							m = "0"+m;
						}
						category.push(d.getHours()+":"+m);
					}
					seriesData.data=dataV;
					seriesData.name = data[serverItem].name;
					categoryAll.push(category);
					series.push(seriesData);
				}
				memoryChart.categoryLinks=categoryLinks;
				memoryChart.series = series;
				memoryChart.xAxis.categories=categoryAll[0];
				console.info(JSON.stringify(memoryChart));
				$('#memory').highcharts(memoryChart);
			},
			cpuChart : function(data){
				var categoryLinksCpu = {};
				var categoryAll = [];
				var series = [];
				for(var serverItem in data){
					var category = [];
					var dataV = [];
					var seriesData = {};//柱形图
					var detailArr = data[serverItem].itemDetailList;
					if(detailArr==null || detailArr==''){
						dataV=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
					}
					for(var detail in detailArr){
						dataV.push(parseFloat(detailArr[detail].value_avg));
						category.push(detailArr[detail].showTime);
					}
					var t = new Date().getTime();
					for(var timeD in dataV){
						d=new Date(t-1000*60*60+1000*timeD*60);
						var m = d.getMinutes();
						if(m>0 && m<10){
							m = "0"+m;
						}
						category.push(d.getHours()+":"+m);
					}
					seriesData.data=dataV;
					seriesData.name = data[serverItem].name;
					categoryAll.push(category);
					series.push(seriesData);
				}
				cpuChart.categoryLinks=categoryLinks;
				cpuChart.series = series;
				cpuChart.xAxis.categories=categoryAll[0];
				console.info(JSON.stringify(cpuChart));
				$('#cpu').highcharts(cpuChart);
			}
		},
		controller : {
			cpu : function(hostid){
				$.ajax({
					url:'../serverAnalysis/getCpuDetailPro',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.cpuChart(data.attach);
						}
					},
					error:function(){
						//layer.msg("操作失败");
					}
				});
			},
			memory : function(hostid){
				$.ajax({
					url:'../serverAnalysis/getMemoryDetailPro',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.memoryChart(data.attach);
						}
					},
					error:function(){
						//layer.msg("操作失败");
					}
				});
			}
		}
};

var cpuChart = {
		title: {
            text: 'CPU使用率(%)'
        },
        credits:{
			enabled:false
		},
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            valueSuffix: '%'
        },
        series: [{
            name: 'ump',
            color:'#9c8ade',
            data: []
        }]
};
var memoryChart = {
		title: {
			text: '内存使用率(%)'
		},
		credits:{
			enabled:false
		},
		xAxis: {
			categories: []
		},
		yAxis: {
			title: {
				text: ''
			}
		},
		legend: {
			enabled: false
		},
		tooltip: {
			valueSuffix: '%'
		},
		series: [{
			name: 'ump',
			color:'#9c8ade',
			data: []
		}]
};
