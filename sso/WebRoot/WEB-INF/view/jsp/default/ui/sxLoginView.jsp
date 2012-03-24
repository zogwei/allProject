<%
  //
  // 陕西版本登录页面
  // 需要在 default_views.properties文件中配置该属性
  // @Author: cameron
 %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:directive.include file="includes/sxtop.jsp" />
<%if(request.getParameter("user") != null && "true".equals(request.getParameter("validated"))) {%>
<form:form  method="post" id="fm1"  commandName="${commandName}" htmlEscape="true" >
	<div style="display:none;">
	<spring:message code="screen.welcome.label.netid" />
	<c:if test="${not empty sessionScope.openIdLocalId}">
	<strong>${sessionScope.openIdLocalId}</strong>
	<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" cssClass="input120" />
	</c:if>
	
	<c:if test="${empty sessionScope.openIdLocalId}">
	<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
	<form:input cssClass="required input120" cssErrorClass="error" id="username" size="18" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
	</c:if>
	
	<spring:message code="screen.welcome.label.password" />
	<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
	<form:password cssClass="required input120" cssErrorClass="error" id="password" size="18" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
	
	<input type="hidden" name="lt" value="${flowExecutionKey}" />
	<input type="hidden" name="_eventId" value="submit"  class="btn-login"  />

    <input class="btn-login" name="submit" accesskey="l"   value="" tabindex="4" type="submit" />&nbsp;&nbsp;&nbsp;<input class="btn-chongzhi" name="reset" accesskey="c" value=""  tabindex="5" type="reset" />
	</div>
	<form:errors path="*"  id="status" element="div" />
	</form:form>
	<script type="text/javascript">
		document.getElementById("username").value="<%=request.getParameter("user")%>";
		document.getElementById("password").value="dbp@:true";
		document.getElementById("submit").click(); 
	</script>
	
<%} else {%>
<style type="text/css">
<!--
body,html{
	background-repeat: repeat;
	font-size: 12px;
	margin: 0 auto;
	padding: 0;
	line-height: 21px;
	text-align:center;
	height:100%; overflow:hidden;
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
</script>
<form:form  method="post" id="fm1"  commandName="${commandName}" htmlEscape="true" >
	<div style="width:100%; height:100%;" class="div">	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">

<!--1-->
  <tr>
    <td height="100" bordercolor="#993300" valign="bottom">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td>&nbsp;</td>
			<td class="tc">
				<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="images/blue/frame/login_teleLogo.gif" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td class="tc" style=" font-size:12px; color:#FFF; ">
                    <b style="font-size:18px;line-height:24px;">陕西公司企业数据应用门户</b><br />
					Shan Xi Gong Si Qi Ye Shu Ju Ying Yong Men Hu
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
    <td id="main" align="center"  valign="top" style="background:url(images/blue/frame/dImg2.gif) right bottom no-repeat;">
	<table border="0" cellspacing="0" cellpadding="0" height="30">
	<tr><td>&nbsp;</td></tr></table>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
			  <table background="images/blue/frame/login_signIn.jpg" width="300" height="260" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="90" height="70">&nbsp;</td>
					<td width="20"></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td height="30" align="right" style="font-size:14px;"><spring:message code="screen.welcome.label.netid" /></td>
					<td><img src="images/blue/frame/user.gif" width="10" height="15" /></td>
				  <td align="left"><c:if test="${not empty sessionScope.openIdLocalId}">
								<strong>${sessionScope.openIdLocalId}</strong>
								<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" cssClass="input120" />
								</c:if>
								
								<c:if test="${empty sessionScope.openIdLocalId}">
								<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
								<form:input cssClass="required input120" cssErrorClass="error" id="username" size="18" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
								</c:if></td>
				</tr>
				<tr>
					<td height="30" align="right" style="font-size:14px;"><spring:message code="screen.welcome.label.password" /></td>
					<td><img src="images/blue/frame/password.gif" width="12" height="15" /></td>
				  <td align="left"><spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
								<form:password cssClass="required input120" cssErrorClass="error" id="password" size="18" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" /></td>
				</tr>

				<tr>
					<td height="30">&nbsp;</td>
					<td></td>
					<td align="left"><input type="hidden" name="lt" value="${flowExecutionKey}" />
								<input type="hidden" name="_eventId" value="submit"  class="btn-login"  />

                        		<input class="btn-login" name="submit" accesskey="l"   value="" tabindex="4" type="submit" />&nbsp;&nbsp;&nbsp;<input class="btn-chongzhi" name="reset" accesskey="c" value=""  tabindex="5" type="reset" /></td>
                        		
				</tr>

				<tr>
					<td colspan="3" height="60" style="padding:5px;color:red;"><form:errors path="*"  id="status" element="div" /></td>
			    </tr>
				
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
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td align="center" class="tl" style="color:#CADDEE;"> &nbsp;&nbsp;版权所有：中国电信陕西分公司 地址：中国陕西省西安市高新路五十六号 </td><!-- 维护支撑电话：0891-6834000  支撑投诉电话：0891-6325318 -->
	
	  </tr>
	</table>
	</td>
  </tr>
</table></div>      
</form:form>
<%} %>	
<jsp:directive.include file="includes/bottom.jsp" />
