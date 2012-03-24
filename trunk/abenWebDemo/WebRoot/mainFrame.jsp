<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>abenWebDemo</title>
		<link rel="stylesheet" type="text/css" href="/AbenDemo/css/Extmain.css">
		<!--加载ext框架样式-->
		<link rel="stylesheet" type="text/css" href="/AbenDemo/js/ext-2.2/resources/css/ext-all.css">
		<link rel="stylesheet" type="text/css" href="/AbenDemo/js/ext-2.2/resources/css/portal.css">
		<!--导入prototype文件 -->
		<script language="javascript" type="text/javascript"
			src="/AbenDemo/js/prototype/prototype.js"></script>
		<!--加载ext核心文件-->
		<script language="javascript" type="text/javascript"
			src="/AbenDemo/js/ext-2.2/adapter/ext/ext-base.js"></script>
		<script language="javascript" type="text/javascript"
			src="/AbenDemo/js/ext-2.2/ext-all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/AbenDemo/js/ext-2.2/source/locale/ext-lang-zh_CN.js"></script>
		<!--加载ext自定义组件-->
		<script language="javascript" type="text/javascript" src="/AbenDemo/js/frame/mainFrameView.js"></script>
	</head>

	<body>
	<!--loading加载 -->
		<div id="loadingTab">
		    <div class="loading-indicator">
		        <img src="/AbenDemo/images/public/loader.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/>
		    	<span id="loading-msg">加载样式表和图片</span>
		    </div>
		</div>
		<div id="north" style="visibility: hidden;"></div>
		<div id="south">
			<div class="power" id="power" style="visibility: hidden;">
				Power By:
				aben&nbsp;
			</div>
			<div class="bq" id="banquan" style="visibility: hidden;">
				版权所有：
				aben
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	$('loading-msg').innerHTML = '初始化完毕！';
	Ext.get('loadingTab').fadeOut({remove: true});//让加载标签消失
	$('north').style.visibility="visible";
	$('power').style.visibility="visible";
	$('banquan').style.visibility="visible"
</script>