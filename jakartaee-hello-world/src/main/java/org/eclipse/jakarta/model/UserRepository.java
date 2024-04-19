package org.eclipse.jakarta.model;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.Account;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class UserRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Account create(Account user) {
        logger.info("Creating user " + user.getUserName());
        em.persist(user);

        return user;
    }

    public List<Account> findAll() {
        logger.info("Getting all room");
        return em.createQuery("SELECT c FROM User c", Account.class).getResultList();
    }

    public Optional<Account> findByUsername(String username) {
        logger.info("Getting user by username " + username);
        return Optional.ofNullable(em.find(Account.class, username));
    }

    public void delete(String username) {
        logger.info("Deleting user by username " + username);
        var user = findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        em.remove(user);
    }

    public Account update(Account user) {
        logger.info("Updating user " + user.getUserName());
        return em.merge(user);
    }
}