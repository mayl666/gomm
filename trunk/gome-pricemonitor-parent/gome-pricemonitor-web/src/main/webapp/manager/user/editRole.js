/**
 * Created by hanpan on 2015/10/29.
 */
var setting = {
    check: {
        enable: true
    },
    data: {
    	key: {
			name: "funcName"
		},
        simpleData: {
            enable: true,
            idKey: "funcId",
			pIdKey: "parentId",
			rootPId: 0
        }
    	
//        simpleData: {
//            enable: true,
//            
//			rootPId: 0
//        }
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

var zTreeNodes = [{
	nocheck  : true
}];
var zNodes =[
             { funcId:1, parentId:0, funcName:"后台管理平台1",open:true},
             { funcId:11, parentId:1, funcName:"用户管理", open:true},
             { funcId:111, parentId:11, funcName:"用户管理列表", checked:true},
             { funcId:12, parentId:1, funcName:"设备管理", open:true},
             { funcId:121, parentId:12, funcName:"设备类型列表"},
             { funcId:122, parentId:12, funcName:"设备品牌列表"},
             { funcId:123, parentId:12, funcName:"设备列表"},
             { funcId:13, parentId:1, funcName:"商品管理", open:true},
             { funcId:131, parentId:13, funcName:"商品类别列表"},
             { funcId:132, parentId:13, funcName:"商品列表"},
             { funcId:14, parentId:1, funcName:"广告管理", open:true},
             { funcId:141, parentId:14, funcName:"频道列表"},
             { funcId:142, parentId:14, funcName:"广告列表"},
             //{ id:15, pId:1, name:"主题管理（待定）", open:false},
             { funcId:16, parentId:1, funcName:"消息管理", open:true},
             { funcId:161, parentId:16, funcName:"意见列表"},
             { funcId:17, parentId:1, funcName:"数据统计", open:true},
             { funcId:171, parentId:17, funcName:"用户统计"},
             { funcId:172, parentId:17, funcName:"设备统计"},
             { funcId:173, parentId:17, funcName:"商品统计"},
             //{ id:174, pId:17, name:"主题统计（待定）"},
             { funcId:18, parentId:1, funcName:"权限管理", open:true},
             { funcId:181, parentId:18, funcName:"账号列表"},
             { funcId:182, parentId:18, funcName:"角色列表"},
             { funcId:19, parentId:1, funcName:"操作日志", open:true},
             { funcId:191, parentId:19, funcName:"日志列表"},

         ];

var code;

function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo")
    //    py = $("#py").attr("checked")? "p":"",
    //    sy = $("#sy").attr("checked")? "s":"",
    //    pn = $("#pn").attr("checked")? "p":"",
    //    sn = $("#sn").attr("checked")? "s":"",
    //    type = { "Y":py + sy, "N":pn + sn};
    //zTree.setting.check.chkboxType = type;
    ////setting.check.chkboxType = { "Y" : "s", "N" : "s" };
    //showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
function showCode(str) {
    if (!code) code = $("#code");
    code.empty();
    code.append("<li>"+str+"</li>");
}
//全选
function CheckAllNodes() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.checkAllNodes(true);
}

//全部展开
function openAllNodes() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.expandAll(true); 
}

function getCheckNodes(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes=treeObj.getCheckedNodes(true);
	var result='';
	//[{'funcId':1,'parentId':0},{'funcId':2,'parentId':0}]
	for(var i=0;i<nodes.length;i++){
		var funcId=nodes[i].funcId;
		if(funcId==0){
			continue;
		}
		var parentId=nodes[i].parentId;
		//checkedIds=checkedIds+{'funcId':funcId,'parentId':parentId};
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


function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}


$(document).ready(function(){
	var roleId = GetQueryString("roleId");
	
	//获取功能权限列表
	$.ajax({
		url:'../manager/ajax/func/getForRoleEdit?roleId='+roleId,
		type:'POST',
		dataType : 'json',
		data:{},
		success:function(data){
			
			
			if(data.code==1){
				zNodes=data.attach;
				//console.info(zNodes);
				//alert("获取功能列表成功！");
				
				
			  var treeobj =  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			 
			    //$("#treeDemo_1_check").click(function(){
			    //
			    //    $(this).attr("checked");
			    //});
			    setCheck();
			   // CheckAllNodes();
			    openAllNodes();
			}else{
				alert("获取功能列表失败");
			}
		},
		error:function(){
			alert("操作失败");
			
		}
		
	});
   // $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    //$("#treeDemo_1_check").click(function(){
    //
    //    $(this).attr("checked");
    //});
   // setCheck();
   // CheckAllNodes();
	
	
	//提交
	$("#user_submit").click(function(event){
		event.preventDefault();
		if(checkRoleName() && checkRoleDic() && checkTreee()){
		//	var resu=$.formValidator.isOneValid("roleForm")
//		if(!$.formValidator.pageIsValid('1')){
//			return;
//		}
		var content={};
		content.roleId=$("#roleId").val();
		content.roleName=$.trim($("#roleName").val());
		content.desc=$("#roledic").val();
		content.state=$('input:radio[name=newWork_type]:checked').val();
		content.functions=getCheckNodes();
		$.ajax({
    		url:'../manager/ajax/role/edit',
    		type:'POST',
			dataType : 'json',
    		data:{content:JSON.stringify(content)},
    		success:function(data){
    			if(data.code==1){
    				alert("修改角色成功！");
    				window.location.href="../role/list";
    			}else{
    				alert("修改角色失败！");
    			}
    		},
    		error:function(){
    			alert("操作失败");
    			
    		}
    		
    	});
		}
		
	});
	
	
	$("#roleName").focus(RoleNameFocus);
	$("#roleName").blur(RoleNameBlur);
	$("#roledic").focus(roleDicFocus);
	$("#roledic").blur(roleDicBlur);
	

});







function checkRoleName(){
	var flag = false;
	var roleName = $.trim($("#roleName").val());
	var oldRoleName = $("#oldRoleName").val();
	if(roleName == "" || roleName.length<4 || roleName.length>16){
		$("#error_tree_span_rolename").show().text("请输入4~16位字符");
	}else if(roleName == oldRoleName){
		flag = true;
		$("#error_tree_span_rolename").hide();
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
	var oldRoleName = $("#oldRoleName").val();
	var roleName = $.trim($("#roleName").val());
	if(roleName == "" || roleName.length<4 || roleName.length>16){
		$("#error_tree_span_rolename").show().text("请输入4~16位字符");
	}else if(oldRoleName==roleName){
		$("#error_tree_span_rolename").hide();
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
    			//alert("验证失败");
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