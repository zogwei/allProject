<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>订单管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/add_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/product.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
body {
	background-image: url(<%=basePath%>img/web/22.jpg);
}

table tr td {
	background-image: url(<%=basePath%>img/web/left_bg.gif);
	border-collapse: collapse;
	padding: 5px;
}
</style>
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:订单管理&raquo;订单详情</font>
		<br>
		<br>
		<form name="form1" method="post" action="">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">

				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								订单详情
							</div>
						</div>
						<span style="color: red" id="accountInfo">${errorMessage }</span>
					</td>
				</tr>

				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">
						订单编号：
					</td>
					<td  align="left" nowrap class="col2">
						${order.orderNo }
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							下单时间：
						</div>
					</td>
					<td  align="left" class="col2">
						${createDate }
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							当前状态：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						<c:choose>
							<c:when test="${order.currentState==1}">已下单 </c:when>
							<c:when test="${order.currentState==2}">已确认</c:when>
							<c:when test="${order.currentState==3}">已退货</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							顾客：
						</div>
					</td>
					<td  align="left" class="col2">
						${order.customer.name }
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							下单时间：
						</div>
					</td>
					<td  align="left" class="col2">
						${createDate }
					</td>
				</tr>
				<tr>
					<td width="100px" align="left" nowrap class="col2">
						<div align="right">
							最晚交货时间：
						</div>
					</td>
					<td  align="left" class="col2">
						${order.deliveryDate }
					</td>
				</tr>
				<tr>
					<td width="100px" align="left" nowrap class="col2">
						<div align="right">
							描述：
						</div>
					</td>
					<td  align="left" class="col2">
						${order.desription }
					</td>
				</tr>
				<c:choose>
					<c:when test="${order.currentState==1}">
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									订单总额：
								</div>
							</td>
							<td  align="left" class="col2">
								${order.amount}
							</td>
						</tr>
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									预付金额：
								</div>
							</td>
							<td align="left" class="col2">
								${order.imprest}
							</td>

						</tr>
					</c:when>

					<c:when test="${order.currentState==2}">
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									订单总额：
								</div>
							</td>
							<td  align="left" class="col2">
								${order.amount}
							</td>
						</tr>
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									实收金额：
								</div>
							</td>
							<td  align="left" class="col2">
								${order.imprest+order.received}
							</td>
						</tr>
					</c:when>
					<c:when test="${order.currentState==3}">
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									订单总额：
								</div>
							</td>
							<td align="left" class="col2">
								${order.amount}
							</td>
						</tr>
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									退款金额：
								</div>
							</td>
							<td align="left" class="col2">
								${order.refund}
							</td>
						</tr>
					</c:when>
				</c:choose>

			</table>
			<c:forEach items="${order.items}" var="item">
				<br />
				<table width="60%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr>
						<td width="95px" height="25" align="left" class="col2">
							<div align="right">
								地板：
							</div>
						</td>
						<td  align="left" class="col2">
							${item.floor.name}
						</td>
					</tr>
					<tr>
						<td width="95px" align="left" class="col2">
							<div align="right">
								单价：
							</div>
						</td>
						<td  align="left" class="col2">
							${item.sellPrice}
					</tr>
					<tr>
						<td width="95px" align="left" class="col2">
							<div align="right">
								面积：
							</div>
						</td>
						<td  align="left" class="col2">
							${item.area}
						</td>
					</tr>
					<tr>
						<td width="95px" align="left" class="col2">
							<div align="right">
								小计：
							</div>
						</td>
						<td  align="left" class="col2">
							${item.amount}
						</td>
					</tr>
				</table>

			</c:forEach>
		</form>
		<br>
		<c:if test="${order.currentState==1}">
			<div align="center">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="Submit2" value="确认订单"
					class="button_style"
					onClick="window.location.href='<%=basePath%>order/one?orderId=${order.id }'" />
			</div>
			<br />
		</c:if>
		<div align="center">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=basePath%>order/employeeList?tenantId=${userSession.tenantId}" >返回</a>
		</div>

		<br />
	</body>
</html>