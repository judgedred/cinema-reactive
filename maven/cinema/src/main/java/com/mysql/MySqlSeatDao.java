package com.mysql;

import com.dao.*;
import com.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlSeatDao implements SeatDao
{
	private EntityManager em;

	@Override
	public Seat create(Seat seat) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.persist(seat);
			em.getTransaction().commit();
			em.refresh(seat);
			return seat;
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Seat seat) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.merge(seat);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Seat seat) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			Seat seatToBeRemoved = em.getReference(Seat.class, seat.getSeatId());
			em.remove(seatToBeRemoved);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Seat> getSeatAll() throws DaoException
	{
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Seat.class));
			return (List<Seat>) em.createQuery(cq).getResultList();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Seat getSeatById(int id) throws DaoException
	{
		try
		{
			return em.find(Seat.class, id);
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

	MySqlSeatDao() throws DaoException
	{
		em = MySqlDaoFactory.createEntityManager();
	}
}