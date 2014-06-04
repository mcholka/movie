package com.film.project.control;

import com.film.project.db.Counter;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
@Stateless
public class CountManager {
    private static final Logger logger = Logger.getLogger(CountManager.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Counter getLastParsedPage(){
        logger.info("Try to find counter");
        Query query = entityManager.createQuery("" +
                "SELECT i FROM Counter i");
        try {
            Counter counter = (Counter) query.getSingleResult();
            logger.info("counter found");
            return counter;
        } catch (NoResultException e) {
            logger.info("No counter found");
            return null;
        }
    }

    public Counter persist(Counter counter){
        logger.info("Persist new Counter");
        entityManager.persist(counter);
        entityManager.flush();
        logger.info("New Counter persist");
        return counter;
    }
}
