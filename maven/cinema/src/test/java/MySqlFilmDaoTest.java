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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/beans.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
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
		Film film = new Film();
		film.setFilmId(3);
		filmDao.delete(film);
		Assert.assertNull(filmDao.getFilmById(3));
	}

	@Test
	public void testGetFilmAll() throws DaoException
	{
		List<Film> listTest = filmDao.getFilmAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}
}
