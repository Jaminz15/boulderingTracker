package matc.persistence;

import matc.entity.GeocodeResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpenStreetMapDaoTest {

    @Test
    void getGeocodeSuccess() {
        OpenStreetMapDao dao = new OpenStreetMapDao();
        GeocodeResponse response = dao.getGeocode("Madison, WI");

        assertNotNull(response, "Response should not be null");
        assertEquals("Madison", response.getName(), "City name should be Madison");
        assertEquals("43.074761", response.getLatitude(), "Latitude should match Madison, WI");
        assertEquals("-89.3837613", response.getLongitude(), "Longitude should match Madison, WI");
    }

    @Test
    void getGeocodeNotFound() {
        OpenStreetMapDao dao = new OpenStreetMapDao();
        GeocodeResponse response = dao.getGeocode("CityThatDoesNotExist");

        assertNull(response, "Response should be null for an invalid city");
    }
}
