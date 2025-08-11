<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 11/08/2025
  Time: 10:48 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Product</title>
</head>
<body>
<h2>Edit Product</h2>
<form action="/products/update" method="post">
    <input type="hidden" name="id" value="${product.id}"/>

    <label>Name:</label><br/>
    <input type="text" name="name" value="${product.name}" required/><br/>

    <label>Price:</label><br/>
    <input type="number" step="0.01" name="price" value="${product.price}" required/><br/>

    <label>Quantity:</label><br/>
    <input type="number" name="quantity" value="${product.quantity}" required/><br/>

    <label>Color:</label><br/>
    <input type="text" name="color" value="${product.color}" required/><br/>

    <label>Category ID:</label><br/>
    <input type="number" name="c_id" value="${product.categoryName}" required/><br/>

    <button type="submit">Update Product</button>
</form>
<a href="/products">Back to list</a>
</body>
</html>
