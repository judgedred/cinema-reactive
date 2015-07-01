package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;



public class MySqlFilmDao implements FilmDao
{

	private SessionFactory sessionFactory;
    private Session session;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

	@Override
	public Film create(Film film) throws DaoException
	{
		try
		{

			sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().save(film);
            sessionFactory.getCurrentSession().getTransaction().commit();
			Integer lastId = ((BigInteger) sessionFactory.getCurrentSession().createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
            return (Film) sessionFactory.getCurrentSession().load(Film.class, lastId);
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
            session.getTransaction().rollback();
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
            session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
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
//		session = MySqlDaoFactory.createSessionFactory().openSession();
	}
}