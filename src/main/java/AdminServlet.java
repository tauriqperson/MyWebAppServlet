import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*  Admin credentials:
Username = admin
Password/email = test1234@email.com
*/

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminServlet.class);
    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            userService = new UserService(connection);
        } catch (SQLException e) {
            throw new ServletException("Database Connection Failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Unauthorized access attempt to admin page");
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        
        //Role-based authorization
        if (!"ADMIN".equals(user.getRole())) {
            logger.warn("Non-admin user attempted to access admin page: {}", user.getUsername());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Admin privileges required");
            return;
        }

        logger.info("Admin page accessed by: {}", user.getUsername());
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
    }
}
