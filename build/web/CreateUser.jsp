<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create User</title>
</head>
<body>
    <c:if test="${empty sessionScope.USER_LOGGED_IN}">
        <c:redirect url="Login.jsp"/>
    </c:if>

    <h2>Create User</h2>

    <a href="UserController?action=Search">Back to Search</a>
    |
    <a href="UserController?action=Logout">Logout</a>

    <hr>

    <c:if test="${not empty message}">
        <p style="color:green"><c:out value="${message}"/></p>
    </c:if>

    <form action="UserController" method="post">
        <input type="hidden" name="action" value="Create">

        UserName:
        <input type="text" name="userName"
               value="<c:out value='${userName}'/>">
        <span style="color:red">
            <c:out value="${ERROR_DETAIL.userNameError}"/>
            <c:out value="${ERROR_DETAIL.duplicateUserName}"/>
        </span>
        <br><br>

        Password:
        <input type="password" name="password">
        <span style="color:red">
            <c:out value="${ERROR_DETAIL.passwordError}"/>
        </span>
        <br><br>

        LastName:
        <input type="text" name="lastName"
               value="<c:out value='${lastName}'/>">
        <span style="color:red">
            <c:out value="${ERROR_DETAIL.lastNameError}"/>
        </span>
        <br><br>

        IsAdmin:
        <input type="checkbox" name="admin"
               <c:if test="${admin}">checked</c:if>>
        <br><br>

        <input type="submit" value="Create">
        <input type="reset" value="Reset">
    </form>
</body>
</html>
