package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.ConfirmationRepository;
import org.eclipse.jakarta.model.entity.Confirmation;

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
@Path("confirmations")
public class ConfirmationResource {

	private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    @Inject
    private ConfirmationRepository confirmationRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Confirmation findConfirmation(@PathParam("id") Long id) {
        logger.info("Getting room by id " + id);
        return confirmationRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Confirmation> findAll() {
        logger.info("Getting all confirmations");
        return confirmationRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Confirmation confirmation(Confirmation confirmation) {
        logger.info("Creating room " + confirmation.getId());
        try{
            return confirmationRepository.create(confirmation);
        }catch (PersistenceException ex){
            logger.info("Error creating room " + confirmation.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room by id " + id);
        try{
        	confirmationRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting coffee by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Confirmation update(Confirmation confirmation) {
        logger.info("Updating confirmation " + confirmation.getId());
        try{
            return confirmationRepository.create(confirmation);
        }catch (PersistenceException ex){
            logger.info("Error updating confirmation " + confirmation.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}

