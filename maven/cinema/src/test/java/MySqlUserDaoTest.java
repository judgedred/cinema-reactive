import com.dao.*;
import com.domain.*;
import com.mysql.*;
import java.util.*;
import org.junit.Test;
import org.junit.Assert;


public class MySqlUserDaoTest
{
	@Test
	public void testGetUserById() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		UserDao userDao = daoFactory.getUserDao();
		User userTest = userDao.getUserById(1);
		Assert.assertNotNull(userTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		UserDao userDao = daoFactory.getUserDao();
		User user = new User();
		user.setLogin("testCreatePassed");
		user.setPassword("testCreatePassed");
		user.setEmail("testCreatePassed@gmail.com");
		String loginExpected = user.getLogin();
		String passwordExpected = user.getPassword();
		String emailExpected = user.getEmail();
		User userTest = userDao.create(user);
		Assert.assertNotNull(userTest);
		String loginResult = userTest.getLogin();
		String passwordResult = userTest.getPassword();
		String emailResult = userTest.getEmail();	
		Assert.assertEquals(loginExpected, loginResult);
		Assert.assertEquals(passwordExpected, passwordResult);
		Assert.assertEquals(emailExpected, emailResult);
	}
	
	@Test
	public void testUpdate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		UserDao userDao = daoFactory.getUserDao();
		User user = new User();
		user.setUserId(2);
		user.setLogin("testUpdatePassed");
		user.setPassword("testUpdatePassed");
		user.setEmail("testUpdatePassed@gmail.com");
		String loginExpected = user.getLogin();
		String passwordExpected = user.getPassword();
		String emailExpected = user.getEmail();
		userDao.update(user);
		User userTest = userDao.getUserById(user.getUserId());
		Assert.assertNotNull(userTest);	
		String loginResult = userTest.getLogin();
		String passwordResult = userTest.getPassword();
		String emailResult = userTest.getEmail();	
		Assert.assertEquals(loginExpected, loginResult);
		Assert.assertEquals(passwordExpected, passwordResult);
		Assert.assertEquals(emailExpected, emailResult);
	}

	@Test
	public void testDelete() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		UserDao userDao = daoFactory.getUserDao();
		User user = new User();
		user.setUserId(3);
		userDao.delete(user);
		Assert.assertNull(userDao.getUserById(user.getUserId()));
	}

	@Test
	public void testGetUserAll() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		UserDao userDao = daoFactory.getUserDao();
		List<User> listTest = userDao.getUserAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}
}
