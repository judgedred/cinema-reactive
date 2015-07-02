package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;


@Repository
public class MySqlReservationDao implements ReservationDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
	public Reservation create(Reservation reservation) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(reservation);
			session.flush();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			reservation = (Reservation) session.load(Reservation.class, lastId);
            session.getTransaction().commit();
            return reservation;
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
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
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
    @SuppressWarnings("unchecked")
	public List<Reservation> getReservationAll() throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
            session.beginTransaction();
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

	MySqlReservationDao()
	{
	}
}