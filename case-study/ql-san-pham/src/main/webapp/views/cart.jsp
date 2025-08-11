<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Giỏ hàng</title>
</head>
<body>
<h2>Giỏ hàng</h2>

<table border="1">
    <tr>
        <th>#</th>
        <th>Tên sản phẩm</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Thành tiền</th>
        <th>Action</th>
    </tr>
    <c:choose>
        <c:when test="${not empty cartItems}">
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.product.name}</td>
                    <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="₫"/></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/cart/update" style="display:inline">
                            <input type="hidden" name="cartId" value="${item.id}"/>
                            <input type="number" name="quantity" value="${item.quantity}" min="1"/>
                            <button type="submit">Cập nhật</button>
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₫"/></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/cart/remove" style="display:inline">
                            <input type="hidden" name="cartId" value="${item.id}"/>
                            <button type="submit">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr><td colspan="6">Giỏ hàng trống</td></tr>
        </c:otherwise>
    </c:choose>
</table>

<p>Tổng tiền: <fmt:formatNumber value="${total}" type="currency" currencySymbol="₫"/></p>

<form method="post" action="${pageContext.request.contextPath}/cart/clear">
    <button type="submit">Xóa toàn bộ giỏ hàng</button>
</form>
<form method="post" action="${pageContext.request.contextPath}/orders/place">
    <button type="submit">Đặt hàng</button>
</form>
<p><a href="${pageContext.request.contextPath}/products">Tiếp tục mua hàng</a></p>
</body>
</html>
