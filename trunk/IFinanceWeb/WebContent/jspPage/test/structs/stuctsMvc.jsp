<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>structs MVC Test</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
   welcome!
   <br/>
   link:<a href = ""> structs MVC Test</a>
   
   <br/>
   <form action="<%=basePath%>demo/structs_Demo_addition.do">      
        <table>      
            <tr>      
                <td>Number 1:</td>      
                <td><input type="text" name="numberOne"/></td>      
            </tr>      
            <tr>      
                <td>Number 2:</td>      
                <td><input type="text" name="numberTwo"/></td>      
            </tr>      
            <tr>      
                <td colspan="2">      
                   <input type="submit"name="addition" value="Add"/>                          
                </td>      
            </tr>      
            <tr>      
                <td>Result:</td>      
                <td>${result}</td>      
            </tr>      
        </table>      
    </stripes:form>      
  </body>
</html>
