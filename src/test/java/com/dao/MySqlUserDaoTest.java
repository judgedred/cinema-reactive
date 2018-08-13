package com.dao;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

import com.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
	public void testCreate() throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException
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
	
	@Test
	public void testUpdate() throws DaoException,NoSuchAlgorithmException, UnsupportedEncodingException
	{
        User user = new User();
        user.setUserId(3);
        user.setLogin("testUpdatePassed");
        String password = "testUpdatePassed";
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        String passwordHash = DatatypeConverter.printHexBinary(hash);
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

	@Test
	public void testDelete() throws DaoException
	{
		User user = userDao.getUserById(4);
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

    @Test
    public void testGetUserByUser() throws DaoException
    {
        User userExpected = userDao.getUserById(1);
        User userResult = userDao.getUserByUser(userExpected);
        Assert.assertNotNull(userResult);
        Assert.assertEquals(userExpected, userResult);
    }

    @Test
    public void testGetUserByLogin() throws DaoException
    {
        User userExpected = userDao.getUserById(1);
        List<User> testList = userDao.getUserByLogin(userExpected.getLogin());
        Assert.assertNotNull(testList);
        Assert.assertTrue(testList.size() == 1);
        Assert.assertEquals(userExpected, testList.get(0));
    }

    @Test
    public void testGetUserByEmail() throws DaoException
    {
        User userExpected = userDao.getUserById(1);
        List<User> testList = userDao.getUserByEmail(userExpected.getEmail());
        Assert.assertNotNull(testList);
        Assert.assertTrue(testList.size() == 1);
        Assert.assertEquals(userExpected, testList.get(0));
    }

    @After
    public void cleanUp() throws DaoException
    {
        List<User> lst = userDao.getUserAll();
        for(User u : lst)
        {
            if(u.getLogin().equals("testCreatePassed") || u.getLogin().equals("testUpdatePassed"))
            {
                userDao.delete(u);
            }
        }
    }
}
