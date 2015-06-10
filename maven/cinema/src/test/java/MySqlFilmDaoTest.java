import com.dao.*;
import com.domain.*;
import com.mysql.*;
import java.util.*;
import org.junit.Test;
import org.junit.Assert;


public class MySqlFilmDaoTest
{
	@Test
	public void testGetFilmById() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmDao filmDao = daoFactory.getFilmDao();
		Film filmTest = filmDao.getFilmById(1);
		Assert.assertNotNull(filmTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmDao filmDao = daoFactory.getFilmDao();
		Film film = new Film();
		film.setFilmName("Интерстеллар");
		film.setDescription("Сквозь пространство и время");
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
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmDao filmDao = daoFactory.getFilmDao();
		Film film = new Film();
		film.setFilmId(2);
		film.setFilmName("Кибер");
		film.setDescription("Экшн");
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
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmDao filmDao = daoFactory.getFilmDao();
		Film film = new Film();
		film.setFilmId(3);
		filmDao.delete(film);
		Assert.assertNull(filmDao.getFilmById(3));
	}

	@Test
	public void testGetFilmAll() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		FilmDao filmDao = daoFactory.getFilmDao();
		List<Film> listTest = filmDao.getFilmAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}
}
