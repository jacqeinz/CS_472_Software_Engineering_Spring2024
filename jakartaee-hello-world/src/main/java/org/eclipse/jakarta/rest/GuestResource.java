package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.GuestRepository;
import org.eclipse.jakarta.model.entity.Guest;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;


@Path("guests")
public class GuestResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private GuestRepository guestRepository;

    @GET
    @Path("{username}")
    @Produces("application/json")
    public Guest findRoom(@PathParam("username") String username) {
        logger.info("Getting user by username " + username);
        return guestRepository.findByUsername(username)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Guest> findAll() {
        logger.info("Getting all users");
        return guestRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Guest room(Guest user) {
        logger.info("Creating room " + user.getUserName());
        try{
            return guestRepository.create(user);
        }catch (PersistenceException ex){
            logger.info("Error creating user " + user.getUserName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{username}")
    public void delete(@PathParam("username") String username) {
        logger.info("Deleting user by username " + username);
        try{
        	guestRepository.delete(username);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting coffee by id " + username);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Guest update(Guest guest) {
        logger.info("Updating room " + guest.getUserName());
        try{
            return guestRepository.create(guest);
        }catch (PersistenceException ex){
            logger.info("Error updating user " + guest.getUserName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}