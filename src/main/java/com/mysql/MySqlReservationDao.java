package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    @SuppressWarnings("unchecked")
	public Reservation create(Reservation reservation) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
            List<Reservation> resultList = (List<Reservation>) session.createCriteria(Reservation.class).add(Restrictions.eq("ticket", reservation.getTicket())).list();
            if(resultList.isEmpty())
            {
                session.beginTransaction();
                session.save(reservation);
                session.flush();
                session.getTransaction().commit();
                return reservation;
            }
            else
            {
                return null;
            }
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
        }
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<Reservation> getReservationAllByUser(User user) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            return (List<Reservation>) session.createCriteria(Reservation.class).add(Restrictions.eq("user", user)).list();
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
        }
    }

}