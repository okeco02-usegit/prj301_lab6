<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>

    <c:if test="${not empty message}">
        <p style="color:red"><c:out value="${message}"/></p>
    </c:if>

    <form action="LoginController" method="post">
        Username:
        <input type="text" name="userName">
        <br><br>

        Password:
        <input type="password" name="password">
        <br><br>

        <input type="submit" value="Login">
        <input type="reset" value="Reset">
    </form>

    <p>Demo: admin / admin123</p>
</body>
</html>
