package matc.persistence;

import matc.entity.User;
import matc.entity.Climb;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private GenericDao<User> userDao;
    private GenericDao<Climb> climbDao;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        userDao = new GenericDao<>(User.class);
        climbDao = new GenericDao<>(Climb.class);
    }
}