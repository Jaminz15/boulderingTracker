package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.logging.log4j.*;

/**
 * Dashboard servlet to handle displaying the main dashboard page.
 * Retrieves and displays user-specific or admin-specific climb and gym data.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDao<Climb> climbDao;

    /**
     * Initializes the Dashboard servlet by setting up the Climb DAO.
     * Logs the initialization process.
     */
    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
    }

    /**
     * Handles GET requests to display the dashboard.
     * Retrieves the list of climbs and gyms based on user session information.
     * Displays the last logged climb date and gym data.
     * Redirects to login if the user session is not present.
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

        if (user == null) {
            logger.error("No user in session — redirecting to login");
            resp.sendRedirect("logIn.jsp");
            return;
        }

        // Retrieve all climbs for the current user or all climbs if the user is an admin
        List<Climb> userClimbs = user.isAdmin()
                ? climbDao.getAll()
                : climbDao.findByPropertyEqual("user", user);

        // Collect unique gyms from the user's climbs
        Set<Gym> userGyms = userClimbs.stream()
                .map(Climb::getGym)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Determine the date of the most recent climb log
        LocalDate lastLogDate = userClimbs.stream()
                .map(Climb::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);

        req.setAttribute("userName", user.getUsername());
        req.setAttribute("gyms", userGyms);
        req.setAttribute("lastLogDate", lastLogDate);

        logger.debug("Dashboard loaded for user {} (Admin: {}) — Gyms: {}, Climbs: {}, Last log: {}",
                user.getUsername(), user.isAdmin(), userGyms.size(), userClimbs.size(), lastLogDate);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}