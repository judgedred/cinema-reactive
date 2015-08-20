package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Repository
public class MySqlHallDao implements HallDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
	public Hall create(Hall hall) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(hall);
			session.flush();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			hall = (Hall) session.load(Hall.class, lastId);
            session.getTransaction().commit();
            return hall;
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
	public void update(Hall hall) throws DaoException
	{
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(hall);
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
	public void delete(Hall hall) throws DaoException
	{
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(hall);
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
	public List<Hall> getHallAll() throws DaoException
	{
        try
        {
            session = sessionFactory.openSession();
            return (List<Hall>) session.createCriteria(Hall.class).list();
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
	public Hall getHallById(int id) throws DaoException
	{
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Hall hall = (Hall)session.get(Hall.class, id);
            if(hall != null)
            {
                return hall;
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

	MySqlHallDao() throws DaoException
	{

	}
}