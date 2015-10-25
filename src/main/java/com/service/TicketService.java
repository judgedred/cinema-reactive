package com.service;


import com.dao.DaoException;
import com.domain.Ticket;
import java.util.List;

public interface TicketService
{
    public Ticket create(Ticket ticket) throws DaoException;
    public void update(Ticket ticket) throws DaoException;
    public void delete(Ticket ticket) throws DaoException;
    public List<Ticket> getTicketAll() throws DaoException;
    public Ticket getTicketById(int id) throws DaoException;
}
