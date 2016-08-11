/**
 * Created by hanpan on 2015/10/29.
 */
var setting = {
	check : {
		enable : true
	},
	data : {
		key : {
			name : "funcName"
		},
		simpleData : {
			enable : true,
			idKey : "funcId",
			pIdKey : "parentId",
			rootPId : 0
		}

	// simpleData: {
	// enable: true,
	//            
	// rootPId: 0
	// }
	},
	callback : {
		onCheck : hideErorZtree
	}
};

function hideErorZtree() {
	if (!$("#error_tree_span_tree").is(":hidden")) {
		$("#error_tree_span_tree").hide();
	}
}

var zTreeNodes = [ {
	nocheck : true
} ];
// var zNodes =[
// { id:1, pId:0, name:"后台管理平台", open:true},
// { id:11, pId:1, name:"用户管理", open:true},
// { id:111, pId:11, name:"用户管理列表"},
// { id:12, pId:1, name:"设备管理", open:true},
// { id:121, pId:12, name:"设备类型列表"},
// { id:122, pId:12, name:"设备品牌列表"},
// { id:123, pId:12, name:"设备列表"},
// { id:13, pId:1, name:"商品管理", open:true},
// { id:131, pId:13, name:"商品类别列表"},
// { id:132, pId:13, name:"商品列表"},
// { id:14, pId:1, name:"广告管理", open:true},
// { id:141, pId:14, name:"频道列表"},
// { id:142, pId:14, name:"广告列表"},
// //{ id:15, pId:1, name:"主题管理（待定）", open:false},
// { id:16, pId:1, name:"消息管理", open:true},
// { id:161, pId:16, name:"意见列表"},
// { id:17, pId:1, name:"数据统计", open:true},
// { id:171, pId:17, name:"用户统计"},
// { id:172, pId:17, name:"设备统计"},
// { id:173, pId:17, name:"商品统计"},
// //{ id:174, pId:17, name:"主题统计（待定）"},
// { id:18, pId:1, name:"权限管理", open:true},
// { id:181, pId:18, name:"账号列表"},
// { id:182, pId:18, name:"角色列表"},
// { id:19, pId:1, name:"操作日志", open:true},
// { id:191, pId:19, name:"日志列表"},
//
// ];

var zNodes = [ {
	funcId : 1,
	parentId : 0,
	funcName : "后台管理平台1",
	open : true
}, {
	funcId : 11,
	parentId : 1,
	funcName : "用户管理",
	open : true
}, {
	funcId : 111,
	parentId : 11,
	funcName : "用户管理列表",
	checked : false
}, {
	funcId : 12,
	parentId : 1,
	funcName : "设备管理",
	open : true
}, {
	funcId : 121,
	parentId : 12,
	funcName : "设备类型列表"
}, {
	funcId : 122,
	parentId : 12,
	funcName : "设备品牌列表"
}, {
	funcId : 123,
	parentId : 12,
	funcName : "设备列表"
}, {
	funcId : 13,
	parentId : 1,
	funcName : "商品管理",
	open : true
}, {
	funcId : 131,
	parentId : 13,
	funcName : "商品类别列表"
}, {
	funcId : 132,
	parentId : 13,
	funcName : "商品列表"
}, {
	funcId : 14,
	parentId : 1,
	funcName : "广告管理",
	open : true
}, {
	funcId : 141,
	parentId : 14,
	funcName : "频道列表"
}, {
	funcId : 142,
	parentId : 14,
	funcName : "广告列表"
},
// { id:15, pId:1, name:"主题管理（待定）", open:false},
{
	funcId : 16,
	parentId : 1,
	funcName : "消息管理",
	open : true
}, {
	funcId : 161,
	parentId : 16,
	funcName : "意见列表"
}, {
	funcId : 17,
	parentId : 1,
	funcName : "数据统计",
	open : true
}, {
	funcId : 171,
	parentId : 17,
	funcName : "用户统计"
}, {
	funcId : 172,
	parentId : 17,
	funcName : "设备统计"
}, {
	funcId : 173,
	parentId : 17,
	funcName : "商品统计"
},
// { id:174, pId:17, name:"主题统计（待定）"},
{
	funcId : 18,
	parentId : 1,
	funcName : "权限管理",
	open : true
}, {
	funcId : 181,
	parentId : 18,
	funcName : "账号列表"
}, {
	funcId : 182,
	parentId : 18,
	funcName : "角色列表"
}, {
	funcId : 19,
	parentId : 1,
	funcName : "操作日志",
	open : true
}, {
	funcId : 191,
	parentId : 19,
	funcName : "日志列表"
},

];

var code;

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo")
	// py = $("#py").attr("checked")? "p":"",
	// sy = $("#sy").attr("checked")? "s":"",
	// pn = $("#pn").attr("checked")? "p":"",
	// sn = $("#sn").attr("checked")? "s":"",
	// type = { "Y":py + sy, "N":pn + sn};
	// zTree.setting.check.chkboxType = type;
	// //setting.check.chkboxType = { "Y" : "s", "N" : "s" };
	// showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' +
	// type.N + '" };');
}
function showCode(str) {
	if (!code)
		code = $("#code");
	code.empty();
	code.append("<li>" + str + "</li>");
}
// 全选
function CheckAllNodes() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.checkAllNodes(true);
}

// 全部展开
function openAllNodes() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.expandAll(true);
}

function getCheckNodes() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	var result = '';
	// [{'funcId':1,'parentId':0},{'funcId':2,'parentId':0}]
	for (var i = 0; i < nodes.length; i++) {
		var funcId = nodes[i].funcId;
		// if(funcId==0){
		// continue;
		// }
		var parentId = nodes[i].parentId;
		// checkedIds=checkedIds+{'funcId':funcId,'parentId':parentId};
		if (result != '') {
			result += ',';
		}

		result += '{' + '"funcId"' + ":" + funcId + ',';
		result += '"parentId"' + ":" + parentId + '}';
	}

	result = "[" + result + "]";
	// console.info(result);
	return result;
}

function checkRoleName() {
	var roleName = $("#roleName").val();
	if (roleName.length < 4 || roleName.length > 16) {

	}
}

$(document).ready(function() {

	// 获取功能权限列表
	$.ajax({
		url : '../manager/ajax/func/get',
		type : 'POST',
		dataType : 'json',
		data : {},
		success : function(data) {

			if (data.code == 1) {
				zNodes = data.attach;
				// console.info(zNodes);
				// alert("获取功能列表成功！");

				var treeobj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);

				// $("#treeDemo_1_check").click(function(){
				//
				// $(this).attr("checked");
				// });
				setCheck();
				CheckAllNodes();
				openAllNodes();
			} else {
				alert("获取功能列表失败");
			}
		},
		error : function() {
			alert("操作失败");

		}

	});

	// 提交
	$("#user_submit").click(function(event) {
		if(checkRoleName() && checkRoleDic() && checkTreee()){
			var content = {};
			content.roleName = $.trim($("#roleName").val());
			content.desc = $("#roledic").val();
			content.state = $('input:radio[name=newWork_type]:checked').val();
			content.functions = getCheckNodes();
			$.ajax({
				url : '../manager/ajax/role/save',
				type : 'POST',
				dataType : 'json',
				data : {
					content : JSON.stringify(content)
				},
				success : function(data) {
					if (data.code == 1) {
						alert("添加角色成功！");
						window.location.href = "../role/list";
					} else {
						alert("添加角色失败！");
					}
				},
				error : function() {
					alert("操作失败");

				}

			});
		}



	});

//	$.formValidator.initConfig({
//		theme : "baidu",
//		mode : "AutoTip",
//		submitOnce : true,
//		formID : "roleForm",
//		onsuccess : false,
//		submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
//	});

//	$("#roleName").formValidator({
//		onEmpty : "请输入4~16位字符的帐号",
//		onFocus : "4~16个字符，包括字母、数字、下划线，以字母开头，字母或数字结尾",
//		onCorrect : "该名称可以使用",
//		tipCss : {
//			height : 36,
//			width : 460
//		}
//	});
//	.inputValidator({
//		min : 4,
//		max : 16,
//		onError : "你输入的帐号名长度不正确,请确认"
//	});
//	.ajaxValidator({
//		type : 'POST',
//		dataType : "json",
//		async : true,
//		data : {
//			'roleName' : function() {
//				return $("#roleName").val()
//			}
//		},
//		url : "../manager/ajax/role/checkRoleName",
//		success : function(data) {
//			if (data.code == 1) {
//				return true;
//			} else {
//				return false;
//			}
//
//		},
//		buttons : $("#user_submit"),
//		error : function(jqXHR, textStatus, errorThrown) {
//			alert("服务器没有返回数据，可能服务器忙，请重试" + errorThrown);
//		},
//		onError : "该名称不可用，请更换",
//		onWait : "正在进行合法性校验，请稍候..."
//	});


	// $("#roleName").formValidator({onEmpty:"请输入角色名称",
	// onFocus:"请输入4~16位字符的帐号",tipCss:{height:36,width:460}}).inputValidator({min:4,max:16,onError:"你输入的角色名称长度不正确,请确认"});
//	$("#roledic").formValidator({
//		onEmpty : "请输入角色描述",
//		onFocus : "请输入角色描述，不超过200字符",
//		tipCss : {
//			height : 36,
//			width : 460
//		}
//	}).inputValidator({
//		min : 4,
//		max : 200,
//		onError : "你输入的角色描述长度长度不对，请确认"
//	});
//	$.formValidator.reloadAutoTip();
	
	$("#roleName").focus(RoleNameFocus);
	$("#roleName").blur(RoleNameBlur);
	$("#roledic").focus(roleDicFocus);
	$("#roledic").blur(roleDicBlur);
});





function checkRoleName(){
	var flag = false;
	var roleName = $.trim($("#roleName").val());
	if(roleName == "" || roleName.length<4 || roleName.length>16){
		$("#error_tree_span_rolename").show().text("请输入4~16位字符");
	}else{
		//ajax验证
		$.ajax({
    		url:'../manager/ajax/role/checkRoleName',
    		type:'POST',
			dataType : 'json',
			async: false,
    		data:{"roleName":roleName},
    		beforeSend: function () {
    		    $("#error_tree_span_rolename").show().text("正在验证，请稍后。。。");
    		},
    		success:function(data){
    			if(data.code==5){
    				$("#error_tree_span_rolename").show().text("角色名称已被占用");
    			}else if(data.code==503){
    				$("#error_tree_span_rolename").show().text("验证失败");
    			}else if(data.code==1){
    				$("#error_tree_span_rolename").hide();
    				flag = true;
    			}
    		},
    		error:function(){
    			alert("验证失败");
    			
    		}
    		
    	});
			
			
	}
	//alert("checkName:"+flag);
	return flag;
}


function checkRoleDic(){
	var flag = false;
	var roledic = $("#roledic").val();
	if(roledic == "" || roledic.length > 200){
		$("#error_tree_span_roledic").show().text("描述不超过200字符");
	}else{
		$("#error_tree_span_roledic").hide();
		flag = true;
	}
	//alert("checkdir:"+flag);
	return flag;
}

function checkTreee(){
	var flag = false;
	var checkNum = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true).length;
	if(checkNum < 1){
		$("#error_tree_span_tree").show();
	}else{
		$("#error_tree_span_tree").hide();
		flag = true;
	}
	//alert("checkTree:"+flag);
	return flag;
}












//roleName获取焦点
function RoleNameFocus(){
	$("#error_tree_span_rolename").hide();
}
//roleName失去焦点
function RoleNameBlur(){
	var roleName = $.trim($("#roleName").val());
	if(roleName == "" || roleName.length<4 || roleName.length>16){
		$("#error_tree_span_rolename").show().text("请输入4~16位字符");
	}else{
		//ajax验证
		$.ajax({
    		url:'../manager/ajax/role/checkRoleName',
    		type:'POST',
			dataType : 'json',
    		data:{"roleName":roleName},
    		beforeSend: function () {
    		    $("#error_tree_span_rolename").show().text("正在验证，请稍后。。。");
    		},
    		success:function(data){
    			if(data.code==5){
    				$("#error_tree_span_rolename").show().text("角色名称已被占用");
    			}else if(data.code==503){
    				$("#error_tree_span_rolename").show().text("验证失败");
    			}else if(data.code==1){
    				$("#error_tree_span_rolename").hide();
    			}
    		},
    		error:function(){
    		//	alert("验证失败");
    			$("#error_tree_span_rolename").show().text("验证失败");
    		}
    		
    	});
			
			
	}
}


function roleDicFocus(){
	$("#error_tree_span_roledic").hide();
}

function roleDicBlur(){
	var roledic = $("#roledic").val();
	if(roledic == "" || roledic.length > 200){
		$("#error_tree_span_roledic").show().text("描述不超过200字符");
	}
	
}

