<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 03/08/2025
  Time: 10:57 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Subject List</title>
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
            width: 30%;
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
        .width-15 {
            width: 15%;
        }
        .width-55 {
            width: 55%;
        }
    </style>
</head>
<body>
<h2>Subject List</h2>
<a href="/students/create-subject">Add New Subject</a>
<table>
    <tr>
        <th class="width-15">STT</th>
        <th class="width-55">Name</th>
        <th class="width-15">Delete</th>
        <th class="width-15">Edit</th>
    </tr>
    <c:forEach var="subject" items="${requestScope.listSubject}">
        <tr>
            <td>1</td>
            <td><c:out value="${subject.name}"/></td>
            <td>
                <a href="/students/delete-subject?id=<c:out value="${subject.id}"/>">Delete</a>
            </td>
            <td>
                <a href="/students/edit-subject?id=<c:out value="${subject.id}"/>">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/students">Back To List Students</a>
</body>
</html>
