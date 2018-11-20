package com.service;

import com.dao.DaoException;
import com.domain.Filmshow;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FilmshowService {

    Filmshow save(Filmshow filmshow) throws DaoException;

    void delete(Filmshow filmshow) throws DaoException;

    List<Filmshow> getFilmshowAll() throws DaoException;

    Optional<Filmshow> getFilmshowById(BigInteger id) throws DaoException;

    boolean checkFilmshowInTicket(Filmshow filmshow) throws DaoException;

    List<Filmshow> getFilmshowToday() throws DaoException;

    List<Filmshow> getFilmshowWeek() throws DaoException;

}
