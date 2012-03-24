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
		<title>租户管理--增加</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/tenant.js"></script>
		<script type="text/javascript">
			basePath = '<%=basePath %>';
		</script>
		<style type="text/css">
			body {
				background-image: url(<%=basePath %>img/web/22.jpg);
			}
			table tr td{
				background-image: url(<%=basePath %>img/web/left_bg.gif);
				border-collapse:collapse;
				padding:5px;
			}
		</style>
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:租户管理&raquo;添加租户</font>
		<br>
		<br>
		<form name="form1" id="form_name" method="post" action="<%=basePath %>tenant/add">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
					<tr>
						<td height="25" colspan="4" align="right" class="col1">
							<div align="center" class="td_title">
								<div align="left">
									添加租户
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="95px" height="25" align="right" nowrap bgcolor="#FFFFFF" class="col2">
							名称：
						</td>
						<td width="400px" colspan="3" align="left" bgcolor="#FFFFFF" class="col2">
							&nbsp;
							<input name="name" type="text" id="name" maxlength="24">
							<span id="tenant_msg" style="color: red"></span>
						</td>
					</tr>
					<tr>
						<td width="95px" height="25" align="right" nowrap bgcolor="#FFFFFF"
							class="col2">
							备注：
						</td>
						<td colspan="3" align="left" bgcolor="#FFFFFF" class="col2">
							&nbsp;
							<input name="desc" type="text" id="desc" maxlength="64">
							<span id="tenant_msg" style="color: red"></span>
						</td>
					</tr>
			</table>
			<br/>
			<div align="center">
				<input type="submit" name="Submit22" class="button_style" id="submit3_id" value="添加">
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath %>tenant/list" target="main">返回</a>	
			</div>
		</form>
	</body>
</html>
