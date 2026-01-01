<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="/webapp/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Register</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form action="auth/register" method="post">
            <div>
                <label>Username:</label>
                <input type="text" name="username" required>
            </div>
            <div>
                <label>Email:</label>
                <input type="email" name="email" required>
            </div>
            <div>
                <label>Password:</label>
                <input type="password" name="password" required minlength="6">
            </div>
            <div>
                <button type="submit">Register</button>
            </div>
        </form>
        <p>Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
</body>
</html>