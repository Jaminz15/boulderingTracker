package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * GymPage servlet to display climbing logs for a specific gym.
 * Handles retrieval of climb data based on the selected gym and user permissions.
 * Admin users can view all climbs for a gym, while regular users see only their own.
 */
@WebServlet("/gymPage")
public class GymPage extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymPage.class);

    /**
     * Handles GET requests to display the climbing logs for a specific gym.
     * Retrieves climbs for the selected gym, filtered by user role (admin or regular).
     * Redirects to login if the user is not authenticated.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gymIdParam = req.getParameter("gymId");

        // Validate the gym ID parameter from the request
        if (gymIdParam == null) {
            logger.warn("GymPage accessed with no gymId parameter");
            resp.sendRedirect("index.jsp?error=noGymSelected");
            return;
        }

        int gymId = Integer.parseInt(gymIdParam);

        // DAOs
        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

        // Get gym by ID
        Gym selectedGym = gymDao.getById(gymId);

        if (selectedGym == null) {
            logger.warn("GymPage: No gym found with ID {}", gymId);
            resp.sendRedirect("index.jsp?error=invalidGym");
            return;
        }

        // Get logged-in user from session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Check if the user is logged in; if not, redirect to login page
        if (user == null) {
            logger.error("No user in session — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        boolean isAdmin = user.isAdmin();

        List<Climb> climbs;

        // Fetch climbs based on user role (admin sees all, regular user sees only their own)
        if (isAdmin) {
            // Admin sees all climbs for the selected gym
            Map<String, Object> gymOnly = new HashMap<>();
            gymOnly.put("gym", selectedGym);
            climbs = climbDao.findByPropertyEqual(gymOnly);
        } else {
            Map<String, Object> filters = new HashMap<>();
            filters.put("user", user);
            filters.put("gym", selectedGym);
            climbs = climbDao.findByPropertyEqual(filters);
        }

        logger.debug("GymPage: Retrieved {} climbs for user {} at gym ID {}", climbs.size(), user.getUsername(), gymId);

        // Pass the selected gym and climb list to the JSP for rendering
        req.setAttribute("gym", selectedGym);
        req.setAttribute("climbs", climbs);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/gymPage.jsp");
        dispatcher.forward(req, resp);
    }
}