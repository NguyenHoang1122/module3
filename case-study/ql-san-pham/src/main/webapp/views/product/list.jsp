<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 07/08/2025
  Time: 2:45 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Danh sách sản phẩm</title>
</head>
<body>
<h2>Sản phẩm</h2>

<p>
    <a href="${pageContext.request.contextPath}/cart">Xem giỏ hàng</a> |
    <a href="${pageContext.request.contextPath}/auth/logout">Đăng xuất</a>
</p>

<form method="get" action="${pageContext.request.contextPath}/products/search">
    <input type="text" name="q" placeholder="Tìm sản phẩm..."/>
    <button type="submit">Tìm</button>
</form>

<h3>Danh mục</h3>
<ul>
    <li><a href="${pageContext.request.contextPath}/products">Tất cả</a></li>
    <c:forEach var="c" items="${categories}">
        <li>
            <a href="${pageContext.request.contextPath}/products/category?cid=${c.id}">
                    ${c.name}
            </a>
        </li>
    </c:forEach>
</ul>

<table border="1">
    <tr>
        <th>#</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Stock</th>
        <th>Action</th>
    </tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>
                <a href="${pageContext.request.contextPath}/products/detail?id=${p.id}">
                        ${p.name}
                </a>
            </td>
            <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫"/></td>
            <td>${p.stock}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/cart/add" style="display:inline">
                    <input type="hidden" name="productId" value="${p.id}"/>
                    <button type="submit">Thêm vào giỏ</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
