package com.service;

import com.dao.DaoException;
import com.dao.FilmDao;
import com.dao.FilmshowDao;
import com.domain.Film;
import com.domain.Filmshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private FilmshowDao filmshowDao;

    @Override
    public Film create(Film film) throws DaoException {
        return filmDao.create(film);
    }

    @Override
    public void update(Film film) throws DaoException {
        filmDao.update(film);
    }

    @Override
    public void delete(Film film) throws DaoException {
        filmDao.delete(film);
    }

    @Override
    public List<Film> getFilmAll() throws DaoException {
        return filmDao.getFilmAll();
    }

    @Override
    public Film getFilmById(int id) throws DaoException {
        return filmDao.getFilmById(id);
    }

    @Override
    public boolean checkFilmInFilmshow(Film film) throws DaoException {
        List<Filmshow> filmshowList = filmshowDao.getFilmshowAllByFilm(film);
        if (filmshowList != null) {
            return true;
        }
        return false;
    }
}
