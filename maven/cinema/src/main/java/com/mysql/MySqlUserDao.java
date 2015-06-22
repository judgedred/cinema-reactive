package com.mysql;

import com.dao.*;
import com.domain.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MySqlUserDao implements UserDao
{
	private EntityManager em;

	@Override
	public User create(User user) throws DaoException
	{
        try
        {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
			em.refresh(user);
            return user;
        }
        catch(Exception e)
        {
            throw new DaoException(e);
        }
	}

	@Override
	public void update(User user) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(User user) throws DaoException
	{
		try
		{
			em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<User> getUserAll() throws DaoException
	{
		try
		{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
			return (List<User>) em.createQuery(cq).getResultList();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public User getUserById(Integer id) throws DaoException
	{
		try
		{
            return em.find(User.class, id);
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

	@Override
	public User register(User user) throws DaoException
	{
		return null;
	}

	@Override
	public void changePassword(User user) throws DaoException
	{
		
	}

	MySqlUserDao()
	{
		em = MySqlDaoFactory.createEntityManager();
	}
}






