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
<title>监控管理系统</title>
</head>
<!-- 设置了class就可在进入页面加载layout -->
<body class="easyui-layout">
	<!-- 正上方panel -->
    <div region="north" style="height:100px;padding:10px;" href="<%=root%>/main/top.html"></div>
    <!-- 正左边panel -->
    <div region="west" title="菜单栏" split="true" style="width:280px;padding1:1px;overflow:hidden;">
		<div class="easyui-panel" style="padding:5px">
			<ul class="easyui-tree">
				<li>
					<span>菜单</span>
					<ul>
						<li>
							<span>监控管理</span>
							<ul>
								<li>
									<span><a href="javascript:addTab('tabId_1','订单状态','<%=basePath %>jsp/monitoring/orderState.jsp');">订单状态</a></span>
								</li>
								<li>
									<span><a href="javascript:addTab('tabId_2','需求2','<%=basePath %>jsp/monitoring/NewFile2.jsp');">需求2</a></span>
								</li>
							</ul>
						</li>
						<li>
							<span>页面测试</span>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_LoginInfo','登录监控','<%=basePath %>jsp/test/MoLoginInfo.jsp');">登录监控</a></span>
								</li>
							</ul>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_test_NotRecharge','5分钟非充值订单','<%=basePath %>jsp/test/MoOrderNotRecharge.jsp');">5分钟非充值订单</a></span>
								</li>
							</ul>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_test_Recharge','5分钟充值订单','<%=basePath %>jsp/test/MoOrderRecharge.jsp');">5分钟充值订单</a></span>
								</li>
							</ul>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_test_DRAGON','DRAGON','<%=basePath %>jsp/test/DRAGON.jsp');">DRAGON</a></span>
								</li>
							</ul>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_test_EC','EC','<%=basePath %>jsp/test/EC.jsp');">EC</a></span>
								</li>
							</ul>
							<ul>
								<li>
								<span><a href="javascript:addTab('tabId_test_CPS','CPS','<%=basePath %>jsp/test/CPS.jsp');">CPS</a></span>
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
					<h3>你好，欢迎来到监控管理系统</h3>
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