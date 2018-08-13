package com.dao;


import java.util.List;

import com.domain.Film;
import com.mysql.MySqlFilmDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MySqlFilmDaoTest
{
    @Autowired
    private MySqlFilmDao filmDao;

	@Test
	public void testGetFilmById() throws DaoException
	{
		Film filmTest = filmDao.getFilmById(1);
		Assert.assertNotNull(filmTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
		Film film = new Film();
		film.setFilmName("testCreatePassed");
		film.setDescription("testCreatePassed");
		String filmNameExpected = film.getFilmName();
		String descriptionExpected = film.getDescription();
		Film filmTest = filmDao.create(film);
		Assert.assertNotNull(filmTest);
		String filmNameResult = filmTest.getFilmName();
		String descriptionResult = filmTest.getDescription();	
		Assert.assertEquals(filmNameExpected, filmNameResult);
		Assert.assertEquals(descriptionExpected, descriptionResult);
	}
	
	@Test
	public void testUpdate() throws DaoException
	{
		Film film = new Film();
		film.setFilmId(4);
		film.setFilmName("testUpdatePassed");
		film.setDescription("testUpdatePassed");
		String filmNameExpected = film.getFilmName();
		String descriptionExpected = film.getDescription();
		filmDao.update(film);
		Film filmTest = filmDao.getFilmById(film.getFilmId());
		Assert.assertNotNull(filmTest);	
		String filmNameResult = filmTest.getFilmName();
		String descriptionResult = filmTest.getDescription();
		Assert.assertEquals(filmNameExpected, filmNameResult);
		Assert.assertEquals(descriptionExpected, descriptionResult);
	}

	@Test
	public void testDelete() throws DaoException
	{
		Film film = filmDao.getFilmById(5);
		filmDao.delete(film);
		Assert.assertNull(filmDao.getFilmById(film.getFilmId()));
	}

	@Test
	public void testGetFilmAll() throws DaoException
	{
		List<Film> listTest = filmDao.getFilmAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}

    @After
    public void cleanUp() throws DaoException
    {
        List<Film> lst = filmDao.getFilmAll();
        for(Film f : lst)
        {
            if(f.getFilmName().equals("testCreatePassed") || f.getFilmName().equals("testUpdatePassed"))
            {
                filmDao.delete(f);
            }
        }
    }
}
