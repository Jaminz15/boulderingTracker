package matc.controller;

import matc.entity.Climb;
import matc.entity.User;
import matc.persistence.GenericDao;
import com.auth0.jwt.interfaces.Claim;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/trackProgress")
public class TrackProgress extends HttpServlet {
    private GenericDao<User> userDao;
    private GenericDao<Climb> climbDao;

    @Override
    public void init() {
        userDao = new GenericDao<>(User.class);
        climbDao = new GenericDao<>(Climb.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Map<String, Claim> claims = (Map<String, Claim>) session.getAttribute("userClaims");
        String cognitoSub = claims != null ? claims.get("sub").asString() : null;

        List<User> users = userDao.findByPropertyEqual("cognitoSub", cognitoSub);
        if (users.isEmpty()) {
            resp.sendRedirect("logIn.jsp");
            return;
        }

        User user = users.get(0);
        List<Climb> userClimbs = climbDao.findByPropertyEqual("user", user);

        int totalClimbs = userClimbs.size();
        int totalAttempts = userClimbs.stream().mapToInt(Climb::getAttempts).sum();
        double averageAttempts = totalClimbs > 0 ? (double) totalAttempts / totalClimbs : 0;
        long successfulClimbs = userClimbs.stream().filter(Climb::isSuccess).count();
        double successRate = totalClimbs > 0 ? (double) successfulClimbs / totalClimbs * 100 : 0;

        // Best grade (sorted by V number)
        String bestGrade = userClimbs.stream()
                .map(Climb::getGrade)
                .max(Comparator.comparingInt(TrackProgress::extractGradeValue))
                .orElse("N/A");

        req.setAttribute("totalClimbs", totalClimbs);
        req.setAttribute("totalAttempts", totalAttempts);
        req.setAttribute("averageAttempts", String.format("%.1f", averageAttempts));
        req.setAttribute("successRate", String.format("%.0f", successRate));
        req.setAttribute("bestGrade", bestGrade);

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