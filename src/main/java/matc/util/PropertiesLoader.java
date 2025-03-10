package matc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This interface contains a default method that can be used anywhere a Properties
 * object is needed to be loaded.
 * @author Eric Knapp
 */
public interface PropertiesLoader {

    /**
     * Loads a properties file into a Properties instance and returns it.
     *
     * @param propertiesFilePath a path to a file on the Java classpath
     * @return a populated Properties instance
     * @throws IOException if the properties file cannot be read
     */
    default Properties loadProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();

        try (InputStream input = this.getClass().getResourceAsStream(propertiesFilePath)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + propertiesFilePath);
            }
            properties.load(input);
        }

        return properties;
    }
}