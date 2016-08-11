/**
 * Created by caowei on 2015/10/31
 */
$(function(){
	//删除广告
	$("a[name=deleteAd]").unbind("click");
	$("a[name=deleteAd]").bind("click", function(){
		if(window.confirm("您确定要删除这个广告吗？")){
			var id = $(this).attr("adId");
	    	$.ajax({
	    		url:'../ad/deleteAd',
	    		type:'POST',
	    		dataType:'json',
	    		data:{
	    			id:id
	    		},
	    		success:function(data){
	    			if(data.code==1){
	    				pop_up("删除成功",true);
						window.location.href="../ad/toAdListView";
	    			}else{
	    				pop_up("删除失败",false);
	    			}
	    		},
	    		error:function(){
	    			pop_up("系统异常",false);
	    		}
	    		
	    	});
		}
	});
	
	//分页搜索
	$(".pageNumber").unbind("click");
	$(".pageNumber").bind("click", function(){
		var page = $(this).attr("pageNo");
		if(typeof(page) == "undefined"){
			page = null;
		}
		var searchConditions = $("#searchConditions").val();
		if(searchConditions == ""){
			var content = {};
			content.search=true;
			searchConditions = JSON.stringify(content);
		}
		$.ajax({
			url:'../ad/toAdListView',
			type:'POST',
			dataType:'html',
			data:{
				content:searchConditions,
				page:page
			},
			success:function(data){
				$(".table-area").empty();
				$(".table-area").append(data);
			},
			error:function(){
				pop_up("系统异常",false);
			}
			
		});
	});
});
