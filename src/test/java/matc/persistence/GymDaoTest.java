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


}
