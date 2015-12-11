package com.service;


import com.dao.DaoException;
import com.domain.Film;
import java.util.List;

public interface FilmService
{
    public Film create(Film film) throws DaoException;
    public void update(Film film) throws DaoException;
    public void delete(Film film) throws DaoException;
    public List<Film> getFilmAll() throws DaoException;
    public Film getFilmById(int id) throws DaoException;
    public boolean checkFilmInFilmshow(Film film) throws DaoException;
}
