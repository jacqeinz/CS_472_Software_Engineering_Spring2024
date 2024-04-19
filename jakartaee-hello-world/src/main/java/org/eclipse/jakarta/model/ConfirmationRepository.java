package org.eclipse.jakarta.model;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.entity.Confirmation;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



	@Stateless
	public class ConfirmationRepository {
	    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	    @PersistenceContext
	    private EntityManager em;

	    public Confirmation create(Confirmation confirmation) {
	        logger.info("Creating room " + confirmation.getId());
	        em.persist(confirmation);

	        return confirmation;
	    }

	    public List<Confirmation> findAll() {
	        logger.info("Getting all room");
	        return em.createQuery("SELECT c FROM Room c", Confirmation.class).getResultList();
	    }

	    public Optional<Confirmation> findById(Long id) {
	        logger.info("Getting room by id " + id);
	        return Optional.ofNullable(em.find(Confirmation.class, id));
	    }

	    public void delete(Long id) {
	        logger.info("Deleting room by id " + id);
	        var confirmation = findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
	        em.remove(confirmation);
	    }

	    public Confirmation update(Confirmation confirmation) {
	        logger.info("Updating room " + confirmation.getId());
	        return em.merge(confirmation);
	    }
	}


