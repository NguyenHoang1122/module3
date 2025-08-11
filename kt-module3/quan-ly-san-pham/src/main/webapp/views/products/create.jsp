<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 11/08/2025
  Time: 10:15 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Product</title>
</head>
<body>
<h2>Add new Product</h2>
<form action="/products/store" method="post">
    <label>Name:</label><br/>
    <input type="text" name="name" required/><br/>

    <label>Price:</label><br/>
    <input type="number" step="0.01" name="price" required/><br/>

    <label>Quantity:</label><br/>
    <input type="number" name="quantity" required/><br/>

    <label>Color:</label><br/>
    <input type="text" name="color" required/><br/>

    <label>Category ID:</label><br/>
    <input type="number" name="c_id" required/><br/>

    <button type="submit">Add Product</button>
</form>
<a href="/products">Back to List</a>
</body>
</html>
