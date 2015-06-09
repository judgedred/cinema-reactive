package test;

import com.dao.*;
import com.domain.*;
import com.mysql.*;
import java.util.*;
import org.junit.Test;
import org.junit.Assert;


public class MySqlFilmshowDaoTest
{
	@Test
	public void testGetFilmshowById() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmshowDao filmshowDao = daoFactory.getFilmshowDao();
		Filmshow filmshowTest = filmshowDao.getFilmshowById(1);
		Assert.assertNotNull(filmshowTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmshowDao filmshowDao = daoFactory.getFilmshowDao();
		FilmDao filmDao = daoFactory.getFilmDao();
		HallDao hallDao = daoFactory.getHallDao();
		Filmshow filmshow = new Filmshow();
		Film film = filmDao.getFilmById(1);
		Hall hall = hallDao.getHallById(1);
		filmshow.setFilm(film);
		filmshow.setHall(hall);
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 8, 21, 30);
		filmshow.setDateTime(cal.getTime());
		Film filmExpected = filmshow.getFilm();
		Hall hallExpected = filmshow.getHall();
		Date dateTimeExpected = filmshow.getDateTime();
		Filmshow filmshowTest = filmshowDao.create(filmshow);
		Assert.assertNotNull(filmshowTest);
		Film filmResult = filmshowTest.getFilm();
		Hall hallResult = filmshowTest.getHall();
		Date dateTimeResult = filmshowTest.getDateTime();	
		Assert.assertEquals(filmExpected.getFilmId(), filmResult.getFilmId());
		Assert.assertEquals(filmExpected.getFilmName(), filmResult.getFilmName());
		Assert.assertEquals(filmExpected.getDescription(), filmResult.getDescription());
		Assert.assertEquals(hallExpected.getHallId(), hallResult.getHallId());
		Assert.assertEquals(hallExpected.getHallNumber(), hallResult.getHallNumber());
		Assert.assertEquals(hallExpected.getHallName(), hallResult.getHallName());
		Assert.assertEquals(dateTimeExpected.toString(), dateTimeResult.toString());
	}
	
	@Test
	public void testUpdate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmshowDao filmshowDao = daoFactory.getFilmshowDao();
		FilmDao filmDao = daoFactory.getFilmDao();
		HallDao hallDao = daoFactory.getHallDao();
		Filmshow filmshow = new Filmshow();
		Film film = filmDao.getFilmById(2);
		Hall hall = hallDao.getHallById(2);
		filmshow.setFilmshowId(2);
		filmshow.setFilm(film);
		filmshow.setHall(hall);
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 5, 8, 20, 00);
		filmshow.setDateTime(cal.getTime());
		Film filmExpected = filmshow.getFilm();
		Hall hallExpected = filmshow.getHall();
		Date dateTimeExpected = filmshow.getDateTime();
		filmshowDao.update(filmshow);
		Filmshow filmshowTest = filmshowDao.getFilmshowById(filmshow.getFilmshowId());
		Assert.assertNotNull(filmshowTest);
		Film filmResult = filmshowTest.getFilm();
		Hall hallResult = filmshowTest.getHall();
		Date dateTimeResult = filmshowTest.getDateTime();	
		Assert.assertEquals(filmExpected, filmResult);
		Assert.assertEquals(hallExpected, hallResult);
		Assert.assertEquals(dateTimeExpected, dateTimeResult);
	}

	@Test
	public void testDelete() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmshowDao filmshowDao = daoFactory.getFilmshowDao();
		Filmshow filmshow = new Filmshow();
		filmshow.setFilmshowId(3);
		filmshowDao.delete(filmshow);
		Assert.assertNull(filmshowDao.getFilmshowById(filmshow.getFilmshowId()));
	}

	@Test
	public void testGetFilmshowAll() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmshowDao filmshowDao = daoFactory.getFilmshowDao();
		List<Filmshow> listTest = filmshowDao.getFilmshowAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}
}
