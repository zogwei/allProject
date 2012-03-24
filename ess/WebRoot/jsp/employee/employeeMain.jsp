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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>员工管理</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
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
		<font class="navigation_style">当前位置:员工管理</font>
		<br>
		<br>
		<form name="form1" method="post" action="">
			<table id="search_table" width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="6" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								员工查询 
								<input type="hidden" name="tenantId" value="${userSession.tenantId}"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" align="right" class="col2">
						姓名：
					</td>
					<td width="20%" align="left" class="col2">
						&nbsp;
						<input name="name" type="text" id="name" size="25" value="${query.name}">
					</td>
					<td width="10%" align="right" class="col2">
						<div align="right">
							角色：
						</div>
					</td>
					<td width="20%" align="left" class="col2">
						&nbsp;
						<select name="category" style="width: 150;">
							<option value="-1">--请选择--</option>
							<option value="1" ${query.category==1?"selected":""}>管理员</option>
							<option value="2" ${query.category==2?"selected":""}>销售经理</option>
							<option value="3" ${query.category==3?"selected":""}>销售人员</option>
						</select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr><td colspan="6">&nbsp;</td></tr>
			</table>
			<br>
			<div align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="Submit2" value="查询员工"  class="button_style"
					onClick="form1.action = '<%=basePath %>employee/list';form1.submit();">
				<input type="button" name="Submit22" value="添加员工" class="button_style"
					onClick="window.location.href='<%=basePath %>jsp/employee/employeeAdd.jsp'";>
			</div>
		</form>
		
		<table id="result_table" width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr align="center">
				<td  width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td width="15%" align="center" nowrap class="col1">
					账号
				</td>
				<td  width="15%" align="center" nowrap class="col1">
					姓名
				</td>
			<td  width="10%" align="center" nowrap class="col1">
					性别
				</td>
			<td  width="15%" align="center" nowrap class="col1">
					电话
				</td>
			<td width="15%"  align="center" nowrap class="col1">
					证件号码
				</td>
			<td  wdith="10%" align="center" nowrap class="col1">
					角色
				</td>
			<td width="15%" nowrap class="col1">
					管理操作
			</td>
			</tr>
		<c:forEach items="${pageSupport.result}" var="employee" varStatus="s">	
			<tr align="center">
				<td height="24" align="center" nowrap class="col2">
					${s.index+1}
				</td>
				<td align="center" class="col2">
					${employee.account }
				</td>
				<td align="center" class="col2">
					<div align="center">
						${employee.name }
					</div>
				</td>
				<td align="center" class="col2">
					<div align="center">
							<c:choose>
								<c:when test="${employee.sex==1}">男</c:when>
								<c:when test="${employee.sex==2}">女</c:when>
							</c:choose>
					</div>
				</td>
				<td align="center" class="col2">
					${employee.phone }
				</td>
				<td align="center" class="col2">
					${employee.cardNo }
				</td>
				<td align="center" class="col2">
					<c:choose>
						<c:when test="${employee.category ==1}">
							管理员 
						</c:when>
						<c:when test="${employee.category ==2}">
							销售经理
						</c:when>
						<c:otherwise>
							销售人员
						</c:otherwise>
					</c:choose>
				</td>
				<td align="center" nowrap class="col2">
					<a href="<%=basePath %>employee/one?id=${employee.id}">详情</a>
			         	|
					<a href="<%=basePath %>employee/editDetail?id=${employee.id}">修改</a>
				</td>
			</tr>
			</c:forEach>
		</table>
		<br/>
		<table width="95%" border="0" align="center" cellpadding="5">
			<tr align="center">
				<td height="24" colspan="4" align="center">
					<form name="form2" method="post" action="">
					<input type="hidden" name="name" value="${query.name}"/>
					<input type="hidden" name="category" value="${query.category}"/>
					<c:if test="${pageSupport.currentPage > 1}">
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>employee/list?currentPage=1';form2.submit();">首页</a>
						&nbsp;
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>employee/list?currentPage=${pageSupport.currentPage - 1}';form2.submit();">上一页</a>
					</c:if>
					&nbsp;&nbsp;
					--第${pageSupport.currentPage}页/共${pageSupport.totalPages}页--
					&nbsp;&nbsp;
					<c:if test="${pageSupport.currentPage < pageSupport.totalPages}">
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>employee/list?currentPage=${pageSupport.currentPage + 1}';form2.submit();">下一页</a>
						&nbsp;
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>employee/list?currentPage=${pageSupport.totalPages}';form2.submit();">尾页</a>
					</c:if>
					</form>
				</td>
			</tr>
		</table>

	</body>
</html>