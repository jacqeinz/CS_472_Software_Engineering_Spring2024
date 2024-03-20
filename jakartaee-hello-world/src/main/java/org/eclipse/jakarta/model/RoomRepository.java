package org.eclipse.jakarta.model;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.Room;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class RoomRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Room create(Room room) {
        logger.info("Creating room " + room.getName());
        em.persist(room);

        return room;
    }

    public List<Room> findAll() {
        logger.info("Getting all coffee");
        return em.createQuery("SELECT c FROM Coffee c", Room.class).getResultList();
    }

    public Optional<Room> findById(Long id) {
        logger.info("Getting room by id " + id);
        return Optional.ofNullable(em.find(Room.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting room by id " + id);
        var room = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
        em.remove(room);
    }

    public Room update(Room room) {
        logger.info("Updating room " + room.getName());
        return em.merge(room);
    }
}