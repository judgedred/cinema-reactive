package com.dao;

import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MySqlReservationDaoTest {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReservationDao reservationDao;

    @Test
    public void testGetReservationById() throws DaoException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(BigInteger.ONE);
        Reservation reservationTest = reservationDao.getReservationById(reservation.getReservationId());
        Assert.assertNotNull(reservationTest);
    }

    @Test
    public void testCreate() throws DaoException {
        Reservation reservation = new Reservation();
        User user = userDao.getUserById(BigInteger.ONE);
        Ticket ticket = ticketDao.getTicketById(BigInteger.valueOf(2));
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
    public void testUpdate() throws DaoException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(BigInteger.valueOf(2));
        User user = userDao.getUserById(BigInteger.valueOf(2));
        Ticket ticket = ticketDao.getTicketById(BigInteger.valueOf(2));
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
    public void testDelete() throws DaoException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(BigInteger.valueOf(3));
        reservationDao.delete(reservation);
        Assert.assertNull(reservationDao.getReservationById(reservation.getReservationId()));
    }

    @Test
    public void testGetAll() throws DaoException {
        List<Reservation> listTest = reservationDao.getReservationAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }

    @Test
    public void testGetAllByUser() throws DaoException {
        User user = userDao.getUserById(BigInteger.valueOf(2));
        List<Reservation> listTest = reservationDao.getReservationAllByUser(user);
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() == 1);
    }

    @Test
    public void testGetAllByTicket() throws DaoException {
        Ticket ticketExpected = ticketDao.getTicketById(BigInteger.valueOf(7));
        List<Reservation> listTest = reservationDao.getReservationAllByTicket(ticketExpected);
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() == 1);
    }

}


