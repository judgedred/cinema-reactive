package com.service;


import com.dao.DaoException;
import com.dao.TicketDao;
import com.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService
{
    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket create(Ticket ticket) throws DaoException
    {
        return ticketDao.create(ticket);
    }

    @Override
    public void update(Ticket ticket) throws DaoException
    {
        ticketDao.update(ticket);
    }

    @Override
    public void delete(Ticket ticket) throws DaoException
    {
        ticketDao.delete(ticket);
    }

    @Override
    public List<Ticket> getTicketAll() throws DaoException
    {
        return ticketDao.getTicketAll();
    }

    @Override
    public Ticket getTicketById(int id) throws DaoException
    {
        return ticketDao.getTicketById(id);
    }
}
