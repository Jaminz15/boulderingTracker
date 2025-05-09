package matc.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.logging.log4j.*;
import java.io.IOException;

/**
 * Logout servlet to handle user logout and session invalidation.
 * Redirects the user to the AWS Cognito logout endpoint after clearing the session.
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Logout.class);

    /**
     * Handles GET requests to log out the user.
     * Invalidates the user session and redirects to the AWS Cognito logout URL.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("User logged out");
        // Invalidate session
        req.getSession().invalidate();

        // Redirect to Cognito logout endpoint
        String logoutUrl = "https://us-east-2i2vlnbseg.auth.us-east-2.amazoncognito.com/logout"
                + "?client_id=9t6nbohkhkplr0gjstbfdvrut"
                + "&logout_uri=http://localhost:8080/boulderingTracker_war/";

        resp.sendRedirect(logoutUrl);
    }
}