package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.ReservationRepository;
import org.eclipse.jakarta.model.entity.Reservation;

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


@Path("reservations")
public class ReservationResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private ReservationRepository reservationRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Reservation findReservation(@PathParam("id") Long id) {
        logger.info("Getting room by id " + id);
        return reservationRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Reservation> findAll() {
        logger.info("Getting all rooms");
        return reservationRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Reservation reservation(Reservation reservation) {
        logger.info("Creating reservation " + reservation.getId());
        try{
            return reservationRepository.create(reservation);
        }catch (PersistenceException ex){
            logger.info("Error creating reservation " + reservation.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room by id " + id);
        try{
        	reservationRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting reservation by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Reservation update(Reservation reservation) {
        logger.info("Updating reservation " + reservation.getId());
        try{
            return reservationRepository.create(reservation);
        }catch (PersistenceException ex){
            logger.info("Error updating room " + reservation.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}