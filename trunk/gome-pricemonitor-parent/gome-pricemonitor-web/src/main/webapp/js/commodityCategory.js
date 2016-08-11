/**
 * Created by yangdongxue on 2015/11/5
 */
$(function() {
	//点击高级搜索，出现高级搜索选项
    $("#hight_click").on('click',function(){
    	if($("#hight_click em").text()=="高级搜索"){
    		$("#hight_click em").text("收起");
    		$(".search_hight").show();
    	} else {
    		$("#hight_click em").text("高级搜索");
    		$(".search_hight").hide();
    	}
    });

})
