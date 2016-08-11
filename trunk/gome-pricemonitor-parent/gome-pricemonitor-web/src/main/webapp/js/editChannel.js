/**
 * Created by caowei on 2015/10/29
 */
	$(function(){
		
		//保存
		$("#save_btn").click(function(){
			if(checkSubmit()){
				var content={};
				content.id=$("#channelId").val();
		    	content.channelName=$("#channelName").val();
		    	content.picNum=$("#picNum").val();
		    	content.status=$("input[name='status']:checked").val();
		    	content.operateUser=$("#operateUser").val();
		    	$.ajax({
		    		url:'../channel/editChannel',
		    		type:'POST',
		    		dataType:'json',
		    		async:false,
		    		data:{
		    			content:JSON.stringify(content)
		    		},
		    		success:function(data){
		    			if(data.code==1){
		    				pop_up("修改成功",true);
							window.location.href="../channel/toChannelListView";
		    			}else{
		    				pop_up("修改失败",false);
		    			}
		    				
		    		},
		    		error:function(){
		    			pop_up("系统异常",false);
		    		}
		    		
		    	});
			} else {
				return false;
			}
			
		});
		
		//检查是否可以提交
		function checkSubmit(){
			if(!checkChannelName()){
				return false;
			}
			
			//清除所有错误信息
			$("span[id$=ErrorSpan]").html("");
			return true;
		}
		
		//检查频道名称
		function checkChannelName(){
			if(check.checkNull("channelName")){
				check.showErrorMessage("channelName", "请输入频道名称！");
				return false;
			}
			if($("#channelName").val().length < 4){
				check.showErrorMessage("channelName", "名称太短！");
				return false;
			}
			if($("#channelName").val().length > 16){
				check.showErrorMessage("channelName", "名称太长！");
				return false;
			}
			if (!check.checkFormat(check.nameReg, "channelName")) {
				check.showErrorMessage("channelName", '只能包含汉字、数字、字母、下划线并且不能以下划线开头和结尾，长度为4-16位！');
				return false;
			}
			if(checkChannelNameUsed()){
				return false;
			}
			check.clearErrorMessage("channelName");
			return true;
		}
		
		//检查频道名称是否已被占用
		function checkChannelNameUsed(){
			var usedFlag = false;
			var id = $("#channelId").val();
			var channelName = $("#channelName").val();
	    	$.ajax({
	    		url:'../channel/checkIsUsed',
	    		type:'POST',
	    		dataType:'json',
	    		async:false,
	    		data:{
	    			id:id,
	    			channelName:channelName
	    		},
	    		success:function(data){
    				if(data.code == 5){
    					check.showErrorMessage("channelName", "该名称已经被占用！");
    					usedFlag = true;
    				} else if(data.code == -1 || data.code == 2){
    					pop_up("系统异常",false);
    			  		usedFlag = false;
    				} else {
    					check.clearErrorMessage("channelName");
    					usedFlag = false;
    				}
	    				
	    		},
	    		error:function(){
	    			pop_up("系统异常",false);
			  		usedFlag = false;
	    		}
	    		
	    	});
	    	return usedFlag;
		}
		
		//即时检查频道名称
		$("#channelName").blur(function(){
			if(!checkChannelName()){
				return false;
			}
	    	
		});
		
		//返回
		$(".return-btn").click(function(){
			history.back();
		});
	
});
