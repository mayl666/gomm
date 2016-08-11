$(function(){
	
	
    //添加回车事件
	$("#searchCon").keypress(function(event){
		if(event.which==13){
			$("#searchBtn").click();
		}
		
	});
	
	
	$("#searchBtn").click(function(){
		var content={};
		var condition=$("#searchCon").val();
		var roleId=$(".dropdown-select").val();
		if(condition != ""){
            condition=encodeURIComponent(encodeURIComponent(condition));
		}
		
		window.location.href = '../user/query?condition='+condition+'&roleId='+roleId;
	});
	
	
	$(".export-btn").click(function(){
		var condition=$("#searchCon").val();
		if(condition != ""){
			condition=encodeURIComponent(encodeURIComponent(condition));
		}
		var roleId=$(".dropdown-select").val();
		window.location.href = '../user/exportExcel?condition='+condition+'&roleId='+roleId;
	});
	
	
	
	
	
	$(".fa-times").each(function(){
		$(this).click(function(){
			var userId = $(this).attr("userId");
			 var statu = confirm("确定要删除该用户吗");
	 	        if(!statu){
	 	            return false;
	 	        }
			//alert(userId);
			$.ajax({
	    		url:'../manager/ajax/user/del/'+userId,
	    		type:'POST',
				dataType : 'json',
	    		data:{},
	    		success:function(data){
	    			if(data.code==1){
	    				alert("删除成功！");
	    				window.location.href="../user/list";
	    			}else{
	    				alert("删除失败！");
	    			}
	    		},
	    		error:function(){
	    			alert("操作失败");
	    			
	    		}
	    		
	    	});
	
		
      });
		
	});

});