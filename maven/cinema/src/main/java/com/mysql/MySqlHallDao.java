package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlHallDao implements HallDao
{
	private Session session;

	@Override
	public Hall create(Hall hall) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.save(hall);
			session.getTransaction().commit();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			return (Hall) session.load(Hall.class, lastId);
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Hall hall) throws DaoException
	{
        try
        {
            session.beginTransaction();
            session.update(hall);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            session.getTransaction().rollback();
            throw new DaoException(e);
        }
	}

	@Override
	public void delete(Hall hall) throws DaoException
	{
        try
        {
            session.beginTransaction();
            session.delete(hall);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            session.getTransaction().rollback();
            throw new DaoException(e);
        }
	}

	@Override
	public List<Hall> getHallAll() throws DaoException
	{
        try
        {
            return (List<Hall>) session.createCriteria(Hall.class).list();
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
	}

	@Override
	public Hall getHallById(int id) throws DaoException
	{
        try
        {
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
		session = MySqlDaoFactory.createSessionFactory().openSession();
	}
}