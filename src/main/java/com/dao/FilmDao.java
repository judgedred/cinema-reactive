package com.dao;

import com.domain.Film;

import java.math.BigInteger;
import java.util.List;

public interface FilmDao {

    Film create(Film film) throws DaoException;

    void update(Film film) throws DaoException;

    void delete(Film film) throws DaoException;

    List<Film> getFilmAll() throws DaoException;

    Film getFilmById(BigInteger id) throws DaoException;
}