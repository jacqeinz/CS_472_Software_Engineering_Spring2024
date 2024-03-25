package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.UserRepository;
import org.eclipse.jakarta.model.entity.User;

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


@Path("users")
public class UserResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private UserRepository userRepository;

    @GET
    @Path("{username}")
    @Produces("application/json")
    public User findRoom(@PathParam("username") String username) {
        logger.info("Getting user by username " + username);
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<User> findAll() {
        logger.info("Getting all users");
        return userRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public User room(User user) {
        logger.info("Creating room " + user.getUserName());
        try{
            return userRepository.create(user);
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
        	userRepository.delete(username);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting coffee by id " + username);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public User update(User user) {
        logger.info("Updating room " + user.getUserName());
        try{
            return userRepository.create(user);
        }catch (PersistenceException ex){
            logger.info("Error updating user " + user.getUserName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}