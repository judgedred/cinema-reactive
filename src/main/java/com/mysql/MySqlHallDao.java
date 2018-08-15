package com.mysql;

import com.dao.DaoException;
import com.dao.HallDao;
import com.domain.Hall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class MySqlHallDao implements HallDao {

    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public MySqlHallDao(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Hall create(Hall hall) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Hall> resultList = (List<Hall>) session.createCriteria(Hall.class).add(Restrictions.or(
                    Restrictions.eq("hallName", hall.getHallName()),
                    Restrictions.eq("hallNumber", hall.getHallNumber()))).list();
            if (resultList.isEmpty()) {
                session.beginTransaction();
                session.save(hall);
                session.flush();
                session.getTransaction().commit();
                return hall;
            } else {
                return null;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(Hall hall) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(hall);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Hall hall) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(hall);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Hall> getHallAll() throws DaoException {
        try {
            session = sessionFactory.openSession();
            return (List<Hall>) session.createCriteria(Hall.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Hall getHallById(int id) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Hall hall = (Hall) session.get(Hall.class, id);
            if (hall != null) {
                return hall;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}