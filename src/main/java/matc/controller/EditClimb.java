package matc.controller;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

        String climbIdParam = req.getParameter("climbId");

        if (climbIdParam == null) {
            resp.sendRedirect("dashboard?error=missingClimbId");
            return;
        }

        int climbId = Integer.parseInt(climbIdParam);
        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

        Climb climb = climbDao.getById(climbId);
        List<Gym> gyms = gymDao.getAll();

        if (climb == null) {
            resp.sendRedirect("dashboard?error=climbNotFound");
            return;
        }

        req.setAttribute("climb", climb);
        req.setAttribute("gyms", gyms);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/editClimb.jsp");
        dispatcher.forward(req, resp);
    }
}