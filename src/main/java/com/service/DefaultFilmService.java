package com.service;

import com.dao.DaoException;
import com.dao.FilmRepository;
import com.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultFilmService implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public Film create(Film film) throws DaoException {
        return filmRepository.save(film);
    }

    @Override
    public void update(Film film) throws DaoException {

    }

    @Override
    public void delete(Film film) throws DaoException {

    }

    @Override
    public List<Film> getFilmAll() throws DaoException {
        return null;
    }

    @Override
    public Film getFilmById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean checkFilmInFilmshow(Film film) throws DaoException {
        return false;
    }
}
