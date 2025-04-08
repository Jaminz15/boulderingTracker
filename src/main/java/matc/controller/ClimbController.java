package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
        logger.info("ClimbController initialized with DAOs");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.error("No user in session");
            resp.sendRedirect("error.jsp");
            return;
        }

        List<Climb> climbs = user.isAdmin()
                ? climbDao.getAll()
                : climbDao.findByUserCognitoSub(user.getCognitoSub());

        List<Gym> gyms = gymDao.getAll();

        logger.debug("User: {} (Admin: {}) - Retrieved {} climbs", user.getUsername(), user.isAdmin(), climbs.size());

        req.setAttribute("climbs", climbs);
        req.setAttribute("gyms", gyms);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/logClimb.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        logger.debug("Received action: {}", action);

        if ("add".equals(action)) {
            try {
                int gymId = Integer.parseInt(req.getParameter("gymId"));
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                String climbType = req.getParameter("climbType");
                String grade = req.getParameter("grade");
                int attempts = Integer.parseInt(req.getParameter("attempts"));
                boolean success = Boolean.parseBoolean(req.getParameter("success"));
                String notes = req.getParameter("notes");

                // Get logged-in user's Cognito sub
                HttpSession session = req.getSession();
                User user = (User) session.getAttribute("user");

                if (user == null) {
                    logger.error("No user in session");
                    resp.sendRedirect("error.jsp");
                    return;
                }

                Gym gym = gymDao.getById(gymId);
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
        } else if ("update".equals(action)) {
            try {
                int climbId = Integer.parseInt(req.getParameter("climbId"));
                Climb climbToUpdate = climbDao.getById(climbId);

                if (climbToUpdate != null) {
                    int gymId = Integer.parseInt(req.getParameter("gymId"));
                    Gym gym = gymDao.getById(gymId);

                    climbToUpdate.setDate(LocalDate.parse(req.getParameter("date")));
                    climbToUpdate.setGym(gym);
                    climbToUpdate.setClimbType(req.getParameter("climbType"));
                    climbToUpdate.setGrade(req.getParameter("grade"));
                    climbToUpdate.setAttempts(Integer.parseInt(req.getParameter("attempts")));
                    climbToUpdate.setSuccess(Boolean.parseBoolean(req.getParameter("success")));
                    climbToUpdate.setNotes(req.getParameter("notes"));

                    climbDao.update(climbToUpdate);

                    logger.info("Successfully updated climb ID: {}", climbId);
                } else {
                    logger.warn("Climb with ID {} not found for update", climbId);
                }
            } catch (Exception e) {
                logger.error("Error updating climb", e);
            }
        }
        resp.sendRedirect("climb");
    }
}