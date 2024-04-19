package org.eclipse.jakarta.model;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.Cart;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class CartRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Cart create(Cart cart) {
        logger.info("Creating user " + cart.getId());
        em.persist(cart);

        return cart;
    }

    public List<Cart> findAll() {
        logger.info("Getting all room");
        return em.createQuery("SELECT c FROM User c", Cart.class).getResultList();
    }

    public Optional<Cart> findByUsername(String username) {
        logger.info("Getting user by username " + username);
        return Optional.ofNullable(em.find(Cart.class, username));
    }

    public void delete(String username) {
        logger.info("Deleting user by username " + username);
        var user = findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        em.remove(user);
    }

    public Cart update(Cart cart) {
        logger.info("Updating user " + cart.getId());
        return em.merge(cart);
    }
}