package matc.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.logging.log4j.*;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Logout.class);

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