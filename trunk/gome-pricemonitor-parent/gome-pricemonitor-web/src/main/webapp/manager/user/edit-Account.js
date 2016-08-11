

$("#user_submit").click(function(event){
		//alert("ddd");
	event.preventDefault();
	//	var resu=$.formValidator.isOneValid("roleForm")
	//alert($.formValidator.pageIsValid('1'));
	if(!$.formValidator.pageIsValid('1')){
		return;
	}
		var content = {};
		content.userId=$("#userId").val();
		//content.userName=$("#userName").val();
		if($("#editPwd").val() != "" && $("#editPwd").val() != "******"){
			//alert("true");
			content.passwd=$.md5($("#editPwd").val(), 'gome.com');
		}
		content.roleId=$(".add_account_select").val();
		content.realName=$("#editName").val();
		content.contactWay=$("#editWay").val();
		content.state=$('input:radio[name=newWork_type]:checked').val();
		content.headPath=$("#imgurl").val();
		//console.log(content);
		//var s = JSON.stringify(content)
		//alert(s);
		//content="123";
		//alert(content);
		$.ajax({
    		url:'../manager/ajax/user/edit',
    		type:'POST',
			dataType : 'json',
    		data:{content:JSON.stringify(content)},
    		success:function(data){
    			if(data.code==1){
    				alert("编辑用户成功！");
    				window.location.href="../user/list";
    			}else{
    				alert("编辑用户失败！");
    			}
    		},
    		error:function(){
    			alert("操作失败");
    			
    		}
    		
    	});
	
	});





















function iframeClick(){
		$("#img").contents().find("#file").click();
	}
 
 
//图片上传
 function upload() {
 	if (!checkImg("file")) {
 		divshow("图片格式错误！图片格式为jpg,png,gif,bmp");
 		$("#mod-dialog").height(185);
   		$("#altbtn").click(function(){
 			 $("#mod-dialog").hide();
 			 $("#mod-dialog-bg").hide();
 		});
 		return;
 	}
 	$("#img").contents().find("#imgform").submit();
 	if($("#oneImage").attr("src")!=null){
 	    divshow("上传成功");
 		$("#altbtn").click(function(){
 			 $("#mod-dialog").hide();
 			 $("#mod-dialog-bg").hide();
 		});
 	}else{
 		$("#mod-dialog").css({"top":"100%"});
 		divshow("上传失败");
 		$("#altbtn").click(function(){
 			 $("#mod-dialog").hide();
 			 $("#mod-dialog-bg").hide();
 		});	
 	}
 }
 
 
//检查文件格式
 function checkImg(fileId) {
 	// $("#imgMsg").text('');//清空错误信息
 	var fileName = $("#img").contents().find("#file").val(); // 文件名称
 	fileType = [ "jpg", "png", "gif", "bmp" ]; // 图片类型
 	fileExt = ""; // 图片拓展名
 	fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
 	for ( var i in fileType) {
 		if (fileExt == fileType[i]) {
 			return true;
 		}
 	}
 	return false;
 }

 function divshow(msg){
 	var divId=document.getElementById("mod-dialog");     
     divId.style.top = (document.documentElement.scrollTop + (document.documentElement.clientHeight - divId.offsetHeight) / 2.5) + "px";
     divId.style.left = (document.documentElement.scrollLeft + (document.documentElement.clientWidth - divId.offsetWidth) / 2.5) + "px";
 	$("#tishi").text(msg);
 	$("#mod-dialog").show();
 	$("#mod-dialog-bg").show();	
 	
 }
 