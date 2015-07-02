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
public class MySqlSeatDao implements SeatDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
	public Seat create(Seat seat) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(seat);
			session.flush();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			seat = (Seat) session.load(Seat.class, lastId);
            session.getTransaction().commit();
            return seat;
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
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
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
    @SuppressWarnings("unchecked")
	public List<Seat> getSeatAll() throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
            session.beginTransaction();
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

	MySqlSeatDao()
	{
	}
}