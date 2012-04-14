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
		<script type="text/javascript" src="<%=basePath %>js/price.js"></script>
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
		<font class="navigation_style">当前位置:价格管理&raquo;添加价格</font>
		<br>
		<br>
		<form id="spec_form" name="spec_form" method="post" action="<%=basePath %>price/add">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								添加产品价格
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" align="left" nowrap bgcolor="#FFFFFF" class="col2">
						<div align="right">
						&nbsp;&nbsp;&nbsp;&nbsp;
							租户：
						</div>
					</td>
					<td width="50%" align="left" nowrap bgcolor="#FFFFFF" class="col2">
						&nbsp;
						<select id="tenantId" name="tenantId"  size="1">
							<c:forEach items="${tenants}" var="tenantId">
								<c:if test="${tenantId.id != 1}">
										<option value="${tenantId.id}" >${tenantId.name} </option>
								</c:if>
								
							 </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" align="left" nowrap bgcolor="#FFFFFF" class="col2">
						<div align="right">
						&nbsp;&nbsp;&nbsp;&nbsp;
							产品：
						</div>
					</td>
					<td width="50%" align="left" bgcolor="#FFFFFF" class="col2">
						&nbsp;
						<select id="floorId" name="floorId"  size="1">
							<c:forEach items="${floors.result}" var="floor">
								<option value="${floor.id}" >${floor.name} </option>
							 </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							批发价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<input id="amountPrice" name="amountPrice" type="text" id="MidCls3" size="35" value="${floor.sellPrice}">
						<span id="amountPrice_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							零售价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<input id="detailPrice" name="detailPrice" type="text" id="MidCls3" size="35" value="${floor.sellPrice}">
						<span id="detailPrice_msg" class="msg"></span>
					</td>
				</tr>
				<tr>
					<td width="5%" height="25" align="right" nowrap class="col2">
						<div align="right">
							建议销售价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						<input id="sellPrice" name="sellPrice" type="text" id="MidCls3" size="35" value="${floor.sellPrice}">
						<span id="sellPrice_msg" class="msg"></span>
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
				<a href="<%=basePath%>/price/list">返回</a>
			</div>
			<br/>
			<br/>
			<br/>
		</form>
	</body>
</html>
