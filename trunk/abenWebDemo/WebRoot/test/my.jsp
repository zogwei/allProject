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
			Ext.onReady(function()	{
					var _window = new Ext.Window({
							title:"登 陆",
							frame:true,
							width:260,
							height:130,
							plain:true,
							resizable:false,
							layout:"form",
							labelWidth:45,
							buttonAlign:"center",
							closable :true,
							closeAction:"hide",
							draggable :false,
							defaults:{xtype:"textfield",width:180},
							listeners:{
								"show":function(){
									alert("窗体显示");
								},
								"hide":function(){
									alert("窗体隐藏");
								},
								"close":function(){
									alert("窗体关闭");
								}
							},
							bodyStyle:"padding:3px",
							items:[{
								     fieldLabel:"账 号"
								  },{
								    fieldLabel:"密 码" 
							}],
							buttons:[{
								text:"确 定"
							},{
								text:"取 消",
								handler:function(){
									_window.hide();
								}
							}]
					});
					_window.show();
				
				});
		</script>
	</head>
  <body>
    
  </body>
</html>