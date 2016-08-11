/**
 * Created by caowei on 2015/11/06
 */
$(function(){
	//删除商品分类
	$("a[name=deleteGoodsCategory]").unbind("click");
	$("a[name=deleteGoodsCategory]").bind("click", function(){
		var id = $(this).attr("goodsCategoryId");
		if(!checkEnableDelete(id)){
			pop_up("该商品分类下已存在商品，无法删除",false);
	  		return false;
		}
		
		if(window.confirm("您确定要删除这个商品分类吗？")){
	    	$.ajax({
	    		url:'../goodsCategory/deleteGoodsCategory',
	    		type:'POST',
	    		dataType:'json',
	    		async:false,
	    		data:{
	    			id:id
	    		},
	    		success:function(data){
	    			if(data.code==1){
	    				pop_up("删除成功",true);
						window.location.href="../goodsCategory/toGoodsCategoryListView";
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
	
	//检查该商品分类是否可以删除（商品分类下有商品，商品分类将不能删除）
	function checkEnableDelete(goodsCategoryId){
		var deleteFlag = false;
		$.ajax({
    		url:'../goodsCategory/checkEnableDelete',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			id:goodsCategoryId
    		},
    		success:function(data){
    			if(data.code==13){  //不可删除
    				deleteFlag = false;
    			}else if(data.code==14){   //可以删除
    				deleteFlag = true;
    			}else{
    				deleteFlag = false;
    				pop_up("系统异常",false);
    		  		deleteFlag = false;
    			}
    		},
    		error:function(){
    			pop_up("系统异常",false);
		  		deleteFlag = false;
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
			url:'../goodsCategory/toGoodsCategoryListView',
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
