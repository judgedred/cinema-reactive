import com.dao.*;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.mysql.MySqlDaoFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Oleg on 11.06.2015.
 */
public class MySqlReservationDaoTest
{
    private TicketDao ticketDao;
    private UserDao userDao;
    private ReservationDao reservationDao;

    @Before
    public void setup() throws DaoException
    {
        DaoFactory daoFactory = new MySqlDaoFactory();
        ticketDao = daoFactory.getTicketDao();
        userDao = daoFactory.getUserDao();
        reservationDao = daoFactory.getReservationDao();
    }

    @Test
    public void testGetReservationById() throws DaoException
    {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        Reservation reservationTest = reservationDao.getReservationById(reservation.getReservationId());
        Assert.assertNotNull(reservation);
    }

    @Test
    public void testCreate() throws DaoException
    {
        Reservation reservation = new Reservation();
        User user = userDao.getUserById(1);
        Ticket ticket = ticketDao.getTicketById(1);
        reservation.setUser(user);
        reservation.setTicket(ticket);
        User userExpected = reservation.getUser();
        Ticket ticketExpected = reservation.getTicket();
        Reservation reservationTest = reservationDao.create(reservation);
        Assert.assertNotNull(reservationTest);
        User userResult = reservationTest.getUser();
        Ticket ticketResult = reservationTest.getTicket();
        Assert.assertEquals(userExpected, userResult);
        Assert.assertEquals(ticketExpected, ticketResult);
    }

    @Test
    public void testUpdate() throws DaoException
    {
        Reservation reservation = new Reservation();
        reservation.setReservationId(2);
        User user = userDao.getUserById(2);
        Ticket ticket = ticketDao.getTicketById(2);
        reservation.setUser(user);
        reservation.setTicket(ticket);
        User userExpected = reservation.getUser();
        Ticket ticketExpected = reservation.getTicket();
        reservationDao.update(reservation);
        Reservation reservationTest = reservationDao.getReservationById(reservation.getReservationId());
        Assert.assertNotNull(reservationTest);
        User userResult = reservationTest.getUser();
        Ticket ticketResult = reservationTest.getTicket();
        Assert.assertEquals(userExpected, userResult);
        Assert.assertEquals(ticketExpected, ticketResult);
    }

    @Test
    public void testDelete() throws DaoException
    {
        Reservation reservation = new Reservation();
        reservation.setReservationId(3);
        reservationDao.delete(reservation);
        Assert.assertNull(reservationDao.getReservationById(reservation.getReservationId()));
    }

    @Test
    public void testGetAll() throws DaoException
    {
        List<Reservation> listTest = reservationDao.getReservationAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }

}


