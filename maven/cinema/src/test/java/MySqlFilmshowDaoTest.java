import com.dao.*;
import com.domain.*;
import com.mysql.*;
import java.util.*;

import org.junit.After;
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
        Film film = new Film();
        film.setFilmName("filmForCreate");
        film.setDescription("filmForCreate");
        Film filmForCreate = filmDao.create(film);
        Hall hall = new Hall();
        hall.setHallNumber(10);
        hall.setHallName("hallForCreate");
        Hall hallForCreate = hallDao.create(hall);
		Filmshow filmshow = new Filmshow();
		filmshow.setFilm(filmForCreate);
		filmshow.setHall(hallForCreate);
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
        Film film = new Film();
        film.setFilmName("filmForUpdate");
        film.setDescription("filmForUpdate");
        Film filmForUpdate = filmDao.create(film);
        Hall hall = new Hall();
        hall.setHallNumber(10);
        hall.setHallName("hallForUpdate");
        Hall hallForUpdate = hallDao.create(hall);
        Filmshow filmshow = new Filmshow();
        filmshow.setFilm(filmForUpdate);
        filmshow.setHall(hallForUpdate);
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JUNE, 7, 20, 0);
        filmshow.setDateTime(cal.getTime());
        Filmshow filmshowForUpdate = filmshowDao.create(filmshow);
        film.setFilmName("filmUpdatePassed");
        film.setDescription("filmUpdatePassed");
        Film filmUpdatePassed = filmDao.create(film);
        hall.setHallNumber(11);
        hall.setHallName("hallUpdatePassed");
        Hall hallUpdatePassed = hallDao.create(hall);
		filmshow.setFilmshowId(filmshowForUpdate.getFilmshowId());
		filmshow.setFilm(filmUpdatePassed);
		filmshow.setHall(hallUpdatePassed);
		cal.set(2015, Calendar.JUNE, 8, 20, 0);
		filmshow.setDateTime(cal.getTime());
		Film filmExpected = filmshow.getFilm();
		Hall hallExpected = filmshow.getHall();
		String dateTimeExpected = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(filmshow.getDateTime());
		filmshowDao.update(filmshow);
		Filmshow filmshowTest = filmshowDao.getFilmshowById(filmshowForUpdate.getFilmshowId());
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
        Film film = new Film();
        film.setFilmName("filmForDelete");
        film.setDescription("filmForDelete");
        Film filmForDelete = filmDao.create(film);
        Hall hall = new Hall();
        hall.setHallNumber(10);
        hall.setHallName("hallForDelete");
        Hall hallForDelete = hallDao.create(hall);
        Filmshow filmshow = new Filmshow();
        filmshow.setFilm(filmForDelete);
        filmshow.setHall(hallForDelete);
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JUNE, 7, 20, 0);
        filmshow.setDateTime(cal.getTime());
        Filmshow filmshowForDelete = filmshowDao.create(filmshow);
		filmshowDao.delete(filmshowForDelete);
		Assert.assertNull(filmshowDao.getFilmshowById(filmshowForDelete.getFilmshowId()));
	}

	@Test
	public void testGetFilmshowAll() throws DaoException
	{
		List<Filmshow> listTest = filmshowDao.getFilmshowAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}

    @After
    public void cleanUp()
    {
        try
        {
            List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
            for(Filmshow f : filmshowLst)
            {
                if(f.getFilm().getFilmName().equals("filmForCreate") || f.getFilm().getFilmName().equals("filmUpdatePassed"))
                {
                    filmshowDao.delete(f);
                }
            }
            List<Film> filmLst = filmDao.getFilmAll();
            for(Film f : filmLst)
            {
                if(f.getFilmName().equals("filmForCreate")
                        || f.getFilmName().equals("filmForUpdate")
                        || f.getFilmName().equals("filmUpdatePassed")
                        || f.getFilmName().equals("filmForDelete"))
                {
                    filmDao.delete(f);
                }
            }
            List<Hall> hallLst = hallDao.getHallAll();
            for(Hall h : hallLst)
            {
                if(h.getHallName().equals("hallForCreate")
                        || h.getHallName().equals("hallForUpdate")
                        || h.getHallName().equals("hallUpdatePassed")
                        || h.getHallName().equals("hallForDelete"))
                {
                    hallDao.delete(h);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
