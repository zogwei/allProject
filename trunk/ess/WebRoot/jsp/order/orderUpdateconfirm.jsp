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
		<font class="navigation_style">当前位置:订单管理&raquo;订单修改确认</font>
		<br>
		<br>
		<form name="form1" method="post" action="">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">

				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								订单修改确认
							</div>
						</div>
						<span style="color: red" id="accountInfo">${errorMessage }</span>
					</td>
				</tr>
					修改前订单
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
		<form name="form1" method="post" action="">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">

				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								修改后的订单
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
						${orderNew.orderNo }
					</td>
				</tr>
				
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							顾客：
						</div>
					</td>
					<td  align="left" class="col2">
						${orderNew.customer.name }
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							下单时间：
						</div>
					</td>
					<td  align="left" class="col2">
						${createDateNew }
					</td>
				</tr>
				<tr>
					<td width="100px" align="left" nowrap class="col2">
						<div align="right">
							最晚交货时间：
						</div>
					</td>
					<td  align="left" class="col2">
						${orderNew.deliveryDate }
					</td>
				</tr>
				<tr>
					<td width="100px" align="left" nowrap class="col2">
						<div align="right">
							描述：
						</div>
					</td>
					<td  align="left" class="col2">
						${orderNew.desription }
					</td>
				</tr>
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									订单总额：
								</div>
							</td>
							<td  align="left" class="col2">
								${orderNew.amount}
							</td>
						</tr>
						<tr>
							<td width="95px" align="left" nowrap class="col2">
								<div align="right">
									预付金额：
								</div>
							</td>
							<td align="left" class="col2">
								${orderNew.imprest}
							</td>

						</tr>

			</table>
			<c:forEach items="${orderNew.items}" var="item">
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
		
			<div align="center">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="Submit2" value="确认订单修改"
					class="button_style"
					onClick="window.location.href='<%=basePath%>order/orderUpdateConfirm?orderId=${order.id}&newOrderId=${orderNew.id}&result=1'" />
			</div>
			
			<div align="center">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="Submit2" value="取消订单修改"
					class="button_style"
					onClick="window.location.href='<%=basePath%>order/orderUpdateConfirm?orderId=${order.id}&newOrderId=${orderNew.id}&result=2'" />
			</div>
		
		
		<div align="center">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=basePath%>order/employeeList?tenantId=${userSession.tenantId}" >返回</a>
		</div>

		<br />
	</body>
</html>