package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlTicketDao implements TicketDao
{
	private Session session;

	@Override
	public Ticket create(Ticket ticket) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.save(ticket);
			session.getTransaction().commit();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			return (Ticket) session.load(Ticket.class, lastId);
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Ticket ticket) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.update(ticket);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Ticket ticket) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.delete(ticket);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public List<Ticket> getTicketAll() throws DaoException
	{
		try
		{
			return (List<Ticket>) session.createCriteria(Ticket.class).list();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Ticket getTicketById(int id) throws DaoException
	{
		try
		{
			Ticket ticket = (Ticket)session.get(Ticket.class, id);
			if(ticket != null)
			{
				return ticket;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void close() throws DaoException
	{
		try
		{
			if(session != null && session.isOpen())
			{
				session.close();
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	MySqlTicketDao() throws DaoException
	{
		session = MySqlDaoFactory.createSessionFactory().openSession();
	}
}