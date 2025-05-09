package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * EditClimb servlet to handle editing an existing climb.
 * Validates user permissions and retrieves the climb data for editing.
 * Redirects to the login page if the user is not authenticated.
 */
@WebServlet("/editClimb")
public class EditClimb extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EditClimb.class);

    /**
     * Handles GET requests to display the edit climb page.
     * Retrieves the climb data based on the climb ID provided in the request.
     * Validates user permissions and redirects if unauthorized.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Check if the user is logged in and redirect to login page if not
        if (user == null) {
            logger.error("No user in session — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        // Fetch the climb ID from the request and validate it
        String climbIdParam = req.getParameter("climbId");

        if (climbIdParam == null) {
            resp.sendRedirect("dashboard?error=missingClimbId");
            return;
        }

        int climbId = Integer.parseInt(climbIdParam);
        logger.debug("EditClimb requested for Climb ID: {}", climbId);

        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

        Climb climb = climbDao.getById(climbId);

        if (climb == null) {
            logger.warn("Climb not found — ID: {}", climbId);
            resp.sendRedirect("dashboard?error=climbNotFound");
            return;
        }

        // Check if the user has permission to edit the climb
        if (!user.isAdmin() && climb.getUser().getId() != user.getId()) {
            logger.warn("Unauthorized access attempt by user {} for climb ID {}", user.getUsername(), climbId);
            resp.sendRedirect("dashboard?error=unauthorized");
            return;
        }

        List<Gym> gyms = gymDao.getAll();

        // Set climb and gym data as request attributes for the edit page
        req.setAttribute("climb", climb);
        req.setAttribute("gyms", gyms);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/editClimb.jsp");
        dispatcher.forward(req, resp);
    }
}