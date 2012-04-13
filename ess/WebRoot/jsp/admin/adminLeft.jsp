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
		<title>管理界面</title>
		<link href="<%=basePath%>css/adminMyweb.css" rel="stylesheet"
			type="text/css">
		<script src="<%=basePath%>js/jquery-1.4.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin.js" type="text/javascript"></script>
		<style type="text/css">
			body {
				background-image: url(<%=basePath%>img/web/left_bg.gif);
			}
			
			.STYLE5 {
				color: #000000
			}
		</style>
	</head>
	<BODY>
			<c:choose>
			<c:when test="${userSession.tenantId==1}">
		<TABLE id="system_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD height="36" background="<%=basePath%>img/web/menu_bg.gif"
					bgcolor="#FFFFFF" class="menu_title" style="CURSOR: hand"
					onClick="showSystemMenu();">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>system/list" target="main">管理员管理</a> 

				</TD>
			</TR>
		</TABLE>
		<TABLE id="order_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD height="36" background="<%=basePath%>img/web/menu_bg.gif"
					bgcolor="#FFFFFF" class="menu_title" style="CURSOR: hand"
					onClick="showOrderMenu();">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>order/employeeList?tenantId=${userSession.tenantId}" target="main">订单管理</a>
				</TD>
			</TR>
		</TABLE>
		<TABLE id="tenant_menu" cellSpacing="0" cellPadding="0" width="180" align="center">
			<TR>
				<TD background="<%=basePath %>img/web/menu_bg.gif" height="36" class="menu_title"
					onClick="showTenantMenu();" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath %>tenant/list" target="main">
					租户管理
					</a>
				</TD>
			</TR>

		</TABLE>
		
		<TABLE id="product_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath%>img/web/menu_bg.gif" height="36"
					class="menu_title" onClick="showProductMenu();"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>floor/list" target="main"> 产品管理 </a> 
				</TD>
			</TR>
			<TR>
				<TD height="36" class="menu_title" onClick="menu(4);"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>floorCategory/list" target="main">
							添加类别 </a> 
				</TD>
			</TR>
			<TR>
				<TD height="36" class="menu_title" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>spec/list" target="main">
							添加规格 </a> 
				</TD>
			</TR>
			<TR>
				<TD height="36" class="menu_title" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>colorCode/list" target="main">
							添加色号 </a>
				</TD>
			</TR>
			<!-- 
			<TR>
				<TD height="36" class="menu_title" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>vein/list" target="main">
							添加纹理 </a> 
				</TD>
			</TR>
			 -->
		</TABLE>

		<TABLE id="storage_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath %>img/web/menu_bg.gif" height="36" class="menu_title"
					onClick="showStorageMenu();" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath %>inStorage/list"
						target="main">入库管理</a>
				</TD>
			</TR>

		</TABLE>
		
		<TABLE id="storage_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath %>img/web/menu_bg.gif" height="36" class="menu_title"
					onClick="showStorageMenu();" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath %>price/list"
						target="main">价格管理</a>
				</TD>
			</TR>
		</TABLE>

		<TABLE id="supplier_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath%>img/web/menu_bg.gif" height="36"
					class="menu_title" onClick="showSupplierMenu();"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>supplier/list" target="main"> 供应商管理 </a>
				</TD>

			</TR>
		</TABLE>
		
		
		<TABLE id="setPwd_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			
			<TR>
				<TD background="<%=basePath%>img/web/menu_bg.gif" height="36"
					class="menu_title" onClick="showSupplierMenu();"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>jsp/system/systemPwdEdit.jsp" target="main"> 修改密码 </a>
				</TD>

			</TR>

		</TABLE>
		</c:when>
		<c:otherwise>
			<TABLE id="order_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD height="36" background="<%=basePath%>img/web/menu_bg.gif"
					bgcolor="#FFFFFF" class="menu_title" style="CURSOR: hand"
					onClick="showOrderMenu();">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>order/employeeList?tenantId=${userSession.tenantId}" target="main">订单管理</a>
				</TD>
			</TR>
		</TABLE>
		
		<TABLE id="employee_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath%>img/web/menu_bg.gif" height="36"
					class="menu_title" onClick="showEmployeeMenu();"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>employee/list"
						target="main"> 员工管理 </a> 
				</TD>
			</TR>

		</TABLE>


		<TABLE id="product_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath%>img/web/menu_bg.gif" height="36"
					class="menu_title" onClick="showProductMenu();"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>floor/list" target="main"> 产品管理 </a> 
				</TD>
			</TR>
		</TABLE>

		<TABLE id="storage_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			<TR>
				<TD background="<%=basePath %>img/web/menu_bg.gif" height="36" class="menu_title"
					onClick="showStorageMenu();" style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath %>inStorage/list"
						target="main">入库管理</a>
				</TD>
			</TR>

		</TABLE>

		
		<TABLE id="setPwd_menu" cellSpacing="0" cellPadding="0" width="180"
			align="center">
			
			<TR>
				<TD background="<%=basePath%>img/web/menu_bg.gif" height="36"
					class="menu_title" onClick="showSupplierMenu();"
					style="CURSOR: hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="<%=basePath%>jsp/employee/employeePwdEdit.jsp" target="main"> 修改密码 </a>
				</TD>
			</TR>
		</TABLE>
		</c:otherwise>
		</c:choose>
	</body>
</html>
