package com.dao;

import com.domain.Hall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoHallDaoTest {

    @Autowired
    private HallRepository hallRepository;

    @Test
    public void createTest() {
        String hallNameExpected = "testHallName";
        Integer numberExpected = 3;
        Hall hall = createHall(null, hallNameExpected, numberExpected);
        assertNotNull(hall);
        assertThat(hall.getHallName(), is(hallNameExpected));
        assertThat(hall.getHallNumber(), is(numberExpected));
        hallRepository.delete(hall);
    }

    @Test
    public void getByIdTest() throws DaoException {
        Hall hall = createTestHall();
        Optional<Hall> hallTest = hallRepository.findById(hall.getHallId());
        assertTrue(hallTest.isPresent());
        hallRepository.delete(hall);
    }

    @Test
    public void updateTest() {
        Hall hall = createTestHall();
        String hallNameExpected = "updatedHallName";
        Integer numberExpected = 777;
        hall.setHallName(hallNameExpected);
        hall.setHallNumber(numberExpected);
        hallRepository.save(hall);
        Optional<Hall> hallOptional = hallRepository.findById(hall.getHallId());
        assertTrue(hallOptional.isPresent());
        Hall hallUpdated = hallOptional.get();
        assertThat(hallUpdated.getHallName(), is(hallNameExpected));
        assertThat(hallUpdated.getHallNumber(), is(numberExpected));
        hallRepository.delete(hall);
    }

    @Test
    public void deleteTest() {
        Hall hall = createTestHall();
        hallRepository.delete(hall);
        assertFalse(hallRepository.findById(hall.getHallId()).isPresent());
    }

    @Test
    public void getAllHallsTest() throws DaoException {
        Hall hall = createTestHall();
        List<Hall> halls = hallRepository.findAll();
        assertNotNull(halls);
        assertTrue(halls.size() > 0);
        hallRepository.delete(hall);
    }

    private Hall createHall(BigInteger id, String name, Integer number) {
        Hall hall = new Hall();
        hall.setHallId(id);
        hall.setHallName(name);
        hall.setHallNumber(number);
        return hallRepository.save(hall);
    }

    private Hall createTestHall() {
        return createHall(BigInteger.valueOf(555), "testHall", 3);
    }
}
