package matc.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Logger logger = LogManager.getLogger(AppConfig.class); // 👈 Add logger
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getResourceAsStream("/boulderTracker.properties")) {
            if (input != null) {
                properties.load(input);
                logger.info("AppConfig loaded properties file successfully.");
            } else {
                throw new IOException("Properties file not found.");
            }
        } catch (IOException e) {
            logger.error("Error loading properties file in AppConfig", e); // 👈 Use logger
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}