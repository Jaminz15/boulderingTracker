package matc.controller;

import org.apache.logging.log4j.*;
import matc.entity.*;
import matc.persistence.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Gym Management Controller - Handles listing, adding, and deleting gyms.
 */
@WebServlet("/gymManagement")
public class GymManagement extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymManagement.class);
    private GenericDao<Gym> gymDao;

    /**
     * Initializes the DAO for database access.
     */
    @Override
    public void init() throws ServletException {
        gymDao = new GenericDao<>(Gym.class);

        // Load all gyms once and store them in application scope
        List<Gym> gyms = gymDao.getAll();
        getServletContext().setAttribute("gyms", gyms);

        logger.info("Loaded {} gyms into application scope", gyms.size());
    }

    /**
     * Handles GET request to display the list of gyms.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.error("No user in session — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        boolean isAdmin = user.isAdmin();

        List<Gym> gyms = gymDao.getAll();
        req.setAttribute("gyms", gyms);
        req.setAttribute("isAdmin", isAdmin);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/gymManagement.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Handles POST request for adding and deleting gyms.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.error("No user in session — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        boolean isAdmin = user.isAdmin();

        if ("add".equals(action)) {
            String gymName = req.getParameter("gymName");
            String gymLocation = req.getParameter("gymLocation");

            // Call OpenStreetMap API to get lat/lon
            OpenStreetMapDao geoDao = new OpenStreetMapDao();
            GeocodeResponse geocode = geoDao.getGeocode(gymLocation);

            Gym newGym = new Gym(gymName, gymLocation);

            if (geocode != null) {
                newGym.setLatitude(geocode.getLatitude());
                newGym.setLongitude(geocode.getLongitude());
            } else {
                logger.warn("No geocode data found for location: {}", gymLocation);
            }

            gymDao.insert(newGym);
        } else if ("delete".equals(action)) {
            if (!isAdmin) {
                logger.warn("Unauthorized delete attempt by non-admin user.");
                resp.sendRedirect("gymManagement");
                return;
            }

            int gymId = Integer.parseInt(req.getParameter("gymId"));
            Gym gym = gymDao.getById(gymId);
            gymDao.delete(gym);
        }

        // Refresh gym list in application scope
        getServletContext().setAttribute("gyms", gymDao.getAll());

        // Redirect to avoid form resubmission
        resp.sendRedirect("gymManagement");
    }
}