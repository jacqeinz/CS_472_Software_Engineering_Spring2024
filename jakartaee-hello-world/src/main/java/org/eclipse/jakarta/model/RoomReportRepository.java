/*
 * RoomReportRepository.java
 */
package org.eclipse.jakarta.model;

// Imports
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.eclipse.jakarta.model.entity.RoomReport;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * This class constitutes the repository for the entity RoomReport.
 * 
 * @author Team ABCFG
 */
@Stateless
public class RoomReportRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public RoomReport create(RoomReport roomReport) {
        logger.info("Creating room report " + roomReport.getRoomNumber());
        em.persist(roomReport);

        return roomReport;
    }

    public List<RoomReport> findAll() {
        logger.info("Getting all room report");
        return em.createQuery("SELECT c FROM RoomReport c", RoomReport.class).getResultList();
    }

    public Optional<RoomReport> findById(Long id) {
        logger.info("Getting room report by id " + id);
        return Optional.ofNullable(em.find(RoomReport.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting room report by id " + id);
        var roomReport = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid room report Id:" + id));
        em.remove(roomReport);
    }

    public RoomReport update(RoomReport roomReport) {
        logger.info("Updating room report " + roomReport.toString());
        return em.merge(roomReport);
    }
}