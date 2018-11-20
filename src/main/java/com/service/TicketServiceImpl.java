package com.service;

import com.dao.DaoException;
import com.dao.ReservationDao;
import com.dao.TicketDao;
import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public Ticket create(Ticket ticket) throws DaoException {
        return ticketDao.create(ticket);
    }

    @Override
    public void update(Ticket ticket) throws DaoException {
        ticketDao.update(ticket);
    }

    @Override
    public void delete(Ticket ticket) throws DaoException {
        ticketDao.delete(ticket);
    }

    @Override
    public List<Ticket> getTicketAll() throws DaoException {
        return ticketDao.getTicketAll();
    }

    @Override
    public Ticket getTicketById(BigInteger id) throws DaoException {
        return ticketDao.getTicketById(id);
    }

    @Override
    public List<Ticket> getTicketAllByFilmshow(Filmshow filmshow) throws DaoException {
        return ticketDao.getTicketAllByFilmshow(filmshow);
    }

    @Override
    public List<Ticket> getTicketAllBySeat(Seat seat) {
        return null;
    }

    @Override
    public boolean checkTicketInReservation(Ticket ticket) throws DaoException {
        List<Reservation> reservationList = reservationDao.getReservationAllByTicket(ticket);
        if (reservationList != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Ticket> getTicketFreeByFilmshow(Filmshow filmshow) throws DaoException {        // TODO
        return ticketDao.getTicketFreeByFilmshow(filmshow);
    }
}
