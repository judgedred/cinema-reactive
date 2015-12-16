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
public class MySqlFilmshowDao implements FilmshowDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
    @SuppressWarnings("unchecked")
	public Filmshow create(Filmshow filmshow) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
            List<Filmshow> resultList = (List<Filmshow>) session.createCriteria(Filmshow.class).add(Restrictions.conjunction(Restrictions.eq("film", filmshow.getFilm()),
                    Restrictions.eq("hall", filmshow.getHall()), Restrictions.eq("dateTime", filmshow.getDateTime()))).list();
            if(resultList.isEmpty())
            {
                session.beginTransaction();
                session.save(filmshow);
                session.flush();
                session.getTransaction().commit();
                return filmshow;
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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
    public List<Filmshow> getFilmshowAllByFilm(Film film) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<Filmshow> resultList = (List<Filmshow>) session.createCriteria(Filmshow.class).add(Restrictions.eq("film", film)).list();
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
    public List<Filmshow> getFilmshowAllByHall(Hall hall) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<Filmshow> resultList = (List<Filmshow>) session.createCriteria(Filmshow.class).add(Restrictions.eq("hall", hall)).list();
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