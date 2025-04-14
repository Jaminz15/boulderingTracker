package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import javax.servlet.*;
import org.apache.logging.log4j.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/trackProgress")
public class TrackProgress extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(TrackProgress.class);
    private GenericDao<Climb> climbDao;

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("logIn.jsp");
            return;
        }

        logger.debug("TrackProgress: User {} is viewing progress", user.getUsername());

        // Load gyms for dropdown
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);
        List<Gym> gyms = gymDao.getAll();
        req.setAttribute("gyms", gyms);

        // Handle filter inputs
        String gymIdParam = req.getParameter("gymId");

        List<Climb> userClimbs = climbDao.findByPropertyEqual("user", user);

        // Apply gym filter
        if (gymIdParam != null && !gymIdParam.isEmpty()) {
            int gymId = Integer.parseInt(gymIdParam);
            logger.debug("TrackProgress: Filtering by gym ID {}", gymId);
            userClimbs.removeIf(climb -> climb.getGym().getId() != gymId);
        }

        logger.debug("TrackProgress: {} climbs loaded after filters", userClimbs.size());

        // Apply date range filter
        String startDateParam = req.getParameter("startDate");
        String endDateParam = req.getParameter("endDate");

        if ((startDateParam != null && !startDateParam.isEmpty()) ||
                (endDateParam != null && !endDateParam.isEmpty())) {

            userClimbs.removeIf(climb -> {
                String climbDate = climb.getDate().toString();
                return (startDateParam != null && !startDateParam.isEmpty() && climbDate.compareTo(startDateParam) < 0)
                        || (endDateParam != null && !endDateParam.isEmpty() && climbDate.compareTo(endDateParam) > 0);
            });
        }

        // Calculate stats
        int totalClimbs = userClimbs.size();
        int totalAttempts = userClimbs.stream().mapToInt(Climb::getAttempts).sum();
        double averageAttempts = totalClimbs > 0 ? (double) totalAttempts / totalClimbs : 0;
        long successfulClimbs = userClimbs.stream().filter(Climb::isSuccess).count();
        double successRate = totalClimbs > 0 ? (double) successfulClimbs / totalClimbs * 100 : 0;

        String bestGrade = userClimbs.stream()
                .map(Climb::getGrade)
                .max(Comparator.comparingInt(TrackProgress::extractGradeValue))
                .orElse("N/A");

        Climb hardestClimb = userClimbs.stream()
                .max(Comparator.comparingInt(c -> extractGradeValue(c.getGrade())))
                .orElse(null);

        Climb mostAttempts = userClimbs.stream()
                .max(Comparator.comparingInt(Climb::getAttempts))
                .orElse(null);

        // Send data to JSP
        req.setAttribute("totalClimbs", totalClimbs);
        req.setAttribute("totalAttempts", totalAttempts);
        req.setAttribute("averageAttempts", String.format("%.1f", averageAttempts));
        req.setAttribute("successRate", String.format("%.0f", successRate));
        req.setAttribute("bestGrade", bestGrade);
        req.setAttribute("hardestClimb", hardestClimb);
        req.setAttribute("mostAttempts", mostAttempts);
        req.setAttribute("userClimbs", userClimbs);

        req.getRequestDispatcher("/trackProgress.jsp").forward(req, resp);
    }

    // Method to convert grades like "V3" or "V7+" into sortable integers
    private static int extractGradeValue(String grade) {
        if (grade == null || !grade.startsWith("V")) return 0;
        String number = grade.replace("V", "").replace("+", "");
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}