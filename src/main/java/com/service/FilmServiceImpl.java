package com.service;

import com.dao.DaoException;
import com.dao.FilmDao;
import com.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService
{
    @Autowired
    private FilmDao filmDao;

    @Override
    public Film create(Film film) throws DaoException
    {
        List<Film> filmList = filmDao.getFilmAll();
        boolean filmValid = true;
        for(Film f : filmList)
        {
            if(f.getFilmName().equals(film.getFilmName()) && f.getDescription().equals(film.getDescription()))
            {
                filmValid = false;
                break;
            }
        }
        if(filmValid)
        {
            return filmDao.create(film);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void update(Film film) throws DaoException
    {
        filmDao.update(film);
    }

    @Override
    public void delete(Film film) throws DaoException
    {
        filmDao.delete(film);
    }

    @Override
    public List<Film> getFilmAll() throws DaoException
    {
        return filmDao.getFilmAll();
    }

    @Override
    public Film getFilmById(int id) throws DaoException
    {
        return filmDao.getFilmById(id);
    }
}
