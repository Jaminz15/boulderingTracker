package matc.persistence;

import matc.entity.GeocodeResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OpenStreetMapDao class.
 * Verifies the geocoding functionality using valid and invalid city names.
 */
public class OpenStreetMapDaoTest {
    /**
     * Tests the successful retrieval of geocode data for a valid city (Madison, WI).
     */
    @Test
    void getGeocodeSuccess() {
        OpenStreetMapDao dao = new OpenStreetMapDao();
        GeocodeResponse response = dao.getGeocode("Madison, WI");

        assertNotNull(response, "Response should not be null");
        assertEquals("Madison", response.getName(), "City name should be Madison");
        assertEquals(
                43.074761,
                Double.parseDouble(response.getLatitude()),
                0.000001,
                "Latitude should match Madison, WI"
        );
        assertEquals(
                -89.3837613,
                Double.parseDouble(response.getLongitude()),
                0.000001,
                "Longitude should match Madison, WI"
        );
    }

    /**
     * Tests the behavior of the geocode method when the city name does not exist.
     */
    @Test
    void getGeocodeNotFound() {
        OpenStreetMapDao dao = new OpenStreetMapDao();
        GeocodeResponse response = dao.getGeocode("CityThatDoesNotExist");

        assertNull(response, "Response should be null for an invalid city");
    }
}