var tenant_flag =true;
var basePath;
$( function() {
	$("#name").blur( function() {
		var url = basePath + "tenant/json/checkName";
		$.post(
			url, 
			{tenantName :$.trim($("#name").val())}, 
			function(data) {
				if(data == false) {
					$("#tenant_msg").html("租户名已存在");
					tenant_flag = false;
				}else if(data == true && $.trim($("#name").val())==""){
					$("#tenant_msg").html("租户名不能为空");
					tenant_flag = false;
				}
				else if(data == true){
					$("#tenant_msg").html("");
					tenant_flag =true;
				}else{
					$("#tenant_msg").html("");
				}
			}, 
			"json"
		);
	});
	
	//修改时名字的验证
	$("#name1").blur( function() {
		var url = basePath + "tenant/json/check";
		$.post(
			url, 
			{tenantName :$.trim($("#name1").val()),id :$("#tenantId").val()}, 
			function(data) {
				if(data == false) {
					$("#name_msg").html("租户名已存在");
					tenant_flag = false;
				}else if($.trim($("#name1").val())==""){
					$("#name_msg").html("租户名不能为空");
					tenant_flag = false;
				}else if(data == true){
					$("#name_msg").html("");
					tenant_flag = true;
				}
				else{
					$("#name_msg").html("");
				}
			}, 
			"json"
		);
	});
		
	//添加提交时的验证
	$("#submit3_id").click( function() {
		if($.trim($("#name").val())==""){
			$("#tenant_msg").html("租户名不能为空");
			tenant_flag = false;
		}else if($.trim($("#name").val())!=""&& tenant_flag == false){
			$("#tenant_msg").html("租户名已存在");
		}else{
			$("#tenant_msg").html("");
			tenant_flag = true;
		}
	});
	
	//修改备注信息时的验证
	$("#desc").blur( function() {
		if($.trim($("#name1").val())==""||tenant_flag ==false){
		}else{
			tenant_flag = true;
		}
	});
	
	//修改时的验证
	$("#submit1_id").click( function() {
		if($.trim($("#name1").val())==""){
			$("#name_msg").html("租户名不能为空");
			tenant_flag = false;
		}else if($.trim($("#name1").val())!=""&& tenant_flag == false){
			$("#name_msg").html("租户名已存在");
		}else{
			$("#name_msg").html("");
			tenant_flag = true;
		}
	});
	
	//表单提交校验
	 $("#form_name").submit(function(){
	  	if(tenant_flag){
	  		return true;
	  	}else{
	  		return false;
	  	}
	  });
});