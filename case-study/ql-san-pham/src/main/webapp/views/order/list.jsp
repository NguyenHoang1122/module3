<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Đơn hàng của bạn</title>
</head>
<body>
<h2>Đơn hàng của bạn</h2>
<table border="1">
    <tr>
        <th>ID</th><th>Tổng tiền</th><th>Trạng thái</th><th>Ngày tạo</th><th>Action</th>
    </tr>
    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>
            <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/></td>
            <td>
                <c:choose>
                    <c:when test="${o.status == 'pending'}">Đang xử lý</c:when>
                    <c:when test="${o.status == 'completed'}">Hoàn tất</c:when>
                    <c:when test="${o.status == 'cancelled'}">Đã hủy</c:when>
                    <c:otherwise>${o.status}</c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td>
                <c:if test="${o.status != 'cancelled'}">
                    <form method="post" action="${pageContext.request.contextPath}/orders/cancel" style="display:inline" onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này?');">
                        <input type="hidden" name="orderId" value="${o.id}"/>
                        <button type="submit">Hủy</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="${pageContext.request.contextPath}/products">⬅ Quay lại mua hàng</a></p>
</body>
</html>
