package com.mysql;


import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;

import com.dao.DaoException;
import com.dao.FilmshowDao;
import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MySqlFilmshowDao implements FilmshowDao
{
    private SessionFactory sessionFactory;
	private Session session;

	@Autowired
    public MySqlFilmshowDao(EntityManagerFactory entityManagerFactory)
    {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

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

    @Override
    @SuppressWarnings("unchecked")
    public List<Filmshow> getFilmshowAllByDate(Date startDate, Date endDate) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<Filmshow> resultList = (List<Filmshow>) session.createCriteria(Filmshow.class)
                    .add(Restrictions.between("dateTime", startDate, endDate)).list();
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