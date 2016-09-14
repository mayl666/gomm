/**
 * 左侧菜单样式
 *
 * */
$(function(){
    //改变左侧菜单栏
    $(".myApp .treeview").on('click',function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    $('#myApp').on('click',function(){
    	location.href = "appManager.html";
    	return false;
    });
    
    //ajax请求session过期后的页面跳转
    $.ajaxSetup({
    	  type: 'POST',
    	  complete: function(xhr,status) {
    		  var data = xhr.responseJSON;
    		  if(data.modelAndView!=null
    				  &&data.modelAndView.viewName!=null
    				  &&data.modelAndView.viewName=="/login"){
    	       window.location.href = '../threshold/login';      
    	    }
    	  }
    });
});