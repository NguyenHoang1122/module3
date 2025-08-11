<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h2>Đăng nhập</h2>

<c:if test="${not empty error}">
    <p style="color:red"><c:out value="${error}"/></p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/auth/login">
    Email: <input type="email" name="email" required/><br/>
    Mật khẩu: <input type="password" name="password" required/><br/>
    <button type="submit">Đăng nhập</button>
</form>

<p><a href="${pageContext.request.contextPath}/auth/register">Đăng ký</a></p>
</body>
</html>
