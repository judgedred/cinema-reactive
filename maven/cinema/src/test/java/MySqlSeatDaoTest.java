import com.dao.DaoException;
import com.dao.HallDao;
import com.dao.SeatDao;
import com.domain.Hall;
import com.domain.Seat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/beans.xml")
public class MySqlSeatDaoTest
{
    @Autowired
    private SeatDao seatDao;

    @Autowired
    private HallDao hallDao;

    @Test
    public void testGetSeatById() throws DaoException
    {
        Seat seatTest = seatDao.getSeatById(1);
        Assert.assertNotNull(seatTest);
    }

    @Test
    public void testCreate() throws DaoException
    {
        Seat seat = new Seat();
        seat.setSeatNumber(777);
        seat.setRowNumber(777);
        Hall hall = hallDao.getHallById(1);
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
    public void testUpdate() throws DaoException
    {
        Seat seat = new Seat();
        seat.setSeatId(27);
        seat.setSeatNumber(555);
        seat.setRowNumber(555);
        Hall hall = hallDao.getHallById(2);
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
    public void testDelete() throws DaoException
    {
        Seat seat = new Seat();
        seat.setSeatId(28);
        seatDao.delete(seat);
        Assert.assertNull(seatDao.getSeatById(seat.getSeatId()));
    }

    @Test
    public void testGetSeatAll() throws DaoException
    {
        List<Seat> listTest = seatDao.getSeatAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }

    @After
    public void cleanUp() throws DaoException
    {
        List<Seat> lst = seatDao.getSeatAll();
        for(Seat s : lst)
        {
            if(s.getSeatNumber().equals(777) || s.getSeatNumber().equals(555))
            {
                seatDao.delete(s);
            }
        }
    }
}
