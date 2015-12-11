package com.service;


import com.dao.DaoException;
import com.dao.FilmshowDao;
import com.dao.TicketDao;
import com.domain.Filmshow;
import com.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
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
        /*List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
        boolean filmshowValid = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        for(Filmshow f : filmshowLst)
        {
            if(f.getFilm().equals(filmshow.getFilm())
                    && f.getHall().equals(filmshow.getHall())
                    && dateFormat.format(f.getDateTime()).equals(dateFormat.format(filmshow.getDateTime())))
            {
                filmshowValid = false;
                break;
            }
        }
        if(filmshowValid)
        {
            return filmshowDao.create(filmshow);
        }
        else
        {
            return null;
        }*/
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
        List<Ticket> ticketList = ticketDao.getTicketByFilmshow(filmshow);
        if(ticketList != null)
        {
            return true;
        }
        return false;
    }
}
