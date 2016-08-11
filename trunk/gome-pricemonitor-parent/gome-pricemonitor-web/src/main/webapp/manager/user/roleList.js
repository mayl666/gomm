$(function(){
	
    //添加回车事件
	$("#searchCon").keypress(function(event){
		if(event.which==13){
			$("#searchBtn").click();
		}
		
	});
	
     $("#searchBtn").click(function(){
          var condition = $("#searchCon").val();
  		 if(condition != ""){
            condition=encodeURIComponent(encodeURIComponent(condition));
		 }
          window.location.href = '../role/list?condition='+condition;
     
     
     });
     
 	$(".export-btn").click(function(){
		var condition=$("#searchCon").val();
 		 if(condition != ""){
 			condition=encodeURIComponent(encodeURIComponent(condition));
 		 }
		window.location.href = '../role/exportExcel?condition='+condition;
	});
 	
 	$(".fa-times").each(function(){
 		$(this).click(function(){
 			var roleId = $(this).attr("roleId");
 			 var statu = confirm("确定要删除该角色吗，删除后不可恢复");
 	        if(!statu){
 	            return false;
 	        }
 			//alert(userId);
 			$.ajax({
 	    		url:'../manager/ajax/role/del/'+roleId,
 	    		type:'POST',
 				dataType : 'json',
 	    		data:{},
 	    		success:function(data){
 	    			if(data.code==1){
 	    				alert("删除成功！");
 	    				window.location.href="../role/list";
 	    			}else{
 	    				alert(data.attach);
 	    			}
 	    		},
 	    		error:function(){
 	    			alert("操作失败");
 	    			
 	    		}
 	    		
 	    	});
 	
 		
       });
 		
 	});


});