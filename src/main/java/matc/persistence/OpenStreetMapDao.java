package matc.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import matc.entity.GeocodeResponse;
import org.apache.logging.log4j.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * OpenStreetMapDao - Handles geocoding requests using the OpenStreetMap API.
 * Fetches latitude and longitude data based on a given address.
 * Uses Jersey for HTTP requests and Jackson for JSON parsing.
 */
public class OpenStreetMapDao {

    private static final Logger logger = LogManager.getLogger(OpenStreetMapDao.class);
    private final String baseUrl;

    /**
     * Constructs an OpenStreetMapDao object.
     * Initializes the base URL from application properties.
     * Logs an error if the base URL is not configured.
     */
    public OpenStreetMapDao() {
        baseUrl = matc.util.AppConfig.getProperty("openstreetmap.api.url");

        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.error("API base URL is not set. Check boulderTracker.properties.");
        } else {
            logger.info("OpenStreetMapDao initialized with base URL: {}", baseUrl);
        }
    }

    /**
     * Retrieves geocoding information for a given address using the OpenStreetMap API.
     *
     * @param address the address to geocode
     * @return a GeocodeResponse object containing latitude and longitude, or null if not found
     */
    public GeocodeResponse getGeocode(String address) {
        // Check if the base URL is configured correctly
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.error("Base URL is not set. Check properties file.");
            return null;
        }

        Client client = ClientBuilder.newClient();

        // Build the API request target with query parameters
        WebTarget target = client.target(baseUrl)
                .queryParam("q", address)
                .queryParam("format", "json");

        logger.info("Final request URI: {}", target.getUri());

        logger.info("Requesting geocode data for: {}", address);

        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        logger.debug("API Response: {}", response);

        // Parse the JSON response from the OpenStreetMap API
        ObjectMapper mapper = new ObjectMapper();
        List<GeocodeResponse> locations = null;
        try {
            locations = Arrays.asList(mapper.readValue(response, GeocodeResponse[].class));
        } catch (Exception e) {
            logger.error("Error parsing JSON response", e);
        }

        // Return the first result if available
        if (locations == null || locations.isEmpty()) {
            logger.warn("No results found for address: {}", address);
            return null;
        }

        return locations.get(0);
    }
}