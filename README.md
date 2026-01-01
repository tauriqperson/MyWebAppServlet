# MyWebAppServlet - Secure Java Web Application

A secure web application built with Java Servlets, JSP, and MySQL, featuring user authentication, authorization, and profile management.

## Features

### 1. Functionality
- User registration and login
- Profile viewing and updating
- Role-based access control (Admin vs User)
- Logout functionality

### 2. Security
- **Password Hashing**: BCrypt algorithm for secure password storage
- **Data Encryption**: AES encryption for sensitive user data (email addresses)
- **Authentication**: Session-based authentication with AuthFilter
- **Authorization**: Role-based access control for admin features
- **Input Validation**: Server-side validation for all user inputs

### 3. Performance & Monitoring
- **Connection Pooling**: HikariCP for efficient database connections
- **Logging**: SLF4J with Logback for comprehensive event logging
  - User login/logout events
  - Failed authentication attempts
  - Error tracking
  - Admin access monitoring
- **Error Handling**: Graceful error pages for 403, 404, and 500 errors
- **Session Management**: 30-minute session timeout

## Technologies Used

- **Backend**: Java 21, Servlets, JSP
- **Database**: MySQL
- **Build Tool**: Maven
- **Security**: BCrypt (password hashing), AES (data encryption)
- **Connection Pooling**: HikariCP
- **Logging**: SLF4J + Logback
- **Server**: Apache Tomcat

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- Apache Tomcat 9 or 10

## Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE webapp;
USE webapp;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
```

2. Update database credentials in `DatabaseConnection.java` if needed:
```java
private static final String URL = "jdbc:mysql://localhost:3306/webapp";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

## Building the Application

### Package as WAR file
```bash
mvn clean package
```

The WAR file will be generated at: `target/MyWebAppServlet.war`

### Run locally with Maven
```bash
mvn tomcat7:run
```

Access the application at: `http://localhost:8081/MyWebAppServlet`

## Deployment

### Deploy to Tomcat
1. Copy the WAR file to Tomcat's webapps directory:
```bash
cp target/MyWebAppServlet.war $TOMCAT_HOME/webapps/
```

2. Start Tomcat:
```bash
$TOMCAT_HOME/bin/startup.sh
```

3. Access at: `http://localhost:8080/MyWebAppServlet`

### Deploy to Cloud (Render)
1. Ensure your `pom.xml` has proper packaging configuration
2. Create a `render.yaml` configuration file
3. Push to GitHub
4. Connect Render to your GitHub repository
5. Render will automatically build and deploy your application

## Project Structure

```
MyWebAppServlet/
├── src/
│   └── main/
│       ├── Java/
│       │   ├── AdminServlet.java       # Admin panel controller
│       │   ├── AuthServlet.java        # Authentication controller
│       │   ├── AuthFilter.java         # Authentication filter
│       │   ├── DatabaseConnection.java # Connection pool manager
│       │   ├── EncryptionUtil.java     # Data encryption utility
│       │   ├── ProfileServlet.java     # Profile management
│       │   ├── User.java               # User model
│       │   ├── UserDAO.java            # Database operations
│       │   └── UserService.java        # Business logic
│       ├── resources/
│       │   └── logback.xml             # Logging configuration
│       └── webapp/
│           ├── css/
│           │   └── style.css
│           ├── WEB-INF/
│           │   ├── web.xml             # Deployment descriptor
│           │   └── views/
│           │       ├── admin.jsp       # Admin panel page
│           │       └── profile.jsp     # User profile page
│           ├── error.jsp               # Error page
│           ├── index.jsp               # Landing page
│           ├── login.jsp               # Login page
│           └── register.jsp            # Registration page
├── pom.xml
└── README.md
```

## Usage

### User Registration
1. Navigate to `/register.jsp`
2. Fill in username, email, and password (min 8 characters)
3. Submit to create account

### Login
1. Navigate to `/login.jsp`
2. Enter credentials
3. Redirected to profile page upon success

### Profile Management
- View username, email, and role
- Update email address
- Admins can access admin panel

### Admin Access
- Only users with ADMIN role can access `/admin`
- View admin panel with system management options

## Security Features

### OWASP Security Implementations
1. **A02:2021 – Cryptographic Failures**
   - BCrypt for password hashing
   - AES encryption for sensitive data

2. **A07:2021 – Identification and Authentication Failures**
   - Secure session management
   - Password strength requirements
   - Login attempt logging

3. **A03:2021 – Injection**
   - Prepared statements for all database queries
   - Input validation and sanitization

4. **A01:2021 – Broken Access Control**
   - Role-based authorization
   - AuthFilter for protected resources

## Logging

Logs are stored in `logs/webapp.log` with the following events:
- User registration
- Login attempts (successful and failed)
- Logout events
- Admin access
- Database errors
- Encryption/decryption errors

## Performance Optimizations

- HikariCP connection pool (max 10 connections)
- Efficient database query patterns
- Prepared statement caching
- Session management optimization

## License

This project is for educational purposes.

## Author


