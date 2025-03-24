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

@WebServlet("/profile")
public class UserProfile extends HttpServlet {
    private GenericDao<User> userDao;
    private GenericDao<Climb> climbDao;

    @Override
    public void init() {
        userDao = new GenericDao<>(User.class);
        climbDao = new GenericDao<>(Climb.class);
    }




}