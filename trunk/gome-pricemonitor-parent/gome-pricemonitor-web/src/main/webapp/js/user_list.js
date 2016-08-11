/**
 * Created by hanpan on 2015/10/27.
 * userList  用户列表模块
 */
$(function(){
//点击高级搜索，出现高级搜索选项
    $("#hight_click").click(hight_search_show)
});
//高级搜索选择显示
flag=true;
function hight_search_show(){
if(flag){
    $("#hight_click em").text("收起");
    $(".search_hight").show();
    flag=false;
}else{
    $("#hight_click em").text("高级搜索");
    $(".search_hight").hide();
    flag=true;
}


}