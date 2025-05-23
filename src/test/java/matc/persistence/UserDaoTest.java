package matc.persistence;

import matc.entity.User;
import matc.entity.Climb;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserDao class, which handles CRUD operations for User entities.
 * This class tests various scenarios for inserting, updating, deleting, and retrieving users.
 */
class UserDaoTest {

    private GenericDao<User> userDao;
    private GenericDao<Climb> climbDao;

    /**
     * Sets up the test environment by cleaning the database and initializing the User and Climb DAOs.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        userDao = new GenericDao<>(User.class);
        climbDao = new GenericDao<>(Climb.class);
    }

    /**
     * Verifies that a user can be retrieved by their Cognito Sub.
     */
    @Test
    void getByCognitoSubSuccess() {
        List<User> users = userDao.findByPropertyEqual("cognitoSub", "test-cognito-sub");
        assertEquals(1, users.size());
        assertEquals("test-cognito-sub", users.get(0).getCognitoSub());
    }

    /**
     * Verifies that a user can be retrieved by ID.
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = userDao.getById(1);
        assertNotNull(retrievedUser);
        assertEquals("climber123@example.com", retrievedUser.getEmail());
        assertEquals("climber123", retrievedUser.getUsername());
        assertEquals("test-cognito-sub", retrievedUser.getCognitoSub());
    }

    /**
     * Tests successful insertion of a new user record.
     */
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

    /**
     * Verifies that a user's information can be updated successfully.
     */
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

    /**
     * Verifies that a user can be deleted successfully.
     */
    @Test
    void deleteSuccess() {
        userDao.delete(userDao.getById(2));
        assertNull(userDao.getById(2));
    }

    /**
     * Verifies that deleting a user cascades to deleting associated climbs.
     */
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

    /**
     * Verifies that all users can be retrieved from the database.
     */
    @Test
    void getAllSuccess() {
        List<User> users = userDao.getAll();
        assertEquals(2, users.size()); // Assuming cleanDB.sql starts with 2 users
    }

    /**
     * Verifies that a user can be found by email property.
     */
    @Test
    void getByPropertyEqual() {
        List<User> users = userDao.findByPropertyEqual("email", "climber123@example.com");
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
    }

    /**
     * Verifies that the admin flag is correctly inserted and retrieved.
     */
    @Test
    void isAdminInsertedAndRetrievedCorrectly() {
        User newAdmin = new User("admin@example.com", "adminUser", "admin-sub-001");
        newAdmin.setIsAdmin(true);

        int userId = userDao.insert(newAdmin);
        User retrieved = userDao.getById(userId);

        assertNotNull(retrieved);
        assertTrue(retrieved.isAdmin());
        assertEquals("admin@example.com", retrieved.getEmail());
    }

    /**
     * Verifies that the admin flag of a user can be updated.
     */
    @Test
    void updateIsAdminFlag() {
        User user = userDao.getById(1);
        assertFalse(user.isAdmin(), "Expected user to not be admin initially");

        user.setIsAdmin(true);
        userDao.update(user);  // <-- use update() here

        User updatedUser = userDao.getById(1);
        assertTrue(updatedUser.isAdmin(), "Expected user to be admin after update");
    }
}