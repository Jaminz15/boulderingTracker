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

@WebServlet("/profile")
public class UserProfile extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserProfile.class);
    private GenericDao<Climb> climbDao;

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("logIn.jsp");
            return;
        }

        // Format the createdAt timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = user.getCreatedAt().format(formatter);

        // Get all climbs
        List<Climb> userClimbs = climbDao.findByPropertyEqual("user", user);

        // Optional: Find favorite gym
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

        req.setAttribute("user", user);
        req.setAttribute("totalClimbs", userClimbs.size());
        req.setAttribute("favoriteGym", favoriteGym);
        req.setAttribute("formattedDate", formattedDate);

        req.getRequestDispatcher("/userProfile.jsp").forward(req, resp);
    }
}