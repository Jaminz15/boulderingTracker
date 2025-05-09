package matc.controller;

import matc.util.PropertiesLoader;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * LogIn servlet to initiate the authentication process via AWS Cognito.
 * Redirects users to the AWS-hosted Cognito login page.
 * Ensures that required configuration properties are loaded.
 */
@WebServlet(
        urlPatterns = {"/logIn"}
)
public class LogIn extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static String CLIENT_ID;
    public static String LOGIN_URL;
    public static String REDIRECT_URL;

    /**
     * Initializes the servlet by loading client configuration properties.
     * Retrieves CLIENT_ID, LOGIN_URL, and REDIRECT_URL from the application context.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();

        CLIENT_ID = (String) context.getAttribute("CLIENT_ID");
        LOGIN_URL = (String) context.getAttribute("LOGIN_URL");
        REDIRECT_URL = (String) context.getAttribute("REDIRECT_URL");
    }

    /**
     * Handles GET requests to redirect users to the AWS Cognito login page.
     * Verifies that required configuration properties are available.
     * Redirects to the error page if properties are missing.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check for missing authentication properties before redirecting
        if (CLIENT_ID == null || LOGIN_URL == null || REDIRECT_URL == null ||
                CLIENT_ID.isEmpty() || LOGIN_URL.isEmpty() || REDIRECT_URL.isEmpty()) {

            logger.error("Missing required properties for login. Ensure CLIENT_ID, LOGIN_URL, and REDIRECT_URL are set.");

            req.setAttribute("errorMessage", "Configuration error: Missing authentication properties.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("error.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // Construct the AWS Cognito login URL and redirect the user
        String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
        resp.sendRedirect(url);
    }
}