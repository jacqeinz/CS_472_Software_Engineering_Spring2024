package org.eclipse.jakarta.model;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.ReservationDetails;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class ReservationDetailsRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public ReservationDetails create(ReservationDetails reservationDetails) {
        logger.info("Creating reservation " + reservationDetails.getId());
        em.persist(reservationDetails);

        return reservationDetails;
    }

    public List<ReservationDetails> findAll() {
        logger.info("Getting all reservation Details");
        return em.createQuery("SELECT c FROM Room c", ReservationDetails.class).getResultList();
    }

    public Optional<ReservationDetails> findById(Long id) {
        logger.info("Getting reservation by id " + id);
        return Optional.ofNullable(em.find(ReservationDetails.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting reservation details by id " + id);
        var reservationDetails = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        em.remove(reservationDetails);
    }

    public ReservationDetails update(ReservationDetails reservationDetails) {
        logger.info("Updating reservation " + reservationDetails.getId());
        return em.merge(reservationDetails);
    }

}
