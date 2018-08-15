package com.service;

import com.dao.DaoException;
import com.domain.Filmshow;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

public interface FilmshowService {

    Filmshow create(Filmshow filmshow) throws DaoException;

    void update(Filmshow filmshow) throws DaoException;

    void delete(Filmshow filmshow) throws DaoException;

    List<Filmshow> getFilmshowAll() throws DaoException;

    Filmshow getFilmshowById(int id) throws DaoException;

    boolean checkFilmshowInTicket(Filmshow filmshow) throws DaoException;

    List<Filmshow> getFilmshowToday() throws DaoException;

    List<Filmshow> getFilmshowWeek() throws DaoException;

    Map<LocalDate, List<Filmshow>> groupFilmshowByDate(List<Filmshow> filmshowList);
}
