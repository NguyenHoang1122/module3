<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 30/07/2025
  Time: 9:24 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Student</title>
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