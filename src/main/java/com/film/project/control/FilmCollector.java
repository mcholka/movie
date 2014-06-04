package com.film.project.control;

import com.film.project.db.Counter;
import com.film.project.db.Film;
import com.film.project.entity.FilmAlreadyExistException;
import com.film.project.entity.FilmNotFoundException;
import com.film.project.entity.Genres;
import com.film.project.entity.JSONFilm;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
public class FilmCollector {
    private static final Logger logger = Logger.getLogger(FilmCollector.class);
    private static final String FILM_URL =
            "https://api.themoviedb.org/3/movie/%1$s?api_key=470fd2ec8853e25d2f8d86f685d2270e";

    @Inject
    CountManager countManager;
    @Inject
    FilmManager filmManager;

    public void collect(Counter counter) {
        logger.info("Start FilmCollector work");
        Integer currentId = counter.getCount();
        Date limitDate = getLimit();

        while(true) {
            if(currentRunExceeded(limitDate)){
                logger.warn("Current work exceeded! Update counter and finish");
                updateCounter(counter, currentId);
                break;
            }
            processCollect(currentId);
            currentId++;
        }
    }

    private Date getLimit() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 4);
        calendar.add(Calendar.SECOND, 45);
        return calendar.getTime();
    }

    private boolean currentRunExceeded(Date limitDate) {
        return new Date().after(limitDate);
    }

    private void updateCounter(Counter counter, Integer currentId) {
        counter.setCount(currentId);
    }

    private void processCollect(Integer id) {
        String url = String.format(FILM_URL, id.toString());
        logger.info("URL to invoke: " + url);
        ClientRequest clientRequest = new ClientRequest(url);
        try {
            ClientResponse response = clientRequest.get();
            JSONFilm film = (JSONFilm) response.getEntity(JSONFilm.class);
            logger.info("Received correct film " + film.getId());
            checkExist(film);
            Film dbFilm = mappingFilm(film);
            store(dbFilm);
        } catch (FilmAlreadyExistException e){
            logger.info(e.getMessage());
        } catch (Exception e) {
            if(e.getMessage().contains("Unrecognized field \"status_code\"")){
                logger.info("No film with id " + id + " skip it");
            } else {
                logger.error(e);
            }
        }
    }

    private void checkExist(JSONFilm film) throws FilmAlreadyExistException {
        try {
            filmManager.findByApiId(film.getId());
            throw new FilmAlreadyExistException("Film with id " + film.getId() + " already exist, skip it");
        } catch (FilmNotFoundException e) {
            logger.info("Ok, no film with id: " + film.getId());
        }
    }

    private Film mappingFilm(JSONFilm film) {
        Film dbFilm = new Film();
        dbFilm.setTitle(film.getTitle());
        dbFilm.setOriginalTitle(film.getOriginal_title());
        dbFilm.setBuget(BigDecimal.valueOf(film.getBudget()));
        dbFilm.setApiId(film.getId());
        String genre = "";
        for(Genres genres : film.getGenres()){
            genre = genre + " " + genres.getName();
        }

        genre = genre.trim();

        if(genre.length() != 0) {
            dbFilm.setGenre(genre);
        }
        dbFilm.setOverview(film.getOverview());
        dbFilm.setPopularity(film.getPopularity());
        dbFilm.setReleaseDate(DateTime.parse(film.getRelease_date()).toDate());
        dbFilm.setRevenue(BigDecimal.valueOf(film.getRevenue()));
        dbFilm.setVoteAverage(film.getVote_average());
        dbFilm.setVoteCount(film.getVote_count());

        return dbFilm;
    }


    private void store(Film dbFilm) {
        filmManager.persist(dbFilm);
    }
}
