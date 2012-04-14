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
		<title>产品管理</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/product.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<script type="text/javascript">
			basePath = '<%=basePath %>';
			categoryId = '${floor.category.id}';
			colorCodeId = '${floor.colorCode.id}';
			specId = '${floor.spec.id}';
			veinId = '${floor.vein.id}';
			
		</script>
		<style type="text/css">
			.msg{
				font-size:12px;
				color:red;
			}
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
	</head>
	<body>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font class="navigation_style">当前位置:产品管理</font>
		<br>
		<br>
		<form name="form1" method="post" action="<%=basePath %>floor/list">
			<table id="search_table" width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
				<tr>
					<td height="25" colspan="6" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								产品管理
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="100" align="left" nowrap class="col2">
						<div align="right">
							类别：
						</div>
					</td>
					<td width="150" align="left" nowrap class="col2">
						<select id="categoryTag" name="category.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
					</td>
					<!-- 
					<td width="100" height="25" align="right" nowrap class="col2">
						纹理：
					</td>
					<td width="150" align="left" nowrap class="col2">
						<select id="veinTag" name="vein.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
					</td>
					 -->
					<td width="100" height="25" align="right" nowrap class="col2">
						规格：
					</td>
					<td width="150" align="left" nowrap class="col2">
						<select id="specTag" name="spec.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="5%" align="left" nowrap class="col2">
						<div align="right">
							色号：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						<select id="colorCodeTag" name="colorCode.id" size="1">
							<option value="-1">
								-- 请选择 --
							</option>
						</select>
					</td>
					<td colspan="4"></td>
				</tr>
				<tr><td colspan="6">&nbsp;</td></tr>
			</table>
			<br/>
			<div align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="Submit2" value="查询地板" class="button_style"
					onClick="form1.submit();">
		<c:if test="${userSession.tenantId==1}"><input type="button" name="Submit22" value="添加地板" class="button_style"
					onClick="window.location='<%=basePath %>jsp/product/productAdd.jsp';">
				<input type="button" name="Submit2" value="批量导入" class="button_style"
					onClick="window.location='<%=basePath %>jsp/product/productAddExcel.jsp';"> </c:if>
			</div>
		</form>
		
		<table id="result_table" width="95%" border="0" align="center" cellpadding="5"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr align="center">
				<td width="5%" height="26" nowrap class="col1">
					序号
				</td>
				<td width="30%" height="26" align="center" nowrap class="col1">
					名称
				</td>
				<c:if test="${userSession.tenantId==1}">
					<td width="15%" height="26" align="center" nowrap class="col1">
						进货价
					</td>
				</c:if>
				<td width="15%" height="26" align="center" nowrap class="col1">
					 批发价
				</td>
				<td width="15%" height="26" align="center" nowrap class="col1">
					零售价
				</td>
				<td width="15%" height="26" align="center" nowrap class="col1">
					建议销售价
				</td>
				<c:if test="${userSession.tenantId==1}"><td width="35%" height="26" nowrap class="col1">
					上传图片
				</td></c:if>
				<td width="15%" height="26" nowrap class="col1">
					管理操作
				</td>
			</tr>
			
			<c:forEach items="${pageSupport.result}" var="product" varStatus="stat">
				<tr align="center">
					<td  align="center" nowrap class="col2" height="26">
						${stat.index + 1}
					</td>
					<td align="center" nowrap class="col2">
						${product.name}
					</td>
					<c:if test="${userSession.tenantId==1}">
					<td align="center" nowrap class="col2">
						<label></label>
						￥${product.bookPrice}
					</td>
					</c:if>
					<td align="center" nowrap class="col2">
						<label></label>
						￥${product.amountPrice}
					</td>
					<td align="center" nowrap class="col2">
						<label></label>
						￥${product.detailPrice}
					</td>
					<td align="center" nowrap class="col2">
						<label></label>
						￥${product.sellPrice}
					</td>
					
				<c:if test="${userSession.tenantId==1}">	<td align="center" nowrap class="col2">
						<form id="image_form" action="<%=basePath %>floor/upload?id=${product.id}" method="post" encType="multipart/form-data">
							<input type="file" name="image"><input type="submit" value="上传图片">
						</form>
					</td></c:if>
					<td align="center" nowrap class="col2">
						<a href="<%=basePath %>floor/one?id=${product.id}">详情</a>
						<c:if test="${userSession.tenantId==1}">&nbsp;|&nbsp;
						<a href="<%=basePath %>floor/edit?id=${product.id}">修改</a></c:if>
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
							<a href="javascript:;" onclick="showPage(0);">首页</a>
							&nbsp;
							<a href="javascript:;" onclick="showPage(${pageSupport.currentPage - 1});">上一页</a>
						</c:if>
						&nbsp;&nbsp;
						--第${pageSupport.currentPage}页/共${pageSupport.totalPages}页--
						&nbsp;&nbsp;
						<c:if test="${pageSupport.currentPage < pageSupport.totalPages}">
							<a href="javascript:;" onclick="showPage(${pageSupport.currentPage + 1});">下一页</a>
							&nbsp;
							<a href="javascript:;" onclick="showPage(${pageSupport.totalPages});">尾页</a>
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