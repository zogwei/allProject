	var name_flag=true;
	var firstTime=true;
	var basePath;
	$(function(){
			$("#name").blur(function(){
				checkName();
				checkNameJax();
			});
			$("#phone").blur(function(){
				checkPhone($("#phone"));
			});
			$("#form1").submit(function(){
				var bool1=checkName();
				var bool2=checkPhone($("#phone"));
				if(!firstTime){
					checkNameJax();
				}
				return bool1&&bool2&&name_flag;
		});
		});
		
		//异步验证名字是否被占用
		function checkNameJax(){
				$val=$.trim($("#name").val());
				if($val.length==0){
					return false;
				}
				firstTime=false;
				$id=$.trim($("#id").val());
				$url=basePath+"employee/nameCheck";
				$.post($url,
					{"name":$val,"id":$id},
					function(data){
						if(data){
							name_flag=false;
							$msg="&nbsp;&nbsp;&nbsp;该名字已被占用";
							$("#nameInfo").empty().html($msg);
						
						}else{
							name_flag=true;
							cleanMsg($("#nameInfo"));
						}
					},
					"json"
					);
		}
		function checkName(){
			cleanMsg($("#name"));
			return checkEmpty($("#name"),"&nbsp;&nbsp;&nbsp;姓名不能为空");
		}
		function checkPhone($ele){
			cleanMsg($("#phone"));
			$val=$.trim($ele.val());
			if($val.length==0)
				return true;
			$pattern=/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/;
			var mobel=(/^13\d{9}$/g.test($val))
			||(/^15[0-35-9]\d{8}$/g.test($val))
			|| (/^18[05-9]\d{8}$/g.test($val));
			if($pattern.test($val)||mobel){
				return true;
			}else{
				showMsg($ele,"&nbsp;&nbsp;电话格式不正确");
				return false;
			}
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