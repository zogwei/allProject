function isPrice(sellPrice) {
	if(sellPrice <= 0)
		return false;
	var reg = /^\d{0,8}\.{0,1}(\d{1,8})?$/;
	return reg.test(sellPrice);
}

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

function formValidate(event) {
	if(!isPrice($("#sellPrice").val())) {
		$("#sellPrice_msg").html("请填写正确售价");
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
}

$(function() {
	$("#spec_form").submit(function(event) {
		formValidate(event);
	});
});