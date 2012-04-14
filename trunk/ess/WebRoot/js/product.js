//记录工程的完整路径
var basePath;
//用来记录作为查询条件各个条件的id号
var categoryId;
var colorCodeId;
var specId;
var veinId;
var supplierId;
var tenantName;
var floorName;
//用于记录当前为第几张图片
var imageFlag;
var picURL;
var images;
var floorPictureFolder = "img/floor";


$(function() {
	$("body").ready(function() {
		//初始化地板类型下拉菜单
		var url = basePath + "floorCategory/json/list";
		$.post(
			url,
			function(data) {
				$.each(data, function(index, floorCategory){
					var $opt = $("<option value='" + floorCategory.id + "'>" + floorCategory.name + "</option>");
					if(categoryId == floorCategory.id)
						$opt = $("<option value='" + floorCategory.id + "' selected='selected'>" + floorCategory.name + "</option>");
					$opt.appendTo($("#categoryTag"));
				}); 
			},
			"json"
		);
		
		//初始化规格下拉菜单
		var url = basePath + "spec/json/list";
		$("#specTag option:gt(0)").remove();
		$.post(
			url,
			function(data) {
				$.each(data, function(index, spec){
					var $opt = $("<option id='spec_" + spec.id + "' value='" + spec.id + "'>" + spec.name + "</option>");
					if(specId == spec.id)
						$opt = $("<option id='spec_" + spec.id + "' value='" + spec.id + "' selected='selected'>" + spec.name + "</option>");
					$opt.appendTo($("#specTag"));
				}); 
			},
			"json"
		);
		
		//初始化色号下拉菜单
		var url = basePath + "colorCode/json/list";
		$("#colorCodeTag option:gt(0)").remove();
		$.post(
			url,
			function(data) {
				$.each(data, function(index, colorCode){
					var $opt = $("<option id='colorCode_" + colorCode.id + "' value='" + colorCode.id + "'>" + colorCode.name + "</option>");
					if(colorCodeId == colorCode.id)
						$opt = $("<option id='colorCode_" + colorCode.id + "' value='" + colorCode.id + "' selected='selected'>" + colorCode.name + "</option>");
					$opt.appendTo($("#colorCodeTag"));
				}); 
			},
			"json"
		);
		
		
		//初始化纹理下拉菜单
		/*
		var url = basePath + "vein/json/list";
		$("#veinTag option:gt(0)").remove();
		$.post(
			url,
			function(data) {
				$.each(data, function(index, vein){
					var $opt = $("<option id='vein_" + vein.id + "' value='" + vein.id + "'>" + vein.name + "</option>");
					if(veinId == vein.id)
						$opt = $("<option id='vein_" + vein.id + "' value='" + vein.id + "' selected='selected'>" + vein.name + "</option>");
					$opt.appendTo($("#veinTag"));
				}); 
			},
			"json"
		);
		*/
		
		//初始化供应商下拉菜单
		var url = basePath + "supplier/json/list";
		$("#supplierTag option:gt(0)").remove();
		$.post(
			url,
			{name:""},
			function(data) {
				$.each(data, function(index, supplier){
					var $opt = $("<option id='supplier_" + supplier.id + "' value='" + supplier.id + "'>" + supplier.name + "</option>");
					if(supplierId == supplier.id)
						$opt = $("<option id='supplier_" + supplier.id + "' value='" + supplier.id + "' selected='selected'>" + supplier.name + "</option>");
					$opt.appendTo($("#supplierTag"));
				}); 
			},
			"json"
		);
	});
	
	//添加或修改地板信息表单提交事件
	$("#form1").submit(function(event) {
		if($("#imageTag").val().length > 0) {
			if(!isUploadImageType($("#imageTag").val())) {
				alert("上传的图片文件类型不是*.jpg/*.bmp/*.gif/*.png/*.zip/*.rar之一，请重新选择上传图片");
				event.preventDefault();
			}
		}	
		formValidate(event);
	});
	
	//主页面上传图片提交事件
	$("#image_form").live("submit", function(event) {
		var url = $(this).find("input:file").val();
		if(!isUploadImageType(url)) {
			alert("上传的图片文件类型不是*.jpg/*.bmp/*.gif/*.png/*.zip/*.rar之一，请重新选择上传图片");
			event.preventDefault();
		}
	});
	
	$("#excel_form").live("submit", function(event) {
		var url = $(this).find("input:file").val();
		if(!isUploadExcelType(url)) {
			$("#categoryName_msg").html("格式有误，请选择*.xls或*.xlsx文件");
			event.preventDefault();
		}
	});
});

$(function() {
	//添加或修改地板信息的验证
	$("#floorNumber").blur(function() {
		if($("#floorNumber").val().length > 0)
			$("#floorNumber_msg").html("");
		else
			$("#floorNumber_msg").html("请填写地板编号");
	});
	
	$("#floorName").blur(function() {
		if($("#floorName").val().length > 0)
			$("#floorName_msg").html("");
		else
			$("#floorName_msg").html("请填写地板名称");
	});
	
	$("#categoryTag").change(function() {
		if($("#categoryTag").val() >= 0)
			$("#categoryName_msg").html("");
		else
			$("#categoryName_msg").html("请选择类别");
	});
	
	$("#supplierTag").change(function() {
		if($("#supplierTag").val() >= 0)
			$("#supplierName_msg").html("");
		else
			$("#supplierName_msg").html("请选择供应商");
	})
	
	/*
	$("#veinTag").change(function() {
		if($("#veinTag").val() >= 0)
			$("#veinName_msg").html("");
		else
			$("#veinName_msg").html("请选择纹理");
	});
	*/
	
	$("#colorCodeTag").change(function() {
		if($("#colorCodeTag").val() >= 0)
			$("#colorCodeName_msg").html("");
		else
			$("#colorCodeName_msg").html("请选择色号");
	});
	
	$("#specTag").change(function() {
		if($("#specTag").val() >= 0)
			$("#specName_msg").html("");
		else
			$("#specName_msg").html("请选择规格");
	});
	
	$("#bookPrice").blur(function() {
		if(isPrice($("#bookPrice").val()))
			$("#bookPrice_msg").html("");
		else
			$("#bookPrice_msg").html("请填写正确售价");
	});
	
	$("#amountPrice").blur(function() {
		if(isPrice($("#amountPrice").val()))
			$("#amountPrice_msg").html("");
		else
			$("#amountPrice_msg").html("请填写正确售价");
	});
	
	$("#detailPrice").blur(function() {
		if(isPrice($("#detailPrice").val()))
			$("#detailPrice_msg").html("");
		else
			$("#detailPrice_msg").html("请填写正确售价");
	});
	
	$("#sellPrice").blur(function() {
		if(isPrice($("#sellPrice").val()))
			$("#sellPrice_msg").html("");
		else
			$("#sellPrice_msg").html("请填写正确售价");
	});
	
	
	//添加规格，纹理，色号，类别的验证
	$("#categoryName").blur(function() {
		if($("#categoryName").val().length > 0)
			$("#categoryName_msg").html("");
		else
			$("#categoryName_msg").html("请输入类别名称");
	});
	
	/*
	$("#veinName").blur(function() {
		if($("#veinName").val().length > 0)
			$("#veinName_msg").html("");
		else
			$("#veinName_msg").html("请输入纹理名称");
	});
	*/
	
	$("#colorCodeName").blur(function() {
		if($("#colorCodeName").val().length > 0)
			$("#colorCodeName_msg").html("");
		else
			$("#colorCodeName_msg").html("请输入色号名称");
	});
	
	$("#specName").blur(function() {
		if($("#specName").val().length > 0)
			$("#specName_msg").html("");
		else
			$("#specName_msg").html("请输入规格名称");
	});
	
	$("#category_form").submit(function(event) {
		if($("#categoryName").val().length <= 0) {
			$("#categoryName_msg").html("请输入类别名称");
			event.preventDefault();
		}
	});
	
	/*
	$("#vein_form").submit(function(event) {
		if($("#veinName").val().length <= 0) {
			$("#veinName_msg").html("请输入纹理名称");
			event.preventDefault();
		}
	});
	*/
	
	$("#colorCode_form").submit(function(event) {
		if($("#colorCodeName").val().length <= 0) {
			$("#colorCode_msg").html("请输入色号名称");
			event.preventDefault();
		}
	});
	
	
	$("#spec_form").submit(function(event) {
		if($("#specName").val().length <= 0) {
			$("#specName_msg").html("请输入规格名称");
			event.preventDefault();
		}
	});
	
});

function isPrice(sellPrice) {
	if(sellPrice <= 0)
		return false;
	var reg = /^\d{0,8}\.{0,1}(\d{1,8})?$/;
	return reg.test(sellPrice);
}

function formValidate(event) {
	//增加修改地板信息表单验证
	if($("#floorNumber").val().length <= 0) {
		$("#floorNumber_msg").html("请填写地板编号");
		event.preventDefault();
	}
	if($("#floorName").val().length <= 0) {
		$("#floorName_msg").html("请填写地板名称");
		event.preventDefault();
	}
	if($("#supplierTag").val() < 0) {
		$("#supplierName_msg").html("请选择供应商");
		event.preventDefault();
	}
	/*
	if($("#veinTag").val() < 0) {
		$("#veinName_msg").html("请选择纹理");
		event.preventDefault();
	}
	*/
	if($("#colorCodeTag").val() < 0) {
		$("#colorCodeName_msg").html("请选择色号");
		event.preventDefault();
	}
	if($("#specTag").val() < 0) {
		$("#specName_msg").html("请选择规格");
		event.preventDefault();
	}
	if($("#categoryTag").val() < 0) {
		$("#categoryName_msg").html("请选择类别");
		event.preventDefault();
	}
	if(!isPrice($("#bookPrice").val())) {
		$("#bookPrice_msg").html("请填写正确售价");
		event.preventDefault();
	}
	if(!isPrice($("#amountPrice").val())) {
		$("#amountPrice_msg").html("请填写正确售价");
		event.preventDefault();
	}
	if(!isPrice($("#detailPrice").val())) {
		$("#detailPrice_msg").html("请填写正确售价");
		event.preventDefault();
	}
	if(!isPrice($("#sellPrice").val())) {
		$("#sellPrice_msg").html("请填写正确售价");
		event.preventDefault();
	}
	
}

function showPage(page) {
	var url = basePath + "floor/list?currentPage=" + page;
	if(categoryId.length > 0 && categoryId >= 0)
		url = url + "&category.id=" + categoryId;
	if(specId.length > 0 && specId >= 0)
		url = url + "&spec.id=" + specId;
	if(colorCodeId.length > 0 && colorCodeId >= 0)
		url = url + "&colorCode.id=" + colorCodeId;
	if(veinId.length > 0 && veinId >= 0)
		url = url + "&vein.id=" + veinId;
	window.location=url;
}

function showImages(floorId) {
	
	if(floorId >= 0) {
		images = new Array();
		var url = basePath + "floor/json/images";
		$.post(
			url,
			{"floorId":floorId},
			function(data){
				$.each(data, function(index, pic){
					images[index] = pic.picPath;
				}); 
				if(images.length > 0) {
					picURL = basePath + floorPictureFolder + "/"  + tenantName + "/" + floorName + "/" + images[0];
					imageFlag = 0;
					$("#floorImage").attr("src", picURL);
					$("#prev_image").hide();
					if(imageFlag < images.length - 1) {
						$("#next_image").show();
					} else {
						$("#next_image").hide();
					}
				} else {
					$("#floorImage").hide();
					$("#prev_image").hide();
					$("#next_image").hide();
				}
			},
			"json"
		);
	}
}

function prevImage() {
	imageFlag = imageFlag - 1;
	picURL = basePath + floorPictureFolder + "/" + tenantName + "/" + floorName + "/" + images[imageFlag];
	$("#floorImage").attr("src", picURL);
	if(imageFlag <= 0) {
		$("#prev_image").hide();
		$("#next_image").show();
	}
		
	else {
		$("#prev_image").show();
		$("#next_image").show();
	}
		
}

function nextImage() {
	imageFlag = imageFlag + 1;
	picURL = basePath + floorPictureFolder + "/" + tenantName + "/" + floorName + "/" + images[imageFlag];
	$("#floorImage").attr("src", picURL);
	if(imageFlag >= (images.length - 1)) {
		$("#next_image").hide();
		$("#prev_image").show();
	}
	else {
		$("#prev_image").show();
		$("#next_image").show();
	}
		
}

function isUploadImageType(fileName) {
	var reg = /\.(jpg|jpeg|bmp|zip|rar)$/i;
	return reg.test(fileName);
}

function isUploadExcelType(fileName) {
	var reg = /\.(xls|xlsx)$/i;
	return reg.test(fileName);
}













