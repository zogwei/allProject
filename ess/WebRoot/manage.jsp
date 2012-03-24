
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=uft-8">

		<link href="<%=basePath %>css/adminMyweb.css" rel="stylesheet" type="text/css">
		<title>客户管理系统</title>
	</head>

	<frameset rows="94,*" cols="*" framespacing="0" frameborder="NO"
		border="0">
		<frame src="<%=basePath %>jsp/admin/adminTop.jsp" name="top" scrolling="NO"
			noresize>
		<frameset rows="*" cols="198,*" framespacing="0" frameborder="NO"
			border="0">
			<frame src="<%=basePath %>jsp/admin/adminLeft.jsp" name="left" noresize >
			<frame src="<%=basePath %>jsp/admin/adminMain.jsp" name="main" noresize>
		</frameset>
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>