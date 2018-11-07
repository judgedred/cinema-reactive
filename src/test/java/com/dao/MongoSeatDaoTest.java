package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Seat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CinemaTestConfiguration.class)
public class MongoSeatDaoTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @Test
    public void createTest() {
        int rowNumberExpected = 3;
        int seatNumberExpected = 12;
        Seat seat = testDataRepository.createSeat(BigInteger.valueOf(555), rowNumberExpected, seatNumberExpected,
                testDataRepository.createTestHall());
        assertNotNull(seat);
        assertThat(seat.getRowNumber(), is(rowNumberExpected));
        assertThat(seat.getSeatNumber(), is(seatNumberExpected));
        testDataRepository.cleanUpSeat(seat);
    }

    @Test
    public void getByIdTest() {
        Seat seat = testDataRepository.createTestSeat();
        Optional<Seat> seatTest = seatRepository.findById(seat.getSeatId());
        assertTrue(seatTest.isPresent());
        testDataRepository.cleanUpSeat(seat);
    }

    @Test
    public void updateTest() {
        int rowNumberExpected = 1;
        int seatNumberExpected = 11;
        Seat seat = testDataRepository.createTestSeat();
        seat.setRowNumber(rowNumberExpected);
        seat.setSeatNumber(seatNumberExpected);
        seatRepository.save(seat);
        Optional<Seat> seatOptional = seatRepository.findById(seat.getSeatId());
        assertTrue(seatOptional.isPresent());
        Seat seatUpdated = seatOptional.get();
        assertThat(seatUpdated.getRowNumber(), is(rowNumberExpected));
        assertThat(seatUpdated.getSeatNumber(), is(seatNumberExpected));
        testDataRepository.cleanUpSeat(seat);
    }

    @Test
    public void deleteTest() {
        Seat seat = testDataRepository.createTestSeat();
        seatRepository.delete(seat);
        assertFalse(seatRepository.findById(seat.getSeatId()).isPresent());
        testDataRepository.cleanUpSeat(seat);
    }

    @Test
    public void getAllTest() {
        Seat seat = testDataRepository.createTestSeat();
        List<Seat> seats = seatRepository.findAll();
        assertNotNull(seats);
        assertTrue(seats.size() > 0);
        testDataRepository.cleanUpSeat(seat);
    }

    @Test
    public void getSeatsByHallTest() {
        Seat seat = testDataRepository.createTestSeat();
        List<Seat> seats = seatRepository.findAllByHall(seat.getHall());
        assertThat(seats, is(notNullValue()));
        assertThat(seats.size(), greaterThan(0));
        seats.forEach(s -> assertThat(s.getHall(), is(seat.getHall())));
        seats.forEach(testDataRepository::cleanUpSeat);
    }

    @Test
    public void getSeatsByHallAndNotInIdsTest() {
        Seat seat = testDataRepository.createTestSeat();
        Seat seat2 = testDataRepository.createSeat(BigInteger.ONE, 1, 1, seat.getHall());
        List<Seat> seats = seatRepository.findByHallAndSeatIdNotIn(
                seat.getHall(),
                Collections.singletonList(seat2.getSeatId()));
        assertThat(seats, is(notNullValue()));
        assertThat(seats.size(), is(1));
        assertThat(seats.get(0).getSeatId(), is(seat.getSeatId()));
        testDataRepository.cleanUpSeat(seat);
        testDataRepository.cleanUpSeat(seat2);
    }
}
