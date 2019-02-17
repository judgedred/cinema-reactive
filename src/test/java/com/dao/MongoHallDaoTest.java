package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Hall;
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
public class MongoHallDaoTest {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @Test
    public void createTest() {
        String hallNameExpected = "testHallName";
        Integer numberExpected = 3;
        Hall hall = testDataRepository.createHall(null, hallNameExpected, numberExpected);
        assertNotNull(hall);
        assertThat(hall.getHallName(), is(hallNameExpected));
        assertThat(hall.getHallNumber(), is(numberExpected));
        hallRepository.delete(hall);
    }

    @Test
    public void getByIdTest() {
        Hall hall = testDataRepository.createTestHall();
        Optional<Hall> hallTest = hallRepository.findById(hall.getHallId());
        assertTrue(hallTest.isPresent());
        hallRepository.delete(hall);
    }

    @Test
    public void updateTest() {
        Hall hall = testDataRepository.createTestHall();
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
        Hall hall = testDataRepository.createTestHall();
        hallRepository.delete(hall);
        assertFalse(hallRepository.findById(hall.getHallId()).isPresent());
    }

    @Test
    public void getAllHallsTest() {
        Hall hall = testDataRepository.createTestHall();
        List<Hall> halls = hallRepository.findAll();
        assertNotNull(halls);
        assertTrue(halls.size() > 0);
        hallRepository.delete(hall);
    }

}
