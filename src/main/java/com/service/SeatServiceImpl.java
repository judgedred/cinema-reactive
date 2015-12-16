package com.service;


import com.dao.DaoException;
import com.dao.SeatDao;
import com.dao.TicketDao;
import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService
{
    @Autowired
    private SeatDao seatDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Seat create(Seat seat) throws DaoException
    {
        return seatDao.create(seat);
    }

    @Override
    public void update(Seat seat) throws DaoException
    {
        seatDao.update(seat);
    }

    @Override
    public void delete(Seat seat) throws DaoException
    {
        seatDao.delete(seat);
    }

    @Override
    public List<Seat> getSeatAll() throws DaoException
    {
        return seatDao.getSeatAll();
    }

    @Override
    public Seat getSeatById(int id) throws DaoException
    {
        return seatDao.getSeatById(id);
    }

    @Override
    public boolean checkSeatInTicket(Seat seat) throws DaoException
    {
        List<Ticket> ticketList = ticketDao.getTicketAllBySeat(seat);
        if(ticketList != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<Seat> getSeatFreeByFilmshow(Filmshow filmshow) throws DaoException
    {
        return seatDao.getSeatFreeByFilmshow(filmshow);
    }
}
