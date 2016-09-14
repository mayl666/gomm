$(function(){
	var hostid = $('#hostid').val();
	$('#cpu_memory').highcharts(cpu_memoryChart);
	$('#tcp').highcharts(tcpChart);
	networkMonitor.controller.cpu_memory(hostid);
	networkMonitor.controller.tcp(hostid);
});
var categoryLinks = {};

var networkMonitor = {
		service : {
			dealDeviceHealthy : function(error,total){
				$(".health_status_count span:first-child a").text(error);
				$(".health_status_count span:last-child a").text(error);
			},
			tcpChart : function(data){
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
						dataV.push(parseFloat(detailArr[detail].value_max));
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
				tcpChart.categoryLinks=categoryLinks;
				tcpChart.series = series;
				tcpChart.xAxis.categories=categoryAll[0];
				console.info(JSON.stringify(tcpChart));
				$('#tcp').highcharts(tcpChart);
			},
			cpu_memoryChart : function(data){
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
						dataV.push(parseFloat(detailArr[detail].value_max));
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
				cpu_memoryChart.categoryLinks=categoryLinks;
				cpu_memoryChart.series = series;
				cpu_memoryChart.xAxis.categories=categoryAll[0];
				console.info(JSON.stringify(cpu_memoryChart));
				$('#cpu_memory').highcharts(cpu_memoryChart);
			}
		},
		controller : {
			cpu_memory : function(hostid){
				$.ajax({
					url:'../serverAnalysis/getCpuMemoryDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.cpu_memoryChart(data.attach);
						}
					},
					error:function(){
						//layer.msg("操作失败");
					}
				});
			},
			tcp : function(hostid){
				$.ajax({
					url:'../serverAnalysis/getTCPDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.tcpChart(data.attach);
						}
					},
					error:function(){
						//layer.msg("操作失败");
					}
				});
			}
		}
};

var cpu_memoryChart = {
		title: {
            text: 'CPU&内存使用率(%)'
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
var tcpChart = {
		 title: {
	            text: 'Tcp连接数(Conns)'
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
	            valueSuffix: 'Conns'
	        },
	        series: [{
	            name: 'ump',
	            color:'#9c8ade',
	            data: []
	        }]
};
