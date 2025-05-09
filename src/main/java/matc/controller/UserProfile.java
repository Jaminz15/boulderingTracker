package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.logging.log4j.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

/**
 * UserProfile servlet to display user profile information.
 * Retrieves user-specific data including total climbs and favorite gym.
 * Formats the user's registration date for display.
 */
@WebServlet("/profile")
public class UserProfile extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserProfile.class);
    private GenericDao<Climb> climbDao;

    /**
     * Initializes the UserProfile servlet by setting up the Climb DAO.
     * Logs the initialization process.
     */
    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
    }

    /**
     * Handles GET requests to display the user profile page.
     * Retrieves the logged-in user's data, including climb statistics and favorite gym.
     * Formats the registration date for display.
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

        // Check if the user is logged in; if not, redirect to login page
        if (user == null) {
            resp.sendRedirect("logIn.jsp");
            return;
        }

        // Format the user's registration date to a readable format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = user.getCreatedAt().format(formatter);

        // Get all climbs
        List<Climb> userClimbs = climbDao.findByPropertyEqual("user", user);

        // Determine the user's favorite gym based on the number of logged climbs
        Map<Gym, Long> gymCount = userClimbs.stream()
                .collect(Collectors.groupingBy(Climb::getGym, Collectors.counting()));

        Gym favoriteGym = gymCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // logging
        logger.debug("User {} loaded profile page. Total climbs: {}, Favorite Gym: {}",
                user.getUsername(), userClimbs.size(),
                favoriteGym != null ? favoriteGym.getName() : "None");

        // Set attributes for display on the profile page
        req.setAttribute("user", user);
        req.setAttribute("totalClimbs", userClimbs.size());
        req.setAttribute("favoriteGym", favoriteGym);
        req.setAttribute("formattedDate", formattedDate);

        req.getRequestDispatcher("/userProfile.jsp").forward(req, resp);
    }
}