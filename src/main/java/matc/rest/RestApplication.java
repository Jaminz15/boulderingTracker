package matc.rest;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.util.*;

/**
 * RestApplication - Configures the JAX-RS application for the BoulderBook project.
 * Sets up REST endpoints and registers context resolvers and features.
 * Uses the /services path for all RESTful APIs.
 */
@ApplicationPath("/services")
public class RestApplication extends Application {

    /**
     * Registers the classes that will be used as JAX-RS resources and providers.
     *
     * @return a set of resource and provider classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Register RESTful web service and JSON feature classes
        classes.add(GymWebService.class);
        classes.add(JacksonFeature.class);
        classes.add(ObjectMapperContextResolver.class);
        return classes;
    }

    /**
     * Registers singleton instances that are used as context resolvers.
     * Configures a custom ObjectMapper for JSON serialization.
     *
     * @return a set of singleton objects used for context resolution
     */
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();

        singletons.add((javax.ws.rs.ext.ContextResolver<ObjectMapper>) type -> {
            // Create a custom ObjectMapper for handling JSON with Java 8 date/time support
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper;
        });

        return singletons;
    }
}