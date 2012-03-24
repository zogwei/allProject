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
		<title>地板销售管理系统</title>
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.min.js"></script>
		<style type="text/css">
			body {
				background-image: url(<%=basePath %>img/web/22.jpg);
			}
			
			.login_class {
				background-image: url(<%=basePath %>img/web/login_bg.jpg);
			}
			.button_style {
				width: 60px;
				height: 20px;
			}
		</style>
	<script type="text/javascript">
		$(function(){
			$("#account").blur(function(){
				checkUser();
			});
			$("#password").blur(function(){
				checkPwd();
			});
			$("form").submit(function(){
				var bool1=checkUser();
				var bool2=checkPwd();
				return bool1&&bool2;
		});
		
		});
	
		function checkEmpty($ele,$msg){
			if($.trim($ele.val()).length==0){
				showMsg($ele,$msg);
				return false;
			}else{
				return true;
			}
		}
		function checkUser(){
			cleanMsg($("#account"));
			return checkEmpty($("#account"),"&nbsp;&nbsp;&nbsp;用户名不能为空");
		}
		function checkPwd(){
			cleanMsg($("#password"));
			return checkEmpty($("#password"),"&nbsp;&nbsp;&nbsp;密码不能为空");
		}
		//显示信息
		function showMsg($element,$msg){
			$element.next("span").empty().append($msg);
		}
		//清除信息 
		function cleanMsg($element){
			$element.next("span").empty();
		}
	</script>
	</head>
	<body style="margin-top: 200px;">
		<form method=post action="<%=basePath %>userlogin">
			<table width="438" border="0" align="center" cellpadding="0" cellspacing="0" class="login_class">

				<tr>
					<td width="454" height="238" align="center">
						<table width="308" border="0" cellpadding="2" cellspacing="0"
							align="center">
							<tr align="left">
								<td width="100" height="48" align="center">
									用户名
								</td>
								<td width="210" colspan="5">
									<label>
										<input
											style="height: 30px; background: url(<%=basePath %>img/web/input.jpg) no-repeat; width: 212px; border: 0px; padding-left: 10px; line-height: 26px;"
											type="text" name="loginName" id="account" />
											<span style="color:red"></span>
									</label>
								</td>
							</tr>
							<tr align="left">
								<td width="100" height="48" align="center">
									密 &nbsp;码
								</td>
								<td width="210" colspan="5">
									<input
										style="height: 30px; background: url(<%=basePath %>img/web/input.jpg) no-repeat; width: 212px; border: 0px; padding-left: 10px; line-height: 26px;"
										type="password" name="loginPwd" id="password" />
										<span style="color:red">${login_error}</span>
								</td>
							</tr>
							<tr>
								<td align="center" colspan="6">
									<input type="submit" name="imageField" id="imageField" class="button_style" style="width: 60px;height: 20px;background-color:white;"
										value="确定" />
									<input type="reset" name="imageField1" id="imageField1" class="button_style" style="width: 60px;height: 20px;background-color:white;"
										value="取消" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>