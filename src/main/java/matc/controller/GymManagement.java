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
 * GymManagement servlet to handle the management of gyms.
 * Supports listing, adding, and deleting gyms.
 * Restricts certain actions to admin users.
 */
@WebServlet("/gymManagement")
public class GymManagement extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymManagement.class);
    private GenericDao<Gym> gymDao;

    /**
     * Initializes the GymManagement servlet by setting up the Gym DAO.
     * Loads all gyms and stores them in the application scope.
     * Logs the number of gyms loaded.
     *
     * @throws ServletException if an error occurs during initialization
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
     * Handles GET requests to display the list of gyms.
     * Retrieves gym data from the database and displays it on the management page.
     * Redirects to the login page if the user session is not present.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.error("No user in session during GET request — redirecting to login");
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
     * Handles POST requests for adding and deleting gyms.
     * - Add: Validates the gym name and location, fetches geocode data, and inserts the gym.
     * - Delete: Removes a gym by ID, restricted to admin users.
     * Refreshes the gym list after changes.
     *
     * @param req  the HttpServletRequest object containing gym data
     * @param resp the HttpServletResponse object for redirection
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.error("No user in session during POST request — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        boolean isAdmin = user.isAdmin();

        if ("add".equals(action)) {
            String gymName = req.getParameter("gymName");
            String gymLocation = req.getParameter("gymLocation");

            // Validate gym name and location inputs
            if (gymName == null || gymName.isBlank()) {
                logger.warn("Gym name is missing.");
                resp.sendRedirect("gymManagement?error=missingGymName");
                return;
            }

            if (gymLocation == null || gymLocation.isBlank()) {
                logger.warn("Gym location is missing.");
                resp.sendRedirect("gymManagement?error=missingGymLocation");
                return;
            }

            OpenStreetMapDao geoDao = new OpenStreetMapDao();
            GeocodeResponse geocode = geoDao.getGeocode(gymLocation);

            if (geocode == null) {
                logger.warn("No geocode data found for location: {}", gymLocation);
                resp.sendRedirect("gymManagement?error=geocodeFailed");
                return;
            }

            Gym newGym = new Gym(gymName, gymLocation);
            newGym.setLatitude(geocode.getLatitude());
            newGym.setLongitude(geocode.getLongitude());

            gymDao.insert(newGym);

            logger.info("Gym added: {} at {} (Lat: {}, Lon: {})",
                    gymName, gymLocation, newGym.getLatitude(), newGym.getLongitude());

        } else if ("delete".equals(action)) {
            // Check admin privileges before deleting a gym
            if (!isAdmin) {
                logger.warn("Unauthorized delete attempt by non-admin user.");
                resp.sendRedirect("gymManagement");
                return;
            }

            int gymId = Integer.parseInt(req.getParameter("gymId"));
            Gym gym = gymDao.getById(gymId);
            gymDao.delete(gym);
        }

        // Refresh gym list in application scope after adding or deleting
        getServletContext().setAttribute("gyms", gymDao.getAll());

        // Redirect to avoid form resubmission
        resp.sendRedirect("gymManagement");
    }
}