package matc.controller;

import matc.util.PropertiesLoader;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

/**
 * StartupServlet - Initializes application-wide properties on startup.
 * Loads AWS Cognito configuration from a properties file and stores them in the application scope.
 * Implements ServletContextListener to ensure properties are available throughout the application.
 */
@WebListener
public class StartupServlet implements ServletContextListener, PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private Properties properties;

    /**
     * Called when the application context is initialized.
     * Loads Cognito properties from a file and sets them as application attributes.
     *
     * @param sce the ServletContextEvent containing the context that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try {
            properties = loadProperties("/cognito.properties");

            // Store properties in the application scope so other servlets can access them
            context.setAttribute("CLIENT_ID", properties.getProperty("client.id"));
            context.setAttribute("CLIENT_SECRET", properties.getProperty("client.secret"));
            context.setAttribute("OAUTH_URL", properties.getProperty("oauthURL"));
            context.setAttribute("LOGIN_URL", properties.getProperty("loginURL"));
            context.setAttribute("REDIRECT_URL", properties.getProperty("redirectURL"));
            context.setAttribute("REGION", properties.getProperty("region"));
            context.setAttribute("POOL_ID", properties.getProperty("poolId"));

            logger.info("Cognito properties loaded successfully.");
        } catch (IOException e) {
            logger.error("Error loading properties: {}", e.getMessage(), e);
        }
    }

    /**
     * Called when the application context is destroyed.
     * No cleanup is required in this implementation.
     *
     * @param sce the ServletContextEvent containing the context that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No cleanup needed
    }
}