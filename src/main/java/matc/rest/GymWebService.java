package matc.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import matc.entity.Gym;
import matc.persistence.GenericDao;

import java.util.List;

@Path("/gyms")
public class GymWebService {

    private final GenericDao<Gym> gymDao = new GenericDao<>(Gym.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGyms(@QueryParam("name") String name) {
        List<Gym> gyms;

        if (name != null && !name.isEmpty()) {
            gyms = gymDao.findByPropertyEqual("name", name);
        } else {
            gyms = gymDao.getAll();
        }

        return Response.ok(gyms).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGymById(@PathParam("id") int id) {
        Gym gym = gymDao.getById(id);
        if (gym == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Gym not found with ID: " + id).build();
        }
        return Response.ok(gym).build();
    }
}