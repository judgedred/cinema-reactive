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
public class MySqlFilmshowDao implements FilmshowDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
	public Filmshow create(Filmshow filmshow) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(filmshow);
			session.flush();
			Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
			filmshow = (Filmshow) session.load(Filmshow.class, lastId);
            session.getTransaction().commit();
            return filmshow;
		}
		catch(Exception e)
		{
            session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Filmshow filmshow) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(filmshow);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
            session.getTransaction().rollback();
            throw new DaoException(e);
		}
	}

	@Override
	public void delete(Filmshow filmshow) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(filmshow);
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
	public List<Filmshow> getFilmshowAll() throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
            session.beginTransaction();
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