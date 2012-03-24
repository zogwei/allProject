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
		<title>员工详细信息</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
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
		<font class="navigation_style">当前位置:员工管理&raquo;员工详细信息</font>
		<br>
		<br>
		<input type="hidden" name="id" value="${employee.id}"/>
		<table width="60%" border="0" align="center" cellpadding="0"  bgcolor="#FFFFFF"
			cellspacing="1" >
			<tr>
				<td height="25" colspan="7" colspan="30" align="right" nowrap class="col1">
					<div align="center" class="td_title" >
						<div align="left">
							员工详细信息
						</div>
					</div>
				</td>
			</tr>
			<tr align="center">
				<td align="right" class="col1" width="95px">
					<div align="right">
						账号：
					</div>
				</td>
		
				<td align="center" class="col2">
				<div align="left">
					&nbsp;
				${employee.account }
				</div>
				</td>
			</tr>
			<tr>
				<td  align="center" class="col1">
					<div align="right">
					姓名：
					</div>
				</td>
				<td align="center" class="col2">
					<div align="left">
							&nbsp;
						${employee.name }
					</div>
				</td>
				</tr>
			<tr>
				<td  class="col1">
				<div align="right">
					性别：
					</div>
				</td>
					<td align="center" class="col2">
					<div align="left">
							&nbsp;	
						<c:choose>
								<c:when test="${employee.sex==1}">男</c:when>
								<c:when test="${employee.sex==2}">女</c:when>
						</c:choose>
					</div>
				</td>
					</tr>
				<tr>
				<td  class="col1">
				<div align="right">
					电话：
					</div>
				</td>
				<td align="center" class="col2">
				<div align="left">
						&nbsp;
					${employee.phone }
					</div>
				</td>
						</tr>
				<tr>
				<td  class="col1">
				<div align="right">
					证件号：
					</div>
				</td>
				<td align="center" class="col2">
					<div align="left">
					&nbsp;
					${employee.cardNo }
					</div>
				</td>
						</tr>
				<tr>
				<td  class="col1">
				<div align="right">
					地址：
					</div>
				</td>
					<td align="center" class="col2">
					<div align="left">
					&nbsp;
						${employee.address }
					</div>
				</td>
						</tr>
				<tr>
				<td  class="col1">
				<div align="right">
					角色：
					</div>
				</td>
				<td align="center" class="col2">
				<div align="left">
				&nbsp;
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
					</div>
				</td>
						</tr>
				<tr>
				<td  class="col1">
					<div align="right">
					备注：
					</div>
				</td>
					
				
				<td align="center" class="col2">
				<div align="left">
				&nbsp;
				${employee.desc }
				</div>
				</td>
						</tr>
				<tr>
				<td  class="col1">
				
				<div align="right">
					创建日期：
					</div>
				</td>
		
					<td align="center" class="col2">
					<div align="left">
					&nbsp;
					${employee.strCreatedDate }
					</div>
				</td>
			</tr>
			</table>
				<br/>
			<div align="center">
				<a href="<%=basePath%>employee/list?tenantId=${userSession.id}">返回</a>	
			</div>
		
	</body>
</html>
