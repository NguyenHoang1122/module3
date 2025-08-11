<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 31/07/2025
  Time: 2:16 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f7f7f7;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 70%;
            margin: 20px 0;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }
        th, td {
            border: 1px solid #e0e0e0;
            padding: 10px 15px;
            text-align: left;
        }
        th {
            background: #f0f0f0;
            color: #444;
        }
        tr:nth-child(even) {
            background: #fafafa;
        }
        a, button {
            background: #1976d2;
            color: #fff;
            padding: 6px 14px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            transition: background 0.2s;
            margin-right: 5px;
        }
        a:hover, button:hover {
            background: #1565c0;
        }
        input[type="text"], select {
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 3px;
            width: 95%;
        }
        input[type="radio"], input[type="checkbox"] {
            margin-right: 5px;
        }
        .width-3 {
            width: 3%;
        }
        .width-5 {
            width: 5%;
        }
        .width-6 {
            width: 6%;
        }
        .width-15 {
            width: 15%;
        }
        .width-20 {
            width: 20%;
        }
        .margin-top{
            margin-top: 5px;
        }
    </style>
</head>
<body>
<h2>Student list</h2>
<form action="/students/search" method="get">
    <input type="text" name="keyword">
    <button class="margin-top" type="submit">Search</button>
</form>
<a href="/students/create">Create</a>
<a href="/students/subjects">Show Subject</a>
<table>
    <tr>
        <th class="width-3">STT</th>
        <th class="width-20">Name</th>
        <th class="width-5">Gender</th>
        <th class="width-20">Email</th>
        <th class="width-15">Phone</th>
        <th class="width-5">Group</th>
        <th class="width-20">Subject</th>
        <th class="width-6">Delete</th>
        <th class="width-6">Edit</th>
    </tr>
    <c:forEach var="student" items="${requestScope.listStudent}">
        <tr>
            <td>1</td>
            <td><c:out value="${student.name}"/></td>
            <c:if test="${student.gender == 1}">
                <td><c:out value="Male"/></td>
            </c:if>
            <c:if test="${student.gender == 2}">
                <td><c:out value="Famale"/></td>
            </c:if>
            <td><c:out value="${student.email}"/></td>
            <td><c:out value="${student.phone}"/></td>
            <td><c:out value="${student.getGroup().getName()}"/></td>
            <td><c:out value="${student.getSubject().getName()}"/></td>
            <td>
                <a href="/students/delete?id=<c:out value="${student.id}"/>">Delete</a>
            </td>
            <td>
                <a href="/students/edit?id=<c:out value="${student.id}"/>">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>