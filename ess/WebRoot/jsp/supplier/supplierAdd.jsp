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
		<title>添加供应商信息</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/supplier.js"></script>
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
	<body>&nbsp; 
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:供应商管理&raquo;添加供应商</font>
		<br>
		<br>
		<form name="form1" id="form_id" method="post" action="<%=basePath %>supplier/add">
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="2" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								添加供应商信息
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col1">
						供应商名称：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="name" id="supplierName" type="text" size="35" maxlength="16">
						<input name="id" id="supplier_id" type="hidden" value="0"/>
						<span id="supplier_msg" style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td height="25" width="95px" align="left" nowrap class="col1">
						<div align="right">
							联系人：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="linkman" type="text" size="35" maxlength="16">
					</td>
				</tr>
				<tr>
					<td height="25" width="95px" align="right" nowrap class="col1">
						联系电话：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="phone" id="phone" type="text" size="35" maxlength="50">
						<span id="phone_msg" style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td height="25" width="95px" align="right" nowrap class="col1">
							地址：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="address" type="text" size="35" maxlength="64">
					</td>
				</tr>
				<tr>
					<td height="25" width="95px" align="right" nowrap class="col1">
						备注：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="desc" type="text" size="35"  maxlength="64">
						<div align="right"></div>
					</td>
				</tr>
			</table>
			<br/>
			<div align="center">
				<input type="submit" name="Submit22" class="button_style" value="添加">
				&nbsp;
				<input type="reset" class="button_style" value="重置">
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath %>supplier/list">返回</a>	
			</div>
		
		</form>
	</body>
</html>
