<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 31/07/2025
  Time: 4:00 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Student</title>
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
            width: 600px;
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
    </style>
</head>
<body>
<h2>Edit Student</h2>
<form action="/students/edit" method="post">
    <input type="hidden" name="id" value="${studentEdit.id}" />
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name" value="${studentEdit.name}" required /></td>
        </tr>
        <tr>
            <td>Gender:</td>
            <td>
                <input type="radio" name="gender" value="1" <c:if test="${studentEdit.gender == 1}">checked</c:if> /> Male
                <input type="radio" name="gender" value="2" <c:if test="${studentEdit.gender == 2}">checked</c:if> /> Female
            </td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="text" name="email" value="${studentEdit.email}" required /></td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td><input type="text" name="phone" value="${studentEdit.phone}" required /></td>
        </tr>
        <tr>
            <td>Subjects</td>
            <td>
                <c:forEach var="subject" items="${listSubject}">
                    <label>
                        <input type="checkbox" name="subject_ids"
                               value="${subject.id}"
                        <c:forEach var="s" items="${studentEdit.subjects}">
                               <c:if test="${s.id == subject.id}">checked</c:if>
                        </c:forEach>
                        >
                            ${subject.name}
                    </label><br/>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td>Group:</td>
            <td>
                <select name="group_id">
                    <c:forEach var="group" items="${listGroup}">
                        <option value="${group.id}" <c:if test="${group.id == studentEdit.group.id}">selected</c:if>>
                                ${group.name}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><button type="submit">Update</button></td>
        </tr>
    </table>
</form>
<a href="/students">Back to list</a>
</body>
</html>
