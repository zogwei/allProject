function checklogin()
{
  if(document.form1.admin_name.value=='')
     {alert('�������û���');
      document.form1.admin_name.focus();
      return false
    }
  if (document.form1.admin_pass.value=='')
   {alert('����������');
    document.form1.admin_pass.focus();
    return false
   }
  if (document.form1.code.value=='')
  {alert('��������֤��');
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
if (confirm("���Ҫɾ��������¼��ɾ����˼�¼����������ݶ�����ɾ�������޷��ָ���"))
window.location = params ;
}

function CheckForm()
{
if(document.form1.name.value.length<1)
	{
	    alert("�������Ʋ���Ϊ��!");
	    return false;
	}
if(document.form1.ulr.value.length<1)
	{
	    alert("������ַ����Ϊ��!");
	    return false;
	}
}