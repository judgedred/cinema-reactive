package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
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
        Optional<Reservation> reservationTest = reservationRepository.findById(reservation.getReservationId())
                .blockOptional();
        assertTrue(reservationTest.isPresent());
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void updateTest() {
        Ticket ticketExpected = testDataRepository.createTicket(
                BigInteger.valueOf(556),
                60F,
                testDataRepository.createTestFilmshow(),
                testDataRepository.createTestSeat());
        User userExpected = testDataRepository.createUser("updated", "testUpdated", "testUpdated@gmail.com");
        Reservation reservation = testDataRepository.createTestReservation();
        User userInitial = reservation.getUser();
        Ticket ticketInitial = reservation.getTicket();
        reservation.setTicket(ticketExpected);
        reservation.setUser(userExpected);
        reservationRepository.save(reservation).block();
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getReservationId())
                .blockOptional();
        assertTrue(reservationOptional.isPresent());
        Reservation reservationUpdated = reservationOptional.get();
        assertThat(reservationUpdated.getTicket(), is(ticketExpected));
        assertThat(reservationUpdated.getUser(), is(userExpected));
        testDataRepository.cleanUpReservation(reservation);
        testDataRepository.cleanUpUser(userInitial);
        testDataRepository.cleanUpTicket(ticketInitial);
    }

    @Test
    public void deleteTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        reservationRepository.delete(reservation).block();
        assertFalse(reservationRepository.findById(reservation.getReservationId()).blockOptional().isPresent());
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void getAllTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        List<Reservation> reservations = reservationRepository.findAll().collectList().block();
        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);
        testDataRepository.cleanUpReservation(reservation);
    }

    @Test
    public void getAllByUserTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        List<Reservation> reservations = reservationRepository.findAllByUser(reservation.getUser())
                .collectList()
                .block();
        assertThat(reservations, is(notNullValue()));
        assertThat(reservations.size(), greaterThan(0));
        reservations.forEach(r -> assertThat(r.getUser(), is(reservation.getUser())));
        reservations.forEach(testDataRepository::cleanUpReservation);
    }

    @Test
    public void getAllByTicketTest() {
        Reservation reservation = testDataRepository.createTestReservation();
        List<Reservation> reservations = reservationRepository.findAllByTicket(reservation.getTicket())
                .collectList()
                .block();
        assertThat(reservations, is(notNullValue()));
        assertThat(reservations.size(), greaterThan(0));
        reservations.forEach(r -> assertThat(r.getTicket(), is(reservation.getTicket())));
        reservations.forEach(testDataRepository::cleanUpReservation);
    }
}


