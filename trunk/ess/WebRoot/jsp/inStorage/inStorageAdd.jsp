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
		<title>入库管理</title>
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/product.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/inStorage.js"></script>
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
			#floors tr td {
				background-image: url(<%=basePath %>img/web/main_bg.gif);
				border-collapse:collapse;
				padding:0px;
			}
       </style>
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:入库管理&raquo;产品入库</font>
		<br>
		<br>
		<form name="form1" id="form_id" method="post" action="<%=basePath %>inStorage/add">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="7" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								产品入库<input id="floor.id" name="floor.id" type="hidden" value="-1">
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						地板名称：
					</td>
					
					<td align="left" nowrap class="col2">
						&nbsp;
						<select id="floor_name" name="floor_name"  size="1">
						</select>
						<span id="supplierName_msg" class="msg"></span>
					</td>
				</tr>
					
					<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						入库片数：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="quantity" id="quantity" type="text" size="35" value="0">
						（块）
						<span id="quantity_msg" style="color: red"></span>
					</td>
					</tr>
					<tr>
						<td width="95px" align="left" nowrap class="col2">
							<div align="right">
								进价：
							</div>
						</td>
						<td align="left" nowrap class="col2">
							&nbsp;
							<input name="price" type="text" size="35" id="price" value = "0">
							元/平方米(￥/㎡)
							<span id="price_msg" style="color: red"></span>
						</td>
					</tr>
					<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						入库面积：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="areaDispaly" type="text" size="35" id="areaDispaly" disabled="true">
						<input name="area" type="hidden"  id="area">
						平方名(㎡)
					</td>
					</tr>
					
					<tr>
						<td width="95px" align="left" nowrap class="col2">
							<div align="right">
								总价：
							</div>
						</td>
						<td align="left" nowrap class="col2">
							&nbsp;
							<input name="total" type="text" size="35" id="total" disabled="true">
							元(￥)
	                        <input name="count" type="hidden" id="count">
	                         <input name="length" type="hidden" id="length">
	                          <input name="width" type="hidden" id="width">
						</td>
					</tr>
					<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						经办人：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="operator" type="text" size="35" value="${sessionScope.userSession.name}"  maxlength="16" readonly="readonly">
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
						<input name="desc" type="text" size="35" maxlength="64">
					</td>
				</tr>
			</table>
			<br/>
			<div align="center">
				<input id="submitButton" type="button" class="button_style" value="添加">
				&nbsp;<input class="button_style" type="reset" value="重置">
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath %>inStorage/list">返回</a>	
			</div>
		</form>
	</body>
</html>
