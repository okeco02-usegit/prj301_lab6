<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
</head>
<body>
    <c:if test="${empty sessionScope.USER_LOGGED_IN}">
        <c:redirect url="Login.jsp"/>
    </c:if>

    <h2>User Details</h2>

    <c:url var="backUrl" value="UserController">
        <c:param name="action" value="Search"/>
        <c:param name="searchValue" value="${searchValue}"/>
    </c:url>

    <a href="${backUrl}">Back to Search</a>
    |
    <a href="UserController?action=Logout">Logout</a>

    <hr>

    <c:if test="${not empty message}">
        <p style="color:green"><c:out value="${message}"/></p>
    </c:if>

    <form action="UserController" method="post">
        <input type="hidden" name="action" value="Update">
        <input type="hidden" name="userName" value="${USER_DETAIL.userName}">
        <input type="hidden" name="searchValue" value="${searchValue}">

        UserName:
        <input type="text" value="<c:out value='${USER_DETAIL.userName}'/>" readonly>
        <br><br>

        Password:
        <input type="text" name="password"
               value="<c:out value='${USER_DETAIL.password}'/>">
        <span style="color:red">
            <c:out value="${ERROR_DETAIL.passwordError}"/>
        </span>
        <br><br>

        LastName:
        <input type="text" name="lastName"
               value="<c:out value='${USER_DETAIL.lastName}'/>">
        <span style="color:red">
            <c:out value="${ERROR_DETAIL.lastNameError}"/>
        </span>
        <br><br>

        IsAdmin:
        <input type="checkbox" name="admin"
               <c:if test="${USER_DETAIL.admin}">checked</c:if>>
        <br><br>

        <input type="submit" value="Update">
    </form>
</body>
</html>
