package com.mysql;

import com.dao.*;
import com.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    @SuppressWarnings("unchecked")
	public User create(User user) throws DaoException
	{
        try
        {
            session = sessionFactory.openSession();
            List<User> resultList = (List<User>) session.createCriteria(User.class).add(Restrictions.or(Restrictions.eq("login", user.getLogin()),
                    Restrictions.eq("email", user.getEmail()))).list();
            if(resultList.isEmpty())
            {
                session.beginTransaction();
                session.save(user);
                session.flush();
                session.getTransaction().commit();
                return user;
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
    @SuppressWarnings("unchecked")
    public User getUserByUser(User user) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<User> resultList = (List<User>) session.createCriteria(User.class).add(Restrictions.and(Restrictions.eq("login", user.getLogin()),
                    Restrictions.eq("password", user.getPassword()))).list();
            if(resultList.size() == 1)
            {
                return resultList.get(0);
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
    public List<User> getUserByLogin(String login) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<User> resultList = (List<User>) session.createCriteria(User.class).add(Restrictions.eq("login", login)).list();
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
    public List<User> getUserByEmail(String email) throws DaoException
    {
        try
        {
            session = sessionFactory.openSession();
            List<User> resultList = (List<User>) session.createCriteria(User.class).add(Restrictions.eq("email", email)).list();
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






