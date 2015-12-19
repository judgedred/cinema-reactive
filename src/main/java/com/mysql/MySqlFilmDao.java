package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class MySqlFilmDao implements FilmDao
{
    @Autowired
	private SessionFactory sessionFactory;
    private Session session;

	@Override
    @SuppressWarnings("unchecked")
    public Film create(Film film) throws DaoException
	{
		try
		{
			session = sessionFactory.openSession();
            List<Film> resultList = (List<Film>) session.createCriteria(Film.class).add(Restrictions.and(Restrictions.eq("filmName", film.getFilmName()),
                    Restrictions.eq("description", film.getDescription()))).list();
            if(resultList.isEmpty())
            {
                session.beginTransaction();
                session.save(film);
                session.flush();
                session.getTransaction().commit();
                return film;
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
        }
	}
}