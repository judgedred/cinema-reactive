package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;



public class MySqlFilmshowDao implements FilmshowDao
{
	private Session session;

	@Override
	public Filmshow create(Filmshow filmshow) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.save(filmshow);
			session.getTransaction().commit();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			return (Filmshow) session.load(Filmshow.class, lastId);
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Filmshow filmshow) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.update(filmshow);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Filmshow filmshow) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.delete(filmshow);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Filmshow> getFilmshowAll() throws DaoException
	{
		try
		{
			return (List<Filmshow>) session.createCriteria(Filmshow.class).list();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Filmshow getFilmshowById(int id) throws DaoException
	{
		try
		{
			Filmshow filmshow = (Filmshow)session.get(Filmshow.class, id);
			if(filmshow != null)
			{
				return filmshow;
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

	MySqlFilmshowDao()
	{

	}
}