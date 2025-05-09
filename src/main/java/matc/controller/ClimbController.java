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
 * ClimbController - Handles the logging, editing, and deleting of climbs.
 * Manages the interaction between the user and the Climb entity.
 * Supports adding, updating, and deleting climb records.
 */
@WebServlet("/climb")
public class ClimbController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ClimbController.class);
    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;

    /**
     * Initializes the ClimbController servlet by setting up DAOs.
     * Logs the initialization process.
     */
    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
        logger.info("ClimbController initialized with DAOs");
    }

    /**
     * Handles GET requests to display the logClimb page.
     * Retrieves the list of climbs and gyms based on user session information.
     * Redirects to the error page if no user is logged in.
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
            logger.error("No user in session during GET request");
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

    /**
     * Handles POST requests to log, update, or delete a climb.
     * Determines the action from the request parameter and processes accordingly.
     *
     * @param req  the HttpServletRequest object containing climb data
     * @param resp the HttpServletResponse object for redirection and response handling
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        logger.debug("Received action: {}", action);

        if ("add".equals(action)) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                logger.error("No user in session during POST request");
                resp.sendRedirect("logIn.jsp");
                return;
            }

            // Validate required fields before parsing
            String climbType = req.getParameter("climbType");
            String grade = req.getParameter("grade");
            String attemptsParam = req.getParameter("attempts");

            if (climbType == null || climbType.isBlank()) {
                resp.sendRedirect("climb?error=missingClimbType");
                return;
            }

            if (grade == null || grade.isBlank()) {
                resp.sendRedirect("climb?error=missingGrade");
                return;
            }

            int attempts;
            try {
                attempts = Integer.parseInt(attemptsParam);
                if (attempts <= 0) {
                    resp.sendRedirect("climb?error=invalidAttempts");
                    return;
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid attempts value while adding climb: {}", attemptsParam);
                resp.sendRedirect("climb?error=invalidAttempts");
                return;
            }

            try {
                int gymId = Integer.parseInt(req.getParameter("gymId"));
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                boolean success = Boolean.parseBoolean(req.getParameter("success"));
                String notes = req.getParameter("notes");

                logger.debug("Climb form input - Gym ID: {}, Date: {}, Type: {}, Grade: {}, Attempts: {}, Success: {}, Notes: {}",
                        gymId, date, climbType, grade, attempts, success, notes);

                Gym gym = gymDao.getById(gymId);
                Climb newClimb = new Climb(gym, user, date, climbType, grade, attempts, success, notes);
                climbDao.insert(newClimb);

                logger.info("Successfully added new climb: {}", newClimb);
            } catch (Exception e) {
                logger.error("Error adding new climb", e);
                resp.sendRedirect("climb?error=unexpected");
                return;
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

                if (climbToUpdate == null) {
                    resp.sendRedirect("editClimb?climbId=" + climbId + "&error=invalidClimbId");
                    return;
                }

                String climbType = req.getParameter("climbType");
                String grade = req.getParameter("grade");
                String attemptsParam = req.getParameter("attempts");

                if (climbType == null || climbType.isBlank()) {
                    resp.sendRedirect("editClimb?climbId=" + climbId + "&error=missingClimbType");
                    return;
                }

                if (grade == null || grade.isBlank()) {
                    resp.sendRedirect("editClimb?climbId=" + climbId + "&error=missingGrade");
                    return;
                }

                int attempts;
                try {
                    attempts = Integer.parseInt(attemptsParam);
                    if (attempts <= 0) {
                        resp.sendRedirect("editClimb?climbId=" + climbId + "&error=invalidAttempts");
                        return;
                    }
                } catch (NumberFormatException e) {
                    logger.error("Invalid attempts value while updating climb: {}", attemptsParam);
                    resp.sendRedirect("editClimb?climbId=" + climbId + "&error=invalidAttempts");
                    return;
                }

                int gymId = Integer.parseInt(req.getParameter("gymId"));
                Gym gym = gymDao.getById(gymId);

                climbToUpdate.setDate(LocalDate.parse(req.getParameter("date")));
                climbToUpdate.setGym(gym);
                climbToUpdate.setClimbType(climbType);
                climbToUpdate.setGrade(grade);
                climbToUpdate.setAttempts(attempts);
                climbToUpdate.setSuccess(Boolean.parseBoolean(req.getParameter("success")));
                climbToUpdate.setNotes(req.getParameter("notes"));

                climbDao.update(climbToUpdate);
                logger.info("Successfully updated climb ID: {}", climbId);

            } catch (Exception e) {
                logger.error("Error updating climb", e);
                resp.sendRedirect("dashboard?error=updateFailed");
                return;
            }
        }
        resp.sendRedirect("climb");
    }
}