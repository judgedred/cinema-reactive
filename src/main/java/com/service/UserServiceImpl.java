package com.service;


import com.dao.DaoException;
import com.dao.ReservationDao;
import com.dao.UserDao;
import com.domain.Reservation;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public User create(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
        String passwordHash = DatatypeConverter.printHexBinary(hash);
        user.setPassword(passwordHash);
        return userDao.create(user);
    }

    @Override
    public void update(User user) throws DaoException
    {
        userDao.update(user);
    }

    @Override
    public void delete(User user) throws DaoException
    {
        userDao.delete(user);
    }

    @Override
    public List<User> getUserAll() throws DaoException
    {
        return userDao.getUserAll();
    }

    @Override
    public User getUserById(Integer id) throws DaoException
    {
        return userDao.getUserById(id);
    }

    @Override
    public boolean checkUserInReservation(User user) throws DaoException
    {
        List<Reservation> reservationList = reservationDao.getReservationAllByUser(user);
        if(reservationList != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public User authenticateAdmin(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException
    {
        if(user.getLogin().equals("admin"))
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            user.setPassword(passwordHash);
            User adminUser = userDao.getUserByUser(user);
            if(adminUser != null)
            {
                return adminUser;
            }
        }
        return null;
    }

    @Override
    public User authenticateUser(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
        String passwordHash = DatatypeConverter.printHexBinary(hash);
        user.setPassword(passwordHash);
        User validUser = userDao.getUserByUser(user);
        if(validUser != null)
        {
            return validUser;
        }
        return null;
    }
}
