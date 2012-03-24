<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@page import="java.util.Map"%>
<jsp:directive.include file="includes/top.jsp" />
<%
	// oa传入参数--singleFlag、staffAccount
	String singleFlag = request.getParameter("singleFlag");
	String staffAccount="";
	String password="";
	System.out.println(singleFlag);
	int isExist=0;
	if(singleFlag!=null && singleFlag.equals("1"))
	{
		staffAccount = request.getParameter("staff");
		DataSource ds =null;
		InitialContext ctx = null;
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql="select password from dbp_sys_staff where staff_account='"+staffAccount+"'";
		try
		{
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/dbp");
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs =  stmt.executeQuery(sql);
			while(rs.next())
			{
			   password=rs.getString("PASSWORD");
			   isExist=1;
			}
		}
		catch (Exception ex)
		{
			System.out.println("使用连接池连接ORACLE--gaw 数据库失败" + ex.toString());
			ex.printStackTrace();
		}
		finally
		{
			if(conn!=null)
			{
				conn.close();
			}	
			if(stmt!=null)
			{
				stmt.close();
			}
			if(rs!=null)
			{
				rs.close();
			}
		}
	}

	String localIp = "135.64.128.12";
	
	
	
%>
<style type="text/css">
<!--
*{padding:0px;margin:opx;}
body,html{
	background-repeat: repeat;
	font-size: 12px;
	margin: 0 auto;
	padding: 0;
	line-height: 21px;
	text-align:center;
	background:url(images/blue/frame/login_Bg.jpg) no-repeat;
}
.div{
	
	background-repeat: repeat;
	font-size: 12px;
	margin: 0 auto;
	padding: 0;
	line-height: 21px;
	text-align:center;
	height:100%; overflow:hidden;
	background:url(images/blue/frame/login_Bg.jpg) no-repeat -24px -17px;
}
.page-bg{
	background-image: url(images/blue/login-pagebg.gif);
	background-repeat: repeat;
	font-size: 12px;
	margin: 0 auto;
	padding: 0;
	line-height: 21px;
	text-align:center;
}
.side4-gray{
	border: 1px solid #d5d5d5;
	border-collapse: collapse;
}
.fl{
	float: left;
}

.btn-login{
background:url(images/blue/btn-login.gif) no-repeat 0 0 ;
height:21px;
width:59px;
border:none;
cursor:pointer;}

.btn-chongzhi{
background:url(images/blue/btn-chongzhi.gif) no-repeat 0 0 ;
height:21px;
width:59px;
border:none;
cursor:pointer;}

.input120{
width:130px;
	margin-left: 2px;
	padding: 0 2px;
	vertical-align:middle;
border: #a1c0db solid 1px;
}
.tl{
	text-align: left;
}
-->

</style>
<script>
function heightValue()
{
    //如果是从OA链接过来的单点登录，则执行下面代码
    var isSingleLogin = "<%=singleFlag%>";
    if(isSingleLogin==1)
    {
        var isExist="<%=isExist%>";
        if(isExist!=0)
        {       
	    	var staffAccount="<%=staffAccount%>";
	    	var password="<%=password%>";
	    	document.getElementById("username").value=staffAccount;
	    	document.getElementById("password").value=password;		
			document.getElementById("submit").click();
		}

    }
    
    //普通门户系统登录
	if(document.documentElement.clientHeight > 0)
	{
		document.getElementById('main').height = (document.documentElement.offsetHeight-160);
	}
	else
	{
		window.location.reload();	
	}
}
window.onload = heightValue;
window.onresize = heightValue;


function installWindow()
{  

    var ip = "<%=localIp%>";
	var url = "http://"+ip+":8083/EDSWeb/jsp/plugin/setup.jsp";
	var win=window.open(url,'s',"height=400,width=550,status=yes,toolbar=no,menubar=no,location=no,scrollbars=1,top=200,left=300");
	win.focus();
	return false;
}
 

</script>
			<form:form  method="post" id="fm1" name="fm11" commandName="${commandName}" htmlEscape="true" >
	<div style="width:100%; height:100%;" class="div">		
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!--1-->
  <tr>
    <td height="100" bordercolor="#993300" valign="bottom" align="center" >
		<table border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td>&nbsp;</td>
			<td class="tc">
				<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="images/blue/frame/login_teleLogo.gif" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td class="tc" style=" font-size:12px; color:#FFF; ">
                    <b style="font-size:18px;line-height:24px;">安徽公司企业数据应用门户</b><br />
					</td>
				</tr>
				</table>
			</td>
			<td>&nbsp;</td>
		  </tr>
		</table>
	</td>
  </tr>
  
<!--2-->  
  <tr>
    <td id="main" align="center"  valign="top">
	<table border="0" cellspacing="0" cellpadding="0" height="30">
	<tr><td>&nbsp;</td></tr></table>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top" >
			  <table background="images/blue/frame/login_signIn.jpg" width="442" height="238" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="150" height="85">&nbsp;</td>
					<td width="20"></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td height="30" align="right" style="font-size:14px;font-weight:bold"><spring:message code="screen.welcome.label.netid" /></td>
					<td><img src="images/blue/frame/user.gif" width="10" height="15" /></td>
				  <td align="left"><c:if test="${not empty sessionScope.openIdLocalId}">
								<strong>${sessionScope.openIdLocalId}</strong>
								<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" cssClass="input120" />
								</c:if>
								
								<c:if test="${empty sessionScope.openIdLocalId}">
								<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
								<form:input cssClass="required input120" cssErrorClass="error" id="username" size="18" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" onblur="javascript:this.value=this.value.replace(/(^\s*)|(\s*$)/g, '');" htmlEscape="true" />
								</c:if></td>
				</tr>
				<tr>
					<td height="30" align="right" style="font-size:14px; font-weight:bold"><spring:message code="screen.welcome.label.password" /></td>
					<td><img src="images/blue/frame/password.gif" width="12" height="15" /></td>
				  <td align="left"><spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
								<form:password cssClass="required input120" cssErrorClass="error" id="password" size="18" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" /></td>
				</tr>

				<tr>
					<td height="30">&nbsp;</td>
					<td></td>
					<td align="left"><input type="hidden" name="lt" value="${flowExecutionKey}" />
								<input type="hidden" name="_eventId" value="submit"  class="btn-login"  />

                        		<input class="btn-login" id="submit" name="submit" accesskey="l"   value="" tabindex="4" type="submit" />&nbsp;&nbsp;&nbsp;<input class="btn-chongzhi" name="reset" accesskey="c" value=""  tabindex="5" type="reset" /></td>
                        		
				</tr>

				<tr>
					<td colspan="3" height="50" style="padding:5px;color:red;">
						<form:errors path="*"  id="status" element="div" />									
					</td>
			    </tr>

				<!--tr>
					<td height="10" colspan="3" valign="top" style="font-size:14px;"><a href='javascript:void(0)' onclick='installWindow()'>首次使用请先安装专业插件</a></td>
				</tr -->
			  </table>
			</td>

			
		</tr>
	</table>
	<br /><br /><br /><br /><br /><br />
	</td>
  </tr>
  
<!--3-->   
  <tr>
    <td height="40" bgcolor="#2970A9">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" align="center">
	  <tr align="center">
		<td align="center" style="color:#CADDEE;"> &nbsp;&nbsp;版权所有：中国电信安徽分公司 地址：安徽省合肥市长江西路307号</td>
	
	  </tr>
	</table>
	</td>
  </tr>
</table></div>

                    
        	</form:form>
<jsp:directive.include file="includes/bottom.jsp" />

