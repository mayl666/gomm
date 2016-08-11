	
 	function iframeClick(){
    	$("#img").contents().find("#file").click();
    }
	
	// 图片上传
	function upload() {
		if (!checkImg("file")) {
			pop_up("图片格式错误！图片格式为jpg,png,gif,bmp",false);
			return;
		}
		$("#img").contents().find("#imgform").submit();
		
    }
    // 检查文件格式
    function checkImg(fileId) {
    	// $("#imgMsg").text('');//清空错误信息
    	var fileName = $("#img").contents().find("#" + fileId).val(); // 文件名称
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
    
