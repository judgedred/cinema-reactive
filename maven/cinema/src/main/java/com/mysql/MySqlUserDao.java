package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public class MySqlUserDao implements UserDao
{
    @Autowired
    private SessionFactory sessionFactory;
	private Session session;

	@Override
	public User create(User user) throws DaoException
	{
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.flush();
            Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
            user = (User) session.load(User.class, lastId);
            session.getTransaction().commit();
            return user;
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
	public void update(User user) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
            session.update(user);
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
	public void delete(User user) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			session.beginTransaction();
            session.delete(user);
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
	public List<User> getUserAll() throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
			return (List<User>) session.createCriteria(User.class).list();
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
	public User getUserById(Integer id) throws DaoException
	{
		try
		{
            session = sessionFactory.openSession();
            session.beginTransaction();
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
        finally
        {
            if(session != null && session.isOpen())
            {
                session.close();
            }
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

	}
}






