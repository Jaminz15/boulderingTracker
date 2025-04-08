package matc.controller;

import matc.util.PropertiesLoader;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

/**
 * This class loads Cognito properties once at application startup and stores them in the application scope.
 */
@WebListener
public class StartupServlet implements ServletContextListener, PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private Properties properties;

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
            logger.error("Error loading properties: " + e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No cleanup needed
    }
}