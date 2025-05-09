package matc.persistence;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.entity.User;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ClimbDao class, which handles CRUD operations for Climb entities.
 * This class tests various scenarios for inserting, updating, deleting, and retrieving climbs.
 */
class ClimbDaoTest {

    private GenericDao<Climb> climbDao;
    private GenericDao<Gym> gymDao;
    private GenericDao<User> userDao;

    /**
     * Sets up the test environment by cleaning the database and initializing DAOs.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        climbDao = new GenericDao<>(Climb.class);
        gymDao = new GenericDao<>(Gym.class);
        userDao = new GenericDao<>(User.class);
    }

    /**
     * Verifies that a climb can be retrieved by ID.
     */
    @Test
    void getByIdSuccess() {
        Climb retrievedClimb = climbDao.getById(1);
        assertNotNull(retrievedClimb);
        assertEquals("Overhang", retrievedClimb.getClimbType());
        assertEquals("V5", retrievedClimb.getGrade());
    }

    /**
     * Verifies that all climbs can be retrieved from the database.
     */
    @Test
    void getAllSuccess() {
        List<Climb> climbs = climbDao.getAll();
        assertEquals(3, climbs.size()); // Ensure this matches your cleanDB.sql
    }

    /**
     * Tests successful insertion of a new climb record.
     */
    @Test
    void insertSuccess() {
        User user = userDao.getById(1);
        Gym gym = gymDao.getById(1);

        // Store the current date in a variable to ensure consistency
        LocalDate fixedDate = LocalDate.now();

        Climb expectedClimb = new Climb(gym, user, fixedDate, "Slab", "V3", 2, true, "Felt great!");
        int insertedId = climbDao.insert(expectedClimb);

        Climb retrievedClimb = climbDao.getById(insertedId);
        assertNotNull(retrievedClimb);

        // Ensure the retrieved climb has the same ID
        expectedClimb.setId(insertedId);

        assertEquals(expectedClimb, retrievedClimb);
    }

    /**
     * Verifies that a climb can be updated successfully.
     */
    @Test
    void updateSuccess() {
        Climb climbToUpdate = climbDao.getById(1);
        climbToUpdate.setNotes("Updated Note: Was harder than expected.");
        climbDao.update(climbToUpdate);

        Climb retrievedClimb = climbDao.getById(1);
        assertEquals("Updated Note: Was harder than expected.", retrievedClimb.getNotes());
    }

    /**
     * Verifies that a climb can be deleted successfully.
     */
    @Test
    void deleteSuccess() {
        climbDao.delete(climbDao.getById(3)); // Adjust to last Climb record from cleanDB.sql
        assertNull(climbDao.getById(3));
    }

    /**
     * Verifies that deleting a climb does not delete the associated user.
     */
    @Test
    void deletingClimbDoesNotDeleteUser() {
        Climb climb = climbDao.getById(1); // assuming Climb with ID 1 exists
        User user = climb.getUser();

        climbDao.delete(climb);

        assertNull(climbDao.getById(climb.getId())); // Climb should be gone
        assertNotNull(userDao.getById(user.getId())); // User should still exist
    }

    /**
     * Verifies cascading delete for gym and associated climbs.
     */
    @Test
    void deleteCascadeGym() {
        Gym gym = gymDao.getById(1);
        List<Climb> gymClimbs = climbDao.findByPropertyEqual("gym", gym);

        gymDao.delete(gym);

        assertNull(gymDao.getById(1));
        for (Climb climb : gymClimbs) {
            assertNull(climbDao.getById(climb.getId()));
        }
    }

    /**
     * Finds climbs by user using Cognito Sub.
     */
    @Test
    void findByUserCognitoSubSuccess() {
        User user = userDao.getById(1);
        List<Climb> climbs = climbDao.findByUserCognitoSub(user.getCognitoSub());

        assertNotNull(climbs);
        assertFalse(climbs.isEmpty());
        assertEquals(user.getId(), climbs.get(0).getUser().getId());
    }

    /**
     * Verifies that climbs can be found by the associated user.
     */
    @Test
    void findByUserSuccess() {
        User user = userDao.getById(1);
        List<Climb> climbs = climbDao.findByPropertyEqual("user", user);

        assertNotNull(climbs);
        assertFalse(climbs.isEmpty());
        for (Climb climb : climbs) {
            assertEquals(user.getId(), climb.getUser().getId());
        }
    }

    /**
     * Verifies that climbs can be found by a combination of user and gym.
     */
    @Test
    void findByUserAndGymSuccess() {
        User user = userDao.getById(1);
        Gym gym = gymDao.getById(1);

        Map<String, Object> filters = new HashMap<>();
        filters.put("user", user);
        filters.put("gym", gym);

        List<Climb> climbs = climbDao.findByPropertyEqual(filters);

        assertNotNull(climbs);
        assertFalse(climbs.isEmpty());
        for (Climb climb : climbs) {
            assertEquals(user.getId(), climb.getUser().getId());
            assertEquals(gym.getId(), climb.getGym().getId());
        }
    }

    /**
     * Verifies that climbs can be found by climb type.
     */
    @Test
    void findByClimbTypeSuccess() {
        List<Climb> climbs = climbDao.findByPropertyEqual("climbType", "Overhang");

        assertNotNull(climbs);
        assertFalse(climbs.isEmpty());
        assertEquals("Overhang", climbs.get(0).getClimbType());
    }

    /**
     * Verifies that climbs can be found by grade.
     */
    @Test
    void findByGradeSuccess() {
        List<Climb> climbs = climbDao.findByPropertyEqual("grade", "V5");

        assertNotNull(climbs);
        assertFalse(climbs.isEmpty());
        assertEquals("V5", climbs.get(0).getGrade());
    }

    /**
     * Verifies that all fields of a climb can be updated successfully.
     */
    @Test
    void updateAllFieldsSuccess() {
        Climb climb = climbDao.getById(1);
        Gym gym = gymDao.getById(2); // use a different gym than original

        climb.setGym(gym);
        climb.setClimbType("Overhang");
        climb.setGrade("V4");
        climb.setAttempts(3);
        climb.setSuccess(false);
        climb.setNotes("Testing full update");

        climbDao.update(climb);
        Climb updated = climbDao.getById(1);

        assertEquals("Overhang", updated.getClimbType());
        assertEquals("V4", updated.getGrade());
        assertEquals(3, updated.getAttempts());
        assertFalse(updated.isSuccess());
        assertEquals("Testing full update", updated.getNotes());
        assertEquals(gym.getId(), updated.getGym().getId());
    }
}