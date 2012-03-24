<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <script type="text/javascript">
        var remoteUrl = "${remoteLoginUrl}?validated=true";
        // 构造错误消息
        var errorMessage = "";
        <spring:hasBindErrors name="credentials">
        errorMessage = "&errorMessage=" + encodeURIComponent('<c:forEach
 var="error" items="${errors.allErrors}"><spring:message code="${error.code}"
 text="${error.defaultMessage}" /></c:forEach>');
        </spring:hasBindErrors>
        
        // 构造service
        var serivce = "";
        <c:if test="${service != null && service != ''}">
        service = "&service=" + encodeURIComponent("${service}");
        </c:if>
        // 跳转回去
        window.location.href = remoteUrl + errorMessage + service;
    </script>
</head>
<body>
    ${remoteLoginMessage}
</body>
</html>

