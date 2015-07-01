package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;


@Repository
public class MySqlFilmDao implements FilmDao
{
    @Autowired
	private SessionFactory sessionFactory;
    private Session session;



	@Override
    public Film create(Film film) throws DaoException
	{
		try
		{
			sessionFactory.openSession();
			sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().save(film);
            sessionFactory.getCurrentSession().flush();
			Integer lastId = ((BigInteger) sessionFactory.getCurrentSession().createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
            film = (Film) sessionFactory.getCurrentSession().load(Film.class, lastId);
            sessionFactory.getCurrentSession().getTransaction().commit();
            return film;

		}
		catch(Exception e)
		{
            sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Film film) throws DaoException
	{
        try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(film);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
            sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Film film) throws DaoException
	{
        try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(film);
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
            sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Film> getFilmAll() throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
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
			session = sessionFactory.openSession();
            session.beginTransaction();
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