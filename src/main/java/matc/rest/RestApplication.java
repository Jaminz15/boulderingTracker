package matc.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * REST configuration for BoulderBook application.
 * Registers JAX-RS resources and sets the base URI path.
 */
@ApplicationPath("/services")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(GymWebService.class); // Register Gym REST endpoint
        return classes;
    }
}