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

    @Test
    void getByIdSuccess() {
        User retrievedUser = userDao.getById(1);
        assertNotNull(retrievedUser);
        assertEquals("climber123@example.com", retrievedUser.getEmailOrUsername());
    }

    @Test
    void insertSuccess() {
        User newUser = new User("newclimber@example.com");
        int insertedUserId = userDao.insert(newUser);

        User retrievedUser = userDao.getById(insertedUserId);
        assertNotNull(retrievedUser);
        assertEquals("newclimber@example.com", retrievedUser.getEmailOrUsername());
    }

    @Test
    void updateSuccess() {
        User userToUpdate = userDao.getById(1);
        userToUpdate.setEmailOrUsername("updatedclimber@example.com");
        userDao.update(userToUpdate);

        User retrievedUser = userDao.getById(1);
        assertEquals("updatedclimber@example.com", retrievedUser.getEmailOrUsername());
    }


}