var http_request = false;
var callobj;
function makeRequest(url, functionName, httpType, sendData) {
	http_request = false;
	if (!httpType) httpType = "GET";
	if (window.XMLHttpRequest) { 
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
			http_request.overrideMimeType('text/plain');
		}
		} else if (window.ActiveXObject) {
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	if (!http_request) {
		alert('Cannot send an XMLHTTP request');
		return false;
	}
	var changefunc="http_request.onreadystatechange = "+functionName;
	eval (changefunc);
	http_request.open(httpType, url, true);
	http_request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	http_request.send(sendData);
}

function getReturnedText () {
	if (http_request.readyState == 4) {
		if (http_request.status == 200) {
			var http_msg = http_request.responseText;
			if (http_msg=='0') {
				form1.submits.disabled="disabled";
				show.style.display='';
			}else{
				form1.submits.disabled="";
				show.style.display='none';
			}
		}
	}
}

function ShowObj(objname){var obj = document.getElementById(objname);obj.style.display = "block";}
function HideObj(objname){var obj = document.getElementById(objname);obj.style.display = "none";}


// Ñ¡Ôñ
function CheckAll(){
	var chk = chkall.checked;
	for(var i = 0; i < form1.ids.length; i++){form1.ids[i].checked=chk;}
}
