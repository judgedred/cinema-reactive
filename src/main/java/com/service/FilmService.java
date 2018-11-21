package com.service;

import com.domain.Film;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FilmService {

    Film save(Film film);

    void delete(Film film);

    List<Film> getFilmAll();

    Optional<Film> getFilmById(BigInteger id);

    Optional<Film> getFilmByName(String filmName);

    boolean checkFilmInFilmshow(Film film);
}
