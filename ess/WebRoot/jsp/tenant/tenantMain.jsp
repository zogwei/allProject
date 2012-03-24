<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>租户管理</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/tenant.js"></script>
		<script type="text/javascript">
			basePath = '<%=basePath %>';
		</script>
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
		<font class="navigation_style">当前位置:租户管理</font>
		<br>
		<br>
		<form name="form1" method="post"  id="form_name" action="<%=basePath %>/tenant/getTenantByName?name=${name}">
			<table id="search_table" width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFF">
					<tr>
						<td height="25" colspan="6" align="right" nowrap class="col1">
							<div align="center" class="td_title">
								<div align="left">
									租户查询
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%" height="25" align="right" nowrap class="col2">
							名称：
						</td>
						<td width="400px" align="left" nowrap class="col2">
							&nbsp;
							<input name="tenantName" type="text" id="name2" size="25" maxlength="24">
							<span id="name_msg" style="color: red"></span>
						</td>
						<td width="70%" colspan="4">&nbsp;</td>
					</tr>
					<tr><td colspan="6">&nbsp;</td></tr>
			</table>
			<br/>
			<div align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="Submit2" value="查询租户"  class="button_style" id="submit_id"/>
				<input type="button" name="Submit22" class="button_style" value="添加租户" onClick="
			      window.location='<%=basePath%>jsp/tenant/tenantAdd.jsp';" >
			</div>
			<br/>
		</form>
		
		<table id="result_table" width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			<tr align="center" class="col1">
				<td width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td width="15%" align="center" nowrap class="col1">
					名称
				</td>
				<td width="50%" align="center" nowrap class="col1">
					备注
				</td>
				<td width="15%" align="center" nowrap class="col1">
					创建时间
				</td>
				<td width="15%" nowrap class="col1">
					管理操作
				</td>
			</tr>
			<c:forEach items="${tenants}" var="tenant" >
				<tr align="center">
					<td height="24" align="center" nowrap class="col2">
						${tenant.id}
					</td>
					<td height="24" align="center" nowrap class="col2">
						${tenant.name}
					</td>
					<td height="24" align="left" nowrap class="col2">
						<label>&nbsp;&nbsp;&nbsp;${tenant.desc}</label>
					</td>
					<td height="24" align="center" nowrap class="col2">
						<label>${tenant.strCreatedDate}</label>
					</td>
					<td align="center" nowrap class="col2">
						<a href="<%=basePath %>tenant/one?id=${tenant.id}">修改</a>
					</td>
				</tr>
			</c:forEach>

		</table>
	</body>
</html>