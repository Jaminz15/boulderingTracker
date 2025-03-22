package matc.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import matc.entity.Gym;
import matc.persistence.GenericDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * Gym Management Controller - Handles listing, adding, and deleting gyms.
 */
@WebServlet("/gymManagement")
public class GymManagement extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymManagement.class);
    private GenericDao<Gym> gymDao;

    /**
     * Initializes the DAO for database access.
     */
    @Override
    public void init() throws ServletException {
        gymDao = new GenericDao<>(Gym.class);
    }

    /**
     * Handles GET request to display the list of gyms.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Gym> gyms = gymDao.getAll();

        logger.debug("Fetched gyms: {}", gyms);
        req.setAttribute("gyms", gyms);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/gymManagement.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Handles POST request for adding and deleting gyms.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String gymName = req.getParameter("gymName");
            String gymLocation = req.getParameter("gymLocation");
            Gym newGym = new Gym(gymName, gymLocation);
            gymDao.insert(newGym);
        } else if ("delete".equals(action)) {
            int gymId = Integer.parseInt(req.getParameter("gymId"));
            Gym gym = gymDao.getById(gymId);
            gymDao.delete(gym);
        }
        resp.sendRedirect("gymManagement");
    }
}