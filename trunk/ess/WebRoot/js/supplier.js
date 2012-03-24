//记录工程的完整路径
var basePath;
var supplier_flag = true;
var phone_flag = true;
$( function() {	
	$("#supplierName").blur( function() {
		var url = basePath + "supplier/json/one";
		$.post(
		url, 
		{name :$("#supplierName").val(),id:$("#supplier_id").val()}, 
		function(data) {
			if(data) {
				supplier_flag = false;
				$("#supplier_msg").html("供应商名已存在");
			}else{
				if($.trim($("#supplierName").val()) == ""){
					   $("#supplier_msg").html("供应商名不能为空");
					   supplier_flag = false;
					}else{
						$("#supplier_msg").html("");
						supplier_flag = true;
					}
			}
			}, 
			"json"
				);
			});
	
	$("#phone").blur(function(){
		var phone = $.trim($("#phone").val());
		var str = /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/;
		var str1 = /^13\d{9}$/;
		var str2 = /^15[0-35-9]\d{8}$/;
		var str3 = /^18[05-9]\d{8}$/;
		if(str.test(phone) || str1.test(phone) || str2.test(phone) || str3.test(phone)){
			phone_flag = true;
			$("#phone_msg").html("");
		}else{
			if(phone == ""){
				phone_flag = true;
				$("#phone_msg").html("");
			}else{
				$("#phone_msg").html("号码格式不正确");
				phone_flag = false;
			}
		}
	});
			    
		$("#form_id").submit(function(){
			var supplierName = $.trim($("#supplierName").val());
			if(supplierName == ""){
			   $("#supplier_msg").html("供应商名不能为空");
			   supplier_flag = false;
			}
			if(supplier_flag && phone_flag){
		  		return true;
		  	}else{
		  		return false;
		  	}
		});
});