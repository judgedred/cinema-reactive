import com.dao.*;
import com.domain.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/beans.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class MySqlUserDaoTest
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @Before
    public void setUp()
    {
        session = sessionFactory.openSession();
    }

    @Test
	public void testGetUserById() throws DaoException
	{
		User userTest = userDao.getUserById(1);
		Assert.assertNotNull(userTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
        try
        {
            User user = new User();
            user.setLogin("testCreatePassed");
            String password = "testCreatePassed";
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            user.setPassword(passwordHash);
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
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	@Test
	public void testUpdate() throws DaoException
	{
        try
        {
            User user = new User();
            user.setLogin("userForUpdate");
            String password = "userForUpdate";
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            user.setPassword(passwordHash);
            user.setEmail("userForUpdate@gmail.com");
            userDao.create(user);
            Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
            user.setUserId(lastId);
            user.setLogin("testUpdatePassed");
            password = "testUpdatePassed";
            digest.reset();
            hash = digest.digest(password.getBytes("UTF-8"));
            passwordHash = DatatypeConverter.printHexBinary(hash);
            user.setPassword(passwordHash);
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
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}

	@Test
	public void testDelete() throws DaoException
	{
        try
        {
            User user = new User();
            user.setLogin("userForDelete");
            String password = "userForDelete";
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            user.setPassword(passwordHash);
            user.setEmail("userForDelete@gmail.com");
            userDao.create(user);
            Integer lastId = ((BigInteger) session.createSQLQuery("Select last_insert_id()").uniqueResult()).intValue();
            user.setUserId(lastId);
            userDao.delete(user);
            Assert.assertNull(userDao.getUserById(user.getUserId()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}

	@Test
	public void testGetUserAll() throws DaoException
	{
		List<User> listTest = userDao.getUserAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}

    @After
    public void close()
    {
        if(session != null && session.isOpen())
        {
            session.close();
        }
    }
}
