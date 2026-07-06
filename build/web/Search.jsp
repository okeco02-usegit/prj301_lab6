<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Users</title>
</head>
<body>
    <c:if test="${empty sessionScope.USER_LOGGED_IN}">
        <c:redirect url="Login.jsp"/>
    </c:if>

    <h2>Welcome, <c:out value="${sessionScope.USER_LOGGED_IN.lastName}"/></h2>

    <a href="UserController?action=Search">Search</a>

    <c:if test="${sessionScope.USER_LOGGED_IN.admin}">
        |
        <a href="UserController?action=CreatePage">Create User</a>
    </c:if>

    |
    <a href="UserController?action=Logout">Logout</a>

    <hr>

    <c:if test="${not empty sessionScope.FLASH_MESSAGE}">
        <p style="color:green">
            <c:out value="${sessionScope.FLASH_MESSAGE}"/>
        </p>
        <c:remove var="FLASH_MESSAGE" scope="session"/>
    </c:if>

    <c:if test="${not empty message}">
        <p style="color:green"><c:out value="${message}"/></p>
    </c:if>

    <h3>Search user by last name</h3>

    <form action="UserController" method="get">
        <input type="hidden" name="action" value="Search">
        Search value:
        <input type="text" name="searchValue"
               value="<c:out value='${searchValue}'/>">
        <input type="submit" value="Search">
    </form>

    <br>

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>No.</th>
            <th>UserName</th>
            <th>Password</th>
            <th>LastName</th>
            <th>Role</th>
            <c:if test="${sessionScope.USER_LOGGED_IN.admin}">
                <th>Action</th>
            </c:if>
        </tr>

        <c:forEach var="user" items="${SEARCH_RESULT}" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td><c:out value="${user.userName}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${user.admin}">Admin</c:when>
                        <c:otherwise>User</c:otherwise>
                    </c:choose>
                </td>

                <c:if test="${sessionScope.USER_LOGGED_IN.admin}">
                    <td>
                        <c:url var="detailUrl" value="UserController">
                            <c:param name="action" value="Details"/>
                            <c:param name="userName" value="${user.userName}"/>
                            <c:param name="searchValue" value="${searchValue}"/>
                        </c:url>

                        <a href="${detailUrl}">Details</a>

                        <c:if test="${user.userName != sessionScope.USER_LOGGED_IN.userName}">
                            |
                            <form action="UserController"
                                  method="post"
                                  style="display:inline"
                                  onsubmit="return confirm('Delete this user?');">
                                <input type="hidden" name="action" value="Delete">
                                <input type="hidden" name="userName"
                                       value="${user.userName}">
                                <input type="hidden" name="searchValue"
                                       value="${searchValue}">
                                <input type="submit" value="Delete">
                            </form>
                        </c:if>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>

    <p>Number of users: <c:out value="${SEARCH_RESULT.size()}"/></p>
</body>
</html>
