package com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.StringJoiner;

@Document
public class Reservation implements Serializable {

    @Id
    private BigInteger reservationId;
    @DBRef
    @Valid
    private User user;
    @DBRef
    @Valid
    private Ticket ticket;

    public Reservation() {
    }

    public Reservation(User user, Ticket ticket) {
        this.user = user;
        this.ticket = ticket;
    }

    public BigInteger getReservationId() {
        return reservationId;
    }

    public Reservation setReservationId(BigInteger reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Reservation setUser(User user) {
        this.user = user;
        return this;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Reservation setTicket(Ticket ticket) {
        this.ticket = ticket;
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

        Reservation that = (Reservation) o;

        if (!Objects.equals(reservationId, that.reservationId)) {
            return false;
        }
        if (!ticket.equals(that.ticket)) {
            return false;
        }
        if (!user.equals(that.user)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return reservationId != null ? reservationId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ", "", "")
                .add(user != null ? user.getLogin() : "")
                .add(ticket != null ? ticket.toString() : "")
                .toString();
    }
}