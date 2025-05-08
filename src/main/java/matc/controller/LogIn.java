package matc.controller;

import matc.util.PropertiesLoader;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * LogIn Servlet - Begins the authentication process using AWS Cognito.
 */
@WebServlet(
        urlPatterns = {"/logIn"}
)
public class LogIn extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static String CLIENT_ID;
    public static String LOGIN_URL;
    public static String REDIRECT_URL;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();

        CLIENT_ID = (String) context.getAttribute("CLIENT_ID");
        LOGIN_URL = (String) context.getAttribute("LOGIN_URL");
        REDIRECT_URL = (String) context.getAttribute("REDIRECT_URL");
    }

    /**
     * Route to the AWS-hosted Cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during redirection
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CLIENT_ID == null || LOGIN_URL == null || REDIRECT_URL == null ||
                CLIENT_ID.isEmpty() || LOGIN_URL.isEmpty() || REDIRECT_URL.isEmpty()) {

            logger.error("Missing required properties for login. Ensure CLIENT_ID, LOGIN_URL, and REDIRECT_URL are set.");

            req.setAttribute("errorMessage", "Configuration error: Missing authentication properties.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("error.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
        resp.sendRedirect(url);
    }
}