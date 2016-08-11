/**
 * Created by caowei on 2015/11/09
 */
$(function(){
	//商品搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var goodsName = $("#goodsName").val();
		if(goodsName != "" && goodsName != null){
			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
			if(goodsName.indexOf("%") != -1){
				goodsName = goodsName.replace(/%/g, "\\%");
			}else if(goodsName.indexOf("_") != -1){
				goodsName = goodsName.replace(/_/g, "\\_");
			}
			content.goodsName=goodsName;
		}
		if(!$(".search_hight").is(":hidden")){
			var categoryId = $("#categoryId").val();
			var status = $("#status").val();
			if(categoryId != ""){
				content.categoryId=categoryId;
			}
			if(status != ""){
				content.status=status;
			}
		}
		content.search=true;
		
		$.ajax({
			url:'../goods/toGoodsListView',
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
	
	//导出
	$(".export-btn").click(function(){
		var searchConditions = $("#searchConditions").val();
		window.location.href = '../goods/exportExcel?conditions='+searchConditions;
	});
	
	//回车搜索事件
	$("#goodsName").bind("keydown",function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {    
	        //回车执行查询
	        $(".search_btn").click();
	     }    
	});
});