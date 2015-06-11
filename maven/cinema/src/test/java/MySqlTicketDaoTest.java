import com.dao.*;
import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import com.mysql.MySqlDaoFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Oleg on 11.06.2015.
 */
public class MySqlTicketDaoTest
{
    private TicketDao ticketDao;
    private FilmshowDao filmshowDao;
    private SeatDao seatDao;

    @Before
    public void setup()throws DaoException
    {
        DaoFactory daoFactory = new MySqlDaoFactory();
        ticketDao = daoFactory.getTicketDao();
        filmshowDao = daoFactory.getFilmshowDao();
        seatDao = daoFactory.getSeatDao();
    }

    @Test
    public void testGetTicketById() throws DaoException
    {
        Ticket ticketTest = ticketDao.getTicketById(1);
        Assert.assertNotNull(ticketTest);
    }

    @Test
    public void testCreate() throws DaoException
    {
        Ticket ticket = new Ticket();
        Filmshow filmshow = filmshowDao.getFilmshowById(1);
        Seat seat = seatDao.getSeatById(1);
        ticket.setFilmshow(filmshow);
        ticket.setSeat(seat);
        ticket.setPrice(60000);
        Filmshow filmshowExpected = ticket.getFilmshow();
        Seat seatExpected = ticket.getSeat();
        float priceExpected = ticket.getPrice();
        Ticket ticketTest = ticketDao.create(ticket);
        Assert.assertNotNull(ticketTest);
        Filmshow filmshowResult = ticketTest.getFilmshow();
        Seat seatResult = ticketTest.getSeat();
        float priceResult = ticketTest.getPrice();
        Assert.assertEquals(filmshowExpected, filmshowResult);
        Assert.assertEquals(seatExpected, seatResult);
        Assert.assertTrue(priceExpected == priceResult);
    }

    @Test
    public void testUpdate() throws DaoException
    {
        Ticket ticket = new Ticket();
        ticket.setTicketId(2);
        Filmshow filmshow = filmshowDao.getFilmshowById(2);
        Seat seat = seatDao.getSeatById(2);
        ticket.setFilmshow(filmshow);
        ticket.setSeat(seat);
        ticket.setPrice(777);
        Filmshow filmshowExpected = ticket.getFilmshow();
        Seat seatExpected = ticket.getSeat();
        float priceExpected = ticket.getPrice();
        ticketDao.update(ticket);
        Ticket ticketTest = ticketDao.getTicketById(ticket.getTicketId());
        Assert.assertNotNull(ticketTest);
        Filmshow filmshowResult = ticketTest.getFilmshow();
        Seat seatResult = ticketTest.getSeat();
        float priceResult = ticketTest.getPrice();
        Assert.assertEquals(filmshowExpected, filmshowResult);
        Assert.assertEquals(seatExpected, seatResult);
        Assert.assertTrue(priceExpected == priceResult);
    }

    @Test
    public void testDelete() throws DaoException
    {
        Ticket ticket = new Ticket();
        ticket.setTicketId(4);
        ticketDao.delete(ticket);
        Assert.assertNull(ticketDao.getTicketById(ticket.getTicketId()));
    }

    @Test
    public void testGetAll() throws DaoException
    {
        List<Ticket> listTest = ticketDao.getTicketAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }
}
