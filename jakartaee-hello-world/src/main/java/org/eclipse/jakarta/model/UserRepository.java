package org.eclipse.jakarta.model;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.User;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class UserRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public User create(User user) {
        logger.info("Creating user " + user.getUserName());
        em.persist(user);

        return user;
    }

    public List<User> findAll() {
        logger.info("Getting all room");
        return em.createQuery("SELECT c FROM User c", User.class).getResultList();
    }

    public Optional<User> findByUsername(String username) {
        logger.info("Getting user by username " + username);
        return Optional.ofNullable(em.find(User.class, username));
    }

    public void delete(String username) {
        logger.info("Deleting user by username " + username);
        var user = findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        em.remove(user);
    }

    public User update(User user) {
        logger.info("Updating user " + user.getUserName());
        return em.merge(user);
    }
}