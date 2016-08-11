/**
 * Created by caowei on 2015/11/26
 */
$(function(){
	//版本搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var version = $("#version").val();
		if(version != "" && version != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(version.indexOf("%") != -1){
				version = version.replace(/%/g, "\\%");
			}else if(version.indexOf("_") != -1){
				version = version.replace(/_/g, "\\_");
			}
			content.version=version;
		}
		content.search=true;
		
		$.ajax({
			url:'../version/toVersionListView',
			type:'POST',
			dataType:'html',
			data:{
				content:JSON.stringify(content)
			},
			success:function(data){
				$(".table-area").empty();
				$(".table-area").append(data);
				$("#searchConditions").val(JSON.stringify(content));
			},
			error:function(){
				pop_up("系统异常",false);
			}
			
		});
	});
	
	//回车搜索事件
	$("#version").bind("keydown",function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {    
	        //回车执行查询
	        $(".search_btn").click();
	     }    
	});
});