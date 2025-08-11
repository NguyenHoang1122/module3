<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 10/08/2025
  Time: 10:25 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entities.Product" %>
<html>
<head>
    <title>Thêm/Sửa sản phẩm</title>
</head>
<body>
    <%
    Product p = (Product) request.getAttribute("product");
    boolean isEdit = p != null;
%>
<h2><%= isEdit ? "Sửa sản phẩm" : "Thêm sản phẩm" %></h2>
<form method="post" action
