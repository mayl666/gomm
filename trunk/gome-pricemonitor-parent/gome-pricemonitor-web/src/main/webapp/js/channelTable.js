/**
 * Created by caowei on 2015/11/01
 */
$(function(){
	//删除频道
	$("a[name=deleteChannel]").unbind("click");
	$("a[name=deleteChannel]").bind("click", function(){
		var id = $(this).attr("channelId");
		if(!checkEnableDelete(id)){
			pop_up("该频道下已存在广告位，无法删除",false);
	  		return false;
		}
		
		if(window.confirm("您确定要删除这个频道吗？")){
	    	$.ajax({
	    		url:'../channel/deleteChannel',
	    		type:'POST',
	    		dataType:'json',
	    		async:false,
	    		data:{
	    			id:id
	    		},
	    		success:function(data){
	    			if(data.code==1){
	    				pop_up("删除成功",true);
						window.location.href="../channel/toChannelListView";
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
	
	//检查该频道是否可以删除（频道下有广告位，频道将不能删除）
	function checkEnableDelete(channelId){
		var deleteFlag = false;
		$.ajax({
    		url:'../channel/checkEnableDelete',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			id:channelId
    		},
    		success:function(data){
    			if(data.code==11){  //不可删除
    				deleteFlag = false;
    			}else if(data.code==12){   //可以删除
    				deleteFlag = true;
    			}else{
    				deleteFlag = false;
    				pop_up("系统异常",false);
    			}
    		},
    		error:function(){
    			pop_up("系统异常",false);
    		}
    		
    	});
		return deleteFlag;
	}
	
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
			url:'../channel/toChannelListView',
			type:'POST',
			dataType:'html',
			async:false,
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
