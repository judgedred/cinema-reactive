package com.dao;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;

import java.util.List;

public interface TicketDao {

    Ticket create(Ticket ticket) throws DaoException;

    void update(Ticket ticket) throws DaoException;

    void delete(Ticket ticket) throws DaoException;

    List<Ticket> getTicketAll() throws DaoException;

    Ticket getTicketById(int id) throws DaoException;

    List<Ticket> getTicketAllByFilmshow(Filmshow filmshow) throws DaoException;

    List<Ticket> getTicketFreeByFilmshow(Filmshow filmshow) throws DaoException;

    List<Ticket> getTicketAllBySeat(Seat seat) throws DaoException;
}
