$(function() {
	var normal = $("#normal_id").text();
	var error = $("#error_id").text();
	draw_url_health_index(parseInt(normal), parseInt(error));
	// 删除url
	$("#btn-del").click(function() {
		allUrl.controller.delUrl();
	});
	$("#btn_search").click(function() {
		allUrl.controller.search();
	});
	

	$("#defaultReturnCode").on("click", function() {
		allUrl.service.defaultReturnCode();
	});

	$("#customReturnCode").on("click", function() {
		allUrl.service.customReturnCode();
	});
	
	allUrl.service.init();

});
function openReport(id){
	var pageNum = $("#pagination-digg").find(".active").text();
	var search = $("#hidden_search").val();
	window.location.href =contextPath+"/url/report?id="+id+"&pageNum="+pageNum+"&search="+search;
}
function regCode(){
	var returnCode= $("#returnCode").val();
		if(returnCode.length==0){
			layer.msg("URL请求返回码不能为空");
			$("#returnCode").focus();
			return false;
		}else{
			var reg= /(([2-9]\d{2})+[,]?)+/;
			var out= reg.test(returnCode);
			if(!out){
				layer.msg("URL请求返回码格式错误");
				$("#returnCode").focus();
				return false;
			}
			var s =returnCode.split(",")
			if(s.length>0){
				for(var i=0;i<s.length;i++){
					if(s[i]>=200&&s[i]<=600){
						
					}else{
						layer.msg("URL请求返回码格式错误");
						$("#returnCode").focus();
						return false;
					}
				}
			}else{
				if(returnCode>=200&&returnCode<=600){
					
				}else{
					layer.msg("URL请求返回码格式错误");
					$("#returnCode").focus();
					return false;
				}
			}
			return true;
		}
}
function change(count){
	if(count==1){
		$("#post_param").show();
	}else{
		$("#post_param").hide();
		$("#postParameter").text("");
	}
}
function importExcel(){
	var excel = $("#excel").val();
	if(isExcel(excel)){
		$("#import_close").click();
		layer.msg("导入中请等待...", {shade: [0.5, '#000'],scrollbar: false,offset: '50%', time:1000},function(){
			$.ajaxFileUpload({
		                url: contextPath+'/url/import',     //需要链接到服务器地址
		                secureuri: false,
		                type:'post',
		                fileElementId: 'excel',              //文件选择框的id属性
		                contentType:'text/html',
		                dataType:'text',
		                success: function (obj) {          //相当于java中try语句块的用法
		                	
							layer.msg(obj, {shade: [0.5, '#000'],scrollbar: false,offset: '50%', time:3000},function(){
								window.location.href=contextPath+"/url/get";
							});
							
		                },
		                error: function (data, status, e) {
		                    layer.msg('server error!');
		                }
		            });
		         });
	            
	}
}
function isExcel(filepath){
		if(filepath.length==0){
			layer.msg('请选择Excel导入');
			return false;
		}
        var extStart=filepath.lastIndexOf('.');
        var ext=filepath.substring(extStart,filepath.length).toUpperCase();
        if(ext!='.XLSX'&&ext!='.XLS'){
            layer.msg('导入文件不是Excel');
            return false;
        }
        return true;
}
function quickSearch(evt) {
	evt = (evt) ? evt : ((window.event) ? window.event : "") // 兼容IE和Firefox获得keyBoardEvent对象
	var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
	if (key == 13) { // 判断是否是回车事件。
		// 根据需要执行某种操作。
		// 根据需要执行某种操作。
		allUrl.controller.search();
	}
}

function draw_url_health_index(normal, error) {
	$('#url_health_index').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		// 禁用版权信息
		},
		title : {
			text : ''
		},
		tooltip : {
			pointFormat : '<b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				size : 200,
				dataLabels : {
					enabled : false,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f}%'
				},events: {
                    'click': function (e) {
                        console.log(e.point.name);
                        /*if(e.point.name=='异常'){
                        	window.location.href=contextPath+"/url/warning";
                        }*/
                    }
                }

			}
		},
		series : [ {
			type : 'pie',
			name : '健康指数',
			data : [ {
				name : '正常率:',
				y : normal,
				color : '#9c8ade'
			}, {
				name : '异常率:',
				y : error,
				sliced : true,
				selected : true,
				color : '#CFCFCF'
			} ]
		} ]
	});
	$("#h2").show();
	$("#div_id").show();
}

var allUrl = {
	service : {
		init : function() {
			allUrl.service.checkAndNoCheck();
			allUrl.controller.useOrNoUse();
			allUrl.service.alarmCheckedVari();
			allUrl.controller.page();
			allUrl.controller.editUrl();
			allUrl.controller.editUrlSubmit();
			loadBind();
		},
		getCheckedUrl : function() {
			var ids = "";
			$("input[name='checkbox']:checkbox").each(function() {
				if ($(this).is(":checked")) {
					ids += $(this).val() + ",";
				}
			});
			return ids.substring(0, ids.length - 1);
		},
		defaultReturnCode : function() {
			$("#urlBackCode").val("200");
			$('#urlBackCode').attr("disabled", "disabled");
		},
		customReturnCode : function() {
			$("#urlBackCode").val("200");
			$('#urlBackCode').attr("disabled", false);
		},
		checkAndNoCheck : function() {
			$("#checkAll").click(
					function() {
						// $("input[name='checkbox']:checkbox").prop("checked",this.checked);
						console.log($(this));
						$("input[name='checkbox']:checkbox").prop("checked",
								$(this).is(":checked"));
					});
			
			$("input[name='checkbox']:checkbox")
					.click(
							function() {
								var allBox = $("input[name='checkbox']:checkbox");
								$("#checkAll")
										.prop(
												"checked",
												allBox.length == allBox
														.filter(":checked").length ? true
														: false);
							});
		},
		getCheckedAlarm : function() {
			return $("input[name='alarmWay']:checked").val();
		},
		alarmCheckedVari : function() {
			$(".list_table").on("click", "#inlineCheckbox1", function() {
				$("#inlineCheckbox2").prop("checked", false);
			});

			$(".list_table").on("click", "#inlineCheckbox2", function() {
				$("#inlineCheckbox1").prop("checked", false);
			});

		}
	},
	controller : {
		createStep1 : function() {
			$.ajax({
				url : contextPath + '/url/create/step1',
				type : 'POST',
				dataType : 'html',
				data : {},
				success : function(data) {
					console.info(data);
					$(".content-wrapper").empty();
					$(".content-wrapper").append(data);
				},
				error : function() {
					layer.msg("操作失败");

				}

			});
		},
		delUrl : function() {
			var ids = allUrl.service.getCheckedUrl();
			if (ids == "") {
				layer.msg("请选择数据")
				return false;
			}
			layer.confirm("确定要删除吗?，删除后不可恢复",{
           	btn: ['确定','取消'] //按钮

           },function(){
			  	layer.close();
				$.ajax({
					url : contextPath + '/url/del',
					type : 'POST',
					dataType : 'json',
					data : {
						"id" : ids
					},
					success : function(data) {
						console.info(data);
						if (data.code == 1) {
							layer.msg("删除成功", {shade: [0.5, '#000'],scrollbar: false,offset: '50%', time:1000},function(){
								window.location.href=contextPath+"/url/get";
							});
						}
					},
					error : function() {
						layer.msg("删除失败");

					}

				});
			},function(){
				layer.close();
			});
		},
		useOrNoUse : function() {

			$(".list_table").on("click", "a[name='status']", function() {
				var urlId = $(this).attr("urlId");
				var status = $(this).attr("urlStatus");
				$.ajax({
					url : contextPath + '/url/changeStatus',
					type : 'POST',
					dataType : 'json',
					data : {
						"id" : urlId,
						"status" : status
					},
					success : function(data) {
						console.info(data);
						if (data.code == 1) {
							layer.msg('操作成功', {shade: [0.5, '#000'],scrollbar: false,offset: '50%', time:1000},function(){
								refresh();
							});
						}
					},
					error : function() {
						layer.msg("操作失败");

					}

				});
			});
		},
		page : function() {
			$(".list_table").on("click", ".pageNumber", function() {
				var startTime = $("#hiddenStartTime").val();
				var endTime = $("#hiddenEndTime").val();
				var survival = $("#hiddenSurvival").val();
				var urlAddress = $("#hiddenUrlAddress").val();
				var pageNo = $(this).attr("pageNo");
				var pageSize = 10;
				// queryListForPageDetail(pageNo);
				$.ajax({
					url : contextPath + '/url/getUrlTable',
					type : 'POST',
					dataType : 'html',
					data : {
						"startTime" : startTime,
						"endTime" : endTime,
						"urlAddress" : urlAddress,
						"pageNo" : pageNo
					},
					success : function(data) {
						console.info(data);
						var bool = data.indexOf("sessionTimeOut");
						if (bool < 0) {
							$(".list_table").empty();
							$(".list_table").append(data);
							allUrl.service.checkAndNoCheck();
							loadBind();
						} else {
							window.location.href = contextPath + "/home";
						}

					},
					error : function() {
						layer.msg("操作失败");

					}

				});
			});
		},
		search : function() {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var survival = $("#selectSurvival").val();
			var urlAddress = $("#search_name").val().trim();
			$("#hidden_search").val(urlAddress);
			$.ajax({
				url : contextPath + '/url/getUrlTable',
				type : 'POST',
				dataType : 'html',
				data : {
					"startTime" : startTime,
					"endTime" : endTime,
					"survival" : survival,
					"urlAddress" : urlAddress
				},
				success : function(data) {
					console.info(data);
					var bool = data.indexOf("sessionTimeOut");
					if (bool < 0) {
						$(".list_table").empty();
						$(".list_table").append(data);
						allUrl.service.checkAndNoCheck();
						loadBind();
						
					} else {
						window.location.href = contextPath + "/home";
					}

				},
				error : function() {
					layer.msg("操作失败");

				}

			});

		},
		editUrl : function() {
			$(".list_table")
					.on(
							"click",
							"a[name='edit_table']",
							function() {
								
								var urlId = $(this).attr("urlId");
								$
										.ajax({
											url : contextPath + '/url/getById',
											type : 'POST',
											dataType : 'json',
											data : {
												"id" : urlId
											},
											success : function(data) {
												if (data.code == 1) {
													console.info(data);
													$("#hiddenUrlId").val(
															data.attach.id);
													$("#address").val(
															data.attach.url)
															.attr("disabled",
																	true);
													$("#urlFrc")
															.val(
																	data.attach.frequency);
													$("#accTimeOut")
															.val(
																	data.attach.timeout);
													$("#time_number")
															.val(
																	data.attach.overtimes);
													$("#warn_time")
															.val(
																	data.attach.interval);
													$("#inputContent")
															.val(
																	data.attach.matchContent);
													if(data.attach.matchContent==null||data.attach.matchContent==''){
														$("#incloude_id").hide();
													}else{
														$("#incloude_id").show();
													}
													$(
															"input[name='method'][value="
																	+ data.attach.requestMethod
																	+ "]")
															.prop("checked",
																	true);
													if(data.attach.requestMethod=='POST'){
														$("#post_param").show();
														$("#postParameter").text(data.attach.postParameter);
													}
													$(
															"input[name='isContainsContent'][value="
																	+ data.attach.matchMethod
																	+ "]")
															.prop("checked",
																	true);
													$("#returnCode")
															.val(
																	data.attach.returnCode);
													var urlBackCodeValue = 1;
													/*if (data.attach.returnCode != "200") {
														urlBackCodeValue = 2;
														$("#urlBackCode").attr(
																"disabled",
																false);
													}*/
													$("input[name='alarmWay']:radio[value="+ data.attach.alarmMethod+ "]").prop("checked",true);


												}

											},
											error : function() {
												// layer.msg("操作失败");

											}

										});
							});

		},
		editUrlSubmit : function() {
			$(".list_table")
					.on(
							"click",
							"#modal_btn_submit",
							function() {
								var content = {};
								content.id = $("#hiddenUrlId").val();
								;
								content.urlAddress = $("#address").val();
								content.accFre = $("#urlFrc").val();
								content.accTimeOut = $("#accTimeOut").val();
								content.timeOutNum = $("#time_number").val();
								content.alarmInter = $("#warn_time").val();
								content.postParameter=$("#postParameter").val();
								content.method = $(
										"input[name='method']:checked").val();
								content.resContent = $("#inputContent").val();
								content.isContainsCon = $(
										"input[name='isContainsContent']:checked")
										.val();
								content.isDefaultCode = $(
										"input[name='isDefaultCode']:checked")
										.val();
								content.returnCode = $("#returnCode").val();
								if ($("#inlineCheckbox1").is(":checked")) {
									var checkedLength = $("input[name='alarmWay']:checked").length;
									if (checkedLength > 1) {
										layer.msg("报警方式中不报警不能和其他同时选中");
										return false;
									}
								}
								content.alarmWay = allUrl.service
										.getCheckedAlarm();
								console.info(content.alarmWay);
								// if(content.key == ""){
								// layer.msg("请返回第一步填写必选项");
								// return false;
								// }

								if (content.urlAddress == ""
										|| content.accTimeOut == ""
										|| content.timeOutNum == ""
										|| content.alarmInter == "") {
									layer.msg("请输入必填项");
									return false;
								}
								var returnCode = $("#returnCode").val();
								if(!regCode()){
									return ;
								}
								$.ajax({
									url : contextPath + '/url/saveUpdate',
									type : 'POST',
									dataType : 'json',
									data : {
										'content' : JSON.stringify(content)
									},
									success : function(data) {
										if (data.code == 1) {
											layer.msg("修改成功", {shade: [0.5, '#000'],scrollbar: false,time:1000},function(){
												refresh();
												 $(".modal-open").css({"overflow":"auto","padding-right":0});
												 $("body").removeClass("modal-open"); 
											});
										}
									},
									error : function() {
										layer.msg("操作失败");

									}

								});
							});

		}
	}
};

function refresh(){
var pageNum = $("#pagination-digg").find(".active").text();
var search =$("#hidden_search").val();
$.ajax({
		url : contextPath + '/url/getUrlTable',
		type : 'POST',
		dataType : 'html',
		data : {
			"urlAddress" : search,
			"pageNo":pageNum
		},
		success : function(data) {
			var bool = data.indexOf("sessionTimeOut");
			console.log(data);
			if (bool < 0) {
				$(".list_table").empty();
				$(".list_table").append(data);
				allUrl.service.checkAndNoCheck();
				loadBind();
			} else {
				window.location.href = contextPath + "/home";
			}

		},
		error : function() {
			layer.msg("操作失败");

		}

	});
}
function loadBind(){
	$("#inputContent").blur(function(){
		   var inputContent =$("#inputContent").val();
		   if(inputContent.length>0){
			   $("#incloude_id").show();
			   return ;
		   }
		   $("#incloude_id").hide();
	   });
	$("#inputContent").mouseout(function(){
		   var inputContent =$("#inputContent").val();
		   if(inputContent.length>0){
			   $("#incloude_id").show();
			   return ;
		   }
		   $("#incloude_id").hide();
	   });

	$("#returnCode").change(function(){
		regCode();
	});
	$("#import_submit").click(function(){
	
		importExcel();
	})
}