/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var dbName = $.trim($("#dbName").val());
		if(dbName != "" && dbName != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(dbName.indexOf("%") != -1){
				dbName = dbName.replace(/%/g, "\\%");
			}else if(dbName.indexOf("_") != -1){
				dbName = dbName.replace(/_/g, "\\_");
			}
			content.dbName=dbName;
		}
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
		var masterTbs = $.trim($("#masterTbs").val());
		if(masterTbs != "" && masterTbs != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(masterTbs.indexOf("%") != -1){
				masterTbs = masterTbs.replace(/%/g, "\\%");
			}else if(masterTbs.indexOf("_") != -1){
				masterTbs = masterTbs.replace(/_/g, "\\_");
			}
			content.masterTbs=masterTbs;
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
		var slaveTbs = $.trim($("#slaveTbs").val());
		if(slaveTbs != "" && slaveTbs != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(slaveTbs.indexOf("%") != -1){
				slaveTbs = slaveTbs.replace(/%/g, "\\%");
			}else if(slaveTbs.indexOf("_") != -1){
				slaveTbs = slaveTbs.replace(/_/g, "\\_");
			}
			content.slaveTbs=slaveTbs;
		}
		content.search=true;
		
		$.ajax({
			url:'../db/allOracle',
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