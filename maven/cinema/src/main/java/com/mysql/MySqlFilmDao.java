package com.mysql;

import com.dao.*;
import com.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MySqlFilmDao implements FilmDao
{
    @PersistenceContext
	private EntityManager em;

	@Override
    @Transactional
	public Film create(Film film) throws DaoException
	{
        try
		{
			em.persist(film);
			em.flush();
			em.refresh(film);
			return film;
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
			em.getTransaction().begin();
			em.merge(film);
			em.getTransaction().commit();
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
			em.getTransaction().begin();
			Film filmToBeRemoved = em.getReference(Film.class, film.getFilmId());
			em.remove(filmToBeRemoved);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<Film> getFilmAll() throws DaoException
	{
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Film.class));
			return (List<Film>) em.createQuery(cq).getResultList();
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
			return em.find(Film.class, id);
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

	MySqlFilmDao()
	{
		em = MySqlDaoFactory.createEntityManager();
	}
}