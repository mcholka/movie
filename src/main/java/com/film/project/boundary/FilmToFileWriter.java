package com.film.project.boundary;

import com.film.project.control.FilmManager;
import com.film.project.db.Film;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by mcholka on 2014-06-03. Enjoy!
 */
@Singleton
@Startup
public class FilmToFileWriter {
    private static final Logger logger = Logger.getLogger(FilmToFileWriter.class);

    @Inject
    private FilmManager filmManager;

    private static final String FILE_LOCATION = "C:\\UG\\MovieDBProject\\best.arff";

    private static final String FILE_INIT = "@relation films\n" +
            "\n" +
            "@attribute ID numeric\n" +
            "@attribute budget numeric\n" +
            "@attribute genre1 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute genre2 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute genre3 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute genre4 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute genre5 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute genre6 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute genre7 {Action,Crime,Drama,Thriller,Adventure,Science,Fiction,Western,Fantasy,\"\",Animation,Family,Disaster,Romance,Mystery,Comedy,War,History,Horror,Sport,Musical,Suspense,Neo-noir,Music,Holiday,Sports,Film,Noir,Indie,Short,Documentary,Foreign,Road,Movie,Erotic,Eastern,TV,movie,Kids,Sporting,Event,Fan,Sci-Fi,&,British}\n" +
            "@attribute popularity numeric\n" +
            "@attribute releasedate date \"yyyy-MM-dd\"\n" +
            "@attribute revenue numeric\n" +
            "@attribute title string\n" +
            "@attribute voteaverage numeric\n" +
            "@attribute votecount numeric\n" +
            "\n" +
            "@data\n\n";

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void write() throws IOException {
        List<Film> films = findAllToStore();
        logger.info("Start write");
        Files.write(Paths.get(FILE_LOCATION), FILE_INIT.getBytes());

        for (Film film : films) {
            logger.info("Add film " + film.getId());
            String toApend = buildContent(film);
            Files.write(Paths.get(FILE_LOCATION), toApend.getBytes(), StandardOpenOption.APPEND);
            logger.info("Added");
        }
    }

    private List<Film> findAllToStore() {
        return filmManager.findBestFilms();
    }

    @SuppressWarnings("all")
    private String buildContent(Film film) {
        String content;
        String[] genres;
        if(film.getGenre() != null) {
            genres = film.getGenre().trim().split(" ");
        } else {
            genres = null;
        }
        content = film.getId() + "," +film.getBuget() + ",";

        for(int i = 0; i < 7; i++){
            try {
                content = content + "\"" + genres[i] + "\",";
            } catch (Exception e){
                content = content + "\"\",";
            }
        }
        content = content +
                film.getPopularity() + "," +
                "\"" + new DateTime(film.getReleaseDate()).toString("yyyy-MM-dd") + "\"," +
                film.getRevenue() + "," +
                "\"" +film.getTitle().replaceAll("\"", "") + "\"," +
                film.getVoteAverage() + "," +
                BigDecimal.valueOf(film.getVoteCount()) + "\n";
        logger.info("Content to add: " + content);
        return content;
    }
}
