/*
 * RoomReportGuestRepository.java
 */
package org.eclipse.jakarta.model;

// Imports
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.eclipse.jakarta.model.entity.RoomReportGuest;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * This class constitutes the repository for the entity RoomReportGuest.
 * 
 * @author Team ABCFG
 */
@Stateless
public class RoomReportGuestRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public RoomReportGuest create(RoomReportGuest roomReportGuest) {
        logger.info("Creating room report guest " + roomReportGuest.getRoomReport().getRoomNumber());
        em.persist(roomReportGuest);

        return roomReportGuest;
    }

    public List<RoomReportGuest> findAll() {
        logger.info("Getting all room report guest");
        return em.createQuery("SELECT c FROM RoomReportGuest c", RoomReportGuest.class).getResultList();
    }

    public Optional<RoomReportGuest> findById(Long id) {
        logger.info("Getting room report guest by id " + id);
        return Optional.ofNullable(em.find(RoomReportGuest.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting room report guest by id " + id);
        var roomReportGuest = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid room report guest Id:" + id));
        em.remove(roomReportGuest);
    }

    public RoomReportGuest update(RoomReportGuest roomReportGuest) {
        logger.info("Updating room report guest " + roomReportGuest.toString());
        return em.merge(roomReportGuest);
    }
}