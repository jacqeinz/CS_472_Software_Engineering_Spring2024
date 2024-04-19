package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.RoomReservationRepository;
import org.eclipse.jakarta.model.entity.RoomReservation;

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


@Path("roomReservations")
public class RoomReservationResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private RoomReservationRepository roomReservationRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public RoomReservation findRoomReservation(@PathParam("id") Long id) {
        logger.info("Getting roomReservation by id " + id);
        return roomReservationRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<RoomReservation> findAll() {
        logger.info("Getting all room reservations");
        return roomReservationRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReservation roomReservation(RoomReservation roomReservation) {
        logger.info("Creating room reservation " + roomReservation.getId());
        try{
            return roomReservationRepository.create(roomReservation);
        }catch (PersistenceException ex){
            logger.info("Error creating roomreservation " + roomReservation.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room by id " + id);
        try{
        	roomReservationRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting coffee by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReservation update(RoomReservation roomReservation) {
        logger.info("Updating room " + roomReservation.getId());
        try{
            return roomReservationRepository.create(roomReservation);
        }catch (PersistenceException ex){
            logger.info("Error updating room reservation" + roomReservation.getId());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}