package matc.controller;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * GymPage - Displays climbing logs for a specific gym.
 */
@WebServlet("/gymPage")
public class GymPage extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymPage.class);
    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gymIdParam = req.getParameter("gymId");

        if (gymIdParam == null) {
            resp.sendRedirect("index.jsp?error=noGymSelected");
            return;
        }

        int gymId = Integer.parseInt(gymIdParam);
        Gym selectedGym = gymDao.getById(gymId);
        List<Climb> climbs = climbDao.findByPropertyEqual("gym.id", gymId);

        logger.debug("GymPage: Retrieved {} climbs for gym ID {}", climbs.size(), gymId);

        req.setAttribute("gym", selectedGym);
        req.setAttribute("climbs", climbs);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/gymPage.jsp");
        dispatcher.forward(req, resp);
    }
}