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
		<title>修改员工</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/employeeEdit.js"></script>
		
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
		<font class="navigation_style">当前位置:员工管理&raquo;修改员工</font>
		<br>
		<br>
		<form name="form1" method="post" action="<%=basePath %>employee/modify" id="form1">
			<input type="hidden" name="id" value="${employee.id}" id="id"/>
			<table width="60%" border="0" align="center" cellpadding="0"  bgcolor="#FFFFFF"
				cellspacing="1" >
				<tr>
					<td height="25" colspan="7" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								修改员工
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
						<input  name="name" type="text" id="name" size="20" value="${employee.name}" maxlength="16">
						&nbsp;
								<span id="nameInfo" style="color:red"></span>
					</td>
					</tr>
					<tr>
					<td width="95px" align="right" nowrap class="col2">
						性别：
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<select name="sex"
							size="1">
							<option value="0" ${employee.sex==0?"selected":""}>
								--请选择--
							</option>
							<option value="2" ${employee.sex==2?"selected":""}>
								女
							</option>
							<option value="1" ${employee.sex==1?"selected":""}>
								男
							</option>
						</select>
					</td>
					</tr>
					<tr>
					<td  align="left" nowrap class="col2">
						<div align="right">
							电话：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="phone" type="text" id="phone" size="20"  value="${employee.phone }">
						&nbsp;
								<span style="color:red"></span>
					</td>
					</tr>
					<tr>
					<td  align="left" nowrap class="col2">
						<div align="right">
							证件号：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="cardNo" type="text" id="cardNo" size="20" value="${employee.cardNo }" maxlength="96">
						&nbsp;
					</td>
					</tr>
				<tr>
					<td height="25" align="right" nowrap class="col2">
						地址：
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="address" type="text" id="address" size="20" value="${employee.address }" maxlength="64">
						&nbsp;
					</td>
					</tr>
					<tr>
					<td  align="left" nowrap class="col2">
						<div align="right">
							角色：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<select name="category">
							<option value="1" ${employee.category==1?"selected":""}>管理员</option>
							<option value="2" ${employee.category==2?"selected":""}>销售经理</option>
							<option value="3" ${employee.category==3?"selected":""}>销售人员</option>
						</select>
					</td>
					</tr>
					<tr>
					<td  align="left" nowrap class="col2">
						<div align="right">
							备注：
						</div>
					</td>
					<td  align="left" nowrap class="col2">
						&nbsp;
						<input name="desc" type="text" id="desc" size="20" value="${employee.desc }" maxlength="64">
						&nbsp;
					</td>
				</tr>
			</table>
			<br/>
			<div align="center">
				<input type="submit" name="Submit22" class="button_style" value="修改"><span style="color:red">&nbsp;&nbsp;${editError }</span>
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath%>employee/list?tenantId=${userSession.id}">返回</a>	
			</div>
		</form>
	</body>
</html>
