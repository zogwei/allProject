<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>入库管理</title>
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/admin.js"></script>
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
		<font class="navigation_style">当前位置:库存管理&raquo;库存详细信息</font>
		<br>
		<br>
		<table width="60%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">

			<tr>
				<td height="25" colspan="2" align="left" nowrap class="col1">
					<div align="center" class="td_title">
						<div align="left">
							库存详细信息
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="95px" height="25" align="right" nowrap class="col1">
					地板名称：
				</td>
				<td align="left" nowrap class="col2">
					&nbsp;
					${inStorage.floor.name }
				</td>
			</tr>
			<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						入库片数：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;${inStorage.quantity}
						（块）
						<span id="quantity_msg" style="color: red"></span>
					</td>
					</tr>
					
					<tr>
				<td width="95px" height="25" align="right" nowrap class="col1">
					进价：
				</td>
				<td align="left" nowrap class="col1">
					&nbsp;
					${inStorage.price }(￥/㎡)
				</td>
			</tr>
			<tr>
				<td width="95px" height="25" align="right" nowrap class="col1">
					入库面积：
				</td>
				<td align="left" nowrap class="col1">
					&nbsp;
					${inStorage.areaStr }(㎡)
				</td>
			</tr>
			
			<tr>	
				<td width="95px" align="right" nowrap class="col1">
					总价：
				</td>
				<td align="left" nowrap class="col1">
					&nbsp;
					${inStorage.countStr }￥
				</td>
			</tr>
			<tr>
				<td width="95px" height="25" align="right" nowrap class="col1">
					经办人：
				</td>
				<td align="left" nowrap class="col1">
					&nbsp;
					${inStorage.operator }
				</td>
			</tr>
			<tr>
				<td width="95px" height="25" align="right" nowrap class="col1">
					创建时间：
				</td>
				<td align="left" nowrap class="col1">
					&nbsp;
					${time }
				</td>
			</tr>
			<tr>
				<td width="95px" align="right" nowrap class="col1">
					<div align="right">
						备注：
					</div>
				</td>
				<td align="left" nowrap class="col1">
					&nbsp;
					${inStorage.desc  }
				</td>
			</tr>
		</table>
		<br/>
		<div align="center">
			<a href="<%=basePath %>inStorage/list">返回</a>		
		</div>
	</body>
</html>
