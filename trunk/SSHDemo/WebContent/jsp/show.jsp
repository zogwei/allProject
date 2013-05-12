<%@ include file="/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<br>
The user name:<c:out value="${name}"></c:out>
<br>
The password:<c:out value="${password}"></c:out>
<br>
<br>
The password:<c:out value="${errorMsg}"></c:out>
<c:choose>
	<c:when test="${users }==null">
The result is null
	</c:when>
	<c:otherwise>
		<table>
			<tr>
				<td>id</td>
				<td>name</td>
				<td>password</td>
				<td>email</td>
			</tr>
			<c:forEach var="user" items="${users}">
				<tr>
					<td><c:out value="${user.id }" /></td>
					<td><c:out value="${user.name}" /></td>
					<td><c:out value="${user.password}" /></td>
					<td><c:out value="${user.email}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
</body>
</html>