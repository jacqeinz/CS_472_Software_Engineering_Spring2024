/*
 * RoomReportResource.java
 */
package org.eclipse.jakarta.rest;

// Imports
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;
import org.eclipse.jakarta.model.RoomReportRepository;
import org.eclipse.jakarta.model.entity.RoomReport;
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
 * This class constitutes the resources for the entity RoomReport.
 * 
 * @author Team ABCFG
 */
@Path("roomReports")
public class RoomReportResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private RoomReportRepository roomReportRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public RoomReport findRoomReport(@PathParam("id") Long id) {
        logger.info("Getting room report by id " + id);
        return roomReportRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<RoomReport> findAll() {
        logger.info("Getting all room reports");
        return roomReportRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReport roomReport(RoomReport roomReport) {
        logger.info("Creating room report " + roomReport.getRoomNumber());
        try{
            return roomReportRepository.create(roomReport);
        }catch (PersistenceException ex){
            logger.info("Error creating room report " + roomReport.getRoomNumber());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room report by id " + id);
        try{
        	roomReportRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting room report by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReport update(RoomReport roomReport) {
        logger.info("Updating room report " + roomReport.getRoomNumber());
        try{
            return roomReportRepository.create(roomReport);
        }catch (PersistenceException ex){
            logger.info("Error updating room report " + roomReport.getRoomNumber());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}