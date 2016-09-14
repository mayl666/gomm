/**
 * Created by wangxiaye on 2016/08/31
 */

$(function() {
	// 搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function() {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime.length != 0 && endTime.length == 0) {
			layer.msg('请输入结束时间！');
			return;
		} else if (startTime.length == 0 && endTime.length != 0) {
			layer.msg('请输入开始时间！');
			return;
		}
		var content = {};
		if (startTime.length > 0 && endTime.length > 0) {
			var startDate = new Date(startTime.replace(/-/g, "/"));
			var endDate = new Date(endTime.replace(/-/g, "/"));
			if (startDate >= endDate) {
				// alert("截止时间必须大于开始时间");
				layer.msg('开始时间不能晚于结束时间！');
				return;
			}
			content.startTime = startTime;
			content.endTime = endTime;
		}
		var type = $("#type").val();
		if (type != "" && type != null) {
			content.type = type.trim();
		}
		var businessType = $("#businessType").val();
		if (businessType != "" && businessType != null) {
			content.businessType = businessType;
		}
		
		var level = $("#level").val();
		if (level != "" && level != null) {
			content.level = level;
		}
		content.search = true;
		$.ajax({
			url : '../alarmRange/list',
			type : 'POST',
			async : true,
			dataType : 'html',
			data : {
				content : JSON.stringify(content)
			},
			success : function(data) {
				$(".list_table").empty();
				$(".list_table").append(data);
				$("#searchConditions").val(JSON.stringify(content));
			},
			error : function() {
				layer.msg('服务器异常');
			}
		});
	});
	laydate({
		elem : '#startTime',
		format : 'YYYY-MM-DD hh:mm:ss',
		// min: laydate.now(), //设定最小日期为当前日期
		// max: '2099-06-16 23:59:59', //最大日期
		istime : true,
		istoday : true,
		choose : function(date) {
			// end.min = datas; //开始日选好后，重置结束日的最小日期
			// end.start = datas //将结束日的初始值设定为开始日
			// alert("startTime:" + date);
		}
	});

	laydate({
		elem : '#endTime',
		format : 'YYYY-MM-DD hh:mm:ss',
		// min: laydate.now(), //设定最小日期为当前日期
		// max: '2099-06-16 23:59:59', //最大日期
		istime : true,
		istoday : true,
		choose : function(date) {
			// end.min = datas; //开始日选好后，重置结束日的最小日期
			// end.start = datas //将结束日的初始值设定为开始日
			// alert("endTime:" + date);
		}
	});
});

function changeValue() {
	var id = $("#hiddenRangeId").val();
	var rangeValue = $("#rangeValue").val();
	if(isNull(rangeValue)){
		layer.msg("输入不能为空");
		return;
	}
	var content = {};
	content.id = id;
	content.value = rangeValue;
	$.ajax({
		url : contextPath + '/alarmRange/update',
		type : 'POST',
		dataType : 'json',
		data : {
			content : JSON.stringify(content)
		},
		success : function(data) {
			if (data.code == 1) {
				layer.msg("修改成功", {
					shade : [ 0.5, '#000' ],
					scrollbar : false,
					offset : '50%',
					time : 1000
				}, function() {
					refresh();
				});
			}
		},
		error : function() {
			layer.msg("操作失败");
		}
	});
}

function addAlarmRange(){
	var content = {};
	var businessType = $("#add_businessType").val();
	if (businessType != "" && businessType != null) {
		content.businessType = businessType;
	}else{
		layer.msg("模块不能为空");
		return;
	}
	
	var type = $("#add_type").val();
	if (type != "" && type != null) {
		content.type = type;
	}else{
		layer.msg("类型不能为空");
		return;
	}
	
	var level_one = $("#level_one").val();
	if (level_one != "" && level_one != null) {
		content.level_one = level_one;
	}
	var level_two = $("#level_two").val();
	if (level_two != "" && level_two != null) {
		content.level_two = level_two;
	}
	var level_three = $("#level_three").val();
	if (level_three != "" && level_three != null) {
		content.level_three = level_three;
	}
	if(isNull(level_one)&&isNull(level_two)&&isNull(level_three)){
		layer.msg("阈值不能全为空");
		return;
	}
	$.ajax({
		url : contextPath + '/alarmRange/add',
		type : 'POST',
		dataType : 'json',
		data : {
			content : JSON.stringify(content)
		},
		success : function(data) {
			if (data.code == 1) {
				layer.msg("保存成功", {
					shade : [ 0.5, '#000' ],
					scrollbar : false,
					offset : '50%',
					time : 1000
				}, function() {
					refresh();
				});
			}
		},
		error : function() {
			layer.msg("操作失败");
		}
	});
	$("#addForm")[0].reset();
}


function delAlarmRange(obj){
	if(confirm("确定要删除吗，删除后不可恢复")){
		var id = $(obj).attr("rangeId");
		$.ajax({
			url : contextPath + '/alarmRange/del',
			type : 'POST',
			dataType : 'json',
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 1) {
					layer.msg("删除成功", {
						shade : [ 0.5, '#000' ],
						scrollbar : false,
						offset : '50%',
						time : 1000
					}, function() {
						refresh();
					});
				}
			},
			error : function() {
				layer.msg("操作失败");
			}
		});
	}
	
}

function refresh() {
//	var page = $("#pagination-digg").find(".active").text();
	if (typeof (page) == "undefined") {
		page = null;
	}
	var searchConditions = $("#searchConditions").val();
	if (searchConditions == "") {
		var content = {};
		content.search = true;
		searchConditions = JSON.stringify(content);
	}
	$.ajax({
		url : '../alarmRange/list',
		type : 'POST',
		dataType : 'html',
		async : false,
		data : {
			content : searchConditions
//			page : page
		},
		success : function(data) {
			$(".list_table").empty();
			$(".list_table").append(data);
		},
		error : function() {
			layer.msg('服务器异常');
		}
	});
}

function changeBusinessType(from){
	var businessType ;
	var typeObj;
	if(from=="list"){
		typeObj = $("#type")
		businessType = $("#businessType").val();
	}else{
		typeObj = $("#add_type")
		businessType = $("#add_businessType").val();
	}
	$.ajax({
		url : '../alarmRange/getTypeByBusiness',
		type : 'POST',
		dataType : 'json',
		async : false,
		data : {
			businessType : businessType
		},
		success : function(data) {
			typeObj.empty();
			typeObj.append("<option value=''>请选择</option>");
			if(typeof(data.responsesDTO)!="undefied"&&data.responsesDTO!=null){
				var alarmRangeList = data.responsesDTO.attach;
				for(var i=0;i<alarmRangeList.length;i++){
					var range = alarmRangeList[i];
					typeObj.append("<option value="+range.type+">"+range.type+"</option>");
				}
			}
		},
		error : function() {
			layer.msg('服务器异常');
		}
	});
}

function showAddTypeDiv(){
	var businessType = $("#add_businessType").val();
	if(isNull(businessType)){
		layer.msg("模块不能为空");
		return;
	}
	$("#rangeTypeTextDiv").css({
		  "display":"block",
		  "white-space":"nowrap"});
}

function addTypeToSelect(){
	var rangeTypeText = $("#add_type_text").val();
	if(isNull(rangeTypeText)){
		layer.msg("输入不能为空");
		return;
	}
	var returnFlag=false;
	$("#add_type").find("option").each(function(){
		var temp = $(this).html();
		if(temp==rangeTypeText){
			returnFlag = true;
			return false;
		}
	});
	if(returnFlag){
		layer.msg("输入类型重复");
		return;
	}
	$("#add_type").append("<option value=\""+rangeTypeText+"\">"+rangeTypeText+"</option>"); 
	$("#rangeTypeTextDiv").hide();
}

function AddAlarmRangeModal(){
	$("#rangeTypeTextDiv").hide();
}

function isNotNull(str){
	var flag = false;
	if(str!=null&&str.trim().length!=0){
		flag=true;
	}
	return flag;
}
function isNull(str){
	var flag = false;
	if(str==null||str.trim().length==0){
		flag=true;
	}
	return flag;
}