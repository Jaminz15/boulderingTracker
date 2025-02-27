package matc.controller;

import matc.entity.Climb;
import matc.persistence.GenericDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display climb data.
 */
@WebServlet("/searchClimbs")
public class ClimbServlet extends HttpServlet {
    private GenericDao<Climb> climbDao;

    @Override
    public void init() throws ServletException {
        climbDao = new GenericDao<>(Climb.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Fetch all climbs
        List<Climb> climbs = climbDao.getAll();

        // Debugging: Print climb data to the console
        System.out.println("=== Climb Data Retrieved ===");
        if (climbs == null) {
            System.out.println("ERROR: climbs is NULL!");
        } else if (climbs.isEmpty()) {
            System.out.println("No climbs found.");
        } else {
            System.out.println("SUCCESS: Found " + climbs.size() + " climbs.");
            for (Climb climb : climbs) {
                System.out.println(climb);
            }
        }

        // Set the climbs as a request attribute
        req.setAttribute("climbs", climbs);

        // Forward to results page
        RequestDispatcher dispatcher = req.getRequestDispatcher("/climbResults.jsp");
        dispatcher.forward(req, resp);
    }
}