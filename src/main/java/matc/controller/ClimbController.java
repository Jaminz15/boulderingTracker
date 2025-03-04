package matc.controller;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.entity.User;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;

/**
 * ClimbController - Handles logging, editing, and deleting climbs.
 */
@WebServlet("/climb")
public class ClimbController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ClimbController.class);
    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;
    private GenericDao<User> userDao;

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
        userDao = new GenericDao<>(User.class);
        logger.info("ClimbController initialized with DAOs");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Climb> climbs = climbDao.getAll();
        List<Gym> gyms = gymDao.getAll();

        req.setAttribute("climbs", climbs);
        req.setAttribute("gyms", gyms);

        logger.debug("Retrieved {} climbs and {} gyms from database", climbs.size(), gyms.size());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/logClimb.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        logger.debug("Received action: {}", action);

        if (req.getParameter("gymId") == null || req.getParameter("userId") == null) {
            logger.error("Missing gymId or userId in request");
            resp.sendRedirect("logClimb.jsp?error=missingData");
            return;
        }

        if ("add".equals(action)) {
            try {
                int gymId = Integer.parseInt(req.getParameter("gymId"));
                int userId = Integer.parseInt(req.getParameter("userId"));
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                String climbType = req.getParameter("climbType");
                String grade = req.getParameter("grade");
                int attempts = Integer.parseInt(req.getParameter("attempts"));
                boolean success = Boolean.parseBoolean(req.getParameter("success"));
                String notes = req.getParameter("notes");

                Gym gym = gymDao.getById(gymId);
                User user = userDao.getById(userId);
                Climb newClimb = new Climb(gym, user, date, climbType, grade, attempts, success, notes);
                climbDao.insert(newClimb);

                logger.info("Successfully added new climb: {}", newClimb);
            } catch (Exception e) {
                logger.error("Error adding new climb", e);
            }
        } else if ("delete".equals(action)) {
            try {
                int climbId = Integer.parseInt(req.getParameter("climbId"));
                Climb climb = climbDao.getById(climbId);
                climbDao.delete(climb);
                logger.info("Successfully deleted climb with ID: {}", climbId);
            } catch (Exception e) {
                logger.error("Error deleting climb", e);
            }
        }

        resp.sendRedirect("climbResults.jsp");
    }
}