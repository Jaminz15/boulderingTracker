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
    void getByCognitoSubSuccess() {
        List<User> users = userDao.findByPropertyEqual("cognitoSub", "test-cognito-sub");
        assertEquals(1, users.size());
        assertEquals("test-cognito-sub", users.get(0).getCognitoSub());
    }

    @Test
    void getByIdSuccess() {
        User retrievedUser = userDao.getById(1);
        assertNotNull(retrievedUser);
        assertEquals("climber123@example.com", retrievedUser.getEmail());
        assertEquals("climber123", retrievedUser.getUsername());
        assertEquals("test-cognito-sub", retrievedUser.getCognitoSub());
    }

    @Test
    void insertSuccess() {
        User newUser = new User("newclimber@example.com", "newclimber", "unique-cognito-sub-123"); // Added username
        int insertedUserId = userDao.insert(newUser);

        User retrievedUser = userDao.getById(insertedUserId);
        assertNotNull(retrievedUser);
        assertEquals("newclimber@example.com", retrievedUser.getEmail());
        assertEquals("newclimber", retrievedUser.getUsername());
        assertEquals("unique-cognito-sub-123", retrievedUser.getCognitoSub());
    }

    @Test
    void updateSuccess() {
        User userToUpdate = userDao.getById(1);
        userToUpdate.setEmail("updatedclimber@example.com"); // Updated field
        userToUpdate.setUsername("updatedClimber"); // Added field
        userToUpdate.setCognitoSub("updated-cognito-sub");
        userDao.update(userToUpdate);

        User retrievedUser = userDao.getById(1);
        assertEquals("updatedclimber@example.com", retrievedUser.getEmail());
        assertEquals("updatedClimber", retrievedUser.getUsername());
        assertEquals("updated-cognito-sub", retrievedUser.getCognitoSub());
    }

    @Test
    void deleteSuccess() {
        userDao.delete(userDao.getById(2));
        assertNull(userDao.getById(2));
    }

    @Test
    void deleteCascadeClimbs() {
        User user = userDao.getById(1);
        List<Climb> userClimbs = climbDao.findByPropertyEqual("user", user);

        userDao.delete(user);

        assertNull(userDao.getById(1));
        for (Climb climb : userClimbs) {
            assertNull(climbDao.getById(climb.getId()));
        }
    }

    @Test
    void getAllSuccess() {
        List<User> users = userDao.getAll();
        assertEquals(2, users.size()); // Assuming cleanDB.sql starts with 2 users
    }

    @Test
    void getByPropertyEqual() {
        List<User> users = userDao.findByPropertyEqual("email", "climber123@example.com");
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
    }
}