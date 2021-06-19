<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>left</title>
	<link rel="stylesheet" href="<%=basePath%>Font-Awesome-master/css/font-awesome.css" />
	<link rel="stylesheet" href="<%=basePath%>css/left/left.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
</head>
<body>
	<div class="container-left">
		<div class="nav">
			<s:if test="#request.user.roleID==0">
				<a href="<%=basePath%>MenuAction!oneLevelMenuQuery.action?page=1" class="listButton" target="rightframe"><h3><i class="fa fa-bullhorn"></i>一级菜单管理</h3></a>
				<a href="<%=basePath%>MenuAction!twoLevelMenuManage.action?page=1&menu.parentID=0" class="listButton" target="rightframe"><h3><i class="fa fa-server" target="rightframe"></i>二级菜单管理</h3></a>
				<a href="<%=basePath%>ContentAction!ContentQueryInit.action" class="listButton" target="rightframe"><h3><i class="fa fa-reorder" target="rightframe"></i>内容管理??</h3></a>
				<a href="<%=basePath%>ContentAction!informationQuery.action?page=1" class="listButton" target="rightframe"><h3><i class="fa fa-suitcase"  ></i>信息发布</h3></a>
				<a href="<%=basePath%>MenuAction!indexConfig.action" class="listButton" target="rightframe"><h3><i class="fa fa-suitcase"  ></i>主页配置</h3></a>
			</s:if>
			<s:if test="#request.user.roleID==1">
				<a href="<%=basePath%><%=basePath%>userAction!queryUser.action?page=1" class="listButton" target="rightframe"><h3><i class="fa fa-suitcase"  ></i>用户管理</h3></a>
			</s:if>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			var listButton = $('.listButton');
			listButton.click(function(){
				listButton.css('background-image','none');
				$(this).css('background','url("../image/li_current.png") right center no-repeat');
			});
		});
	</script>
</body>
</html>