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
		<title>修改管理员</title>
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/add_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/admin.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/employeeEdit.js"></script>
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
      	<script type="text/javascript">
			basePath = '<%=basePath %>';
		</script>
		
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:管理员管理&raquo;修改管理员信息</font>
		<br>
		<br>
		<form name="form1" id="form1" method="post" action="<%=basePath%>system/modify">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								修改管理员
								<input type="hidden" name="id" value="${employee.id }" id="id"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							姓名：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="name" type="text" id="name"
							value="${employee.name }" size="30" maxlength="16">
							<span style="color: red" id="nameInfo"></span>
					</td>
				</tr>
				
				<tr>
					<td height="25" width="95px" align="right" nowrap class="col2">
						性别：
					</td>
					<td  align="left" nowrap class="col2" >
						&nbsp;
						<select name="sex" size="1">
							<c:choose>
								<c:when test="${employee.sex==0}">
									<option value="1">
										男
									</option>
									<option value="2">
										女
									</option>
								</c:when>
								<c:when test="${employee.sex==1}">
									<option value="1" selected="selected">
										男
									</option>
									<option value="2">
										女
									</option>
								</c:when>
								<c:when test="${employee.sex==2}">
									<option value="1">
										男
									</option>
									<option value="2" selected="selected">
										女
									</option>
								</c:when>
							</c:choose>
						</select>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							电话：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="phone" type="text" id="phone" value="${employee.phone }"
							 size="30" maxlength="50">
						<span style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							证件号：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="cardNo" type="text"  id="cardNo" value="${employee.cardNo }"
							 size="30" maxlength="96">
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							地址：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="address" type="text" value="${employee.address }"
							id="address" size="30" maxlength="64">
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							备注：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="desc" type="text" value="${employee.desc} "
							id="desc" size="30" maxlength="64">
					</td>
				</tr>
			</table>
			<br/>
			<div align="center">
				<input type="submit" class="button_style" name="Submit22" value="修改" >
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath%>system/list?tenantId=${userSession.id}">返回</a>	
			</div>
		</form>
	</body>
</html>
