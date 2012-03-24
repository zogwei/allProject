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
		<base href="<%=basePath%>">

		<title>系统管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/adminMyweb.css">
	</head>
	
	<body bgcolor="#FFFFFF" style="background-image: url('<%=basePath%>img/web/22.jpg')">
		<div align="center" class="style1">
			<table width="96%" height="465">
				<tr>
					<td>
						<p align="center" />
							<br></br>
					</td>
				</tr>
			</table>
			<table width="95%" border="0" cellpadding="0" cellspacing="0"
				height="108">
				<tr>
					<td>
						<p align="center" />
					</td>
				</tr>

			</table>
		</div>
	</body>
</html>
