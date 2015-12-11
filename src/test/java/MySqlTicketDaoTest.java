import com.dao.*;
import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/beans.xml")
public class MySqlTicketDaoTest
{
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private FilmshowDao filmshowDao;

    @Autowired
    private SeatDao seatDao;

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
        float price = 60000;
        ticket.setPrice(price);
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
        float price = 50000;
        ticket.setPrice(price);
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
        Ticket ticket = ticketDao.getTicketById(6);
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

    @Test
    public void testGetTicketByFilmshow() throws DaoException
    {
        Filmshow filmshow = filmshowDao.getFilmshowById(1);
        Ticket ticketExpected = ticketDao.getTicketById(1);
        Ticket ticketResult = ticketDao.getTicketByFilmshow(filmshow).get(0);
        Assert.assertNotNull(ticketResult);
        Assert.assertEquals(ticketExpected, ticketResult);
    }
}
