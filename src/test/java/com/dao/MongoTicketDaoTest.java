package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Ticket;
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
public class MongoTicketDaoTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @Test
    public void createTest() {
        float priceExpected = 6;
        Ticket ticket = testDataRepository.createTicket(
                BigInteger.valueOf(555),
                priceExpected,
                testDataRepository.createTestFilmshow(),
                testDataRepository.createTestSeat());
        assertNotNull(ticket);
        assertThat(ticket.getPrice(), is(priceExpected));
        testDataRepository.cleanUpTicket(ticket);
    }

    @Test
    public void getByIdTest() {
        Ticket ticket = testDataRepository.createTestTicket();
        Optional<Ticket> ticketTest = ticketRepository.findById(ticket.getTicketId());
        assertTrue(ticketTest.isPresent());
        testDataRepository.cleanUpTicket(ticket);
    }

    @Test
    public void updateTest() {
        float priceExpected = 6;
        Ticket ticket = testDataRepository.createTestTicket();
        ticket.setPrice(priceExpected);
        ticketRepository.save(ticket);
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticket.getTicketId());
        assertTrue(ticketOptional.isPresent());
        Ticket ticketUpdated = ticketOptional.get();
        assertThat(ticketUpdated.getPrice(), is(priceExpected));
        testDataRepository.cleanUpTicket(ticket);
    }

    @Test
    public void deleteTest() {
        Ticket ticket = testDataRepository.createTestTicket();
        ticketRepository.delete(ticket);
        assertFalse(ticketRepository.findById(ticket.getTicketId()).isPresent());
        testDataRepository.cleanUpTicket(ticket);
    }

    @Test
    public void getAllTest() {
        Ticket ticket = testDataRepository.createTestTicket();
        List<Ticket> tickets = ticketRepository.findAll();
        assertNotNull(tickets);
        assertTrue(tickets.size() > 0);
        testDataRepository.cleanUpTicket(ticket);
    }

    @Test
    public void getTicketsByFilmshowTest() {
        Ticket ticket = testDataRepository.createTestTicket();
        List<Ticket> tickets = ticketRepository.findAllByFilmshow(ticket.getFilmshow());
        assertThat(tickets, is(notNullValue()));
        assertThat(tickets.size(), greaterThan(0));
        tickets.forEach(t -> assertThat(t.getFilmshow(), is(ticket.getFilmshow())));
        tickets.forEach(testDataRepository::cleanUpTicket);
    }

    @Test
    public void getTicketsBySeatTest() {
        Ticket ticket = testDataRepository.createTestTicket();
        List<Ticket> tickets = ticketRepository.findAllBySeat(ticket.getSeat());
        assertThat(tickets, is(notNullValue()));
        assertThat(tickets.size(), greaterThan(0));
        tickets.forEach(t -> assertThat(t.getSeat(), is(ticket.getSeat())));
        tickets.forEach(testDataRepository::cleanUpTicket);
    }

    @Test
    public void getTicketsByFilmshowAndNotInIdsTest() {
        Ticket ticket = testDataRepository.createTestTicket();
        Ticket ticket2 = testDataRepository.createTicket(BigInteger.ONE, 1F, ticket.getFilmshow(), ticket.getSeat());
        List<Ticket> tickets = ticketRepository.findByFilmshowAndTicketIdNotIn(
                ticket.getFilmshow(),
                Collections.singletonList(ticket2.getTicketId()));
        assertThat(tickets, is(notNullValue()));
        assertThat(tickets.size(), is(1));
        assertThat(tickets.get(0).getTicketId(), is(ticket.getTicketId()));
        testDataRepository.cleanUpTicket(ticket);
        testDataRepository.cleanUpTicket(ticket2);
    }
}
