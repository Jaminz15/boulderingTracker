package matc.controller;

import matc.entity.Climb;
import matc.entity.User;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet to display climb data based on logged-in user.
 */
@WebServlet("/searchClimbs")
public class ClimbServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ClimbServlet.class);
    private GenericDao<Climb> climbDao;
    private GenericDao<User> userDao;

    @Override
    public void init() throws ServletException {
        climbDao = new GenericDao<>(Climb.class);
        userDao = new GenericDao<>(User.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Get user claims from session
        Map<String, Object> userClaims = (Map<String, Object>) session.getAttribute("userClaims");
        String cognitoSub = userClaims != null && userClaims.get("sub") != null
                ? userClaims.get("sub").toString().replaceAll("\"", "")
                : null;
        String userRole = (String) session.getAttribute("userRole");

        if (userRole == null) {
            userRole = "User"; // Default role if not set
        }

        List<Climb> climbs;

        if ("Admin".equals(userRole)) {
            climbs = climbDao.getAll(); // Admin sees all climbs
        } else {
            // Find the logged-in user by Cognito Sub
            List<User> users = userDao.findByPropertyEqual("cognitoSub", cognitoSub);
            if (users.isEmpty()) {
                logger.error("No user found with Cognito Sub: {}", cognitoSub);
                resp.sendRedirect("climbResults.jsp?error=userNotFound");
                return;
            }
            int userId = users.get(0).getId();

            // Fetch only the climbs for this user
            climbs = climbDao.findByPropertyEqual("user.id", userId);
        }

        logger.debug("User role: {} - Retrieved {} climbs from database", userRole, climbs.size());

        req.setAttribute("climbs", climbs);

        // Forward to results page
        RequestDispatcher dispatcher = req.getRequestDispatcher("/climbResults.jsp");
        dispatcher.forward(req, resp);
    }
}