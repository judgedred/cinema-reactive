package com.service;


import com.dao.DaoException;
import com.dao.FilmshowDao;
import com.dao.TicketDao;
import com.domain.Filmshow;
import com.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmshowServiceImpl implements FilmshowService
{
    @Autowired
    private FilmshowDao filmshowDao;

    @Autowired
    private TicketDao ticketDao;

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

    @Override
    public boolean checkFilmshowInTicket(Filmshow filmshow) throws DaoException
    {
        List<Ticket> ticketList = ticketDao.getTicketAllByFilmshow(filmshow);
        if(ticketList != null)
        {
            return true;
        }
        return false;
    }
}
