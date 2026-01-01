<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>User Profile</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>

        <div class="user-info">
            <p><strong>Username:</strong> <c:out value="${user.username}"/></p>
            <p><strong>Email:</strong> <c:out value="${user.email}"/></p>
            <p><strong>Role:</strong> <c:out value="${user.role}"/></p>
        </div>

        <form action="${pageContext.request.contextPath}/profile" method="post">
            <div>
                <label>Email:</label>
                <input type="email" name="email" value="<c:out value='${user.email}' />" required>
            </div>
            <div>
                <button type="submit">Update Profile</button>
            </div>
        </form>

        <div class="actions">
            <c:if test="${user.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin" class="admin-btn">Admin Panel</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/auth/logout" class="logout-btn">Logout</a>
        </div>
    </div>
</body>
</html>