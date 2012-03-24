<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link href="<%=basePath %>css/adminMyweb.css" rel="stylesheet" type="text/css">
		<title>地板销售管理系统</title>
		<link href="<%=basePath %>css/adminMyweb.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			height="94" background="<%=basePath %>img/web/logo_bg.gif">
			<tr>
				<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="top">
								<img align="right" src="<%=basePath %>img/web/logo.gif" 
									width="211" height="94" border="0" usemap="#Map">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<map name="Map">
			<area shape="rect" coords="126,69,213,97" href="<%=basePath %>logout"
				target="_parent">
			<area shape="rect" coords="12,69,91,100" href="#" target="_blank">
		</map>
	</body>

</html>
