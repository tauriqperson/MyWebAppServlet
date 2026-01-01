<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Admin Panel</h1>
        <%
            User user = (User) request.getAttribute("user");
            if (user != null) {
        %>
            <div class="admin-info">
                <h2>Welcome, <%= user.getUsername() %></h2>
                <p><strong>Role:</strong> <%= user.getRole() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
            </div>
            
            <div class="admin-actions">
                <h3>Admin Functions</h3>
                <ul>
                    <li>Manage Users</li>
                    <li>View System Logs</li>
                    <li>System Configuration</li>
                </ul>
            </div>
            
            <div class="navigation">
                <a href="${pageContext.request.contextPath}/profile">Back to Profile</a>
                <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </div>
        <%
            }
        %>
    </div>
</body>
</html>
