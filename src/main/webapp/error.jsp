<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - MyWebApp</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Oops! Something went wrong</h1>
        <div class="error-message">
            <p>We're sorry, but an error has occurred.</p>
            <%
                if (response.getStatus() == 403) {
            %>
                <p><strong>Access Denied:</strong> You don't have permission to access this resource.</p>
            <%
                } else if (response.getStatus() == 404) {
            %>
                <p><strong>Page Not Found:</strong> The page you're looking for doesn't exist.</p>
            <%
                } else {
            %>
                <p><strong>Error:</strong> An unexpected error has occurred. Please try again later.</p>
            <%
                }
            %>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/profile">Go to Profile</a>
            <a href="${pageContext.request.contextPath}/login.jsp">Login</a>
        </div>
    </div>
</body>
</html>
