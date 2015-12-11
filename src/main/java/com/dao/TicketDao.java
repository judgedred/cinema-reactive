package com.dao;

import com.domain.Filmshow;
import com.domain.Ticket;
import java.util.List;

public interface TicketDao
{
	public Ticket create(Ticket ticket) throws DaoException;
	public void update(Ticket ticket) throws DaoException;
	public void delete(Ticket ticket) throws DaoException;
	public List<Ticket> getTicketAll() throws DaoException;
	public Ticket getTicketById(int id) throws DaoException;
    public List<Ticket> getTicketByFilmshow(Filmshow filmshow) throws DaoException;
}
