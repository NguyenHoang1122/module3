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
    <title>Quản lý đơn hàng</title>
</head>
<body>
<h2>Đơn hàng</h2>
<table border="1">
    <tr>
        <th>ID</th><th>UserID</th><th>Tổng tiền</th><th>Trạng thái</th><th>Ngày tạo</th><th>Action</th>
    </tr>
    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>
            <td>${o.userId}</td>
            <td>${o.totalAmount}</td>
            <td>${o.status}</td>
            <td>${o.createdAt}</td>
            <td>
                <c:if test="${o.status != 'cancelled'}">
                    <form method="post" action="${pageContext.request.contextPath}/admin/orders/updateStatus" style="display:inline">
                        <input type="hidden" name="orderId" value="${o.id}"/>
                        <input type="hidden" name="status" value="cancelled"/>
                        <button type="submit">Hủy</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
