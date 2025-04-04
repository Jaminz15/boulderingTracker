package matc.persistence;

import matc.entity.Climb;
import matc.entity.Gym;
import matc.entity.User;
import matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

    @Test
    void getByIdSuccess() {
        Climb retrievedClimb = climbDao.getById(1);
        assertNotNull(retrievedClimb);
        assertEquals("Overhang", retrievedClimb.getClimbType());
        assertEquals("V5", retrievedClimb.getGrade());
    }

    @Test
    void getAllSuccess() {
        List<Climb> climbs = climbDao.getAll();
        assertEquals(3, climbs.size()); // Ensure this matches your cleanDB.sql
    }

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

    @Test
    void updateSuccess() {
        Climb climbToUpdate = climbDao.getById(1);
        climbToUpdate.setNotes("Updated Note: Was harder than expected.");
        climbDao.update(climbToUpdate);

        Climb retrievedClimb = climbDao.getById(1);
        assertEquals("Updated Note: Was harder than expected.", retrievedClimb.getNotes());
    }

    @Test
    void deleteSuccess() {
        climbDao.delete(climbDao.getById(3)); // Adjust to last Climb record from cleanDB.sql
        assertNull(climbDao.getById(3));
    }

    @Test
    void deletingClimbDoesNotDeleteUser() {
        Climb climb = climbDao.getById(1); // assuming Climb with ID 1 exists
        User user = climb.getUser();

        climbDao.delete(climb);

        assertNull(climbDao.getById(climb.getId())); // Climb should be gone
        assertNotNull(userDao.getById(user.getId())); // User should still exist
    }

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
}
