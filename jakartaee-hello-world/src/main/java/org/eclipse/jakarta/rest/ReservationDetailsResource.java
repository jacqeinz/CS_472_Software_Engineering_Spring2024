package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.ReservationDetailsRepository;
import org.eclipse.jakarta.model.entity.ReservationDetails;

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
@Path("ReservationDetails")
public class ReservationDetailsResource {
	private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private ReservationDetailsRepository reservationDetailsRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public ReservationDetails findReservationDetails(@PathParam("id") Long id) {
        logger.info("Getting room by id " + id);
        return reservationDetailsRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<ReservationDetails> findAll() {
        logger.info("Getting all rooms");
        return reservationDetailsRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ReservationDetails reservationDetails(ReservationDetails reservationDetails) {
        logger.info("Creating reservation " + reservationDetails.getId());
        try{
            return reservationDetailsRepository.create(reservationDetails);
        }catch (PersistenceException ex){
            logger.info("Error creating reservation " + reservationDetails.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room by id " + id);
        try{
        	reservationDetailsRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting reservation by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public ReservationDetails update(ReservationDetails reservationDetails) {
        logger.info("Updating reservation details " + reservationDetails.getId());
        try{
            return reservationDetailsRepository.create(reservationDetails);
        }catch (PersistenceException ex){
            logger.info("Error updating room details" + reservationDetails.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

}
