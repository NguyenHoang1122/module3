<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 10/08/2025
  Time: 10:25 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý sản phẩm</title>
</head>
<body>
<h2>Sản phẩm</h2>
<a href="${pageContext.request.contextPath}/admin/products/create">Thêm mới sản phẩm</a>
<table border="1">
    <tr>
        <th>ID</th><th>Tên</th><th>Giá</th><th>Stock</th><th>Action</th>
    </tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price}</td>
            <td>${p.stock}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/products/edit?id=${p.id}">Sửa</a>
                <a href="${pageContext.request.contextPath}/admin/products/delete?id=${p.id}" onclick="return confirm('Xóa?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
