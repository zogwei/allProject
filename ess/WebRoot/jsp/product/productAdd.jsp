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
		<title>产品管理</title>
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
		<font class="navigation_style">当前位置:产品管理&raquo;添加产品信息</font>
		<br>
		<br>
		<form id="form1" name="form1" method="post" action="<%=basePath %>floor/add" encType="multipart/form-data">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
			
				<tr>
					<td height="25" colspan="2" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								添加产品
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" height="25" align="right" nowrap class="col2">
						编号：
					</td>
					<td width="50%" align="left" nowrap class="col2">
						&nbsp;
						<input name="number" type="text" id="floorNumber" size="35" value="${floor.number}">
						<span id="floorNumber_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							名称：
						</div>
					</td>
					<td width="30%" align="left" nowrap class="col2">
						&nbsp;
						<input name="name" type="text" id="floorName" size="35" value="${floor.name}">
						<span id="floorName_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							供应商名称：
						</div>
					</td>
					<td width="30%"  align="left" nowrap class="col2">
						&nbsp;
						<select id="supplierTag" name="supplier.id"  size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
						<span id="supplierName_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						类别：
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<select id="categoryTag" name="category.id"  size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
						<span id="categoryName_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							规格：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<select id="specTag" name="spec.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
						<span id="specName_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							色号：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<select id="colorCodeTag" name="colorCode.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
						<span id="colorCodeName_msg" class="msg"></span>
					</td>
				</tr>
				
				<tr style="display:none">
					<input  id="veinTag" name="vein.id" value="1" hidden/>
					<!-- 
					<td width="5%" height="25" align="right" nowrap class="col2">
						纹理：
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<select id="veinTag" name="vein.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
						<span id="veinName_msg" class="msg"></span>
					</td>
					 -->
				</tr>
				
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							单价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<input id="sellPrice" name="sellPrice" type="text" id="MidCls3" size="35" value="${floor.sellPrice}">
						<span id="sellPrice_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							备注：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="desc" type="text" id="MidCls32" size="35" value="${floor.desc}">
					</td>
				</tr>
				<tr>
					<td height="25" align="right" nowrap class="col2">
						地板效果图：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input id="imageTag" name="image" type="file">
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
		<br/>
		<br/>
		<br/>
	</body>
</html>
