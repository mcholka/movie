package com.film.project.boundary;

import com.film.project.control.CountManager;
import com.film.project.control.FilmCollector;
import com.film.project.db.Counter;
import org.apache.log4j.Logger;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
@Singleton
@Startup
public class FilmColectorTimer {
    private static final Logger logger = Logger.getLogger(FilmColectorTimer.class);
    @Inject
    FilmCollector filmCollector;
    @Inject
    CountManager countManager;

//    @Schedule(persistent = false, hour = "*", minute = "*/5")
    public void collect() {
        logger.info("Start FilmColectorTimer work");
        Counter lastCount = countManager.getLastParsedPage();
        if(lastCount != null){
            filmCollector.collect(lastCount);
        } else {
            lastCount = storeCounter();
            filmCollector.collect(lastCount);
        }
        logger.info("End FilmColectorTimer work");
    }

    private Counter storeCounter() {
        Counter counter = new Counter();
        counter.setCount(2);
        return countManager.persist(counter);
    }
}

