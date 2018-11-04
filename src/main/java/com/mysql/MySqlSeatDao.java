package com.mysql;

import com.dao.DaoException;
import com.dao.SeatDao;
import com.domain.Filmshow;
import com.domain.Hall;
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
public class MySqlSeatDao implements SeatDao {

    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public MySqlSeatDao(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Seat create(Seat seat) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Seat> resultList = (List<Seat>) session.createCriteria(Seat.class).add(Restrictions.conjunction(
                    Restrictions.eq("seatNumber", seat.getSeatNumber()),
                    Restrictions.eq("hall", seat.getHall()),
                    Restrictions.eq("rowNumber", seat.getRowNumber()))).list();
            if (resultList.isEmpty()) {
                session.beginTransaction();
                session.save(seat);
                session.flush();
                session.getTransaction().commit();
                return seat;
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
    public void update(Seat seat) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(seat);
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
    public void delete(Seat seat) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(seat);
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
    public List<Seat> getSeatAll() throws DaoException {
        try {
            session = sessionFactory.openSession();
            return (List<Seat>) session.createCriteria(Seat.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Seat getSeatById(BigInteger id) throws DaoException {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Seat seat = (Seat) session.get(Seat.class, id);
            if (seat != null) {
                return seat;
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
    public List<Seat> getSeatAllByHall(Hall hall) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Seat> resultList = (List<Seat>) session
                    .createCriteria(Seat.class)
                    .add(Restrictions.eq("hall", hall))
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
    public List<Seat> getSeatFreeByFilmshow(Filmshow filmshow) throws DaoException {
        try {
            session = sessionFactory.openSession();
            List<Seat> resultList = (List<Seat>) session
                    .createCriteria(Seat.class)
                    .add(Restrictions.eq("hall", filmshow.getHall()))
                    .add(Subqueries.propertyNotIn(
                            "seatId",
                            DetachedCriteria
                                    .forClass(Ticket.class)
                                    .add(Restrictions.eq("filmshow", filmshow))
                                    .setProjection(Property.forName("seat.seatId"))))
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