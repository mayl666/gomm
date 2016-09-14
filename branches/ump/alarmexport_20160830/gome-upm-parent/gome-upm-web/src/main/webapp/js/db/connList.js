/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var serverAddr = $.trim($("#serverAddr").val());
		if(serverAddr != "" && serverAddr != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(serverAddr.indexOf("%") != -1){
				serverAddr = serverAddr.replace(/%/g, "\\%");
			}else if(serverAddr.indexOf("_") != -1){
				serverAddr = serverAddr.replace(/_/g, "\\_");
			}
			content.serverAddr=serverAddr;
		}
		var port = $.trim($("#port").val());
		if(port != "" && port != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(port.indexOf("%") != -1){
				port = port.replace(/%/g, "\\%");
			}else if(port.indexOf("_") != -1){
				port = port.replace(/_/g, "\\_");
			}
			content.port=port;
		}
		var instanceName = $.trim($("#instanceName").val());
		if(instanceName != "" && instanceName != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(instanceName.indexOf("%") != -1){
				instanceName = instanceName.replace(/%/g, "\\%");
			}else if(instanceName.indexOf("_") != -1){
				instanceName = instanceName.replace(/_/g, "\\_");
			}
			content.instanceName=instanceName;
		}
		var dbType = $("#dbType").val();
		if(dbType != "" && dbType != null){
			content.dbType=dbType;
		}
//		if(dbType != "" && dbType != null){
//			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
//			if(dbType.indexOf("%") != -1){
//				dbType = dbType.replace(/%/g, "\\%");
//			}else if(dbType.indexOf("_") != -1){
//				dbType = dbType.replace(/_/g, "\\_");
//			}
//			content.dbType=dbType;
//		}
		content.search=true;
		
		$.ajax({
			url:'../db/allConn',
			type:'POST',
			dataType:'html',
			data:{
				content:JSON.stringify(content)
			},
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
				$("#searchConditions").val(JSON.stringify(content));
			},
			error:function(){
//				alert("服务器异常");
				layer.msg('服务器异常');
			}
			
		});
	});
	
	//回车搜索事件
//	$("#dbType").bind("keydown",function(e){
//        // 兼容FF和IE和Opera    
//	    var theEvent = e || window.event;    
//	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
//	    if (code == 13) {    
//	        //回车执行查询
//	        $(".search_btn").click();
//	     }    
//	});
});