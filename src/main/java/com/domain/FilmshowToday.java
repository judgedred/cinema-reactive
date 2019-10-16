package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document
public class FilmshowToday implements Serializable {

    private List<Filmshow> filmshows;

    public FilmshowToday(List<Filmshow> filmshows) {
        this.filmshows = filmshows;
    }

    public List<Filmshow> getFilmshows() {
        return filmshows;
    }
}