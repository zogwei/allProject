<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>    

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>供应商管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<style type="text/css">
			body {
				background-image: url(<%=basePath %>img/web/22.jpg);
			}
			#search_table {
				background-image: url(<%=basePath %>img/web/left_bg.gif);
				border-collapse:collapse;
				padding:0px;
				border: 1px solid white;
			}
			#search_table tr td {
				margin:10px;
				background-image: url(<%=basePath %>img/web/left_bg.gif);
			}
			
			#result_table tr td {
				margin:10px;
				background-image: url(<%=basePath %>img/web/left_bg.gif);
				border-collapse:collapse;
				padding:0px;
			}
		</style>

	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:供应商管理</font>
		<br>
		<br>
		<form name="form1" method="post" action="<%=basePath %>supplier/likeList">
		<table id="search_table" width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">
			
				<tr>
					<td height="25" colspan="6" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								供应商管理
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" height="25" align="right" nowrap class="col2">
						供应商名称：
					</td>
					<td width="12%" align="left" nowrap class="col2">
						&nbsp;
						<input name="name" type="text" size="25">
					</td>
					<td align="left" nowrap class="col2" colspan="4">
					<input name="tenantId" type="hidden" size="25" value="1">
						<div align="right"></div>
					</td>
				</tr>
				<tr><td colspan="6">&nbsp;</td></tr>
			</table>
			<br/>
			<div align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询供应商" class="button_style"/>
				<c:if test="${userSession.tenantId==1}"><input type="button" value="添加供应商" class="button_style"
					onClick="location.href='<%=basePath %>jsp/supplier/supplierAdd.jsp'"/></c:if>
			</div>
		</form>
		<table id="result_table" width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr align="center">
				<td width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td width="20%" align="center" nowrap class="col1">
					供应商名称
				</td>
				<td width="20%" align="center" nowrap class="col1">
					联系人
				</td>
				<td width="20%" nowrap class="col1">
					联系人电话
				</td>
				<td width="20%" nowrap class="col1">
					地址
				</td>
				<td width="15%" nowrap class="col1">
					管理操作
				</td>
			</tr>
			
			<c:forEach items="${suppliers }" var="supplier" varStatus="status">
			<tr align="center">
				<td height="24" align="center" nowrap class="col2">
					${status.index+1 }
				</td>
				<td align="center" nowrap class="col2">
					${supplier.name }
				</td>
				<td height="24" align="center" nowrap class="col2">
					${supplier.linkman }
				</td>
				<td align="center" nowrap class="col2">
					${supplier.phone }
				</td>
				<td align="left" nowrap class="col2">
					&nbsp;&nbsp;&nbsp;${supplier.address }
				</td>
				<td align="center" nowrap class="col2">
					<a href="<%=basePath %>supplier/one?id=${supplier.id }">详情</a>
				<c:if test="${userSession.tenantId==1}">|	<a href="<%=basePath %>supplier/toModify?id=${supplier.id }">修改</a></c:if>
				</td>
			</tr>
			</c:forEach>

		</table>
		
	</body>
</html>