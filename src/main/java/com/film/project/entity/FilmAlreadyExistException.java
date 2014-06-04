package com.film.project.entity;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
public class FilmAlreadyExistException extends Exception {
    public FilmAlreadyExistException(){
        super();
    }

    public FilmAlreadyExistException(String s) {
        super(s);
    }
}
