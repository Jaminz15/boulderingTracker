package matc.controller;

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
    private GenericDao<Gym> gymDao;

    /**
     * Initializes the DAO for database access.
     */
    @Override
    public void init() throws ServletException {
        gymDao = new GenericDao<>(Gym.class);
    }
}