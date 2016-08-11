/**
 * Created by caowei on 2015/11/01
 */
$(function(){
	//频道搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var channelName = $("#channelName").val();
		if(channelName != "" && channelName != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(channelName.indexOf("%") != -1){
				channelName = channelName.replace(/%/g, "\\%");
			}else if(channelName.indexOf("_") != -1){
				channelName = channelName.replace(/_/g, "\\_");
			}
			content.channelName=channelName;
		}
		content.search=true;
		
		$.ajax({
			url:'../channel/toChannelListView',
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
	$("#channelName").bind("keydown",function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {    
	        //回车执行查询
	        $(".search_btn").click();
	     }    
	});
});