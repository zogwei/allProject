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
		<font class="navigation_style">当前位置:入库管理&raquo;修改库存</font>
		<br>
		<br>
		<form name="form1" id="form_id" method="post" action="<%=basePath %>inStorage/modify">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">

				<tr>
					<td height="25" colspan="7" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								入库管理
								<input id="floor_id" name="floor.id" type="hidden" value="${inStorage.floor.id }">
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
						<input name="floor_name" id="floor_name" disabled="true" size="35"
							value="${inStorage.floor.name }">
						<div style="position: absolute;background-color: white;">
							<table id="floors" width="200px" class="Table_B">
							</table>
						</div>
						<span id="floor_msg" style="color: red"></span>	
						<input name="id" type="hidden" value="${inStorage.id }" >
					</td>
				</tr>	
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						入库面积：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						<input name="area" id="area" type="text" size="35" value="${inStorage.area }">
						平方米(㎡)
						<span id="area_msg" style="color: red"></span>
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
						<input name="price" type="text" size="35" id="price" value="${inStorage.price }">
						元/平米(￥/㎡)
						<span id="price_msg" style="color: red"></span>
					</td>	
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							总价：
						</div>
					</td>
					<td colspan="2" align="left" nowrap class="col2">
						&nbsp;
						<input name="total" type="text" size="35" id="total" disabled="true" value="${inStorage.count }">
						元(￥)
                        <input name="count" type="hidden" id="count" value="${inStorage.count }">
					</td>
				</tr>
				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						经办人：
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;                                                
						<input name="operator" type="text" size="35" value="${inStorage.operator }" maxlength="16">
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
						<input name="desc" type="text" size="35" value="${inStorage.desc }" maxlength="64">
					</td>
				</tr>

			</table>
			<br/>
			<div align="center">
				<input type="submit" value="修改">
			</div>
			<br/>
			<div align="center">
				<a href="<%=basePath %>inStorage/list">返回</a>	
			</div>
		</form>
	</body>
</html>
