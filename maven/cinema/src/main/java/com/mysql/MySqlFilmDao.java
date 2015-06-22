package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import java.math.BigInteger;
import java.util.List;



public class MySqlFilmDao implements FilmDao
{
	private Session session;

	@Override
	public Film create(Film film) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.save(film);
			session.getTransaction().commit();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			return (Film) session.load(Film.class, lastId);
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Film film) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.update(film);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Film film) throws DaoException
	{
		try
		{
			session.beginTransaction();
			session.delete(film);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Film> getFilmAll() throws DaoException
	{
		try
		{
			return (List<Film>) session.createCriteria(Film.class).list();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Film getFilmById(int id) throws DaoException
	{
		try
		{
			Film film = (Film)session.get(Film.class, id);
			if(film != null)
			{
				return film;
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

	MySqlFilmDao()
	{

	}
}