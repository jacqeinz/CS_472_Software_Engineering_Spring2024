package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.CartRepository;
import org.eclipse.jakarta.model.entity.Cart;

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
public class CartResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private CartRepository cartRepository;

    @GET
    @Path("{username}")
    @Produces("application/json")
    public Cart findRoom(@PathParam("username") String username) {
        logger.info("Getting user by username " + username);
        return cartRepository.findByUsername(username)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Cart> findAll() {
        logger.info("Getting all users");
        return cartRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Cart room(Cart cart) {
        logger.info("Creating room " + cart.getId());
        try{
            return cartRepository.create(cart);
        }catch (PersistenceException ex){
            logger.info("Error creating user " + cart.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{username}")
    public void delete(@PathParam("username") String username) {
        logger.info("Deleting user by username " + username);
        try{
        	cartRepository.delete(username);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting coffee by id " + username);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Cart update(Cart cart) {
        logger.info("Updating room " + cart.getId());
        try{
            return cartRepository.create(cart);
        }catch (PersistenceException ex){
            logger.info("Error updating user " + cart.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}