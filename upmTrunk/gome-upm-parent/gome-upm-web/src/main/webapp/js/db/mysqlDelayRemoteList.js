/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var masterAddr = $.trim($("#masterAddr").val());
		if(masterAddr != "" && masterAddr != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(masterAddr.indexOf("%") != -1){
				masterAddr = masterAddr.replace(/%/g, "\\%");
			}else if(masterAddr.indexOf("_") != -1){
				masterAddr = masterAddr.replace(/_/g, "\\_");
			}
			content.masterAddr=masterAddr;
		}
		var masterPort = $.trim($("#masterPort").val());
		var portRegex = /^\d{0,9}$/;
		if(masterPort != "" && masterPort != null){
			if(!portRegex.test(masterPort)){
				layer.msg('端口只能为0~9位的数字');
				return;
			}
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(masterPort.indexOf("%") != -1){
				masterPort = masterPort.replace(/%/g, "\\%");
			}else if(masterPort.indexOf("_") != -1){
				masterPort = masterPort.replace(/_/g, "\\_");
			}
			content.masterPort=masterPort;
		}
		var slaveAddr = $.trim($("#slaveAddr").val());
		if(slaveAddr != "" && slaveAddr != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(slaveAddr.indexOf("%") != -1){
				slaveAddr = slaveAddr.replace(/%/g, "\\%");
			}else if(slaveAddr.indexOf("_") != -1){
				slaveAddr = slaveAddr.replace(/_/g, "\\_");
			}
			content.slaveAddr=slaveAddr;
		}
		var slavePort = $.trim($("#slavePort").val());
		if(slavePort != "" && slavePort != null){
			if(!portRegex.test(slavePort)){
				layer.msg('端口只能为0~9位的数字');
				return;
			}
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(slavePort.indexOf("%") != -1){
				slavePort = slavePort.replace(/%/g, "\\%");
			}else if(slavePort.indexOf("_") != -1){
				slavePort = slavePort.replace(/_/g, "\\_");
			}
			content.slavePort=slavePort;
		}
		content.search=true;
		
		$.ajax({
			url:'../db/allMySQL',
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