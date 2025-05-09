package matc.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import matc.entity.Gym;
import matc.persistence.GenericDao;

/**
 * GymWebService - Provides RESTful web services for gym data.
 * Supports retrieving all gyms or a specific gym by ID.
 * Uses JSON format for responses.
 */
@Path("/gyms")
public class GymWebService {

    private final GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

    /**
     * Retrieves a list of all gyms or filters by name if provided.
     *
     * @param name the name of the gym to filter by (optional)
     * @return a Response containing a list of gyms in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGyms(@QueryParam("name") String name) {
        List<Gym> gyms;

        // Check if a name query parameter is provided and filter gyms by name
        if (name != null && !name.isEmpty()) {
            gyms = gymDao.findByPropertyEqual("name", name);
        } else {
            gyms = gymDao.getAll();
        }

        return Response.ok(gyms).build();
    }

    /**
     * Retrieves a specific gym by its ID.
     *
     * @param id the ID of the gym to retrieve
     * @return a Response containing the gym data in JSON format, or a 404 status if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGymById(@PathParam("id") int id) {
        Gym gym = gymDao.getById(id);
        // Return a 404 response if the gym with the specified ID does not exist
        if (gym == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Gym not found with ID: " + id).build();
        }
        return Response.ok(gym).build();
    }
}