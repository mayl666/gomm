/**
 * Created by hanpan on 2015/8/31.
 */
/**
 * 用来解决，鼠标悬浮左侧菜单子选项的样式
 *
 * */
$(function(){
	//控制左侧菜单的对应项默认选中
	var moduleName = $("#moduleName").text();
	if(moduleName != null && moduleName != ""){
		var a = $("a:contains('" + moduleName + "')");
		a.parent().addClass("currentLi");
		a.parent().parent().parent().addClass("active");
	}
	
    $("#itemadd").click(function(){

        $("#tasklist").append("<li><span class='handle'><i class='fa fa-tasks fa-fw'></i></span><input type='checkbox' value='' name=''/><span class='text'>Task 2015091144102 执行成功</span><small class='label label-success'><i class='fa fa-clock-o'></i> 3 days</small><div class='tools'><i class='fa fa-edit'></i><i class='fa fa-trash-o'></i></div></li>");
    });
    //改变左侧菜单栏
    $(".treeview-menu>li").each(function(){
        $(this).hover(function(){
            $(this).find(".more_fa").removeClass("fa-circle-o").addClass(" fa-dot-circle-o").parents().siblings("li").find(".more_fa").removeClass("fa-dot-circle-o").addClass("fa-circle-o");
        });
        //$(this).click(function(){
        //   $(this).addClass("currentli").siblings("li").removeClass("currentli");
        //});
        $(".currentli").find("i").removeClass("fa-circle-o").addClass(" fa-dot-circle-o");

    });

    //显示区域下拉菜单栏
    $(".current-area").click(areaShow)
});

//显示区域下拉菜单栏
 function areaShow(){
     $(".area-select").toggle();
 }