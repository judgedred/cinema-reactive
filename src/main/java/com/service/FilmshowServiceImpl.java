package com.service;


import com.dao.DaoException;
import com.dao.FilmshowDao;
import com.domain.Filmshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmshowServiceImpl implements FilmshowService
{
    @Autowired
    private FilmshowDao filmshowDao;

    @Override
    public Filmshow create(Filmshow filmshow) throws DaoException
    {
        return filmshowDao.create(filmshow);
    }

    @Override
    public void update(Filmshow filmshow) throws DaoException
    {
        filmshowDao.update(filmshow);
    }

    @Override
    public void delete(Filmshow filmshow) throws DaoException
    {
        filmshowDao.delete(filmshow);
    }

    @Override
    public List<Filmshow> getFilmshowAll() throws DaoException
    {
        return filmshowDao.getFilmshowAll();
    }

    @Override
    public Filmshow getFilmshowById(int id) throws DaoException
    {
        return filmshowDao.getFilmshowById(id);
    }
}
