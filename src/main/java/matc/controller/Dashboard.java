package matc.controller;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.entity.User;
import matc.persistence.GenericDao;
import com.auth0.jwt.interfaces.Claim;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;
    private GenericDao<User> userDao;

    @Override
    public void init() {
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
        userDao = new GenericDao<>(User.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Map<String, Claim> userClaims = (Map<String, Claim>) session.getAttribute("userClaims");
        String cognitoSub = userClaims != null ? userClaims.get("sub").asString() : null;

        List<User> users = userDao.findByPropertyEqual("cognitoSub", cognitoSub);
        if (users.isEmpty()) {
            resp.sendRedirect("logIn.jsp");
            return;
        }

        User user = users.get(0);

        // Get all climbs by this user
        List<Climb> userClimbs = climbDao.findByPropertyEqual("user", user);

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

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}