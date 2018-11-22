package com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

@Document
public class Ticket implements Serializable {

    @Id
    private BigInteger ticketId;
    @NotNull
    private Float price;
    @Valid
    private Filmshow filmshow;
    @Valid
    private Seat seat;

    public Ticket() {
    }

    public Ticket(Float price, Filmshow filmshow, Seat seat) {
        this.price = price;
        this.filmshow = filmshow;
        this.seat = seat;
    }

    public BigInteger getTicketId() {
        return ticketId;
    }

    public Ticket setTicketId(BigInteger ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public Ticket setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Filmshow getFilmshow() {
        return filmshow;
    }

    public Ticket setFilmshow(Filmshow filmshow) {
        this.filmshow = filmshow;
        return this;
    }

    public Seat getSeat() {
        return seat;
    }

    public Ticket setSeat(Seat seat) {
        this.seat = seat;
        return this;
    }

    public String getDescription() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) o;

        if (Float.compare(ticket.price, price) != 0) {
            return false;
        }
        if (!Objects.equals(ticketId, ticket.ticketId)) {
            return false;
        }
        if (!filmshow.equals(ticket.filmshow)) {
            return false;
        }
        if (!seat.equals(ticket.seat)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ticketId != null ? ticketId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ", "", "")
                .add(filmshow != null ? filmshow.getFilm().getFilmName() : "")
                .add(filmshow != null ? filmshow.getDateTime().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")) : "")
                .add(seat != null ? seat.getSeatNumber().toString() : "")
                .add("место")
                .add(seat != null ? seat.getRowNumber().toString() : "")
                .add("ряд")
                .add(price != null ? String.format("%d", price.intValue()) : "")
                .toString();
    }
}