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
		<title>产品详细信息</title>
		<link href="<%=basePath %>css/admin.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/admin_css.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>css/add_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/admin.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/product.js"></script>
		<script type="text/javascript">
			basePath = '<%=basePath %>';
			tenantName = '${tenantSession.name}';
			floorName = '${floor.name}';
			showImages('${floor.id}');
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
		<font class="navigation_style">当前位置:产品管理&raquo;产品详细信息</font>
		<br>
		<br>
		<form name="form1" method="post" action="#">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				
				<tr>
					<td height="25" colspan="2" align="right" nowrap class="col1">
						<div align="center" class="td_title">
							<div align="left">
								产品详细信息
								<input name="floor.id" type="hidden" value="${floor.id}">
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" height="25" align="right" nowrap class="col2">
						编号：
					</td>
					<td width="50%" align="left" nowrap class="col2">
						&nbsp;
						${floor.number}
					</td>
				</tr>
				<tr>
					<td width="4%" align="left" nowrap class="col2">
						<div align="right">
							名称：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						${floor.name}
					</td>
				</tr>
				<tr>
					<td width="5%" align="left" nowrap class="col2">
						<div align="right">
							供应商名称：
						</div>
					</td>
					<td width="52%" align="left" nowrap class="col2">
						&nbsp;
						${floor.supplier.name}
					</td>
				</tr>
				<tr>
					<td height="25" align="right" nowrap class="col2">
						类别：
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						${floor.category.name}
					</td>
				</tr>
				<tr>
					<td width="4%" align="left" nowrap class="col2">
						<div align="right">
							规格：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						${floor.spec.name}
					</td>
				</tr>
				<tr>
					<td width="5%" align="left" nowrap class="col2">
						<div align="right">
							色号：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						${floor.colorCode.name}
					</td>
				</tr>
				<!-- 
				<tr>
					<td height="25" align="right" nowrap class="col2">
						纹理：
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						${floor.vein.name}
					</td>
				</tr>
				 -->
				 <c:if test="${userSession.tenantId==1}">
				<tr>
					<td width="4%" align="left" nowrap class="col2">
						<div align="right">
							进货价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						￥${floor.bookPrice}
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="4%" align="left" nowrap class="col2">
						<div align="right">
							 批发价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						￥${floor.amountPrice}
					</td>
				</tr>
				<tr>
					<td width="4%" align="left" nowrap class="col2">
						<div align="right">
							零售价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						￥${floor.detailPrice}
					</td>
				</tr>
				<tr>
					<td width="4%" align="left" nowrap class="col2">
						<div align="right">
							建议销售价：
						</div>
					</td>
					<td width="17%" align="left" nowrap class="col2">
						&nbsp;
						￥${floor.sellPrice}
					</td>
				</tr>
				<tr>
					<td width="5%" align="left" nowrap class="col2">
						<div align="right">
							备注：
						</div>
					</td>
					<td align="left" nowrap class="col2">
						&nbsp;
						${floor.desc}
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="col2">
						地板效果图：
					</td>
					<td  align="left" nowrap class="col2">
						<img id="floorImage" width="500"/>
						<div align="center">
							<a id="prev_image" href="javascript:;" onclick="prevImage();">上一张</a>
							&nbsp;&nbsp;
							<a id="next_image" href="javascript:;" onclick="nextImage();">下一张</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div align="center">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=basePath%>floor/list">返回</a>
		</div>
		<br/>
		<br/>
		<br/>
		<br/>
	</body>
</html>
