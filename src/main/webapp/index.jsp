<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Welcome to MyWebApp</h2>
        <div class="actions">
            <p><a href="${pageContext.request.contextPath}/login.jsp">Login</a></p>
            <p><a href="${pageContext.request.contextPath}/register.jsp">Register</a></p>
        </div>
    </div>
</body>
</html>
