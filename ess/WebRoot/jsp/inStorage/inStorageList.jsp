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
		<title>入库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=basePath%>css/admin.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>css/admin_css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
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
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/product.js"></script>
        <link type="text/css" href="<%=basePath %>css/ui.all.css" rel="stylesheet" />
	    <link type="text/css" href="<%=basePath %>css/style.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=basePath %>js/ui.core.js"></script>
	    <script type="text/javascript" src="<%=basePath %>js/ui.datepicker.js"></script>
	    <script type="text/javascript" src="<%=basePath %>js/ui.datepicker-zh_CN.js"></script>
	    <script type="text/javascript">
	        basePath = '<%=basePath %>';
			categoryId = '${floor.category.id}';
			colorCodeId = '${floor.colorCode.id}';
			specId = '${floor.spec.id}';
			veinId = '${floor.vein.id}';
			supplierId = '${floor.supplier.id}';
			
			function showSupplierPage(page) {
				var firstDate = $.trim($("#firstDate").val());
			    var lastDate = $.trim($("#lastDate").val());

				var url = basePath + "inStorage/listSearch?currentPage=" + page;
				if(categoryId.length > 0 && categoryId >= 0)
					url = url + "&category.id=" + categoryId;
				if(specId.length > 0 && specId >= 0)
					url = url + "&spec.id=" + specId;
				if(colorCodeId.length > 0 && colorCodeId >= 0)
					url = url + "&colorCode.id=" + colorCodeId;
				if(veinId.length > 0 && veinId >= 0)
					url = url + "&vein.id=" + veinId;
				if(supplierId.length > 0 && supplierId >= 0)
					url = url + "&supplier.id=" + supplierId;
				if(!firstDate == "")
					url = url + "&firstDate=" + firstDate;
				if(!lastDate == "")
					url = url + "&lastDate=" + lastDate;
				window.location=url;
			}
	function datePicker(pickerName,locale) {
		$(pickerName).datepicker($.datepicker.regional[locale]);
		$(pickerName).datepicker('option', 'changeMonth', true);//月份可调
		$(pickerName).datepicker('option', 'changeYear', true);//年份可调
	}
	
	$(function() {
		//var locale = "<s:property value='#request.locale'/>";//struts2取语言环境
		//var locale = "<%=request.getLocale()%>"; //jsp取浏览器语言环境
		//datePicker('#dateDemo',locale);//根据语言环境切换日期控件语言
		datePicker('#firstDate','zh_CN');
		datePicker('#lastDate','zh_CN');
		//datePicker('#dateDemo',''); //''默认的样式在ui.datepicker.js内已定义为英文样式，与附件内的ui.datepicker-en_US.js相同
	});

	</script>
	</head>
	<body style="text-align: left;">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:入库管理</font>
		<br>
		<br>
		<form name="form1" id="form_date" method="post" action="<%=basePath %>inStorage/listSearch">
			<table id="search_table"  width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">

				<tr>
					<td height="25" colspan="6" align="right" class="col1">
						<div align="center" class="td_title">
							<div align="left">
								入库查询
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td width=100" height="25" align="right" nowrap class="col2">
						供应商名字：
					</td>
					<td width="150" align="left" nowrap class="col2">
					  <select id="supplierTag" name="supplier.id" style="width:120px;">
							<option value="-1">
								-- 请选择 --
							</option>
					  </select>
					</td>
					<td width=100" height="25" align="right" nowrap class="col2">
						<div align="right">
							类别名称：
						</div>
					</td>
					<td align="left" class="col2">
					  <select id="categoryTag" name="category.id" style="width:120px;">
							<option value="-1">
								-- 请选择 --
							</option>
					  </select>
					</td>
					<td width=100" height="25" align="right" nowrap class="col2">
						<div align="right">
							规格名称：
						</div>
					</td>
					<td align="left" nowrap class="col2">
					  <select id="specTag" name="spec.id" style="width:120px;">
							<option value="-1">
								-- 请选择 --
							</option>
					  </select>
					</td>
				</tr>
				<tr>
					<td width=100" height="25" align="right" nowrap class="col2">
						<div align="right">
							色号名称：
						</div>
					</td>
					<td align="left" class="col2">
					  <select id="colorCodeTag" name="colorCode.id" style="width:120px;">
							<option value="-1">
								-- 请选择 --
							</option>
					  </select>
					</td>
					<!-- 
					<td width=100" height="25" align="right" nowrap class="col2">
						<div align="right">
							纹理名称：
						</div>
					</td>
					<td align="left" class="col2">
					  <select id="veinTag" name="vein.id" style="width:120px;">
							<option value="-1">
								-- 请选择 --
							</option>
					  </select>
					</td>
					 -->
					<td width=100" height="25" align="right" nowrap class="col2">
						起始日期：
					</td>
					<td align="left" class="col2">
						<input name="firstDate" type="text" id="firstDate" size="15" value="${firstDate }"/>
						--
						<input name="lastDate" type="text" id="lastDate" size="15"  value="${lastDate }">
					</td>
				</tr>
			</table>
			<br/>
			<div align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" class="button_style" value="查询库存">
			<c:if test="${userSession.tenantId==1}">	<input type="button" class="button_style" value="产品入库" 
					onClick="location.href='<%=basePath %>jsp/inStorage/inStorageAdd.jsp'"></c:if>
			</div>
		</form>	
		
		
		<table id="result_table" width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr align="center">
				<td width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td width="20%" align="center" nowrap class="col1">
					地板名称
				</td>
				<td width="15%" align="center" nowrap class="col1">
					入库面积(㎡)
				</td>
				<td width="15%" align="center" nowrap class="col1">
					进价(￥/㎡)
				</td>
				<td width="20%" align="center" nowrap class="col1">
					总价格(￥)
				</td>
				<td width="10%" nowrap class="col1">
					经办人
				</td>
				<td width="15%" nowrap class="col1">
					管理操作
				</td>
			</tr>

			<c:forEach items="${pageSupport.result }" var="inStorage" varStatus="status">
				<tr align="center">
					<td height="24" align="center" nowrap class="col2">
						${status.index+1 }
					</td>
					<td align="center" nowrap class="col2">
						${inStorage.floor.name }
					</td>
					<td height="24" align="center" nowrap class="col2">
						${inStorage.areaStr}(㎡)
					</td>
					<td height="24" align="center" nowrap class="col2">
						${inStorage.price}(￥/㎡)
					</td>
					<td height="24" align="center" nowrap class="col2">
						${inStorage.countStr}(￥)
					</td>
					<td align="center" nowrap class="col2">
						${inStorage.operator }
					</td>
					<td align="center" nowrap class="col2">
						<a href="<%=basePath %>inStorage/one?id=${inStorage.id }">详情</a>
						<c:if test="${userSession.tenantId==1}">|<a href="<%=basePath %>inStorage/toModify?id=${inStorage.id }">修改</a></c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="center">
					<div>
						<c:if test="${pageSupport.currentPage > 1}">
							<a href="javascript:;" onclick="showSupplierPage(0)">首页</a>
							&nbsp;
							<a href="javascript:;" onclick="showSupplierPage(${pageSupport.currentPage - 1});">上一页</a>
						</c:if>
						&nbsp;&nbsp;
						--第${pageSupport.currentPage}页/共${pageSupport.totalPages}页--
						&nbsp;&nbsp;
						<c:if test="${pageSupport.currentPage < pageSupport.totalPages}">
							<a href="javascript:;" onclick="showSupplierPage(${pageSupport.currentPage + 1});">下一页</a>
							&nbsp;
							<a href="javascript:;" onclick="showSupplierPage(${pageSupport.totalPages});">尾页</a>
						</c:if>
					</div>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
		</table>
		
	</body>
</html>