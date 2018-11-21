package com.dao;

import com.CinemaTestConfiguration;
import com.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CinemaTestConfiguration.class)
public class MongoUserDaoTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @Test
    public void createTest() {
        String loginExpected = "testCreate";
        String passwordExpected = "testCreate";
        String emailExpected = "testCreate@gmail.com";
        User user = testDataRepository.createUser(loginExpected, passwordExpected, emailExpected);
        assertNotNull(user);
        assertThat(user.getLogin(), is(loginExpected));
        assertThat(user.getPassword(), is(passwordExpected));
        assertThat(user.getEmail(), is(emailExpected));
        userRepository.delete(user);
    }

    @Test
    public void getByIdTest() {
        User user = testDataRepository.createTestUser();
        Optional<User> userTest = userRepository.findById(user.getUserId());
        assertTrue(userTest.isPresent());
        userRepository.delete(user);
    }

    @Test
    public void updateTest() {
        String loginExpected = "testUpdate";
        String passwordExpected = "testCreate";
        String emailExpected = "testCreate@gmail.com";
        User user = testDataRepository.createTestUser();
        user.setLogin(loginExpected);
        user.setPassword(passwordExpected);
        user.setEmail(emailExpected);
        userRepository.save(user);
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        assertTrue(userOptional.isPresent());
        User userUpdated = userOptional.get();
        assertThat(userUpdated.getLogin(), is(loginExpected));
        assertThat(userUpdated.getPassword(), is(passwordExpected));
        assertThat(userUpdated.getEmail(), is(emailExpected));
        userRepository.delete(user);
    }

    @Test
    public void deleteTest() {
        User user = testDataRepository.createTestUser();
        userRepository.delete(user);
        assertFalse(userRepository.findById(user.getUserId()).isPresent());
        userRepository.delete(user);
    }


    @Test
    public void getAllTest() {
        User user = testDataRepository.createTestUser();
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertTrue(users.size() > 0);
        userRepository.delete(user);
    }

    @Test
    public void getUserByLoginTest() {
        User user = testDataRepository.createTestUser();
        Optional<User> userTest = userRepository.findByLogin(user.getLogin());
        assertTrue(userTest.isPresent());
        assertThat(userTest.get().getLogin(), is(user.getLogin()));
        userRepository.delete(user);
    }

    @Test
    public void getUserByEmailTest() {
        User user = testDataRepository.createTestUser();
        Optional<User> userTest = userRepository.findByEmail(user.getEmail());
        assertTrue(userTest.isPresent());
        assertThat(userTest.get().getEmail(), is(user.getEmail()));
        userRepository.delete(user);
    }
}
