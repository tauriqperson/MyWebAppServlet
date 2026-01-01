<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Admin Panel</h2>
        
        <c:if test="${not empty user}">
            <div class="user-info">
                <p><strong>Welcome:</strong> <c:out value="${user.username}"/></p>
                <p><strong>Role:</strong> <c:out value="${user.role}"/></p>
                <p><strong>Email:</strong> <c:out value="${user.email}"/></p>
            </div>
            
            <div class="user-info">
                <h3 style="margin-top: 0; color: #333;">Admin Functions</h3>
                <p style="margin: 5px 0;">• Manage Users</p>
                <p style="margin: 5px 0;">• View System Logs</p>
                <p style="margin: 5px 0;">• System Configuration</p>
            </div>
            
            <div class="actions" style="display: flex; gap: 10px; justify-content: center;">
                <a href="${pageContext.request.contextPath}/profile" style="background-color: #007bff; color: white; border-radius: 4px; padding: 10px 20px; text-decoration: none;">Back to Profile</a>
                <a href="${pageContext.request.contextPath}/auth/logout" class="logout-btn">Logout</a>
            </div>
        </c:if>
    </div>
</body>
</html>
