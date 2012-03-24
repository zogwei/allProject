$(function(){
			$("#password").val("");
			$("#password").blur(function(){
				checkPwd();
			});
			$("#repwd").blur(function(){
				checkRePwd();
			});
			$("#form1").submit(function(){
				var b1=checkPwd();
				var b2=checkRePwd();
				var b3=true;
				if(b1&&b2){
					cleanMsg($("#repwd"));
					if($.trim($("#password").val())==$.trim($("#repwd").val())){
							b3=true;
					}else{
							b3=false;
							showMsg($("#repwd"),"两次密码不一致");
						}
				}
				return b1&&b2&&b3;
		});
		
		});
		function checkPwd(){
			cleanMsg($("#password"));
			return checkEmpty($("#password"),"&nbsp;&nbsp;&nbsp;不能为空");
		}

		function checkRePwd(){
			cleanMsg($("#repwd"));
			return checkEmpty($("#repwd"),"&nbsp;&nbsp;&nbsp;不能为空");
		}
		
		function checkEmpty($ele,$msg){
			if($.trim($ele.val()).length==0){
				showMsg($ele,$msg);
				return false;
			}else{
				return true;
			}
		}
		//显示信息
		function showMsg($element,$msg){
			$element.next("span").empty().append($msg);
		}
		//清除信息 
		function cleanMsg($element){
			$element.next("span").empty();
		}