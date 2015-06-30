package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlSeatDao implements SeatDao
{
	private Session session;

	@Override
	public Seat create(Seat seat) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.save(seat);
			session.getTransaction().commit();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			return (Seat) session.load(Seat.class, lastId);
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Seat seat) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.update(seat);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Seat seat) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.delete(seat);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public List<Seat> getSeatAll() throws DaoException
	{
		try
		{
			return (List<Seat>) session.createCriteria(Seat.class).list();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Seat getSeatById(int id) throws DaoException
	{
		try
		{
			Seat seat = (Seat)session.get(Seat.class, id);
			if(seat != null)
			{
				return seat;
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

	MySqlSeatDao() throws DaoException
	{
		session = MySqlDaoFactory.createSessionFactory().openSession();
	}
}