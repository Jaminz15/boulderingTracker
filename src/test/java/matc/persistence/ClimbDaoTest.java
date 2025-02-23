package matc.persistence;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.entity.User;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClimbDaoTest {

    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;
    private GenericDao<User> userDao;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
        userDao = new GenericDao<>(User.class);
    }
}