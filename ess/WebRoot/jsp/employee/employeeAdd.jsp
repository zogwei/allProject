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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加员工</title>
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/employeeAdd.js"></script>
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
		<script type="text/javascript">
			basePath = '<%=basePath %>';
		</script>
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:员工管理&raquo;添加员工</font>
		<br>
		<br>
		<form name="form1" method="post" action="<%=basePath%>employee/add"
			id="form1" >
			<input type="hidden" name="tenantId" value="${userSession.tenantId}" />
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="3" align="right" nowrap class="col1">
						<div align="center" >
							<div align="left" class="td_title">
								添加员工
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						账号：
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="account" type="text" id="account" size="35" maxlength="30">
						<span id="accountInfo" style="color: red"></span>
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
						<input name="name" type="text" id="name" size="35" maxlength="16">
						<span id="nameInfo" style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							密码：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="password" type="password" id="password" size="35" maxlength="32">
						<span style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td width="95px" align="right" nowrap class="col2">
						性别：
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<select name="sex">
							<option value="0" ,selected="selected">
								--请选择--
							</option>
							<option value="1">
								男
							</option>
							<option value="2">
								女
							</option>
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
						<input name="phone" type="text" id="phone" size="35">
						<span style="color: red"></span>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							证件号：
						</div>
					</td>
					<td colspan="2" align="left" nowrap class="col2">
						&nbsp;
						<input name="cardNo" type="text" id="cardNo" size="35" maxlength="96"/>
					</td>
				</tr>
				<tr>
					<td width="95px" align="right" nowrap class="col2">
						地址：
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="address" type="text" id="address" size="35" maxlength="64"/>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							角色：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<select name="category">
							<option value="1">
								管理员
							</option>
							<option value="2">
								销售经理
							</option>
							<option value="3" selected>
								销售人员
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							备注：
						</div>
					</td>
					<td colspan="2" align="left" nowrap class="col2">
						&nbsp;
						<input name="desc" type="text" id="desc" size="35" maxlength="64"/>
					</td>
				</tr>
			</table>
			<br/>
			<div align="center">
				<input type="submit" name="Submit22" class="button_style" value="添加"><span style="color:red">&nbsp;&nbsp;${addError }</span>
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath%>employee/list?tenantId=${userSession.id}">返回</a>	
			</div>
		</form>
	</body>
</html>
