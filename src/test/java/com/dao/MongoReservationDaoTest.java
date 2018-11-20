package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class MongoReservationDaoTest {

    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void createTest() {
        Ticket ticketExpected = testDataRepository.createTestTicket();
        User userExpected = testDataRepository.createTestUser();
        Reservation reservation = testDataRepository.createReservation(ticketExpected, userExpected);
        assertNotNull(reservation);
        assertThat(reservation.getTicket(), is(ticketExpected));
        assertThat(reservation.getUser(), is(userExpected));
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void getByIdTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        Optional<Reservation> reservationTest = reservationRepository.findById(reservation.getReservationId());
        assertTrue(reservationTest.isPresent());
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void updateTest() {
        Ticket ticketExpected = testDataRepository.createTestTicket();
        ticketExpected.setPrice(60F);
        User userExpected = testDataRepository.createTestUser();
        userExpected.setLogin("updated");
        Reservation reservation = testDataRepository.createTestReservation();
        reservation.setTicket(ticketExpected);
        reservation.setUser(userExpected);
        reservationRepository.save(reservation);
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getReservationId());
        assertTrue(reservationOptional.isPresent());
        Reservation reservationUpdated = reservationOptional.get();
        assertThat(reservationUpdated.getTicket(), is(ticketExpected));
        assertThat(reservationUpdated.getUser(), is(userExpected));
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void deleteTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        reservationRepository.delete(reservation);
        assertFalse(reservationRepository.findById(reservation.getReservationId()).isPresent());
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void getAllTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        List<Reservation> reservations = reservationRepository.findAll();
        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void getAllByUserTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        List<Reservation> reservations = reservationRepository.findAllByUser(reservation.getUser());
        assertThat(reservations, is(notNullValue()));
        assertThat(reservations.size(), greaterThan(0));
        reservations.forEach(r -> assertThat(r.getUser(), is(reservation.getUser())));
        reservations.forEach(testDataRepository::cleanUpReservation);
    }

    @Test
    public void getAllByTicketTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        List<Reservation> reservations = reservationRepository.findAllByTicket(reservation.getTicket());
        assertThat(reservations, is(notNullValue()));
        assertThat(reservations.size(), greaterThan(0));
        reservations.forEach(r -> assertThat(r.getTicket(), is(reservation.getTicket())));
        reservations.forEach(testDataRepository::cleanUpReservation);
    }
}


