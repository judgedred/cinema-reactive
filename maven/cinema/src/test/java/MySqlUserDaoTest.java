import com.dao.*;
import com.domain.*;
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
@ContextConfiguration("file:src/main/webapp/WEB-INF/beans.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class MySqlUserDaoTest
{
    @Autowired
    private UserDao userDao;

	@Test
	public void testGetUserById() throws DaoException
	{
		User userTest = userDao.getUserById(1);
		Assert.assertNotNull(userTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
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
		User user = new User();
		user.setUserId(3);
		userDao.delete(user);
		Assert.assertNull(userDao.getUserById(user.getUserId()));
	}

	@Test
	public void testGetUserAll() throws DaoException
	{
		List<User> listTest = userDao.getUserAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}
}
