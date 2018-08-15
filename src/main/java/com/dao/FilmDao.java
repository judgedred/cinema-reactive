package com.dao;

import com.domain.Film;

import java.util.List;

public interface FilmDao {

    Film create(Film film) throws DaoException;

    void update(Film film) throws DaoException;

    void delete(Film film) throws DaoException;

    List<Film> getFilmAll() throws DaoException;

    Film getFilmById(int id) throws DaoException;
}