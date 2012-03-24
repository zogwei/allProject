<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>登录</title>
		<!----------加载ext框架核心 样式与js文件-------------->
		<link rel="stylesheet" type="text/css" href="/AbenDemo/js/ext-2.2/resources/css/ext-all.css">
		<script language="javascript" type="text/javascript" src="/AbenDemo/js/ext-2.2/adapter/ext/ext-base.js"></script>
		<script language="javascript" type="text/javascript" src="/AbenDemo/js/ext-2.2/ext-all.js"></script>
		<script language="javascript" type="text/javascript" src="/AbenDemo/js/ext-2.2/source/locale/ext-lang-zh_CN.js"></script>
		<style type="text/css">
			.contain{
					width:100%; 
					height:100%;
					top:0;
					left:0;
				}
			.center {
				position:absolute;
				top:30%;
				left:43%;
				text-align:left;
			}
	    </style>
			
		<script type="text/javascript">
  Ext.onReady(function()
  {
       var form1 = new Ext.form.FormPanel({
       renderTo:"loginForm", //要渲染的div
       method:'POST',
       //title: '登录窗口',
	   width:260,
	   height:130,
	   plain:true,
	   resizable:false,
	   layout:"form",
	   labelWidth:45,
	   buttonAlign:"center",
	   closable :false,
	   draggable :false,
	   defaults:{xtype:"textfield",width:180},
       //实现非AJAX提交表单一定要加下面的两行！
       onSubmit: Ext.emptyFn,
       submit: function() 
       {
           this.getEl().dom.action= '/AbenDemo/sys/login_Login_loginCheck.action'; //连接到服务器的url地址
           this.getEl().dom.submit();
       },
       
       items: [{
           fieldLabel: '用户名',
           id: 'username', 
           name: 'name',   
           allowBlank:false,
           plain:true,
           blankText : "用户名不能为空",
           width:150
           },{
           fieldLabel: '密码',
           blankText : "密码不能为空",
           id: 'password',
           name: 'pwd',
           allowBlank:false,
           minLength : 6,
           width:150,
           inputType:'password' //类型为password
       }
       ],
       buttons: [{
           text: '登录',
           type:'button',
           id:'login',
           handler: function()
           {
                   //表单验证通过
                   if (form1.form.isValid())
                   {    
                       //提交form
                       form1.form.submit();
                   }    
           }
       },{
           text: '重置',
           type:'reset',
           id:'clear',
           handler: function()
           {
                form1.form.reset();
           }
       }
       ]
       }); 
    
    //将form添加window中
    var window = new Ext.Window({
        title: '用户登录',
        width: 260,
        height:130,
        layout: 'fit',
        plain:true,
        resizable:false,
        closable :false,
        moveable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: form1
    });
    //显示window
    window.show();    
  });
  
 </script>
    </head>
    <body>
        <div id="loginForm"></div>
    </body>
</html>
