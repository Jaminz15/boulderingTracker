package matc.controller;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.entity.User;
import matc.persistence.GenericDao;
import com.auth0.jwt.interfaces.Claim;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * GymPage - Displays climbing logs for a specific gym.
 */
@WebServlet("/gymPage")
public class GymPage extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GymPage.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gymIdParam = req.getParameter("gymId");

        if (gymIdParam == null) {
            resp.sendRedirect("index.jsp?error=noGymSelected");
            return;
        }

        int gymId = Integer.parseInt(gymIdParam);

        // DAOs
        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);
        GenericDao<User> userDao = new GenericDao<>(User.class);

        // Get gym by ID
        Gym selectedGym = gymDao.getById(gymId);

        // Get logged-in user from session
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Claim> userClaims = (Map<String, Claim>) session.getAttribute("userClaims");
        Claim groupsClaim = userClaims.get("cognito:groups");
        boolean isAdmin = groupsClaim != null && groupsClaim.asList(String.class).contains("Admin");
        for (Map.Entry<String, Claim> entry : userClaims.entrySet()) {
            logger.debug("Claim key: {}, value: {}", entry.getKey(), entry.getValue().asString());
        }
        String cognitoSub = userClaims != null ? userClaims.get("sub").asString() : null;

        List<User> users = userDao.findByPropertyEqual("cognitoSub", cognitoSub);
        if (users.isEmpty()) {
            resp.sendRedirect("logIn.jsp");
            return;
        }

        User user = users.get(0);

        List<Climb> climbs;

        if (isAdmin) {
            // Admin sees all climbs for the selected gym
            Map<String, Object> gymOnly = new HashMap<>();
            gymOnly.put("gym", selectedGym);
            climbs = climbDao.findByPropertyEqual(gymOnly);
        } else {
            // Regular user sees only their climbs
            Map<String, Object> filters = new HashMap<>();
            filters.put("user", user);
            filters.put("gym", selectedGym);
            climbs = climbDao.findByPropertyEqual(filters);
        }

        logger.debug("GymPage: Retrieved {} climbs for user {} at gym ID {}", climbs.size(), user.getId(), gymId);

        req.setAttribute("gym", selectedGym);
        req.setAttribute("climbs", climbs);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/gymPage.jsp");
        dispatcher.forward(req, resp);
    }
}