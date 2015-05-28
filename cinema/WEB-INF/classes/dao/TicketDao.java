package dao;

import domain.Ticket;
import java.util.List;

public interface TicketDao
{
	public Ticket create(Ticket ticket) throws DaoException;
	public void update(Ticket ticket) throws DaoException;
	public void delete(Ticket ticket) throws DaoException;
	public List<Ticket> getTicketAll() throws DaoException;
	public Ticket getTicketById(int id) throws DaoException;
	public void close() throws DaoException;
}
