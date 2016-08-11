<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/page/jqueryMaster.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- jquery easyui -->
<link rel="stylesheet" type="text/css" href="<%=root%>/common/js/easyui/themes/dayun/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/common/js/easyui/themes/icon.css">
<script type="text/javascript" src="<%=root%>/common/js/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=root%>/common/js/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=root%>/common/js/easyui/validate/easyui_validate.js"></script>

<script type="text/javascript" src="<%=root%>/common/js/main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户权限管理系统</title>
</head>
<!-- 设置了class就可在进入页面加载layout -->
<body class="easyui-layout">
	<!-- 正上方panel -->
    <div region="north" style="height:100px;padding:10px;" href="<%=root%>/common/page/top.html"></div>
    <!-- 正左边panel -->
    <div region="west" title="菜单栏" split="true" style="width:280px;padding1:1px;overflow:hidden;">
    	<!-- 
    	<div class="easyui-accordion" fit="true" border="false">
			<div title="用户权限管理" selected="true">
			<ul>
				<li><a href="javascript:addTab('tabId_loginInfo','连接配置','<%=basePath%>jsp/serviceCon/serviceConList.jsp');">连接配置</a></li>
				<li><a href="javascript:addTab('tabId_privilege','权限管理','<%=root%>/ospm/loginInfo/goPrivilegeMain.jhtml');">应用管理</a></li>
			</ul>
			</div>
		</div>
    	 -->
		<div class="easyui-panel" style="padding:5px">
			<ul class="easyui-tree">
				<li>
					<span>菜单</span>
					<ul>
						<li>
							<span>连接管理</span>
							<ul>
								<li>
									<span><a href="javascript:addTab('tabId_1','连接配置','<%=basePath %>jsp/serviceCon/serviceConList.jsp');">连接配置</a></span>
								</li>
							</ul>
						</li>
						<li>
							<span>应用管理</span>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_2','应用管理','<%=basePath %>jsp/adhibition/adhibitionList.jsp');">应用管理</a></span>
								</li>
								<li>
								<span><a href="javascript:addTab('tabId_3','应用根节点管理','<%=basePath %>jsp/node/adhibitionNodeEdit.jsp');">应用根节点管理</a></span>
								</li>
								<li>
								<span><a href="javascript:addTab('tabId_4','服务节点管理','<%=basePath %>jsp/serviceNode/rootNode.jsp');">服务节点管理</a></span>
								</li>
							</ul>
						</li>
						<li>
							<span>节点管理</span>
							<ul>
								<li>
									<span><a href="javascript:addTab('tabId_5','应用节点管理','<%=basePath %>/jsp/node/ywNodeEdit.jsp');">应用节点管理</a></span>
								</li>
								<li>
								<span><a href="javascript:addTab('tabId_6','服务节点管理','<%=basePath %>jsp/serviceNode/leafNode.jsp');">服务节点管理</a></span>
								</li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	
   	</div>
    <!-- 正中间panel -->
    <div region="center" title="功能区" >
    	<div class="easyui-tabs" id="centerTab" fit="true" border="false">
			<div title="欢迎页" style="padding:20px;overflow:hidden;"> 
				<div style="margin-top:20px;">
					<h3>你好，欢迎来到zookeeper管理系统</h3>
				</div>
			</div>
		</div>
    </div>
    <!-- 正下方panel -->
    <div region="south" style="height:50px;" align="center">
    	<label>
    	</label>
    </div>
</body>
</html>