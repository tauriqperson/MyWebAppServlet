<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/style.css">
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
            <p><strong>Username:</strong> ${user.username}</p>
            <p><strong>Role:</strong> ${user.role}</p>
        </div>

        <form action="profile" method="post">
            <div>
                <label>Email:</label>
                <input type="email" name="email" value="${user.email}" required>
            </div>
            <div>
                <button type="submit">Update Profile</button>
            </div>
        </form>

        <div class="actions">
            <a href="auth/logout">Logout</a>
        </div>
    </div>
</body>
</html>>