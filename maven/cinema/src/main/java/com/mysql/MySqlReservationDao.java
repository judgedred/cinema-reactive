package com.mysql;

import com.dao.*;
import com.domain.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MySqlReservationDao implements ReservationDao
{
	private EntityManager em;


	@Override
	public Reservation create(Reservation reservation) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.persist(reservation);
			em.getTransaction().commit();
			em.refresh(reservation);
			return reservation;
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Reservation reservation) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			em.merge(reservation);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Reservation reservation) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
			Reservation reservationToBeRemoved = em.getReference(Reservation.class, reservation.getReservationId());
			em.remove(reservationToBeRemoved);
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Reservation> getReservationAll() throws DaoException
	{
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Reservation.class));
			return (List<Reservation>) em.createQuery(cq).getResultList();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Reservation getReservationById(int id) throws DaoException
	{
		try
		{
			return em.find(Reservation.class, id);
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

	MySqlReservationDao() throws DaoException
	{
		em = MySqlDaoFactory.createEntityManager();
	}
}