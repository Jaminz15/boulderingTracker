package matc.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import matc.entity.GeocodeResponse;
import matc.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class OpenStreetMapDao implements PropertiesLoader {

    private static final Logger logger = LogManager.getLogger(OpenStreetMapDao.class);
    private String baseUrl;

    public OpenStreetMapDao() {
        try {
            Properties properties = loadProperties("/boulderTracker.properties");
            baseUrl = properties.getProperty("openstreetmap.api.url");
        } catch (IOException e) {
            logger.error("Could not load API URL from properties file", e);
        }
    }

    public GeocodeResponse getGeocode(String address) {
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.error("Base URL is not set. Check properties file.");
            return null;
        }

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl)
                .queryParam("q", address.replace(" ", "+"))
                .queryParam("format", "json");

        logger.info("Requesting geocode data for: {}", address);

        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        logger.debug("API Response: {}", response);

        ObjectMapper mapper = new ObjectMapper();
        List<GeocodeResponse> locations = null;
        try {
            locations = Arrays.asList(mapper.readValue(response, GeocodeResponse[].class));
        } catch (Exception e) {
            logger.error("Error parsing JSON response", e);
        }

        if (locations == null || locations.isEmpty()) {
            logger.warn("No results found for address: {}", address);
            return null;
        }

        return locations.get(0);
    }
}