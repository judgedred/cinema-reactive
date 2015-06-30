package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;


public class MySqlUserDao implements UserDao
{
	private Session session;

	@Override
	public User create(User user) throws DaoException
	{
        try
        {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
            return (User) session.load(User.class, lastId);
        }
        catch(Exception e)
        {
			session.getTransaction().rollback();
            throw new DaoException(e);
        }
	}

	@Override
	public void update(User user) throws DaoException
	{
		try
		{
			session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
		}
		catch(Exception e)
		{
            session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(User user) throws DaoException
	{
		try
		{
			session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
		}
		catch(Exception e)
		{
            session.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@Override
	public List<User> getUserAll() throws DaoException
	{
		try
		{
			return (List<User>) session.createCriteria(User.class).list();
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
            User user = (User)session.get(User.class, id);
			if(user != null)
			{
				return user;
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
	}

	@Override
	public void close() throws DaoException
	{
		try
		{
            if(session != null && session.isOpen())
            {
                session.close();
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
		session = MySqlDaoFactory.createSessionFactory().openSession();
	}
}






