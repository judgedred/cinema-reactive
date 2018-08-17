package com.service;

import com.dao.DaoException;
import com.domain.Film;

import java.math.BigInteger;
import java.util.List;

public interface FilmService {

    Film create(Film film) throws DaoException;

    void update(Film film) throws DaoException;

    void delete(Film film) throws DaoException;

    List<Film> getFilmAll() throws DaoException;

    Film getFilmById(BigInteger id) throws DaoException;

    boolean checkFilmInFilmshow(Film film) throws DaoException;
}
