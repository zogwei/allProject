//记录工程的完整路径
var basePath;
var floor_flag = true;
var block_flag = true;
var price_flag = true;


function setAreaAndTotal()
{
	
	var fOnePrice = parseFloat(document.getElementById('price').value);
	var fBlocks = parseFloat( document.getElementById('quantity').value);
	var totalArea = fOneBlockArea*fBlocks;
	var totalPrice = totalArea* fOnePrice;
	$("#area").val(Math.round(totalArea*100)/100);
	$("#areaDispaly").val(Math.round(totalArea*100)/100);
	$("#count").val(Math.round(totalPrice*100)/100);
    $("#total").val(Math.round(totalPrice*100)/100);
}

$( function() {
	
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
		    	setAreaAndTotal();
		      price_flag = true;
			  $("#price_msg").html("");
		    }
  });
      
   //块数校验
  $("#quantity").blur(function(){
      var str=/^\d+$/;
  	if(!str.test($.trim($("#quantity").val()))){
  		if($.trim($("#quantity").val())==""||$.trim($("#quantity").val())=="0")
  		{
			     $("#quantity_msg").html("块数不能为空");
			     block_flag = false;
			}
			else{
			     $("#quantity_msg").html("请填写正数");
			     block_flag = false;
			}
	    }
	    else
	    {
	    	setAreaAndTotal();
	    	block_flag = true;
		  $("#quantity_msg").html("");
	    }
    });
  
  //表单提交校验
  $("#submitButton").click(function(){

	  	if(!(floor_flag && block_flag && price_flag)){
	  		return false;
	  	}
	  
		if($.trim($("#quantity").val())==""||$.trim($("#quantity").val())=="0")
		{
			block_flag = false;
		    $("#quantity_msg").html("片数不能为空");
		    return false;
		}
		if($.trim($("#price").val())==""||$.trim($("#price").val())=="0")
		{
			price_flag = false;
		    $("#price_msg").html("进价不能为空");
		    return false;
		}
		if($.trim($("#floor_name").val())=="-1")
		{
			floor_flag = false;
		    $("#floor_msg").html("地板名称不能为空");
		    return false;
		}
	  
		 //校验密码
		var pwd = window.prompt("密码验证","请输入您的密码");
		$.post(
				basePath + "login/pwdcheck?loginPwd="+pwd,
				function(data) {
					if(data =="ok")
						{
						  	if(floor_flag && block_flag && price_flag){
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