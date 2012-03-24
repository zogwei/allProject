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
		<title>供应商详细信息</title>
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
		<font class="navigation_style">当前位置:供应商管理&raquo;供应商详细信息</font>
		<br>
		<br>
		<table width="60%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="4" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								供应商详细信息
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						供应商名称：
					</td>
					<td height="25" align="left" class="col2">
						${supplier.name }
					</td>
				</tr>
				<tr>
				   <td width="95px" height="25" align="left" nowrap class="col2">
						<div align="right">
							联系人：
						</div>
					</td>
					<td height="25" align="left" class="col2">
						${supplier.linkman }
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
				联系人电话：
					</td>
					<td align="left" class="col2">
						${supplier.phone }
					</td>
				</tr>
				<tr>
				    <td width="95px" height="25" align="left" nowrap class="col2">
						<div align="right">
							地址：
						</div>
					</td>
					<td height="25" align="left" class="col2">
						${supplier.address }
					</td>
				</tr>
				<tr>
				    <td width="95px" height="25" align="left" nowrap class="col2">
						<div align="right">
							创建时间：
						</div>
					</td>
					<td height="25" align="left" class="col2">
						${time }
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						备注：
					</td>
					<td align="left" class="col2">
						${supplier.desc }
						<div align="right"></div>
					</td>
				</tr>
		</table>
		<br/>
		<div align="center">
			<a href="<%=basePath %>supplier/list">返回</a>
		</div>
	</body>
</html>
