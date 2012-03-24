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
		<title>添加产品规格</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/product.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<script type="text/javascript">
			basePath = '<%=basePath %>';
			
			var operationInfo = '${operationInfo}';
			if(operationInfo.length > 0)

			<% session.removeAttribute("operationInfo"); %>
		</script>
		<style type="text/css">
			.msg{
				font-size:12px;
				color:red;
			}
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
		<font class="navigation_style">当前位置:产品管理&raquo;添加产品规格</font>
		<br>
		<br>
		<form id="spec_form" name="form1" method="post" action="<%=basePath %>spec/add">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								添加产品规格
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" align="left" nowrap bgcolor="#FFFFFF" class="col2">
						<div align="right">
						&nbsp;&nbsp;&nbsp;&nbsp;
							规格名称：
						</div>
					</td>
					<td width="50%" align="left" nowrap bgcolor="#FFFFFF" class="col2">
						&nbsp;
						<input id="specName" name="name" type="text" size="35">
						<span id="specName_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="10%" align="left" nowrap bgcolor="#FFFFFF" class="col2">
						<div align="right">
						&nbsp;&nbsp;&nbsp;&nbsp;
							描述：
						</div>
					</td>
					<td width="50%" align="left" bgcolor="#FFFFFF" class="col2">
						&nbsp;
						<input name="desc" type="text" size="35">
					</td>
				</tr>
			</table>
			<br/>
			<div align="center">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="Submit22" class="button_style" value="添加">
			</div>
			<br/>
			<div align="center">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="<%=basePath%>floor/list">返回</a>
			</div>
			<br/>
			<br/>
			<br/>
		</form>
		
		
		<table width="60%" border="0" align="center" cellpadding="0"
			cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			<tr>
				<td height="25" colspan="3" align="right" class="col1">
					<div align="center" class="td_title">
						<div align="left">
							已有的产品规格
						</div>
					</div>
				</td>
			</tr>
			<tr align="center">
				<td width="10%" nowrap bgcolor="#FFFFFF" class="col2">

					序号
				</td>

				<td width="15%"nowrap bgcolor="#FFFFFF" class="col2">

					规格
				</td>
				<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">

					描述
				</td>
			</tr>
			<c:forEach items="${specs}" var="spec" varStatus="stat">

				<tr align="center">
					<td width="10%" nowrap bgcolor="#FFFFFF" class="col2">

						${stat.index + 1}
					</td>

					<td width="15%"  nowrap bgcolor="#FFFFFF" class="col2">

						${spec.name}
					</td>
					<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
						${spec.desc}
					</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>
