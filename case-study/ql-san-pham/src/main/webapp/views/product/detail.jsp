<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Chi tiết sản phẩm</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty product}">
        <h2>${product.name}</h2>
        <p>Giá: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/></p>
        <p>Mô tả: ${product.description}</p>
        <p>Stock: ${product.stock}</p>

        <form method="post" action="${pageContext.request.contextPath}/cart/add">
            <input type="hidden" name="productId" value="${product.id}"/>
            Số lượng: <input type="number" name="quantity" value="1" min="1"/><br/>
            <button type="submit">Thêm vào giỏ</button>
        </form>
    </c:when>
    <c:otherwise>
        <h2>Không tìm thấy</h2>
    </c:otherwise>
</c:choose>

<p><a href="${pageContext.request.contextPath}/products">Quay lại</a></p>
</body>
</html>
