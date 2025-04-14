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

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
    }

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

        // Get all climbs by this user
        List<Climb> userClimbs = user.isAdmin()
                ? climbDao.getAll()
                : climbDao.findByPropertyEqual("user", user);

        // Get distinct gyms from climbs
        Set<Gym> userGyms = userClimbs.stream()
                .map(Climb::getGym)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Optional: find last log date
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