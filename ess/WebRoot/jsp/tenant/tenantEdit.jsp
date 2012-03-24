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
		<title>租户管理--修改</title>
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
			table tr td{
				background-image: url(<%=basePath %>img/web/left_bg.gif);
				border-collapse:collapse;
				padding:5px;
			}
		</style>
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:租户管理&raquo;修改租户</font>
		<br>
		<br>
		<form name="form1" method="post" id="form_name" action="<%=basePath %>tenant/edit">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF" >
			
					<tr>
						<td height="25" colspan="2" align="right" class="col1">
							<div align="center" class="td_title">
								<div align="left">
									修改租户信息
									<input type="hidden" name="id" value="${tenant.id}" id="tenantId" ></input>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="95px" height="25" align="right" nowrap class="col2">
							名称：
						</td>
						<td width="400px" align="left" class="col2">
							&nbsp;
							<input name="name" type="text" id="name1" value="${tenant.name}" maxlength="24">
							<span id="name_msg" style="color: red"></span>
						</td>
					</tr>
					<tr>
						<td width="95px" height="25" align="right" nowrap class="col2">
							备注：
						</td>
						<td align="left" class="col2">
							&nbsp;
							<input name="desc" type="text" id="desc" value="${tenant.desc}" maxlength="64">
						</td>
					</tr>
				
			</table>
			<br/>
			<div align="center">
				<input type="submit" name="Submit22" class="button_style" id="submit1_id" value="修改">
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath %>tenant/list" target="main">返回</a>	
			</div>
		</form>
	</body>
</html>
