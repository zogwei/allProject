<jsp:directive.include file="includes/top.jsp" />
		
		
<jsp:directive.include file="includes/bottom.jsp" />
<c:if test ="${fn:length(logoutUrls)>0}">
	<c:forEach var="attr" items="${logoutUrls}">
	<script type="text/javascript" src="${attr}"></script>  		
	</c:forEach>
</c:if>
<script type="text/javascript">
var url = "";
<%
	if(request.getParameter("service") != null) {
%>
		url = "<%=request.getParameter("service")%>";
<%
	}
%>
if(url!=""){
	window.location=url;
}
</script>

