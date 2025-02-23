package matc.persistence;

import matc.entity.Gym;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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


}
