package com.film.project.entity;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
public class FilmNotFoundException extends Exception {
    public FilmNotFoundException(Integer id) {
        super("Ok, no film with id: " + id);
    }
}
