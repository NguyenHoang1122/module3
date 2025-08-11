
<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 11/08/2025
  Time: 9:15 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Danh sách sản phẩm</title></head>
<body>
<h2>Danh sách sản phẩm</h2>
<a href="/products/create">Create</a>
<table border="1">
    <tr>
        <th>ID</th><th>Tên</th><th>Giá</th><th>Số lượng</th><th>Màu sắc</th><th>Loại sản phẩm</th><th>Action</th>
    </tr>
    <c:forEach var="product" items="${listProducts}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.quantity}</td>
            <td>${product.color}</td>
            <td>${product.categoryName}</td>
            <td>
                <a href="/products/edit?id=${product.id}">Edit</a>
                <a href="/products/delete?id=${product.id}" onclick="return confirm('Are you sure?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
