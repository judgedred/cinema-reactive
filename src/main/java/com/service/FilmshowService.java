package com.service;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FilmshowService {

    Filmshow save(Filmshow filmshow);

    void delete(Filmshow filmshow);

    List<Filmshow> getFilmshowAll();

    Optional<Filmshow> getFilmshowById(BigInteger id);

    boolean checkFilmshowInTicket(Filmshow filmshow);

    List<Filmshow> getFilmshowToday();

    List<Filmshow> getFilmshowWeek();

    List<Filmshow> getFilmshowByFilm(Film film);

    List<Filmshow> getFilmshowByHall(Hall hall);
}
