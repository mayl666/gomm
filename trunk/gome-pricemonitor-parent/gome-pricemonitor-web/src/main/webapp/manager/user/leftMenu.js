$(function(){
	
	
	$("#sidebar-left-menu").empty();
	
	$.ajax({
		url:'../manager/ajax/func/left',
		type:'get',
		dataType:'json',
		data:{},
		success:function(data){
			if(data.code==1){
				//alert("添加用户成功！");
				var htmls=createLeftMenu(data);
				$(htmls).appendTo($("#sidebar-left-menu"));
				//$("#sidebar-left-menu").append(htmls);
				//alert(createLeftMenu(data));
				//window.location.href="../user/list?pageNo=1&pageSize=20";
			}else{
				alert("添加用户失败！");
			}
		},
		error:function(){
			alert('操作失败');
		}
		
	});
	
});

function createLeftMenu(data){
    var jsonData=data.attach;
    var htmlStr="";
    
    htmlStr += '<ul class="sidebar-menu">\n';
    //遍历一级菜单
    $.each(jsonData,function(n,value){
        htmlStr += '<li class="treeview">\n';
        htmlStr += '<a href="javascript:void(0);">';
        htmlStr += '<i class="fa fa-group"></i>';
        htmlStr += '<span>'+value.funcName+'</span>';
        htmlStr += '<i class="fa fa-angle-left pull-right"></i></a>';
        //遍历二级菜单 
        var childJson=value.childNodes;
        $.each(childJson,function(m,childValue){
           htmlStr += '<ul class="treeview-menu">'; 
           htmlStr += '<li><a href="'+childValue.funcUrl+'">';
           htmlStr += '<i class="fa fa-circle-o more_fa"></i>'+childValue.funcName+'</a>';
           htmlStr += '</li></ul></li>';
        });
        
        
    });
    htmlStr += '</ul>';
   //alert(htmlStr);
  //  console.info(htmlStr);
    return htmlStr;
}

//<li class="treeview">
//<a href="javascript:void(0);">
//    <i class="fa fa-group"></i>
//    <span>用户管理</span>
//    <i class="fa fa-angle-left pull-right"></i>
//</a>
//<ul class="treeview-menu">
//    <li>
//        <a href="userList.html"><i class="fa fa-circle-o more_fa"></i>用户列表</a>
//    </li>
//</ul>
//</li>