/**
 * Created by caowei on 2015/11/01
 */
$(function(){
	//广告搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var picName = $("#picName").val();
		if(picName != "" && picName != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(picName.indexOf("%") != -1){
				picName = picName.replace(/%/g, "\\%");
			}else if(picName.indexOf("_") != -1){
				picName = picName.replace(/_/g, "\\_");
			}
			content.picName=picName;
		}
		if(!$(".search_hight").is(":hidden")){
			var sort = $("#sort").val();
			var status = $("#status").val();
			if(sort != ""){
				content.sort=sort;
			}
			if(status != ""){
				content.status=status;
			}
			
		}
		content.search=true;
		
		$.ajax({
			url:'../ad/toAdListView',
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
	$("#picName").bind("keydown",function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {    
	        //回车执行查询
	        $(".search_btn").click();
	     }    
	});
});