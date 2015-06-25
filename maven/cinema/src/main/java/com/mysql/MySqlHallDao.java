package com.mysql;

import com.dao.*;
import com.domain.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MySqlHallDao implements HallDao
{
	private EntityManager em;

	@Override
	public Hall create(Hall hall) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.persist(hall);
			em.getTransaction().commit();
			em.refresh(hall);
			return hall;
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Hall hall) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.merge(hall);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Hall hall) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			Hall hallToBeRemoved = em.getReference(Hall.class, hall.getHallId());
			em.remove(hallToBeRemoved);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Hall> getHallAll() throws DaoException
	{
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Hall.class));
			return (List<Hall>) em.createQuery(cq).getResultList();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Hall getHallById(int id) throws DaoException
	{
		try
		{
			return em.find(Hall.class, id);
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

	MySqlHallDao() throws DaoException
	{
		em = MySqlDaoFactory.createEntityManager();
	}
}