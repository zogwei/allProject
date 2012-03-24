<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员</title>
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

#search_table {
	background-image: url(<%=basePath%>img/web/left_bg.gif);
	border-collapse: collapse;
	padding: 0px;
	border: 1px solid white;
}

#search_table tr td {
	margin: 10px;
	background-image: url(<%=basePath%>img/web/left_bg.gif);
}

#result_table tr td {
	margin: 10px;
	background-image: url(<%=basePath%>img/web/left_bg.gif);
	border-collapse: collapse;
	padding: 0px;
}
</style>

	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:管理员管理</font>
		<br>
		<br>
		<form name="form1" method="post" action="<%=basePath%>system/list">
			<table id="search_table" width="95%" border="0" align="center"
				cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="6" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								管理员查询
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" height="25" align="right" nowrap class="col2">

						姓名：
					</td>
					<td width="400px" align="left" nowrap class="col2">
						&nbsp;
						<input name="name" type="text" align="right" id="MidCls3"
							value="${name }" size="25">
					</td>
					<td width="70%" colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<input name="page.currentPage" value="1" type="hidden" />
						<input name="page.pageSize" value="15" type="hidden" />
						<input name="category" value="1" type="hidden" />
					</td>
				</tr>
				<tr>
					<td colspan="6">
						&nbsp;
					</td>
				</tr>
			</table>
		</form>

		<div align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="Submit2" value="查询管理员"
				class="button_style" onClick="form1.submit();"/>
			<input type="button" name="Submit22" value="添加管理员"
				class="button_style"
				onClick="window.location.href='<%=basePath%>system/tenantList2'" />
			<span style="color: red" id="accountInfo">${errorMessage }</span>
		</div>
		<br />

		<table id="result_table" width="95%" border="0" align="center"
			cellpadding="5" cellspacing="1" bgcolor="#FFFFFF">

			<tr align="center">
				<td width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td  width="15%" align="center" nowrap class="col1">
					姓名
				</td>
				<td  width="15%" align="center"  nowrap class="col1">
					账号
				</td>
				<td width="35%" align="center" nowrap class="col1">

					住址
				</td>
				<td width="10%" align="center" nowrap class="col1">
					性别
				</td>
				<td width="15%"  align="center"   nowrap class="col1">
					管理操作
				</td>
			</tr>
			<c:forEach items="${pageSupport.result}" var="employee"
				varStatus="userStatus">
				<tr align="center">
					<td height="24" align="center" nowrap class="col2">
						 ${userStatus.count }
					</td>
					<td align="center" nowrap class="col2">
						 ${employee.name }
					</td>
					<td align="center" nowrap class="col2">
						 ${employee.account }
					</td>
					<td align="center" nowrap class="col2">
						 ${employee.address }

					</td>
					<td height="24" align="center" nowrap class="col2">
						<label>
							<c:choose>
								<c:when test="${employee.sex==1}">男</c:when>
								<c:when test="${employee.sex==2}">女</c:when>
							</c:choose>
						</label>
					</td>
					<td align="center" nowrap class="col2">
						<a href="<%=basePath%>system/editDetail?id=${employee.id}">详情</a>
						&nbsp;|&nbsp;
						<a href="<%=basePath%>system/one?id=${employee.id}">修改</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr align="center">
				<td height="24" colspan="4" align="center">
					<form name="form2" method="post" action="">
						<input type="hidden" name="name" value="${query.name}" />
						<c:if test="${pageSupport.currentPage > 1}">
							<a href="javascript:;"
								onclick="
						form2.action = '<%=basePath%>system/list?currentPage=1';form2.submit();">首页</a>
						&nbsp;
						<a href="javascript:;"
								onclick="
						form2.action = '<%=basePath%>system/list?currentPage=${pageSupport.currentPage - 1}';form2.submit();">上一页</a>
						</c:if>
						&nbsp;&nbsp;
						--第${pageSupport.currentPage}页/共${pageSupport.totalPages}页--
						&nbsp;&nbsp;
						<c:if test="${pageSupport.currentPage < pageSupport.totalPages}">
							<a href="javascript:;"
								onclick="
						form2.action = '<%=basePath%>system/list?currentPage=${pageSupport.currentPage + 1}';form2.submit();">下一页</a>
						&nbsp;
						<a href="javascript:;"
								onclick="
						form2.action = '<%=basePath%>system/list?currentPage=${pageSupport.totalPages}';form2.submit();">尾页</a>
						</c:if>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>