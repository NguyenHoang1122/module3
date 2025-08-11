<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 10/08/2025
  Time: 10:27 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý danh mục</title>
</head>
<body>
<h2>Danh mục sản phẩm</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/categories/store">
    Tên danh mục: <input type="text" name="name" required/>
    <button type="submit">Thêm mới</button>
</form>
<table border="1">
    <tr><th>ID</th><th>Tên</th><th>Action</th></tr>
    <c:forEach var="c" items="${categories}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin/categories/update" style="display:inline">
                    <input type="hidden" name="id" value="${c.id}"/>
                    <input type="text" name="name" value="${c.name}" required/>
                    <button type="submit">Sửa</button>
                </form>
                <a href="${pageContext.request.contextPath}/admin/categories/delete?id=${c.id}" onclick="return confirm('Xóa?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>