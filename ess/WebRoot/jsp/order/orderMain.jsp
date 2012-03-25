<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员</title>
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
	 <link type="text/css" href="<%=basePath %>css/ui.all.css" rel="stylesheet" />
	    <link type="text/css" href="<%=basePath %>css/style.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=basePath %>js/ui.core.js"></script>
	    <script type="text/javascript" src="<%=basePath %>js/ui.datepicker.js"></script>
	    <script type="text/javascript" src="<%=basePath %>js/ui.datepicker-zh_CN.js"></script>
		<style type="text/css">
			body {
				background-image: url(<%=basePath %>img/web/22.jpg);
			}
			#search_table {
				background-image: url(<%=basePath %>img/web/left_bg.gif);
				border-collapse:collapse;
				padding:0px;
				border: 1px solid white;
			}
			#search_table tr td {
				margin:10px;
				background-image: url(<%=basePath %>img/web/left_bg.gif);
			}
			
			#result_table tr td {
				margin:10px;
				background-image: url(<%=basePath %>img/web/left_bg.gif);
				border-collapse:collapse;
				padding:0px;
			}
		</style>
		<script type="text/javascript">
				function datePicker(pickerName,locale) {
		$(pickerName).datepicker($.datepicker.regional[locale]);
		$(pickerName).datepicker('option', 'changeMonth', true);//月份可调
		$(pickerName).datepicker('option', 'changeYear', true);//年份可调
	}
	
	$(function() {
		//var locale = "<s:property value='#request.locale'/>";//struts2取语言环境
		//var locale = "<%=request.getLocale()%>"; //jsp取浏览器语言环境
		//datePicker('#dateDemo',locale);//根据语言环境切换日期控件语言
		datePicker('#startTime','zh_CN');
		datePicker('#endTime','zh_CN');
		//datePicker('#dateDemo',''); //''默认的样式在ui.datepicker.js内已定义为英文样式，与附件内的ui.datepicker-en_US.js相同
	}); 
		
		</script>
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:订单管理</font>
		<br>
		<br>
		<form name="form1" method="post" action="">
			<table id="search_table" width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="6" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								订单管理
							</div>
						</div>
					</td>
	
				</tr>
				<tr>
					<td width="10%" height="25" align="right" class="col2">
						操作员工：
					</td>
					<td  align="left" class="col2">
						<c:choose>
							<c:when test="${employees == null}">
							</c:when>
							<c:when test="${employees !=null}">
								<select name="operatorId" style="width:120px;">
									<option value="0">--请选择--</option>
									<c:forEach items="${employees.result}" var="employee">
									<c:if test="${operatorId == employee.id}">
										<option value="${employee.id}" selected="selected">${employee.name}</option>
									</c:if>
									<c:if test="${operatorId != employee.id}" >
										<option value="${employee.id}" >${employee.name}</option>
									</c:if>
									</c:forEach>
								</select>
							</c:when>
						</c:choose>
					</td>
					<td width="10%" height="25" align="right" class="col2">
						订单金额：
					</td>
					<td   align="left" class="col2">
						<input type="text" name="minAmount" onkeyup="value=value.replace(/[^\d]/g,'') "
      onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
						~
						<input type="text" name="maxAmount" onkeyup="value=value.replace(/[^\d]/g,'') "
      onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
					</td>
					<td width="10%" height="25" align="right" class="col2">
						订单状态：
					</td>
					<td   align="left" class="col2">
						<select name="currentState">
							<option value="0">--请选择--</option>
							<option value="1">已下单</option>
							<option value="2" >已确认</option>
							<option value="3" >已退货</option>
						</select>
					</td>
					</tr>
					<tr>
					<td width=100" height="25" align="right" nowrap class="col2">
						起始日期：
					</td>
					<td align="left" class="col2">
						<input name="startTime" type="text" id="startTime" size="15" value="${startTime }"/>
						~
						<input name="endTime" type="text" id="endTime" size="15"  value="${endTime }">
					</td>
					<td colspan="4">
						<input type="hidden" name="tenantId" value="${userSession.tenantId}"/>
						<input type="hidden" name ="currentPage" value="1"/>
						<input type="hidden" name ="pageSize" value = "15"/>
					</td>
				</tr>
				<tr><td colspan="6">&nbsp;</td></tr>
			</table>
			<br/>
			<div align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="Submit2" value="查询订单" class="button_style"
					onClick= "form1.action = '<%=basePath %>order/list';form1.submit();"/>
					&nbsp;&nbsp;
				<span style="color: red" id="accountInfo">${errorMessage }</span>
			</div>
		</form>
		
		<table id="result_table" width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bgcolor="#FFFFFF">
	
			<tr align="center">
				<td width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td width="30%" align="center" nowrap class="col1">
					订单号
				</td>	
				<td width="15%" align="center" nowrap class="col1">
					销售金额
				</td>
				<td  width="20%" align="center" nowrap class="col1">
					顾客
				</td>
				<td  width="15%" align="center" nowrap class="col1">
					销售人员
				</td>
				<td  width="15%" align="center" nowrap class="col1">
					最晚交货日期
				</td>
				<td width="15%" nowrap class="col1">
					管理操作
				</td>
			</tr>
		<c:forEach items="${orders.result}" var="order" varStatus="userStatus">
			<tr align="center">
				<td height="24" align="center" nowrap class="col2">
					&nbsp;
					${userStatus.count }
				</td>
				<td align="center" nowrap class="col2">
					&nbsp;
					${order.orderNo }
				</td>
				<td align="center" nowrap class="col2">
					&nbsp;
					${order.amount }
				</td>	
				<td align="center" nowrap class="col2">
					&nbsp;
					${order.customer.name }
					
				</td>				
				<td align="center" nowrap class="col2">
				&nbsp;
					${order.operator.name }
				</td>
				<td align="center" nowrap class="col2">
				&nbsp;
					${order.deliveryDate}
				</td>
				<td align="center" nowrap class="col2">
					<c:choose>
						<c:when test="${order.currentState ==1}">
							<a href="<%=basePath %>order/editDetail?orderId=${order.id}">详情</a>|
							<a href="<%=basePath %>order/one?orderId=${order.id}">确认</a>|
							<a href="<%=basePath %>order/cancelDetail?orderId=${order.id}">退货</a>
						</c:when>
						<c:otherwise>
							<a href="<%=basePath %>order/editDetail?orderId=${order.id}">详情</a>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr><td>&nbsp;</td></tr>
			<tr align="center">
				<td height="24" colspan="4" align="center">
					<form name="form2" method="post" action="">
					<input type="hidden" name="operatorId" value="${operatorId}"/>
					<input type="hidden" name="tenantId" value="${userSession.tenantId}"/>
					<input type="hidden" name="pageSize" value="${orders.pageSize }"/>
					<input type="hidden" name="currentState" value="${currentState }"/>
					<input type="hidden" name="minAmount" value="${minAmount }"/>
					<input type="hidden" name="maxAmount" value="${maxAmount }"/>
					<input type="hidden" name="endTime" value="${endTime }"/>
					<input type="hidden" name="startTime" value="${startTime }"/>
					<c:if test="${orders.currentPage > 1}">
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>order/list?currentPage=1';form2.submit();">首页</a>
						&nbsp;
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>order/list?currentPage=${orders.currentPage - 1}';form2.submit();">上一页</a>
					</c:if>
					&nbsp;&nbsp;
					--第${orders.currentPage}页/共${orders.totalPages}页--
					&nbsp;&nbsp;
					<c:if test="${orders.currentPage < orders.totalPages}">
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>order/list?currentPage=${orders.currentPage + 1}';form2.submit();">下一页</a>
						&nbsp;
						
						<a href="javascript:;" onclick="
						form2.action = '<%=basePath %>order/list?currentPage=${orders.totalPages}';form2.submit();">尾页</a>
					</c:if>
						<input type="button" name="Submit2" value="导出" class="button_style" onClick= "exportExcel();"/>
					<script type="text/javascript"> 
					   var maxNum = ${orders.count};
					   var exportSize = 1000;
					   var exportPage = 1;
						function exportExcel()
						{
							if(maxNum>exportSize)
							{
								alert("最多导出"+exportSize+"条记录！");
							};
							form2.action = '<%=basePath %>order/exportExcel?exportSize='+exportSize+'&exportPage='+exportPage;
							form2.submit();
						}
					</script>
					</form>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
		</table>
	</body>
</html>