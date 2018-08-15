package com.service;

import com.dao.DaoException;
import com.domain.Filmshow;
import com.domain.Ticket;

import java.util.List;

public interface TicketService {

    Ticket create(Ticket ticket) throws DaoException;

    void update(Ticket ticket) throws DaoException;

    void delete(Ticket ticket) throws DaoException;

    List<Ticket> getTicketAll() throws DaoException;

    Ticket getTicketById(int id) throws DaoException;

    List<Ticket> getTicketFreeByFilmshow(Filmshow filmshow) throws DaoException;

    List<Ticket> getTicketAllByFilmshow(Filmshow filmshow) throws DaoException;

    boolean checkTicketInReservation(Ticket ticket) throws DaoException;
}
