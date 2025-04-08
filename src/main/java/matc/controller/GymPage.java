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
 * GymPage - Displays climbing logs for a specific gym.
 */
@WebServlet("/gymPage")
public class GymPage extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymPage.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gymIdParam = req.getParameter("gymId");

        if (gymIdParam == null) {
            resp.sendRedirect("index.jsp?error=noGymSelected");
            return;
        }

        int gymId = Integer.parseInt(gymIdParam);

        // DAOs
        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

        // Get gym by ID
        Gym selectedGym = gymDao.getById(gymId);

        // Get logged-in user from session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.error("No user in session — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        boolean isAdmin = user.isAdmin();

        List<Climb> climbs;

        if (isAdmin) {
            // Admin sees all climbs for the selected gym
            Map<String, Object> gymOnly = new HashMap<>();
            gymOnly.put("gym", selectedGym);
            climbs = climbDao.findByPropertyEqual(gymOnly);
        } else {
            // Regular user sees only their climbs
            Map<String, Object> filters = new HashMap<>();
            filters.put("user", user);
            filters.put("gym", selectedGym);
            climbs = climbDao.findByPropertyEqual(filters);
        }

        logger.debug("GymPage: Retrieved {} climbs for user {} at gym ID {}", climbs.size(), user.getUsername(), gymId);

        req.setAttribute("gym", selectedGym);
        req.setAttribute("climbs", climbs);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/gymPage.jsp");
        dispatcher.forward(req, resp);
    }
}