package org.eclipse.jakarta.model;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.Reservation;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class ReservationRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Reservation create(Reservation reservation) {
        logger.info("Creating reservation " + reservation.getId());
        em.persist(reservation);

        return reservation;
    }

    public List<Reservation> findAll() {
        logger.info("Getting all reservations");
        return em.createQuery("SELECT c FROM Room c", Reservation.class).getResultList();
    }

    public Optional<Reservation> findById(Long id) {
        logger.info("Getting reservation by id " + id);
        return Optional.ofNullable(em.find(Reservation.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting reservation by id " + id);
        var reservation = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        em.remove(reservation);
    }

    public Reservation update(Reservation reservation) {
        logger.info("Updating reservation " + reservation.getId());
        return em.merge(reservation);
    }
}