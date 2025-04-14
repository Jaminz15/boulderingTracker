package matc.controller;

import matc.entity.*;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/editClimb")
public class EditClimb extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EditClimb.class);

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

        String climbIdParam = req.getParameter("climbId");

        if (climbIdParam == null) {
            resp.sendRedirect("dashboard?error=missingClimbId");
            return;
        }

        int climbId = Integer.parseInt(climbIdParam);
        logger.debug("EditClimb requested for Climb ID: {}", climbId);
        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

        Climb climb = climbDao.getById(climbId);

        if (climb == null) {
            logger.warn("Climb not found — ID: {}", climbId);
            resp.sendRedirect("dashboard?error=climbNotFound");
            return;
        }

        if (!user.isAdmin() && climb.getUser().getId() != user.getId()) {
            logger.warn("Unauthorized access attempt by user {} for climb ID {}", user.getUsername(), climbId);
            resp.sendRedirect("dashboard?error=unauthorized");
            return;
        }

        List<Gym> gyms = gymDao.getAll();

        req.setAttribute("climb", climb);
        req.setAttribute("gyms", gyms);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/editClimb.jsp");
        dispatcher.forward(req, resp);
    }
}