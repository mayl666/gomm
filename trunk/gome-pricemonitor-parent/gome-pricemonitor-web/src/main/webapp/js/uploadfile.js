
	function iframeClick(){
    	$("#fileframe").contents().find("#file").click();
    }
	
	// 软件包上传
	function upload() {
		if (!checkImg("file")) {
			pop_up("软件包格式错误！必须以.apk结尾",false);
			return;
		}
		check.clearErrorMessage("filePath");
		$("#fileName").text($("#fileframe").contents().find("#file").val());
		$("#fileName").parent().show();
		$("#fileframe").contents().find("#fileform").submit();
		
    }
	
	// 检查文件格式
    function checkImg(fileId) {
    	// $("#imgMsg").text('');//清空错误信息
    	var fileName = $("#fileframe").contents().find("#" + fileId).val(); // 文件名称
    	fileType = [ "apk" ]; // 图片类型
    	fileExt = ""; // 图片拓展名
    	fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    	for ( var i in fileType) {
    		if (fileExt == fileType[i]) {
    			return true;
    		}
    	}
    	return false;
    }
