package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlReservationDao implements ReservationDao
{
	private Session session;

	@Override
	public Reservation create(Reservation reservation) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.save(reservation);
			session.getTransaction().commit();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			return (Reservation) session.load(Reservation.class, lastId);
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Reservation reservation) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.update(reservation);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Reservation reservation) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.delete(reservation);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public List<Reservation> getReservationAll() throws DaoException
	{
		try
		{
			return (List<Reservation>) session.createCriteria(Reservation.class).list();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Reservation getReservationById(int id) throws DaoException
	{
		try
		{
			Reservation reservation = (Reservation)session.get(Reservation.class, id);
			if(reservation != null)
			{
				return reservation;
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

	MySqlReservationDao() throws DaoException
	{
		session = MySqlDaoFactory.createSessionFactory().openSession();
	}
}