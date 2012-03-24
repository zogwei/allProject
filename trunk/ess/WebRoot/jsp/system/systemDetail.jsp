<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>管理员详细信息</title>
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/add_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/admin.js"></script>
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
		<font class="navigation_style">当前位置:系统管理员管理&raquo;管理员详细信息</font>
		<br>
		<br>
		<table width="60%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr>
				<td height="25" colspan="7" align="right" class="col1">
					<div align="center" class="td_title">
						<div align="left">
							管理员详细信息
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="95px" height="25" align="right" class="col2">
					账号：
				</td>
				<td  align="left" class="col2">
					&nbsp; ${employee.account }
				</td>
			</tr>
			<tr>
				<td width="95px" align="left" nowrap class="col2">
					<div align="right">
						姓名：
					</div>
				</td>
				<td align="left" class="col2">
					&nbsp; ${employee.name }
				</td>
			</tr>
			<tr>
				<td height="10" width="95px" align="right" class="col2">
					性别：
				</td>
				<td  align="left" class="col2">
					&nbsp;
					<label>
						<c:choose>
							<c:when test="${employee.sex==1}">男</c:when>
							<c:when test="${employee.sex==2}">女</c:when>
						</c:choose>
					</label>
				</td>
			</tr>
			<tr>

				<td width="95px" align="left" nowrap class="col2">
					<div align="right">
						电话：
					</div>
				</td>
				<td  align="left" class="col2">
					&nbsp; ${employee.phone }
				</td>
			</tr>
			<tr>
				<td width="95px" align="left" nowrap class="col2">
					<div align="right">
						证件号：
					</div>
				</td>
				<td align="left" class="col2">
					&nbsp; ${employee.cardNo }
				</td>
			</tr>
			<tr>
				<td height="10" width="95px" align="right" class="col2">
					地址：
				</td>
				<td  align="left" class="col2">
					&nbsp; ${employee.address }
				</td>
			</tr>
			<tr>
				<td width="95px"align="left" nowrap class="col2">
					<div align="right">
						角色：
					</div>
				</td>
				<td align="left" class="col2">
					&nbsp;
					<c:choose>
						<c:when test="${employee.category ==1 }">管理员</c:when>
						<c:when test="${employee.category ==2 }">销售经理</c:when>
						<c:when test="${employee.category ==3 }">销售人员</c:when>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td width="95px" align="left" nowrap class="col2">
					<div align="right">
						备注：
					</div>
				</td>
				<td align="left" class="col2">
					&nbsp; ${employee.desc}
				</td>
			</tr>
			<tr>
				<td height="25" width="95px"align="right" class="col2">
					创建日期：
				</td>
				<td align="left" class="col2">
					&nbsp; ${createDate }
				</td>
			</tr>
		</table>
		<br />
		<div align="center">
			<a href="<%=basePath%>system/list?tenantId=${userSession.id}">返回</a>
		</div>
	</body>
</html>
