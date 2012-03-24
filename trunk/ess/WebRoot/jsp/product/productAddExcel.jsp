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
		<title>批量添加地板</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/product.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<script type="text/javascript">
			basePath = '<%=basePath %>';
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
		<font class="navigation_style">当前位置:产品管理&raquo;导入地板信息</font>
		<br>
		<br>
		<form id="excel_form" name="form1" method="post" action="<%=basePath %>floor/adds" encType="multipart/form-data">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								批量导入地板信息
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" align="right" nowrap bgcolor="#FFFFFF" class="col2">
						<div align="right">
							导入文件：
						</div>
					</td>
					<td align="left" nowrap bgcolor="#FFFFFF" class="col2">
						&nbsp;
						<input width="60px" id="excel" name="excel" type="file" size="35">
						<span id="categoryName_msg" class="msg">请选择*.xls或*.xlsx文件</span>
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
				<a href="javascript:;" onClick="window.history.back()">返回</a>
			</div>
		</form>
	</body>
</html>
