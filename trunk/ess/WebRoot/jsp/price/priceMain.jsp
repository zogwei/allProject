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
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="Submit22" value="添加地板" class="button_style"
					onClick="window.location='<%=basePath %>price/toadd';">
		
		<br/>		
		<br/>			
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			<tr align="center">
				<td width="10%" nowrap bgcolor="#FFFFFF" class="col2">
					序号
				</td>
				<td width="15%"nowrap bgcolor="#FFFFFF" class="col2">
					租户
				</td>
				<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">

					产品
				</td>
				<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">

					批发价
				</td>
				<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">

					零售价
				</td>
				<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">

					建议销售价
				</td>
				<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
 					操作
				</td>
			</tr>
			<c:forEach items="${prices}" var="price" varStatus="stat">

				<tr align="center">
					<td width="10%" nowrap bgcolor="#FFFFFF" class="col2">
						${stat.index + 1}
					</td>

					<td width="15%"  nowrap bgcolor="#FFFFFF" class="col2">
						${price.tenantName}
					</td>
					<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
						${price.floorName}
					</td>
					<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
						￥${price.amountPrice}
					</td>
					<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
						￥${price.detailPrice}
					</td>
					<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
						￥${price.sellPrice}
					</td>
					
					<td width="25%" nowrap bgcolor="#FFFFFF" class="col2">
						<a href="<%=basePath %>price/delete?id=${price.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>
