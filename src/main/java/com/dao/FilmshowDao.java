package com.dao;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;

import java.util.Date;
import java.util.List;

public interface FilmshowDao
{
	public Filmshow create(Filmshow filmshow) throws DaoException;
	public void update(Filmshow filmshow) throws DaoException;
	public void delete(Filmshow filmshow) throws DaoException;
	public List<Filmshow> getFilmshowAll() throws DaoException;
	public Filmshow getFilmshowById(int id) throws DaoException;
    public List<Filmshow> getFilmshowAllByFilm(Film film) throws DaoException;
    public List<Filmshow> getFilmshowAllByHall(Hall hall) throws DaoException;
    public List<Filmshow> getFilmshowAllByDate(Date startDate, Date endDate) throws DaoException;
}
