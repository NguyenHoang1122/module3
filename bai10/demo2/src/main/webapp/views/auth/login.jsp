<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 28/07/2025
  Time: 2:58 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    int a = 10;

    String pageTitle = " Login form";
    String error = request.getParameter("error");
    String mesError = error != null ? " Tai khoan hoac mat khau sai" : "" ;
%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="/auth/login">
    <h1><%= pageTitle%></h1>
    <%= mesError %>
    <label>Username:
        <input type="text" name="username" placeholder="Nhập username" />
    </label><br>
    <label>Password:
        <input type="password" name="password" placeholder="Nhập password" />
    </label><br>
    <input type="submit" value="Login" />
</form>

</body>
</html>