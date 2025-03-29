package matc.rest;

import matc.entity.Gym;
import matc.persistence.GenericDao;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/gyms")
@Produces(MediaType.APPLICATION_JSON)
public class GymWebService {

    private final GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

    /**
     * Get all gyms
     * Endpoint: GET /services/gyms
     */
    @GET
    public Response getAllGyms() {
        List<Gym> gyms = gymDao.getAll();
        return Response.ok(gyms).build();
    }

    /**
     * Get a gym by ID
     * Endpoint: GET /services/gyms/{id}
     */
    @GET
    @Path("/{id}")
    public Response getGymById(@PathParam("id") int id) {
        Gym gym = gymDao.getById(id);
        if (gym != null) {
            return Response.ok(gym).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Gym not found").build();
        }
    }
}