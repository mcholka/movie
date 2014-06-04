package com.film.project.control;

import com.film.project.db.Film;
import com.film.project.entity.FilmNotFoundException;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
@Stateless
public class FilmManager {
    private static final Logger logger = Logger.getLogger(FilmManager.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Film film){
        logger.info("Store film " + film.getApiId());
        entityManager.persist(film);
        entityManager.flush();
        logger.info("Film stored");
    }

    public Film findByApiId(Integer id) throws FilmNotFoundException {
        Query query = entityManager.createQuery("" +
                        "SELECT i FROM Film i " +
                        "WHERE i.apiId = :id"
        );
        query.setParameter("id", id);
        try {
            return (Film) query.getSingleResult();
        } catch (NoResultException e){
            throw new FilmNotFoundException(id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Film> findAllFilmsForFileWrite() {
        Query query = entityManager.createQuery("" +
                        "SELECT i FROM Film i " +
                        "where i.voteCount > 0 " +
                        "order by i.voteCount desc"
        );
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Film> findBestFilms() {
        Query query = entityManager.createQuery("" +
                        "SELECT i FROM Film i " +
                        "where i.voteCount > 100 " +
                        "and i.voteAverage > 7 " +
                        "order by i.voteCount desc, " +
                        "i.voteAverage desc"
        );
        return query.getResultList();
    }
}
