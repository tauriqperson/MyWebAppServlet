import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

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
}
