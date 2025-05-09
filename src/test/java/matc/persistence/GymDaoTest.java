package matc.persistence;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GymDao class, which handles CRUD operations for Gym entities.
 * This class tests various scenarios for inserting, updating, deleting, and retrieving gyms.
 */
class GymDaoTest {

    private GenericDao<Gym> gymDao;

    /**
     * Sets up the test environment by cleaning the database and initializing the Gym DAO.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        gymDao = new GenericDao<>(Gym.class);
    }

    /**
     * Verifies that a gym can be retrieved by ID.
     */
    @Test
    void getByIdSuccess() {
        Gym retrievedGym = gymDao.getById(1);
        assertNotNull(retrievedGym);
        assertEquals("East Side Boulders", retrievedGym.getName());
        assertEquals("123 Boulder St, Madison, WI", retrievedGym.getLocation());
    }

    /**
     * Verifies that all gyms can be retrieved from the database.
     */
    @Test
    void getAllSuccess() {
        List<Gym> gyms = gymDao.getAll();
        assertEquals(2, gyms.size());
    }

    /**
     * Tests successful insertion of a new gym record.
     */
    @Test
    void insertSuccess() {
        Gym newGym = new Gym("West Side Boulders", "789 Climber Ln, Madison, WI");
        int insertedId = gymDao.insert(newGym);

        Gym retrievedGym = gymDao.getById(insertedId);
        assertNotNull(retrievedGym);
        assertEquals("West Side Boulders", retrievedGym.getName());
    }

    /**
     * Verifies that a gym's name can be updated successfully.
     */
    @Test
    void updateSuccess() {
        Gym gymToUpdate = gymDao.getById(1);
        gymToUpdate.setName("East Side Rock Gym");
        gymDao.update(gymToUpdate);

        Gym retrievedGym = gymDao.getById(1);
        assertEquals("East Side Rock Gym", retrievedGym.getName());
    }

    /**
     * Verifies that a gym can be deleted successfully.
     */
    @Test
    void deleteSuccess() {
        gymDao.delete(gymDao.getById(2));
        assertNull(gymDao.getById(2));
    }

    /**
     * Verifies cascading delete of climbs when deleting a gym.
     */
    @Test
    void deleteCascadeClimbs() {
        GenericDao<Climb> climbDao = new GenericDao<>(Climb.class);
        Gym gym = gymDao.getById(1);
        List<Climb> gymClimbs = climbDao.findByPropertyEqual("gym", gym);

        gymDao.delete(gym);

        assertNull(gymDao.getById(1));
        for (Climb climb : gymClimbs) {
            assertNull(climbDao.getById(climb.getId()));
        }
    }

    /**
     * Verifies successful insertion of a gym with coordinates.
     */
    @Test
    void insertWithCoordinatesSuccess() {
        Gym newGym = new Gym("South Boulder Spot", "456 Gravity Ave, Madison, WI");
        newGym.setLatitude("43.0655");
        newGym.setLongitude("-89.3901");

        int id = gymDao.insert(newGym);
        Gym inserted = gymDao.getById(id);

        assertNotNull(inserted);
        assertEquals("43.0655", inserted.getLatitude());
        assertEquals("-89.3901", inserted.getLongitude());
    }

    /**
     * Verifies that gyms can be found by name.
     */
    @Test
    void findByNameSuccess() {
        List<Gym> gyms = gymDao.findByPropertyEqual("name", "East Side Boulders");

        assertNotNull(gyms);
        assertFalse(gyms.isEmpty());
        assertEquals("East Side Boulders", gyms.get(0).getName());
    }
}