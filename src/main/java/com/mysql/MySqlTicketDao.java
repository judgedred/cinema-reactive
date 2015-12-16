package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public class MySqlTicketDao implements TicketDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
    @SuppressWarnings("unchecked")
	public Ticket create(Ticket ticket) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session.createCriteria(Ticket.class).add(Restrictions.conjunction(Restrictions.eq("filmshow", ticket.getFilmshow()),
                    Restrictions.eq("seat", ticket.getSeat()))).list();
            if(resultList.isEmpty())
            {
                session.beginTransaction();
                session.save(ticket);
                session.flush();
                session.getTransaction().commit();
                return ticket;
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
	public void update(Ticket ticket) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(ticket);
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
	public void delete(Ticket ticket) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(ticket);
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
	public List<Ticket> getTicketAll() throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			return (List<Ticket>) session.createCriteria(Ticket.class).list();
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
	public Ticket getTicketById(int id) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
            session.beginTransaction();
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
    public List<Ticket> getTicketAllByFilmshow(Filmshow filmshow) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session.createCriteria(Ticket.class).add(Restrictions.eq("filmshow", filmshow)).list();
            if(resultList.isEmpty())
            {
                return null;
            }
            return resultList;
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
    public List<Ticket> getTicketFreeByFilmshow(Filmshow filmshow) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session.createCriteria(Ticket.class)
                    .add(Restrictions.eq("filmshow", filmshow))
                    .add(Subqueries.propertyNotIn("ticketId", DetachedCriteria.forClass(Reservation.class)
                    .setProjection(Property.forName("ticket.ticketId"))))
                    .list();
            if(resultList.isEmpty())
            {
                return null;
            }
            return resultList;
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
    public List<Ticket> getTicketAllBySeat(Seat seat) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session.createCriteria(Ticket.class).add(Restrictions.eq("seat", seat)).list();
            if(resultList.isEmpty())
            {
                return null;
            }
            return resultList;
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