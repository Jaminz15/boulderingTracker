package matc.util;

import matc.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Provides access to the database
 */
public class Database implements PropertiesLoader {

    // Singleton instance
    private static final Database instance = new Database();

    // Logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    private Properties properties;
    private Connection connection;

    private Database() {
        properties = loadProperties("/database.properties"); // Use PropertiesLoader
    }

    /** Get the singleton instance **/
    public static Database getInstance() {
        return instance;
    }

    /** Get database connection **/
    public Connection getConnection() {
        return connection;
    }

    /** Connect to the database **/
    public void connect() throws Exception {
        if (connection != null) return;

        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new Exception("Database.connect()... Error: MySQL Driver not found", e);
        }

        String url = properties.getProperty("url");
        connection = DriverManager.getConnection(
                url,
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }

    /** Close the database connection **/
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close connection", e);
            }
        }
        connection = null;
    }

    /**
     * Run an SQL script file
     * @param sqlFile the file to execute
     */
    public void runSQL(String sqlFile) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(sqlFile)))
        ) {
            connect();
            Statement stmt = connection.createStatement();
            StringBuilder sql = new StringBuilder();

            int ch;
            while ((ch = br.read()) != -1) {
                char inputValue = (char) ch;
                if (inputValue == ';') {
                    stmt.executeUpdate(sql.toString());
                    sql.setLength(0); // Reset string buffer
                } else {
                    sql.append(inputValue);
                }
            }
        } catch (Exception e) {
            logger.error("Error executing SQL script: " + sqlFile, e);
        } finally {
            disconnect();
        }
    }
}