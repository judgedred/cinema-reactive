package com.dao;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;

import java.util.Date;
import java.util.List;

public interface FilmshowDao {

    Filmshow create(Filmshow filmshow) throws DaoException;

    void update(Filmshow filmshow) throws DaoException;

    void delete(Filmshow filmshow) throws DaoException;

    List<Filmshow> getFilmshowAll() throws DaoException;

    Filmshow getFilmshowById(int id) throws DaoException;

    List<Filmshow> getFilmshowAllByFilm(Film film) throws DaoException;

    List<Filmshow> getFilmshowAllByHall(Hall hall) throws DaoException;

    List<Filmshow> getFilmshowAllByDate(Date startDate, Date endDate) throws DaoException;
}
