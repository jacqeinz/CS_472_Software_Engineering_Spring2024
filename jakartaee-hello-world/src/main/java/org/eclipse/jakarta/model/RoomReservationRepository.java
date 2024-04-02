package org.eclipse.jakarta.model;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.RoomReservation;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class RoomReservationRepository {
	 private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	    @PersistenceContext
	    private EntityManager em;

	    public RoomReservation create(RoomReservation roomReservation) {
	        logger.info("Creating room " + roomReservation.getId());
	        em.persist(roomReservation);

	        return roomReservation;
	    }

	    public List<RoomReservation> findAll() {
	        logger.info("Getting all room reservations");
	        return em.createQuery("SELECT c FROM Room c", RoomReservation.class).getResultList();
	    }

	    public Optional<RoomReservation> findById(Long id) {
	        logger.info("Getting room by id " + id);
	        return Optional.ofNullable(em.find(RoomReservation.class, id));
	    }

	    public void delete(Long id) {
	        logger.info("Deleting room by id " + id);
	        var roomReservation = findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
	        em.remove(roomReservation);
	    }

	    public RoomReservation update(RoomReservation roomReservation) {
	        logger.info("Updating room reeservation" + roomReservation.getId());
	        return em.merge(roomReservation);
	    }

}
