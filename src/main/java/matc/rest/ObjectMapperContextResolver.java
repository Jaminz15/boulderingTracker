package matc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * ObjectMapperContextResolver - Configures the Jackson ObjectMapper for JAX-RS.
 * Registers the JavaTimeModule to support Java 8 date and time types.
 * Disables writing dates as timestamps for better readability.
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    /**
     * Constructs a new ObjectMapperContextResolver.
     * Configures the ObjectMapper to handle Java 8 date and time types.
     * Disables writing dates as timestamps.
     */
    public ObjectMapperContextResolver() {
        objectMapper = new ObjectMapper();
        // Register JavaTimeModule to support LocalDate and LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        // Disable serialization of dates as timestamps for better readability
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Provides the configured ObjectMapper instance to JAX-RS.
     *
     * @param type the class type for which the context is being requested
     * @return the configured ObjectMapper
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}