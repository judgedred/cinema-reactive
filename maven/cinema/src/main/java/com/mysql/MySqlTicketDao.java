package com.mysql;

import com.dao.*;
import com.domain.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MySqlTicketDao implements TicketDao
{
	private EntityManager em;


	@Override
	public Ticket create(Ticket ticket) throws DaoException
	{
        try
        {
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
            em.refresh(ticket);
            return ticket;
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
	}

	@Override
	public void update(Ticket ticket) throws DaoException
	{
        try
        {
            em.getTransaction().begin();
            em.merge(ticket);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
	}

	@Override
	public void delete(Ticket ticket) throws DaoException
	{
        try
        {
            em.getTransaction().begin();
            Ticket ticketToBeRemoved = em.getReference(Ticket.class, ticket.getTicketId());
            em.remove(ticketToBeRemoved);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<Ticket> getTicketAll() throws DaoException
	{
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ticket.class));
            return (List<Ticket>) em.createQuery(cq).getResultList();
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
	}

	@Override
	public Ticket getTicketById(int id) throws DaoException
	{
        try
        {
            return em.find(Ticket.class, id);
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

	MySqlTicketDao() throws DaoException
	{
		em = MySqlDaoFactory.createEntityManager();

	}
}