/*
 * RoomReportAdminRepository.java
 */
package org.eclipse.jakarta.model;

// Imports
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.eclipse.jakarta.model.entity.RoomReportAdmin;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * This class constitutes the repository for the entity RoomReportAdmin.
 * 
 * @author Team ABCFG
 */
@Stateless
public class RoomReportAdminRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public RoomReportAdmin create(RoomReportAdmin roomReportAdmin) {
        logger.info("Creating room report admin " + roomReportAdmin.getRoomReport().getRoomNumber());
        em.persist(roomReportAdmin);

        return roomReportAdmin;
    }

    public List<RoomReportAdmin> findAll() {
        logger.info("Getting all room report admin");
        return em.createQuery("SELECT c FROM RoomReportAdmin c", RoomReportAdmin.class).getResultList();
    }

    public Optional<RoomReportAdmin> findById(Long id) {
        logger.info("Getting room report admin by id " + id);
        return Optional.ofNullable(em.find(RoomReportAdmin.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting room report admin by id " + id);
        var roomReportAdmin = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid room report admin Id:" + id));
        em.remove(roomReportAdmin);
    }

    public RoomReportAdmin update(RoomReportAdmin roomReportAdmin) {
        logger.info("Updating room report admin " + roomReportAdmin.toString());
        return em.merge(roomReportAdmin);
    }
}