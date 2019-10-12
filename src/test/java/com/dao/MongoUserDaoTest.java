package com.dao;

import com.CinemaTestConfiguration;
import com.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
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
        userRepository.delete(user).block();
    }

    @Test
    public void getByIdTest() {
        User user = testDataRepository.createTestUser();
        Optional<User> userTest = userRepository.findById(user.getUserId()).blockOptional();
        assertTrue(userTest.isPresent());
        userRepository.delete(user).block();
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
        userRepository.save(user).block();
        Optional<User> userOptional = userRepository.findById(user.getUserId()).blockOptional();
        assertTrue(userOptional.isPresent());
        User userUpdated = userOptional.get();
        assertThat(userUpdated.getLogin(), is(loginExpected));
        assertThat(userUpdated.getPassword(), is(passwordExpected));
        assertThat(userUpdated.getEmail(), is(emailExpected));
        userRepository.delete(user).block();
    }

    @Test
    public void deleteTest() {
        User user = testDataRepository.createTestUser();
        userRepository.delete(user).block();
        assertFalse(userRepository.findById(user.getUserId()).blockOptional().isPresent());
    }


    @Test
    public void getAllTest() {
        User user = testDataRepository.createTestUser();
        List<User> users = userRepository.findAll().collectList().block();
        assertNotNull(users);
        assertTrue(users.size() > 0);
        userRepository.delete(user).block();
    }

    @Test
    public void getUserByLoginTest() {
        User user = testDataRepository.createTestUser();
        Optional<User> userTest = userRepository.findByLogin(user.getLogin()).blockOptional();
        assertTrue(userTest.isPresent());
        assertThat(userTest.get().getLogin(), is(user.getLogin()));
        userRepository.delete(user).block();
    }

    @Test
    public void getUserByEmailTest() {
        User user = testDataRepository.createTestUser();
        Optional<User> userTest = userRepository.findByEmail(user.getEmail()).blockOptional();
        assertTrue(userTest.isPresent());
        assertThat(userTest.get().getEmail(), is(user.getEmail()));
        userRepository.delete(user).block();
    }
}
