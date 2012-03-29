//记录工程的完整路径
var basePath;
var floor_flag = true;
var area_flag = true;
var price_flag = true;

$( function() {

	var url = basePath + "floor/json/list";
		$.post(
			url,
			function(data) {
				$.each(data, function(index, floorCategory){
					var $opt = $("<option value='" + floorCategory.id + "'>" + floorCategory.name + "</option>");
					if(categoryId == floorCategory.id)
						$opt = $("<option value='" + floorCategory.id + "' selected='selected'>" + floorCategory.name + "</option>");
					$opt.appendTo($("#floor_name"));
				}); 
			},
			"json"
	);
	/*
	var floor_name = $.trim($("#floor_name").val());
	$("#floor_name").keyup( function() {
		//发送ajax请求
	    var url = basePath + "floor/json/list";
		$.post(
			url, 
			{name :$("#floor_name").val()}, 
			function(data) {
			//清空table
				$("#floors tr").remove();
				//循环data,生成tr
				$.each(data, function(index, floor) {
					var $tr = $("<tr><td id='f_name'>" + floor.name + "</td><td><input id='f_id' type='hidden' value='"+floor.id+"'></td></tr>");
					//追加tr的单击事件
					$tr.click( function() {
						var nameStr = $(this).find("#f_name").text();
						var idStr = $(this).find("#f_id").val();
						$("#floor_id").val(idStr);
						$("#floor_name").val(nameStr);//将td信息设置给输入框
						$("#floor_msg").html("");
						floor_flag = true;
						//清空table
						$("#floors tr").remove();
						$("#floors").removeAttr("class");
					});
					$("#floors").append($tr);
				});
				//添加鼠标背景色
				$("#floors tr").hover( function() {
					$(this).css("background", "#e3eafd")
				}, function() {
					$(this).css("background", "white")
				});
			}, 
			"json"
		);
    });
	
	
	$("#floor_name").blur( function() {
		var url = basePath + "floor/json/one";
		$.post(
			url, 
			{name :$("#floor_name").val()}, 
			function(data) {
				if(!data) {
					if($.trim($("#floor_name").val())==""){
						$("#floor_msg").html("地板名不能为空");
						floor_flag = false;
					}else{
						$("#floor_msg").html("地板名不存在");
						floor_flag = false;
					}
				}else{
					$("#floor_msg").html("");
					floor_flag = true;
				}
			}, 
			"json"
		);
	});
	*/
	
	$("#floor_name").change(function(){
		$("input[id=floor.id]").val($(this).children('option:selected').val());
		//Document.getElementById("floor.id").value = $(this).children('option:selected').val();
		//alert($("input[id=floor.id]").val());
	 });
	
    //进价校验
    $("#price").blur(function(){
      var str=/^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|([1-9][0-9]*)|([0][.][0-9]+[1-9]*))$/;
  	if(!str.test($.trim($("#price").val()))){
  		if($.trim($("#price").val())=="")
  		{
			     $("#price_msg").html("进价不能为空");
			     price_flag = false;
			}
			else{
			     $("#price_msg").html("请填写正数");
			     price_flag = false;
			}
	    }
	    else
	    {
	      var price = $("#price").val();
	      var area = $("#area").val();
	      var count = parseFloat(price)*parseFloat(area);
	      $("#count").val("");
          $("#total").val("");
          $("#count").val(Math.round(count*100)/100);
          $("#total").val(Math.round(count*100)/100);
	      price_flag = true;
		  $("#price_msg").html("");
	    }
  });
      
   //面积校验
  $("#area").blur(function(){
      var str=/^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|([1-9][0-9]*)|([0][.][0-9]+[1-9]*))$/;
  	if(!str.test($.trim($("#area").val()))){
  		if($.trim($("#area").val())=="")
  		{
			     $("#area_msg").html("面积不能为空");
			     area_flag = false;
			}
			else{
			     $("#area_msg").html("请填写正数");
			     area_flag = false;
			}
	    }
	    else
	    {
	      var price = $.trim($("#price").val());
	      var area = $.trim($("#area").val());
	      var count = parseFloat(price)*parseFloat(area);
	      $("#count").val("");
          $("#total").val("");
          $("#count").val(Math.round(count*100)/100);
          $("#total").val(Math.round(count*100)/100);
	      area_flag = true;
		  $("#area_msg").html("");
	    }
    });
  
  //表单提交校验
  $("#submitButton").click(function(){
	  
	 //校验密码
	var pwd = window.prompt("密码验证","请输入您的密码");
	$.post(
			basePath + "login/pwdcheck?loginPwd="+pwd,
			function(data) {
				if(data =="ok")
					{
						if($.trim($("#area").val())=="")
						{
							area_flag = false;
						    $("#area_msg").html("面积不能为空");
						}
						if($.trim($("#price").val())=="")
						{
							price_flag = false;
						    $("#price_msg").html("进价不能为空");
						}
						if($.trim($("#floor_name").val())=="-1")
						{
							floor_flag = false;
						    $("#floor_msg").html("地板名称不能为空");
						}

					  	if(floor_flag && area_flag && price_flag){
					  		$("#form_id").submit();
					  	}else{
					  		return false;
					  	}
						
					}
				else{
					alert("密码错误！");
				}
			},
			"json"
	);
  });
  
});