import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Initialization code if needed
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        boolean loginRequest = path.equals("/login.jsp") || path.equals ("/auth/login");
        boolean registerRequest = path.equals("/register.jsp") || path.equals ("/auth/register");
        boolean resourceRequest = path.startsWith("/css") || path.startsWith("/js/");

        if (loggedIn && (loginRequest || registerRequest)) {
            httpResponse.sendRedirect("profile");
            return;

        }else if (!loggedIn && !loginRequest && !registerRequest && !resourceRequest && !path.equals("/")
                    && !path.equals("/index.jsp")) {
            httpResponse.sendRedirect("login.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //Cleanup code if needed
    }
}
