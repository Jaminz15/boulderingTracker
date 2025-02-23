package matc.persistence;

import matc.entity.Gym;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Gym dao test.
 */
class GymDaoTest {

    private GenericDao<Gym> gymDao;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        gymDao = new GenericDao<>(Gym.class);
    }

    @Test
    void getByIdSuccess() {
        Gym retrievedGym = gymDao.getById(1);
        assertNotNull(retrievedGym);
        assertEquals("East Side Boulders", retrievedGym.getName());
        assertEquals("123 Boulder St, Madison, WI", retrievedGym.getLocation());
    }

    @Test
    void getAllSuccess() {
        List<Gym> gyms = gymDao.getAll();
        assertEquals(2, gyms.size());
    }

    @Test
    void insertSuccess() {
        Gym newGym = new Gym("West Side Boulders", "789 Climber Ln, Madison, WI");
        int insertedId = gymDao.insert(newGym);

        Gym retrievedGym = gymDao.getById(insertedId);
        assertNotNull(retrievedGym);
        assertEquals("West Side Boulders", retrievedGym.getName());
    }

    @Test
    void updateSuccess() {
        Gym gymToUpdate = gymDao.getById(1);
        gymToUpdate.setName("East Side Rock Gym");
        gymDao.update(gymToUpdate);

        Gym retrievedGym = gymDao.getById(1);
        assertEquals("East Side Rock Gym", retrievedGym.getName());
    }

    @Test
    void deleteSuccess() {
        gymDao.delete(gymDao.getById(2));
        assertNull(gymDao.getById(2));
    }
}
