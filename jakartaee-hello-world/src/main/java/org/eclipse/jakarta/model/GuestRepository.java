package org.eclipse.jakarta.model;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.Guest;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class GuestRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Guest create(Guest guest) {
        logger.info("Creating user " + guest.getUserName());
        em.persist(guest);

        return guest;
    }

    public List<Guest> findAll() {
        logger.info("Getting all room");
        return em.createQuery("SELECT c FROM User c", Guest.class).getResultList();
    }

    public Optional<Guest> findByUsername(String username) {
        logger.info("Getting user by username " + username);
        return Optional.ofNullable(em.find(Guest.class, username));
    }

    public void delete(String username) {
        logger.info("Deleting user by username " + username);
        var guest = findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        em.remove(guest);
    }

    public Guest update(Guest guest) {
        logger.info("Updating user " + guest.getUserName());
        return em.merge(guest);
    }
}