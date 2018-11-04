package com.dao;

import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;
import com.domain.Ticket;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MySqlSeatDaoTest {

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private HallDao hallDao;

    @Autowired
    private FilmshowDao filmshowDao;

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testGetSeatById() throws DaoException {
        Seat seatTest = seatDao.getSeatById(BigInteger.ONE);
        Assert.assertNotNull(seatTest);
    }

    @Test
    public void testCreate() throws DaoException {
        Seat seat = new Seat();
        seat.setSeatNumber(777);
        seat.setRowNumber(777);
        Hall hall = hallDao.getHallById(BigInteger.ONE);
        seat.setHall(hall);
        int seatNumberExpected = seat.getSeatNumber();
        int rowNumberExpected = seat.getRowNumber();
        Hall hallExpected = seat.getHall();
        Seat seatTest = seatDao.create(seat);
        Assert.assertNotNull(seatTest);
        int seatNumberResult = seatTest.getSeatNumber();
        int rowNumberResult = seatTest.getRowNumber();
        Hall hallResult = seatTest.getHall();
        Assert.assertEquals(seatNumberExpected, seatNumberResult);
        Assert.assertEquals(rowNumberExpected, rowNumberResult);
        Assert.assertEquals(hallExpected, hallResult);
    }

    @Test
    public void testUpdate() throws DaoException {
        Seat seat = new Seat();
        seat.setSeatId(BigInteger.valueOf(27));
        seat.setSeatNumber(555);
        seat.setRowNumber(555);
        Hall hall = hallDao.getHallById(BigInteger.valueOf(2));
        seat.setHall(hall);
        int seatNumberExpected = seat.getSeatNumber();
        int rowNumberExpected = seat.getRowNumber();
        Hall hallExpected = seat.getHall();
        seatDao.update(seat);
        Seat seatTest = seatDao.getSeatById(seat.getSeatId());
        int seatNumberResult = seatTest.getSeatNumber();
        int rowNumberResult = seatTest.getRowNumber();
        Hall hallResult = seatTest.getHall();
        Assert.assertEquals(seatNumberExpected, seatNumberResult);
        Assert.assertEquals(rowNumberExpected, rowNumberResult);
        Assert.assertEquals(hallExpected, hallResult);
    }

    @Test
    public void testDelete() throws DaoException {
        Seat seat = seatDao.getSeatById(BigInteger.valueOf(28));
        seatDao.delete(seat);
        Assert.assertNull(seatDao.getSeatById(seat.getSeatId()));
    }

    @Test
    public void testGetSeatAll() throws DaoException {
        List<Seat> listTest = seatDao.getSeatAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }

    @Test
    public void testGetSeatByHall() throws DaoException {
        Hall hall = hallDao.getHallById(BigInteger.valueOf(1));
        Seat seatExpected = seatDao.getSeatById(BigInteger.ONE);
        Seat seatResult = seatDao.getSeatAllByHall(hall).get(0);
        Assert.assertNotNull(seatResult);
        Assert.assertEquals(seatExpected, seatResult);
    }

    @Test
    public void testGetSeatFreeByFilmshow() throws DaoException {
        Filmshow filmshow = filmshowDao.getFilmshowById(BigInteger.ONE);
        List<Seat> seatList = seatDao.getSeatAll();
        List<Ticket> ticketList = ticketDao.getTicketAll();
        boolean seatFree;
        List<Seat> seatListExpected = new ArrayList<>();
        for (Seat s : seatList) {
            seatFree = true;
            if (s.getHall().equals(filmshow.getHall())) {
                for (Ticket t : ticketList) {
                    if (t.getFilmshow().equals(filmshow) && t.getSeat().equals(s)) {
                        seatFree = false;
                        break;
                    }
                }
                if (seatFree) {
                    seatListExpected.add(s);
                }
            }
        }
        List<Seat> seatListResult = seatDao.getSeatFreeByFilmshow(filmshow);
        Assert.assertNotNull(seatListResult);
        Assert.assertEquals(seatListExpected, seatListResult);
    }

    @After
    public void cleanUp() throws DaoException {
        List<Seat> lst = seatDao.getSeatAll();
        for (Seat s : lst) {
            if (s.getSeatNumber().equals(777) || s.getSeatNumber().equals(555)) {
                seatDao.delete(s);
            }
        }
    }
}
