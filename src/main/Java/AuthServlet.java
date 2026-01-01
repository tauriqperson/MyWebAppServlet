import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.*;



@WebServlet("/auth/*")

public class AuthServlet extends HttpServlet {

    private UserService userService;

    @Override

    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            userService = new UserService(connection);
        }catch (SQLException e){
            throw new ServletException("Database connection failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action == null ? "" : action) {
            case "/logout":
                logout(request, response);
                break;
            default:
                response.sendRedirect("../login.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getPathInfo();

        switch (action == null ? "" : action) {
            case "/register":
                register(request, response);
                break;
            case "/login":
                login(request,response);
                break;
            default:
                response.sendRedirect("../login.jsp");
                break;
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //OWASP Condition: Input validation

        String validationError = userService.validateUserInput(username, password, email);
        if (validationError != null){
            request.setAttribute("error", validationError);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            User user = userService.registerUser(username, password, email);
            request.setAttribute("success", "Registration successfull! Please login.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        }catch (Exception e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.loginUser(username, password);
            if(user != null) {

                //Session management = OWASP broken authentication
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30 * 60);
                response.sendRedirect("../profile");

            }else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }catch (SQLException e) {
            request.setAttribute("error", "Database error occurred");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("../login.jsp");
    }
}
