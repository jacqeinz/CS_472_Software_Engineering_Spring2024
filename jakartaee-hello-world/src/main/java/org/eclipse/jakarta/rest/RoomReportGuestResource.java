/*
 * RoomReportGuestResource.java
 */
package org.eclipse.jakarta.rest;

// Imports
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;
import org.eclipse.jakarta.model.RoomReportGuestRepository;
import org.eclipse.jakarta.model.entity.RoomReportGuest;
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

/**
 * This class constitutes the resources for the entity RoomReportGuest.
 * 
 * @author Team ABCFG
 */
@Path("roomReports")
public class RoomReportGuestResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private RoomReportGuestRepository roomReportGuestRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public RoomReportGuest findRoomReportGuest(@PathParam("id") Long id) {
        logger.info("Getting room report guest by id " + id);
        return roomReportGuestRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<RoomReportGuest> findAll() {
        logger.info("Getting all guest room reports");
        return roomReportGuestRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReportGuest roomReportGuest(RoomReportGuest roomReportGuest) {
        logger.info("Creating room report guest " + roomReportGuest.getRoomReport().getRoomNumber());
        try{
            return roomReportGuestRepository.create(roomReportGuest);
        }catch (PersistenceException ex){
            logger.info("Error creating room report guest " + roomReportGuest.getRoomReport().getRoomNumber());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room report guest by id " + id);
        try{
        	roomReportGuestRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting room report guest by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReportGuest update(RoomReportGuest roomReportGuest) {
        logger.info("Updating room report guest " + roomReportGuest.getRoomReport().getRoomNumber());
        try{
            return roomReportGuestRepository.create(roomReportGuest);
        }catch (PersistenceException ex){
            logger.info("Error updating room report guest " + roomReportGuest.getRoomReport().getRoomNumber());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}