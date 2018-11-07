package com.mysql;

import com.dao.DaoException;
import com.dao.TicketDao;
import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.math.BigInteger;
import java.util.List;

@Repository
public class MySqlTicketDao implements TicketDao {

    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public MySqlTicketDao(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Ticket create(Ticket ticket) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session
                    .createCriteria(Ticket.class)
                    .add(Restrictions.conjunction(Restrictions.eq("filmshow", ticket.getFilmshow()),
                            Restrictions.eq("seat", ticket.getSeat())))
                    .list();
            if (resultList.isEmpty()) {
                session.beginTransaction();
                session.save(ticket);
                session.flush();
                session.getTransaction().commit();
                return ticket;
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
    public void update(Ticket ticket) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(ticket);
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
    public void delete(Ticket ticket) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(ticket);
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
    public List<Ticket> getTicketAll() throws DaoException {
        try {
            session = sessionFactory.openSession();
            return (List<Ticket>) session.createCriteria(Ticket.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Ticket getTicketById(BigInteger id) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Ticket ticket = (Ticket) session.get(Ticket.class, id);
            if (ticket != null) {
                return ticket;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketAllByFilmshow(Filmshow filmshow) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session
                    .createCriteria(Ticket.class)
                    .add(Restrictions.eq("filmshow", filmshow))
                    .list();
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketFreeByFilmshow(Filmshow filmshow) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session
                    .createCriteria(Ticket.class)
                    .add(Restrictions.eq("filmshow", filmshow))
                    .add(Subqueries.propertyNotIn(
                            "ticketId",
                            DetachedCriteria
                                    .forClass(Reservation.class)
                                    .setProjection(Property.forName("ticket.ticketId"))))
                    .list();
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getTicketAllBySeat(Seat seat) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Ticket> resultList = (List<Ticket>) session
                    .createCriteria(Ticket.class)
                    .add(Restrictions.eq("seat", seat))
                    .list();
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}