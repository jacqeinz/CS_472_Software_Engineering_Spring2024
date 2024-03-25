/*
 * RoomReportAdminResource.java
 */
package org.eclipse.jakarta.rest;

// Imports
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;
import org.eclipse.jakarta.model.RoomReportAdminRepository;
import org.eclipse.jakarta.model.entity.RoomReportAdmin;
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
 * This class constitutes the resources for the entity RoomReportAdmin.
 * 
 * @author Team ABCFG
 */
@Path("roomReports")
public class RoomReportAdminResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private RoomReportAdminRepository roomReportAdminRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public RoomReportAdmin findRoomReportAdmin(@PathParam("id") Long id) {
        logger.info("Getting room report admin by id " + id);
        return roomReportAdminRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<RoomReportAdmin> findAll() {
        logger.info("Getting all room admin reports");
        return roomReportAdminRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReportAdmin roomReportAdmin(RoomReportAdmin roomReportAdmin) {
        logger.info("Creating room report admin " + roomReportAdmin.getRoomReport().getRoomNumber());
        try{
            return roomReportAdminRepository.create(roomReportAdmin);
        }catch (PersistenceException ex){
            logger.info("Error creating room report admin " + roomReportAdmin.getRoomReport().getRoomNumber());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting room report admin by id " + id);
        try{
        	roomReportAdminRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting room report admin by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public RoomReportAdmin update(RoomReportAdmin roomReportAdmin) {
        logger.info("Updating room report admin " + roomReportAdmin.getRoomReport().getRoomNumber());
        try{
            return roomReportAdminRepository.create(roomReportAdmin);
        }catch (PersistenceException ex){
            logger.info("Error updating room report admin " + roomReportAdmin.getRoomReport().getRoomNumber());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}