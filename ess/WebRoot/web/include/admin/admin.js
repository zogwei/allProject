function checklogin()
{
  if(document.form1.admin_name.value=='')
     {alert('请输入用户名');
      document.form1.admin_name.focus();
      return false
    }
  if (document.form1.admin_pass.value=='')
   {alert('请输入密码');
    document.form1.admin_pass.focus();
    return false
   }
  if (document.form1.code.value=='')
  {alert('请输入验证码');
   document.form1.code.focus();
   return false
 }
}

function CheckAll(form)
{
for (var i=0;i<form.elements.length;i++)
{
var e = form.elements[i];
if (e.name != 'chkall')
e.checked = form.chkall.checked;
}
}

function left_menu(meval)
{
  var left_n=eval(meval);
  if (left_n.style.display=="none")
  { eval(meval+".style.display='';"); }
  else
  { eval(meval+".style.display='none';"); }
}
function DoEmpty(params)
{
if (confirm("真的要删除这条记录吗？删除后此记录里的所有内容都将被删除并且无法恢复！"))
window.location = params ;
}

function CheckForm()
{
if(document.form1.name.value.length<1)
	{
	    alert("链接名称不能为空!");
	    return false;
	}
if(document.form1.ulr.value.length<1)
	{
	    alert("链接网址不能为空!");
	    return false;
	}
}