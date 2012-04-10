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
	<body> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<font class="navigation_style">当前位置:订单管理&raquo;订单取消</font>
		<br>
		<br>
		<form name="form1" method="post" action="<%=basePath%>order/cancel">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">

				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								订单详情
							</div>
						</div>
						<span  style="color: red " id="accountInfo">${errorMessage }</span>
					</td>
				</tr>

				<tr>
					<td width="95px" height="25" align="right" nowrap class="col2">

						订单编号：
					</td>
					<td align="left" nowrap class="col2">
						${order.orderNo }
						<input type="hidden" name="id" value="${order.id }" />
						<input type="hidden" name="operatorId" value="${order.operator.id }" />
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
					<td align="left" class="col2">
						${order.customer.name }
					</td>
				</tr>
				<tr>
					<td width="95px" align="left" nowrap class="col2">
						<div align="right">
							下单时间：
						</div>
					</td>
					<td align="left" class="col2">
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
							订单金额：
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
					<td  align="left" class="col2">
						${order.imprest}
					</td>
				</tr>
			</table>
			
			<br/>
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="2" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								订单细节
							</div>
						</div>
					</td>
				</tr>
			</table>
			<c:forEach items="${order.items}" var="item">
				<table width="60%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr>
						<!-- <TD ROWSPAN=3 width="20px"></TD> -->
						<td width="95px" height="25"  align="left" class="col2">
							<div align="right">
								地板：
							</div>
						</td>
						<td  align="left" class="col2" COLSPAN=5>
							
							${item.floor.name}    ${item.floor.spec.name}
							<input type="hidden" name="floorIds" value="${item.floor.id }" />
						</td>
					</tr>
					<tr>
						<td width="95px"  align="left" class="col2">
							<div align="right">
								单价：
							</div>
						</td>
						<td align="left" class="col2" width="20%">
							&nbsp;
							${item.sellPrice}
							<input type="hidden" value="${item.sellPrice}" name="sellPrice" size="35" />
						</td>
						
						<td width="95px" align="left" class="col2">
							<div align="right">
								面积：
							</div>
						</td>
						<td align="left" class="col2" width="20%">
							&nbsp;${item.area}
							<input type="hidden" value="${item.area}" name="areas" size="35" />
						</td>
						
						
						<td width="95px" align="left" class="col2" >
							<div align="right">
								小计：
							</div>
						</td>
						<td align="left" class="col2" width="20%">
							&nbsp;${item.amount}
							<input type="hidden" value="${item.amount}" name="amounts"
								size="35" />
						</td>
					</tr>
					<tr>
						<td width="95px" height="25"  align="left" class="col2">
							<div align="right">
								全部退货：
							</div>
						</td>
						<td  align="left" class="col2">
							&nbsp;是<INPUT TYPE="RADIO" NAME="${item.floor.id }_cancelFlag" VALUE="yes" onclick="javascript:changeAllCacel(${item.floor.id })">
							&nbsp;否<INPUT TYPE="RADIO" NAME="${item.floor.id }_cancelFlag" VALUE="no" checked="checked" onclick="javascript:changeAllCacel(${item.floor.id })">
						</td>
						<td width="95px" height="25"  align="left" class="col2">
							<div align="right">
								退货块数：
							</div>
						</td>
						<td  align="left" class="col2" COLSPAN=3>
							&nbsp;	<input type="text" name="${item.floor.id }_cancelblock" id="${item.floor.id }_cancelblock" value="0" onblur="javascript:blockChange(this,${item.onearea },${item.area},${item.sellPrice},${item.floor.id })" />
							<input type="hidden" name="${item.floor.id }_cancelPrice" id="${item.floor.id }_cancelPrice" value="0" />
							<input type="hidden" name="${item.floor.id }_cancelArea" id="${item.floor.id }_cancelArea" value="0" />						
						</td>
					</tr>
				</table>
				<br/>
			</c:forEach>
			<br />
			<table width="60%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					
				<tr>
					<td width="95px" height="25" align="left" class="col2">
						<div align="right">
							退货原因：
						</div>
					</td>
					<td align="left" class="col2">
						<input type="text" name="desc" size="35" maxlength="50"/>
					</td>
				</tr>
			</table>
		
		<br>
		<div align="center">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			<input type="submit" name="Submit2" value="确认" class="button_style"
				onClick="javascript:if(!confirm('确定要取消这个订单么?')){ return false; }" />
				
		</div>
		</form>
		<br />
		<div align="center">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=basePath%>order/employeeList?tenantId=${userSession.tenantId}" >返回</a>
		</div>

		<br />
	</body>
</html>
<script>
function blockChange(item,oneArea,totalArea,oneprice,foorId)
{
	//检查是不是整数
	var itemValue = item.value;
	var r =/^\d+$/;
	var checkFlag =	r.test(itemValue); 
	if(!checkFlag)
	{
		item.value = 0;
		var price = document.getElementById(foorId+"_cancelPrice");
		price.value="0"
		document.getElementById(foorId+"_cancelArea").value="0";
		alert("退货块数必须是整数");
		return ;
	}

	//检查总面积是不是超过了
	if(oneArea*itemValue>totalArea)
	{
		item.value = 0;
		document.getElementById(foorId+"_cancelPrice").value="0";
		document.getElementById(foorId+"_cancelArea").value="0";
		alert("退货总面积不能大于订货面积");
		return ;
	}
	
	//计算退货的总价和退货总面积
	document.getElementById(foorId+"_cancelPrice").value=itemValue*oneprice*oneArea;
	document.getElementById(foorId+"_cancelArea").value=oneArea*itemValue;
}

function changeAllCacel(foorId)
{
	//获得劝退状态
	
	//如果劝退，disable 块数
	
	//如果解开，解开disable 
}

</script>