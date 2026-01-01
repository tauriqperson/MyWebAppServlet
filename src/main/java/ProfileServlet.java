import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/profile")

    public class ProfileServlet extends HttpServlet {
    private UserService userService;

    @Override

    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            userService = new UserService(connection);
        }catch (SQLException e) {
            throw new ServletException("Database Connection Failed", e);
        }
    }

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession (false);
        if (session == null || session.getAttribute("user") == null){
            response.sendRedirect("login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String email = request.getParameter("email");

        //Input Validation
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            request.setAttribute("error", "Valid email is required");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
            return;
        }

        try {
            User updatedUser = userService.updateUserProfile(currentUser.getId(), email);

            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
                request.setAttribute("success", "Profile updated successfully!");
            }else {
                request.setAttribute("error", "Failed to update Profile!");
            }
            request.setAttribute("user", updatedUser != null ? updatedUser : currentUser);
        }catch (SQLException e) {
            request.setAttribute("error", "Database error occurred");
            request.setAttribute("user", currentUser);
        }

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
