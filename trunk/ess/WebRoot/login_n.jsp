<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>嘉森木业有限公司</title>
<link href="images/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#account").blur(function() {
			checkUser();
		});
		$("#password").blur(function() {
			checkPwd();
		});
		$("form").submit(function() {
			var bool1 = checkUser();
			var bool2 = checkPwd();
			return bool1 && bool2;
		});

	});

	function checkEmpty($ele, $msg) {
		if ($.trim($ele.val()).length == 0) {
			showMsg($ele, $msg);
			return false;
		} else {
			return true;
		}
	}
	function checkUser() {
		cleanMsg($("#account"));
		return checkEmpty($("#account"), "&nbsp;&nbsp;&nbsp;用户名不能为空");
	}
	function checkPwd() {
		cleanMsg($("#password"));
		return checkEmpty($("#password"), "&nbsp;&nbsp;&nbsp;密码不能为空");
	}
	//显示信息
	function showMsg($element, $msg) {
		$element.next("span").empty().append($msg);
	}
	//清除信息 
	function cleanMsg($element) {
		$element.next("span").empty();
	}
</script>
</head>

<body>
	<form method=post action="<%=basePath%>userlogin">
		<div class="login_bg">
			<div class="login_1">
				<div class="login_2">
					用户名： <label> <input type="text" name="loginName"
						id="account" size="18" /> <span style="color: red"></span>
					</label>
				</div>
				<div class="login_2">
					密&nbsp;&nbsp;码： <input size="18" type="password" name="loginPwd"
						id="password" /> <span style="color: red">${login_error}</span>
				</div>
				<div class="login_2">
					<a href="javascirpt:document.getElementById('')"><img src="images/login_boutton_1.jpg" border="0" /></a>&nbsp;&nbsp;&nbsp;
					<a href="#"><img src="images/login_boutton_2.jpg" border="0" /></a>
				</div>
			</div>
			<div class="login_3">嘉森木业有限公司 版权所有</div>
		</div>
</body>
</html>
