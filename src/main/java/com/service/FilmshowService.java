package com.service;


import com.dao.DaoException;
import com.domain.Filmshow;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

public interface FilmshowService
{
    public Filmshow create(Filmshow filmshow) throws DaoException;
    public void update(Filmshow filmshow) throws DaoException;
    public void delete(Filmshow filmshow) throws DaoException;
    public List<Filmshow> getFilmshowAll() throws DaoException;
    public Filmshow getFilmshowById(int id) throws DaoException;
    public boolean checkFilmshowInTicket(Filmshow filmshow)throws DaoException;
    public List<Filmshow> getFilmshowToday() throws DaoException;
    public List<Filmshow> getFilmshowWeek() throws DaoException;
    public Map<LocalDate,List<Filmshow>> groupFilmshowByDate(List<Filmshow> filmshowList);
}
