package org.eclipse.jakarta.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.RoomRepository;
import org.eclipse.jakarta.model.entity.Room;

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


@Path("rooms")
public class RoomResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private RoomRepository roomRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Room findRoom(@PathParam("id") Long id) {
        logger.info("Getting room by id " + id);
        return roomRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Room> findAll() {
        logger.info("Getting all coffee");
        return roomRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Room room(Room room) {
        logger.info("Creating room " + room.getName());
        try{
            return roomRepository.create(room);
        }catch (PersistenceException ex){
            logger.info("Error creating coffee " + room.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting coffee by id " + id);
        try{
        	roomRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.info("Error deleting coffee by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Room update(Room room) {
        logger.info("Updating coffee " + room.getName());
        try{
            return roomRepository.create(room);
        }catch (PersistenceException ex){
            logger.info("Error updating coffee " + room.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}