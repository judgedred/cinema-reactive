import com.dao.*;
import com.domain.*;
import com.mysql.*;
import java.util.*;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/beans.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class MySqlFilmshowDaoTest
{
    @Autowired
    private MySqlFilmshowDao filmshowDao;

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private HallDao hallDao;

    @Test
    public void testGetFilmshowById() throws DaoException
    {
        Filmshow filmshowTest = filmshowDao.getFilmshowById(1);
        Assert.assertNotNull(filmshowTest);
    }

    @Test
    public void testCreate() throws DaoException
    {
        Filmshow filmshow = new Filmshow();
        Film film = filmDao.getFilmById(1);
        Hall hall = hallDao.getHallById(1);
        filmshow.setFilm(film);
        filmshow.setHall(hall);
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JUNE, 8, 21, 30);
        filmshow.setDateTime(cal.getTime());
        Film filmExpected = filmshow.getFilm();
        Hall hallExpected = filmshow.getHall();
        String dateTimeExpected = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(filmshow.getDateTime());
        Filmshow filmshowTest = filmshowDao.create(filmshow);
        Assert.assertNotNull(filmshowTest);
        Film filmResult = filmshowTest.getFilm();
        Hall hallResult = filmshowTest.getHall();
        String dateTimeResult = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(filmshowTest.getDateTime());
        Assert.assertEquals(filmExpected, filmResult);
        Assert.assertEquals(hallExpected, hallResult);
        Assert.assertEquals(dateTimeExpected, dateTimeResult);
    }

    @Test
    public void testUpdate() throws DaoException
    {
        Filmshow filmshow = new Filmshow();
        Film film = filmDao.getFilmById(2);
        Hall hall = hallDao.getHallById(2);
        filmshow.setFilmshowId(2);
        filmshow.setFilm(film);
        filmshow.setHall(hall);
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JUNE, 8, 20, 0);
        filmshow.setDateTime(cal.getTime());
        Film filmExpected = filmshow.getFilm();
        Hall hallExpected = filmshow.getHall();
        String dateTimeExpected = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(filmshow.getDateTime());
        filmshowDao.update(filmshow);
        Filmshow filmshowTest = filmshowDao.getFilmshowById(filmshow.getFilmshowId());
        Assert.assertNotNull(filmshowTest);
        Film filmResult = filmshowTest.getFilm();
        Hall hallResult = filmshowTest.getHall();
        String dateTimeResult = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(filmshowTest.getDateTime());
        Assert.assertEquals(filmExpected, filmResult);
        Assert.assertEquals(hallExpected, hallResult);
        Assert.assertEquals(dateTimeExpected, dateTimeResult);
    }

    @Test
    public void testDelete() throws DaoException
    {
        Filmshow filmshow = new Filmshow();
        filmshow.setFilmshowId(3);
        filmshowDao.delete(filmshow);
        Assert.assertNull(filmshowDao.getFilmshowById(filmshow.getFilmshowId()));
    }

    @Test
    public void testGetFilmshowAll() throws DaoException
    {
        List<Filmshow> listTest = filmshowDao.getFilmshowAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }
}
