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
		var tbsName = $.trim($("#tbsName").val());
		if(tbsName != "" && tbsName != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(tbsName.indexOf("%") != -1){
				tbsName = tbsName.replace(/%/g, "\\%");
			}else if(tbsName.indexOf("_") != -1){
				tbsName = tbsName.replace(/_/g, "\\_");
			}
			content.tbsName=tbsName;
		}
		content.search=true;
		
		$.ajax({
			url:'../db/allTbs',
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