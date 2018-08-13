package com.dao;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.mysql.MySqlFilmshowDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
        cal.set(2015, Calendar.NOVEMBER, 8, 21, 30);
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
        cal.set(2015, Calendar.NOVEMBER, 8, 20, 0);
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
        Filmshow filmshow = filmshowDao.getFilmshowById(3);
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

    @Test
    public void testGetFilmshowByFilm() throws DaoException
    {
        Film film = filmDao.getFilmById(1);
        Filmshow filmshowExpected = filmshowDao.getFilmshowById(1);
        Filmshow filmshowResult = filmshowDao.getFilmshowAllByFilm(film).get(0);
        Assert.assertNotNull(filmshowResult);
        Assert.assertEquals(filmshowExpected, filmshowResult);
    }

    @Test
    public void testGetFilmshowByHall() throws DaoException
    {
        Hall hall = hallDao.getHallById(1);
        Filmshow filmshowExpected = filmshowDao.getFilmshowById(1);
        Filmshow filmshowResult = filmshowDao.getFilmshowAllByHall(hall).get(0);
        Assert.assertNotNull(filmshowResult);
        Assert.assertEquals(filmshowExpected, filmshowResult);
    }

    @Test
    public void testGetFilmshowAllByDate() throws DaoException
    {
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.MARCH, 3, 3, 0);
        Date startDate = cal.getTime();
        cal.add(Calendar.MINUTE, 30);
        Date endDate = cal.getTime();
        List<Filmshow> testList = filmshowDao.getFilmshowAllByDate(startDate, endDate);
        Assert.assertNotNull(testList);
        Assert.assertTrue(testList.size() == 1);
    }
}
