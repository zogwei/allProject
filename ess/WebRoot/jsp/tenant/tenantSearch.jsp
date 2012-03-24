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
		<title>租户管理--查询</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
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
		<font class="navigation_style">当前位置:租户管理&raquo;查询租户</font>
		<br>
		<br>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1">
				<tr>
					<td height="25" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="center"> 
								你所查询的租户信息
							</div>
						</div>
					</td>
				</tr>
			
		</table>
		<br>
		<table width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr align="center">
				<td width="10%" height="26" nowrap bgcolor="#FFFFFF" class="col1">
					序号
				</td>
				<td width="30%" align="center" nowrap bgcolor="#FFFFFF" class="col1">
					名称
				</td>
				<td width="40%" nowrap bgcolor="#FFFFFF" class="col1">
					备注
				</td>
				<td width="20%" nowrap bgcolor="#FFFFFF" class="col1">
					创建日期
				</td>
			</tr>
			<tr align="center">
						<td height="24" align="center" nowrap bgcolor="#FFFFFF" class="col2">
								${tenant.id}
						</td>
						<td height="24" align="center" nowrap bgcolor="#FFFFFF" class="col2">
							<label></label>
							<div align="left">
								${tenant.name}
							</div>
						</td>
						<td align="center" nowrap bgcolor="#FFFFFF" class="col2">
							<div align="left">
								${tenant.desc}
							</div>
						</td>
						<td align="center" nowrap bgcolor="#FFFFFF" class="col2">
							<div align="left">
								${time}
							</div>
						</td>
					</tr>			
			</table>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<label></label>
					</td>
				</tr>
			</table>		
		<br/>
		<div align="center">
			<a href="<%=basePath %>tenant/list" target="main">返回</a>	
		</div>
	</body>
</html>