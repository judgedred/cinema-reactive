package com.mysql;

import com.dao.*;
import com.domain.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MySqlFilmshowDao implements FilmshowDao
{
	private EntityManager em;

	@Override
	public Filmshow create(Filmshow filmshow) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.persist(filmshow);
			em.getTransaction().commit();
			em.refresh(filmshow);
			return filmshow;
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
			em.getTransaction().begin();
			em.merge(filmshow);
			em.getTransaction().commit();
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
			em.getTransaction().begin();
			Filmshow filmshowToBeRemoved = em.getReference(Filmshow.class, filmshow.getFilmshowId());
			em.remove(filmshowToBeRemoved);
			em.getTransaction().commit();
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
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Filmshow.class));
			return (List<Filmshow>) em.createQuery(cq).getResultList();
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
			return em.find(Filmshow.class, id);
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
			if(em != null && em.isOpen())
			{
				em.close();
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	MySqlFilmshowDao()
	{
		em = MySqlDaoFactory.createEntityManager();
	}
}