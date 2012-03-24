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
		<title>修改员工</title>
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/employeePwdEdit.js"></script>
		<style type="text/css">
			body {
				background-image: url(<%=basePath%>img/web/22.jpg);
			}
			
			table tr td {
				background-image: url(<%=basePath%>img/web/left_bg.gif);
				border-collapse: collapse;
				padding: 5px;
			}
		</style>
		
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:管理员管理&raquo;修改管理员密码</font>
		<br>
		<br>
		<form name="form1" method="post"
			action="<%=basePath%>system/modifyPwd" id="form1">
			<input type="hidden" name="id" value="${userSession.id}" />
			<table width="60%" border="0" align="center" cellpadding="0"
				bgcolor="#FFFFFF" cellspacing="1">
				<tr>
					<td height="25" colspan="7" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								修改密码
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							新密码：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="password" type="password" id="password" size="25">
						&nbsp;
						<span style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							确认密码：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="repwd" type="password" id="repwd" size="25">
						&nbsp;
						<span style="color: red"></span>
					</td>
				</tr>

			</table>
			<br />
			<div align="center">
				<input type="submit" class="button_style" name="Submit22" value="修改">
			</div>
			<br />
			<div align="center">
				<a href="<%=basePath%>system/list?tenantId=${userSession.id}">返回</a>
			</div>
		</form>
	</body>
</html>
