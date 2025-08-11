<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 10/08/2025
  Time: 10:23 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Đăng ký</title>
</head>
<body>
<h2>Đăng ký</h2>

<c:if test="${not empty error}">
    <p style="color:red"><c:out value="${error}"/></p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/auth/register">
    Họ tên: <input type="text" name="name" required/><br/>
    Email: <input type="email" name="email" required/><br/>
    Số điện thoại: <input type="text" name="phone"/><br/>
    Địa chỉ: <input type="text" name="address"/><br/>
    Mật khẩu: <input type="password" name="password" required/><br/>
    <button type="submit">Đăng ký</button>
</form>
</body>
</html>
